package ui;

import static main.GameStates.MENU;
import static helpers.Constants.Colours.DARK_YELLOW;
import static helpers.Constants.Colours.LIGHT_BLUE;
import static helpers.Constants.Pejuangs.*;
import static helpers.Constants.Sizes.*;
import static helpers.Constants.Traps.*;
import static helpers.Constants.SoundIndex.*;
import static main.GameStates.GAMEOVER;
import static main.GameStates.SetGameState;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import helpers.Constants.Pejuangs;
import objects.Jawara;
import objects.Lubang;
import objects.Lumpur;
import objects.Orator;
import objects.Paku;
import objects.Pejuang;
import objects.Prajurit;
import objects.Trap;
import scenes.Playing;


public class ActionBar extends Bar {

	private Playing playing;
	private MyButton bMenu, startWave, bSell, bUpgrade, bPause;
	
	private MyButton[] pejuangButtons;
	private MyButton[] trapButtons;
	private Pejuang selectedPejuang;
	private Trap selectedTrap;
	private Pejuang displayedPejuang;
	private boolean purchaseSuccess = true;
	private int boxXStart = 970;
	private int boxYStart = 480;
	private int boxW = 230;
	private int boxH = 170;

	private int money = 2000;
	private int lives = 20;

	public ActionBar(int x, int y, int width, int height, Playing playing) {
		super(x, y, width, height);
		this.playing = playing;

		initButtons();
	}
	

	public void resetAll() {
		lives = 20;
		money = 1200;
		selectedPejuang = null;
		selectedTrap = null;
		displayedPejuang = null;
		purchaseSuccess = true;
	}


	private void initButtons() {

		bMenu = new MyButton("Menu", ACTIONBAR_XSTART + 20, 670, 100, 30);
		bPause = new MyButton("Jeda", ACTIONBAR_XSTART + ACTIONBAR_WIDTH/2 + 5, 670, 100, 30);

		startWave = new MyButton("Mulai Gelombang", ACTIONBAR_XSTART + 25, 20, 200, 40);
		bUpgrade = new MyButton("Tingkatkan", boxXStart + 10, boxYStart + boxH - 40, boxW/2 - 20, 35);
		bSell = new MyButton("Jual", boxXStart + boxW/2 + 10, boxYStart + boxH - 40, boxW/2 - 20, 35);
		pejuangButtons = new MyButton[3];
		trapButtons = new MyButton[3];
		int w = 70;
		int h = 110;
		int xStart = 970;
		int yStart = 205;
		int xOffset = (int)(w * 1.1f);
		
		for(int i = 0; i < pejuangButtons.length; i++) {
			pejuangButtons[i] = new MyButton("100", xStart + xOffset * i, yStart, w, h, i);
		}
		for(int i = 0; i < trapButtons.length; i++) {
			trapButtons[i] = new MyButton("", xStart + xOffset * i,  355, w, h, i);
		}
		
	}
	
	
	private void drawButtons(Graphics g) {
		bMenu.draw(g);
		if(playing.gameStarted() && !playing.isWaiting())
			bPause.draw(g);
		
		for(MyButton b : pejuangButtons) {
			g.setColor(DARK_YELLOW);
			g.fillRect(b.x, b.y, b.width, b.height);
			g.drawImage(playing.getPejuangManager().getPejuangImgs()[b.getId()], b.x, b.y + 20, b.width, b.height - 40, null);
			String cost = "Rp" + Integer.toString(helpers.Constants.Pejuangs.GetPejuangCost(b.getId()));
			String name = helpers.Constants.Pejuangs.GetName(b.getId());
			
			g.setColor(Color.black);
			g.setFont(new Font("Monospaced", Font.BOLD, 12));

			int w = g.getFontMetrics().stringWidth(name);
			int h = g.getFontMetrics().getHeight();
			g.drawString(name, b.x - w / 2 + b.width/2, b.y + h);
			w = g.getFontMetrics().stringWidth(cost);
			h = g.getFontMetrics().getHeight();
			g.drawString(cost, b.x - w / 2 + b.width/2, b.y + b.height - 5);

			drawButtonFeedback(g, b);
		}
		
		for(MyButton b : trapButtons) {
			g.setColor(DARK_YELLOW);
			g.fillRect(b.x, b.y, b.width, b.height);
			g.drawImage(playing.getTrapManager().getTrapImgs()[b.getId()], b.x, b.y + 20, b.width, b.height - 40, null);
			String cost = "Rp" + Integer.toString(helpers.Constants.Traps.GetTrapCost(b.getId()));
			String name = helpers.Constants.Traps.GetTrapName(b.getId());
			
			g.setColor(Color.black);
			g.setFont(new Font("Monospaced", Font.BOLD, 12));

			int w = g.getFontMetrics().stringWidth(name);
			int h = g.getFontMetrics().getHeight();
			g.drawString(name, b.x - w / 2 + b.width/2, b.y + h);
			w = g.getFontMetrics().stringWidth(cost);
			h = g.getFontMetrics().getHeight();
			g.drawString(cost, b.x - w / 2 + b.width/2, b.y + b.height - 5);

			drawButtonFeedback(g, b);
		}
		
		if(playing.isWaiting() || !playing.gameStarted()) {
			startWave.draw(g);
		}
	}

	public void draw(Graphics g) {

		// Background
		g.setColor(LIGHT_BLUE);
		g.fillRect(x, y, width, height);

		// Buttons
		drawButtons(g);
		
		drawDisplayedPejuang(g);
		
		g.setFont(new Font("Monospaced", Font.BOLD, 15));
		g.setColor(Color.black);
		
		drawStrings(g);
		
		drawMoneyInfo(g);
		
		if(playing.gameStarted()) {
			drawWaveInfo(g);
		}
		
		drawPurchaseMessage(g);

	}
	
	private void drawStrings(Graphics g) {
		g.drawString("Pejuang", 980, 190);
		g.drawString("Jebakan", 980, 340);
		if(playing.isGamePaused()) {
			g.setColor(Color.black);
			String txt = "Permainan Dijeda!";
			int w = g.getFontMetrics().stringWidth(txt);
			int h = g.getFontMetrics().getHeight();
			g.drawString(txt, boxXStart - w / 2 + boxW / 2, 50 );
		}
		g.drawString("Nyawa: " + lives, ACTIONBAR_XSTART + ACTIONBAR_WIDTH/2 + 20, 140);

	}

	private void drawMoneyInfo(Graphics g) {
		g.drawString("Uang: Rp" + money, ACTIONBAR_XSTART + 20, 140);
	}

	private void drawWaveInfo(Graphics g) {

		drawPenjajahLeftInfo(g);
		drawWavesLeftInfo(g);
	}

	private void drawWavesLeftInfo(Graphics g) {
		int curWave = playing.getWaveManager().getWaveIndex() + 1;
		int size = playing.getWaveManager().getWaves().size();
		g.drawString("Gelombang " + curWave + "/" + size, ACTIONBAR_XSTART + 20, 90);
	}

	private void drawPenjajahLeftInfo(Graphics g) {
		if(!playing.isWaiting()) {
			int remaining = playing.getPenjajahManager().getAmountAlive();
			g.drawString("Penjajah Masih Hidup: " + remaining, ACTIONBAR_XSTART + 20, 110);
		}
	}
	
	private void drawPurchaseMessage(Graphics g) {
	    if (!purchaseSuccess) {
	        g.setColor(Color.RED);
	        g.drawString("Uang tidak mencukupi!", ACTIONBAR_XSTART + 20, 160);
	    }
	}
	
	public void setPurchaceSuccess(boolean bool) {
		purchaseSuccess = bool;
	}



	private void drawDisplayedPejuang(Graphics g) {
		if(!playing.isSelecting()) {
			if(displayedPejuang != null) {
				g.setColor(DARK_YELLOW);
				g.fillRect(boxXStart, boxYStart, boxW, boxH);
				g.setColor(Color.black);
				g.drawRect(boxXStart, boxYStart, boxW, boxH);
				int rectSide = 60;
				g.drawRect(1055, 500, rectSide, rectSide);
				g.drawImage(playing.getPejuangManager().getPejuangImgs()[displayedPejuang.getPejuangType()], 
						ACTIONBAR_XSTART + ACTIONBAR_WIDTH/2 - rectSide/2, 500, rectSide, rectSide, null);
				g.setColor(Color.black);
				g.setFont(new Font("Monospaced", Font.BOLD, 15));
				
				String tipe = Pejuangs.GetName(displayedPejuang.getPejuangType());
				int w = g.getFontMetrics().stringWidth(tipe);
				int h = g.getFontMetrics().getHeight();
				g.drawString(tipe, boxXStart - w / 2 + boxW / 2, boxYStart + h / 2 + boxH / 2 );		
				String amountSell = getSellAmount();
				w = g.getFontMetrics().stringWidth(amountSell);
				h = g.getFontMetrics().getHeight();
				g.setColor(Color.red);
				g.drawString(amountSell, bSell.x + bSell.width/2 - w/2, boxYStart + boxH - 45);
				
				if(displayedPejuang.getTier() < 2) {
					String amountUpgrade = getUpgradeAmount();
					w = g.getFontMetrics().stringWidth(amountUpgrade);
					h = g.getFontMetrics().getHeight();
					g.setColor(Color.green);
					g.drawString(amountUpgrade, bUpgrade.x + bUpgrade.width/2 - w/2, boxYStart + boxH - 45);
					bUpgrade.draw(g);
					drawButtonFeedback(g, bUpgrade);
				}
				drawDisplayedPejuangBorder(g);
				drawDisplayedPejuangRange(g);
				
				bSell.draw(g);
				drawButtonFeedback(g, bSell);
			
			}
		}
	}


	private String getUpgradeAmount() {
		return "-Rp"+ Pejuangs.GetPejuangUpgradeCost(displayedPejuang.getPejuangType(), displayedPejuang.getTier());
	}

	private String getSellAmount() {
		int spentOnUpgrade = displayedPejuang.getMoneySpent();

		return "+Rp" + Integer.toString((int)(((helpers.Constants.Pejuangs.GetPejuangCost(displayedPejuang.getPejuangType()) + spentOnUpgrade) * 0.7)));
	}

	private void drawDisplayedPejuangRange(Graphics g) {
		if(!playing.isSelecting()) {
			if(displayedPejuang != null) {
				g.setColor(LIGHT_BLUE);
				int range = (int)displayedPejuang.getRange() * 2;
				int x = displayedPejuang.getX() + TILE_SIZE/2 - range/2;	
				int y = displayedPejuang.getY() + TILE_SIZE/2 - range/2;
				g.drawOval(x, y, range, range);
			}
		}
	}

	private void drawDisplayedPejuangBorder(Graphics g) {
		if(!playing.isSelecting()) {
			if(displayedPejuang != null) {
				g.setColor(LIGHT_BLUE);
				g.drawRect(displayedPejuang.getX(), displayedPejuang.getY(), TILE_SIZE, TILE_SIZE);
			}
		}
	}
	

	public void payForPejuang(int pejuangType) {
		this.money -= helpers.Constants.Pejuangs.GetPejuangCost(pejuangType);
	}
	

	public void payForTrap(int trapType) {
		this.money -= helpers.Constants.Traps.GetTrapCost(trapType);
	}



	public void displayPejuang(Pejuang p) {
		displayedPejuang = p;
	}
	

	private void sellPejuangClicked() {
		playing.sellPejuang(displayedPejuang);
		int spentOnUpgrade = displayedPejuang.getMoneySpent();
		money += (helpers.Constants.Pejuangs.GetPejuangCost(displayedPejuang.getPejuangType()) + spentOnUpgrade )* 0.7;
		displayedPejuang = null;
	}

	private void togglePause() {
		playing.setGamePause(!playing.isGamePaused());
		
		if(playing.isGamePaused())
			bPause.setText("Lanjutkan");
		else
			bPause.setText("Jeda");

	}
	
	private int getLives() {
		return lives;
	}
	
	public void reduceLives(int liveLost){
		lives -= liveLost;
		if(lives <= 0) {
			playing.getGame().playMusic(LOST);
			SetGameState(GAMEOVER);
		}
	}
	public void mouseClicked(int x, int y) {
		if (bMenu.getBounds().contains(x, y)) {
			SetGameState(MENU);
		}
		else if(bPause.getBounds().contains(x, y))
			togglePause();
		else if (startWave.getBounds().contains(x, y)) {
			playing.setWaitFalse();
			playing.startGame();
		}
		else {
			if(!playing.isGamePaused()) {
				if(displayedPejuang != null) {
					if(bSell.getBounds().contains(x, y)) {
						sellPejuangClicked();
					}
					if(bUpgrade.getBounds().contains(x, y)){
						if(!isGoldEnoughToUpgrade())
							return;
						if(displayedPejuang.getTier() < 2)
							upgradeSelectedPejuang();
					}
				}
				for(MyButton b : pejuangButtons) {
					if(b.getBounds().contains(x, y)) {
						if(!isMoneyEnoughToBuy(b.getId())) 
							return;
						switch(b.getId()) {
						case PRAJURIT:
							selectedPejuang = new Prajurit(ACTIONBAR_XSTART - TILE_SIZE, y, -1);
							break;
						case JAWARA:
							selectedPejuang = new Jawara(ACTIONBAR_XSTART - TILE_SIZE, y, -1);
							break;
						case ORATOR:
							selectedPejuang = new Orator(ACTIONBAR_XSTART - TILE_SIZE, y, -1);
							break;
						}
						if(selectedTrap != null)
							playing.setSelectedTrap(null);
						playing.setSelectedPejuang(selectedPejuang);
						return;
					}
				}
				for(MyButton b : trapButtons) {
					if(b.getBounds().contains(x, y)) {
						if(!isMoneyEnoughToBuyTrap(b.getId())) 
							return;
						switch(b.getId()) {
						case PAKU:
							selectedTrap = new Paku(ACTIONBAR_XSTART - TILE_SIZE, y, -1);
							break;
						case LUMPUR:
							selectedTrap = new Lumpur(ACTIONBAR_XSTART - TILE_SIZE, y, -1);
							break;
						case LUBANG:
							selectedTrap = new Lubang(ACTIONBAR_XSTART - TILE_SIZE, y, -1);
							break;
						}
						if(selectedPejuang != null)
							playing.setSelectedPejuang(null);
						playing.setSelectedTrap(selectedTrap);
						return;
					}
				}
			}
		}
	}

	public boolean isMoneyEnoughToBuyTrap(int trapType) {
		boolean enoughGold = money >= helpers.Constants.Traps.GetTrapCost(trapType);
	    purchaseSuccess = enoughGold; 	    
	    return enoughGold;
	}

	private boolean isGoldEnoughToUpgrade() {
	    boolean enoughMoney = money >= Pejuangs.GetPejuangUpgradeCost(displayedPejuang.getPejuangType(), displayedPejuang.getTier());
	    purchaseSuccess = enoughMoney; 	    
	    return enoughMoney;	
	}

	private void upgradeSelectedPejuang() {
		int cost = Pejuangs.GetPejuangUpgradeCost(displayedPejuang.getPejuangType(), displayedPejuang.getTier());
		playing.upgradePejuang(displayedPejuang, cost);
		this.money -= cost;
	}

	public boolean isMoneyEnoughToBuy(int pejuangType) {
	    boolean enoughGold = money >= Pejuangs.GetPejuangCost(pejuangType);
	    purchaseSuccess = enoughGold; 	    
	    return enoughGold;
	}


	public void mouseMoved(int x, int y) {
		bMenu.setMouseOver(false);
		bPause.setMouseOver(false);
		for(MyButton b : pejuangButtons)
			b.setMouseOver(false);
		for(MyButton b : trapButtons)
			b.setMouseOver(false);
		startWave.setMouseOver(false);
		bSell.setMouseOver(false);
		bUpgrade.setMouseOver(false);
		
		if (bMenu.getBounds().contains(x, y))
			bMenu.setMouseOver(true);
		else if(bPause.getBounds().contains(x, y))
			bPause.setMouseOver(true);
		else {
			if(displayedPejuang != null) {
				if (bSell.getBounds().contains(x, y)) {
					bSell.setMouseOver(true);
					return;
				}
				else if (bUpgrade.getBounds().contains(x, y)) {
					bUpgrade.setMouseOver(true);
					return;
				}
			}
			if (startWave.getBounds().contains(x, y))
				startWave.setMouseOver(true);
			else {
				for(MyButton b : pejuangButtons)
					if(b.getBounds().contains(x, y)) {
						b.setMouseOver(true);
						return;
					}
				for(MyButton b : trapButtons) {
					if(b.getBounds().contains(x, y)) {
						b.setMouseOver(true);
						return;
					}
				}
			}
			
		}
		return;
		
	}

	public void mousePressed(int x, int y) {
		if (bMenu.getBounds().contains(x, y))
			bMenu.setMousePressed(true);
		else if (bPause.getBounds().contains(x, y))
			bPause.setMousePressed(true);
		else {
			if(displayedPejuang != null) {
				if (bSell.getBounds().contains(x, y))
					bSell.setMousePressed(true);
				else if (bUpgrade.getBounds().contains(x, y))
					bUpgrade.setMousePressed(true);
			}
			if (startWave.getBounds().contains(x, y))
				startWave.setMousePressed(true);
			else {
				for(MyButton b : pejuangButtons)
					if(b.getBounds().contains(x, y)) {
						b.setMousePressed(true);
						return;
					}
				for(MyButton b : trapButtons)
					if(b.getBounds().contains(x, y)) {
						b.setMousePressed(true);
						return;
					}
			}
			
		}
	}

	public void mouseReleased(int x, int y) {
		bMenu.resetBooleans();
		bPause.resetBooleans();
		startWave.resetBooleans();
		for(MyButton b : pejuangButtons)
			b.resetBooleans();
		for(MyButton b : trapButtons)
			b.resetBooleans();
		bSell.resetBooleans();
		bUpgrade.resetBooleans();
		purchaseSuccess = true;
		
	}

	public void addMoney(int getMoneyForKill) {
		this.money += getMoneyForKill;
	}


}
