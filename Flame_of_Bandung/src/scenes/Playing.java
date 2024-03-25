package scenes;

import java.awt.Graphics;

import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import helpers.LevelBuild;
import main.Game;
import managers.PejuangManager;
import managers.PenjajahManager;
import managers.ProjectileManager;
import managers.TrapManager;
import managers.WaveManager;
import objects.Jawara;
import objects.Lubang;
import objects.Lumpur;
import objects.Orator;
import objects.Paku;
import objects.Pejuang;
import objects.Prajurit;
import objects.Trap;
import penjajah.Penjajah;
import ui.ActionBar;

import static helpers.Constants.Colours.LIGHT_BLUE;
import static helpers.Constants.Pejuangs.*;
import static helpers.Constants.Sizes.*;
import static helpers.Constants.Tiles.*;
import static helpers.Constants.Traps.*;
import static helpers.Constants.SoundIndex.*;
import static main.GameStates.*;

public class Playing extends GameScene implements SceneMethods {

	private int[][] lvl;
	private ActionBar actionBar;
	private int mouseX, mouseY;
	private PenjajahManager penjajahManager;
	private PejuangManager pejuangManager;
	private ProjectileManager projManager;
	private WaveManager waveManager;
	private TrapManager trapManager;
	private Pejuang selectedPejuang;
	private boolean waitingForWaveStart = true;
	private boolean gameStarted = false;
	private int moneyTick;
	private boolean gamePaused = false;
	private Trap selectedTrap;
	private boolean isUpdated = false;

	public Playing(Game game) {
		super(game);

		loadDefaultLevel();

		actionBar = new ActionBar(ACTIONBAR_XSTART, 0, ACTIONBAR_WIDTH, GAME_HEIGHT, this);

		penjajahManager = new PenjajahManager(this);
		pejuangManager = new PejuangManager(this);
		projManager = new ProjectileManager(this);
		waveManager = new WaveManager(this);
		trapManager = new TrapManager(this);
	}

	private void loadDefaultLevel() {
		lvl = LevelBuild.getLevelData();

	}

	public void setLevel(int[][] lvl) {
		this.lvl = lvl;
	}
	
	public void setSelectedPejuang(Pejuang selectedPejuang) {
		this.selectedPejuang = selectedPejuang;
	}

	public void setSelectedTrap(Trap selectedTrap) {
		this.selectedTrap = selectedTrap;	
	}
	
	public void startGame() {
		gameStarted = true;
	}
	
	public void update() {
		if(gameStarted) {
			if(!gamePaused) {
				updateTick();
				waveManager.update();
				
				if(!waitingForWaveStart) {
					moneyTick++;
					if(moneyTick % (60 * 2) == 0)
						actionBar.addMoney(2);
				}
				
				if(allPenjajahIsDead()) {
					if(isThereMoreWaves()) {
						if(!waitingForWaveStart) {
							waveManager.increaseWaveIndex();
							penjajahManager.getPenjajahs().clear();
							waveManager.resetPenjajahIndex();
							actionBar.addMoney(100);
							waitingForWaveStart = true;
						}
					}
					else {
						winGame();
					}
				}
				
				
				if(isTimeForNewPenjajah() && !waitingForWaveStart) {
					spawnPenjajah();
				}
				
				penjajahManager.update();
				pejuangManager.update();
				projManager.update();
				trapManager.update();
			}	
		}
		isUpdated = true;
	}
	
	private void winGame() {
		game.playMusic(WIN);
		SetGameState(VICTORY);
	}

	private boolean isThereMoreWaves() {
		return waveManager.isThereMoreWaves();
	}

	private boolean allPenjajahIsDead() {
		if(waveManager.isThereMorePenjajahInWave())
			return false;
		for(Penjajah p : penjajahManager.getPenjajahs()) {
			if(p.isAlive())
				return false;
		}
		return true;
	}

	private void spawnPenjajah() {
		penjajahManager.spawnPenjajah(waveManager.getNextPenjajah());
	}

	private boolean isTimeForNewPenjajah() {
		if(waveManager.isTImeForSpawn()) {
			if(waveManager.isThereMorePenjajahInWave())
				return true;
		}
		return false;
	}

	@Override
	public void render(Graphics g) {

		drawLevel(g);
		actionBar.draw(g);
		trapManager.draw(g);
		penjajahManager.draw(g);
		pejuangManager.draw(g);
		projManager.draw(g);
		drawSelectedPejuang(g);
		drawSelectedTrap(g);

	}
	
	private void drawSelectedTrap(Graphics g) {
		if(selectedTrap != null) {
			g.drawImage(trapManager.getTrapImgs()[selectedTrap.getTrapType()], mouseX, mouseY, null);
		}
	}

	private void drawSelectedPejuang(Graphics g) {
		if(selectedPejuang != null) {
			g.drawImage(pejuangManager.getPejuangImgs()[selectedPejuang.getPejuangType()], mouseX, mouseY, null);
			g.setColor(LIGHT_BLUE);
			int range = (int)selectedPejuang.getRange() * 2;
			int x = mouseX + TILE_SIZE/2 - range/2;	
			int y = mouseY + TILE_SIZE/2 - range/2;
			g.drawOval(x, y, range, range);
		}
		
	}
	

	private void drawLevel(Graphics g) {
	    for (int y = 0; y < lvl.length; y++) {
	        for (int x = 0; x < lvl[y].length; x++) {
	            int id = lvl[y][x];
	            g.drawImage(getSprite(id), x * 40, y * 40, null); // Use the new sprite size (40x40)
	        }
	    }
	}

	private BufferedImage getSprite(int spriteID) {
		return game.getTileManager().getSprite(spriteID);
	}

	
	public void dealDmgToPenjajah(Pejuang p, Penjajah ph) {
		if(p.getPejuangType() == PRAJURIT) 
			projManager.newProj(p, ph);
		else
			pejuangManager.dealJawaraAOE(p, ph);
	}

	private Pejuang getPejuangAt(int x, int y) {
		return pejuangManager.getPejuangAt(x, y);
	}
	
	private Trap getTrapAt(int x, int y) {
		return trapManager.getTrapAt(x, y);
	}

	public void sellPejuang(Pejuang p) {
		pejuangManager.removePejuang(p);
	}
	
	public void upgradePejuang(Pejuang p, int cost) {
		pejuangManager.upgradePejuang(p, cost);
	}

	private boolean isTileGrass(int x, int y) {
		int id = lvl[y / TILE_SIZE][x / TILE_SIZE];
		int tileType = game.getTileManager().getTile(id).getTileType();
		
		return tileType == GRASS_TILE;
	}

	private boolean isTileRoad(int x, int y) {
		int id = lvl[y / TILE_SIZE][x / TILE_SIZE];
		int tileType = game.getTileManager().getTile(id).getTileType();
		
		return tileType == ROAD_TILE;
	}
	
	public boolean isSelecting() {
		return (selectedPejuang != null || selectedTrap != null);
	}
	
	private void buyTrap(int trapType) {
		actionBar.payForTrap(trapType);
	}



	public void setWaitFalse() {
		waitingForWaveStart = false;		
	}

	@Override
	public void mouseClicked(int x, int y) {
		if(!gameStarted) {
			if (x >= ACTIONBAR_XSTART)
				actionBar.mouseClicked(x, y);
			else {
				if(selectedPejuang != null) {
					if(isTileGrass(mouseX, mouseY)) {
						if(getPejuangAt(mouseX, mouseY) == null) {
							pejuangManager.addPejuang(selectedPejuang, mouseX, mouseY);
							reduceMoney(selectedPejuang.getPejuangType());
							selectedPejuang = null;
							actionBar.displayPejuang(null);
						}
					}
				}
				else {
					Pejuang p = getPejuangAt(mouseX, mouseY);
					actionBar.displayPejuang(p);
				}
				if(selectedTrap != null) {
					if(isTileRoad(mouseX, mouseY)) {
						if(getTrapAt(mouseX, mouseY) == null) {
							trapManager.addTrap(selectedTrap, mouseX, mouseY);
							buyTrap(selectedTrap.getTrapType());
							selectedTrap = null;
							actionBar.displayPejuang(null);
						}
					}
				}
			}
		}
		else {
				if (x >= ACTIONBAR_XSTART)
					actionBar.mouseClicked(x, y);
				else {
					if(selectedPejuang != null) {
						if(isTileGrass(mouseX, mouseY)) {
							if(getPejuangAt(mouseX, mouseY) == null) {
								pejuangManager.addPejuang(selectedPejuang, mouseX, mouseY);
								reduceMoney(selectedPejuang.getPejuangType());
								selectedPejuang = null;	
								actionBar.displayPejuang(null);
							}
						}
					}
					else {
						Pejuang p = getPejuangAt(mouseX, mouseY);
						actionBar.displayPejuang(p);
					}
					if(selectedTrap != null) {
						if(isTileRoad(mouseX, mouseY)) {
							if(getTrapAt(mouseX, mouseY) == null) {
								trapManager.addTrap(selectedTrap, mouseX, mouseY);
								buyTrap(selectedTrap.getTrapType());
								selectedTrap = null;
								actionBar.displayPejuang(null);
							}
						}
					}
				}
		}
	}

	private void reduceMoney(int pejuangType) {
		actionBar.payForPejuang(pejuangType);
	}
	

	public void removeLives(int liveLost) {
		actionBar.reduceLives(liveLost);
	}
	
	public void setGamePause(boolean gamePaused) {
		this.gamePaused = gamePaused;
	}

	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			selectedPejuang = null;
			selectedTrap = null;
		}
		else {
			if(e.getKeyCode() == KeyEvent.VK_1) {		
				if(actionBar.isMoneyEnoughToBuy(PRAJURIT)) {
					selectedPejuang = null;
					selectedTrap = null;
					selectedPejuang = new Prajurit(mouseX, mouseY, -1);
				}
				else {
					actionBar.setPurchaceSuccess(false);
					selectedPejuang = null;
					selectedTrap = null;
				}
			}
			if(e.getKeyCode() == KeyEvent.VK_2) {
				if(actionBar.isMoneyEnoughToBuy(JAWARA)) {
					selectedPejuang = null;
					selectedTrap = null;
					selectedPejuang = new Jawara(mouseX, mouseY, -1);
				}
				else {
					actionBar.setPurchaceSuccess(false);
					selectedPejuang = null;
					selectedTrap = null;
				}
			}
			if(e.getKeyCode() == KeyEvent.VK_3) {
				if(actionBar.isMoneyEnoughToBuy(ORATOR)) {
					selectedPejuang = null;
					selectedTrap = null;
					selectedPejuang = new Orator(mouseX, mouseY, -1);
				}
				else {
					actionBar.setPurchaceSuccess(false);
					selectedPejuang = null;
					selectedTrap = null;
				}
			}
			if(e.getKeyCode() == KeyEvent.VK_4) {
				if(actionBar.isMoneyEnoughToBuyTrap(PAKU)) {
					selectedPejuang = null;
					selectedTrap = null;
					selectedTrap = new Paku(mouseX, mouseY, -1);
				}
				else {
					actionBar.setPurchaceSuccess(false);
					selectedPejuang = null;
					selectedTrap = null;
				}
			}
			if(e.getKeyCode() == KeyEvent.VK_5) {
				if(actionBar.isMoneyEnoughToBuyTrap(LUMPUR)) {
					selectedPejuang = null;
					selectedTrap = null;
					selectedTrap = new Lumpur(mouseX, mouseY, -1);
				}
				else {
					actionBar.setPurchaceSuccess(false);
					selectedPejuang = null;
					selectedTrap = null;
				}
			}
			if(e.getKeyCode() == KeyEvent.VK_6) {
				if(actionBar.isMoneyEnoughToBuyTrap(LUBANG)) {
					selectedPejuang = null;
					selectedTrap = null;
					selectedTrap = new Lubang(mouseX, mouseY, -1);
				}
				else {
					actionBar.setPurchaceSuccess(false);
					selectedPejuang = null;
					selectedTrap = null;
				}
			}	
		}
	}
	
	@Override
	public void mouseMoved(int x, int y) {

		if (x >= ACTIONBAR_XSTART)
			actionBar.mouseMoved(x, y);
		else {
	        mouseX = (x / TILE_SIZE) * TILE_SIZE; 
	        mouseY = (y / TILE_SIZE) * TILE_SIZE; 
	    }
	}

	@Override
	public void mousePressed(int x, int y) {
		if (x >= ACTIONBAR_XSTART && (gamePaused || !gameStarted)) {
			actionBar.mousePressed(x, y);
		}
	}

	@Override
	public void mouseReleased(int x, int y) {
		actionBar.mouseReleased(x, y);
	}

	@Override
	public void mouseDragged(int x, int y) {

	}

	public int getTileType(int x, int y) {
		int xCord = x / TILE_SIZE;
		int yCord = y / TILE_SIZE;
		
		if(xCord < 0 || xCord > 23)
			return 0;
		if(yCord < 0 || yCord > 17)
			return 0;
		
		
		int id = lvl[y / TILE_SIZE][x / TILE_SIZE];
		return game.getTileManager().getTile(id).getTileType();
	}
	
	public boolean isWaiting() {
		return waitingForWaveStart;
	}
	
	public void rewardPlayer(int penjajahType) {
		actionBar.addMoney(helpers.Constants.Penjajahs.GetMoneyForKill(penjajahType));
	}
	
	public PejuangManager getPejuangManager() {
		return pejuangManager;
	}
	
	public PenjajahManager getPenjajahManager() {
		return penjajahManager;
	}
	
	public WaveManager getWaveManager() {
		return waveManager;
	}
	
	public TrapManager getTrapManager() {
		return trapManager;
	}

	public boolean gameStarted() {
		return gameStarted;
	}
	
	public boolean isGamePaused() {
		return gamePaused;
	}
	
	public boolean getIsUpdated() {
		return isUpdated;
	}

	public void resetAll() {
		actionBar.resetAll();
		penjajahManager.reset();
		pejuangManager.reset();
		projManager.reset();
		waveManager.reset();
		trapManager.reset();
		
		mouseX = 0;
		mouseY = 0;
		
		selectedPejuang = null;
		selectedTrap = null;
		moneyTick = 0;
		gamePaused = false;
		gameStarted = false;
		isUpdated = false;
	}

	public ActionBar getActionBar() {
		return actionBar;
	}


}