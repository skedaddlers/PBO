package managers;

import static helpers.Constants.Direction.*;
import static helpers.Constants.Penjajahs.*;
import static helpers.Constants.Sizes.*;
import static helpers.Constants.Tiles.*;

import java.awt.Color;

import java.awt.Graphics;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import helpers.LoadImage;
import penjajah.LondoIreng;
import penjajah.Penjajah;
import penjajah.Tentara;
import penjajah.Tank;
import scenes.Playing;


public class PenjajahManager {
	private static int xSTART = TILE_SIZE * 0;
	private static int ySTART = TILE_SIZE * 9;
	private static int xEND = TILE_SIZE * 23;
	private static int yEND = TILE_SIZE * 2;
	private Playing playing;
	private BufferedImage[][] penjajahImgs;
	private ArrayList<Penjajah> penjajahs = new ArrayList<>();
	private int hpBarWidth = 30;
	
	public PenjajahManager(Playing playing) {
		this.playing = playing;
		penjajahImgs = new BufferedImage[3][4];
		loadPenjajahImgs();
	}

	private void addPenjajah(int pType) {
		switch (pType) {
		case TENTARA:
			penjajahs.add(new Tentara(xSTART, ySTART, 0, this));
			break;
		case TANK:
			penjajahs.add(new Tank(xSTART, ySTART, 0, this));
			break;
		case LONDO_IRENG:
			penjajahs.add(new LondoIreng(xSTART, ySTART, 0, this));

			break;
		}
	}

	public void loadPenjajahImgs() {
		BufferedImage sprt = LoadImage.getPenjajahAtlas();
		for(int i = 0; i < 3; i++) {
			for(int j =0; j < 4; j++) {
				penjajahImgs[i][j] = sprt.getSubimage(j * TILE_SIZE, i * TILE_SIZE, TILE_SIZE, TILE_SIZE);
			}
		}
	}
	
	public void update() {

		for(Penjajah p : penjajahs) {
			if(p.isAlive())
				updatePenjajahMove(p);
		}
	}


	public void updatePenjajahMove(Penjajah p) {
		if(p.getLastDir() == -1)
			setNewDirectionAndMove(p);
		
		int newX = (int) (p.getX() + getSpeedAndWidth(p.getLastDir(), p.getPenjajahType()));
		int newY = (int) (p.getY() + getSpeedAndHeight(p.getLastDir(), p.getPenjajahType()));

		if (getTileType(newX, newY) == ROAD_TILE) {
			p.move(GetSpeed(p.getPenjajahType()), p.getLastDir());
		} else if (isAtEnd(p)) {
			p.kill();
			int liveLost = helpers.Constants.Penjajahs.GetLiveLost(p.getPenjajahType());
			playing.removeLives(liveLost);
		} else {
			setNewDirectionAndMove(p);
		}
	}
	
	private void setNewDirectionAndMove(Penjajah p) {
		int dir = p.getLastDir();
		
		if (p instanceof LondoIreng) {
	        LondoIreng londoIreng = (LondoIreng) p;
	        long currentTime = System.currentTimeMillis();
	        long invisTime = helpers.Constants.Penjajahs.GetLondoIrengInvisTime();
	        if (londoIreng.isInvisible() && (currentTime - londoIreng.getInvisibleStartTime() >= invisTime)) {
	            londoIreng.setVisible(false);
	        }
	    }

		int xCord = (int) (p.getX() / TILE_SIZE);
		int yCord = (int) (p.getY() / TILE_SIZE);

		fixPenjajahOffsetTile(p, dir, xCord, yCord);
		
		if(isAtEnd(p))
			return;
		
		if (dir == LEFT || dir == RIGHT) {
			int newY = (int) (p.getY() + getSpeedAndHeight(UP, p.getPenjajahType()));
			if (getTileType((int) p.getX(), newY) == ROAD_TILE)
				p.move(GetSpeed(p.getPenjajahType()), UP);
			else
				p.move(GetSpeed(p.getPenjajahType()), DOWN);
		} else {
			int newX = (int) (p.getX() + getSpeedAndWidth(RIGHT, p.getPenjajahType()));
			if (getTileType(newX, (int) p.getY()) == ROAD_TILE)
				p.move(GetSpeed(p.getPenjajahType()), RIGHT);
			else
				p.move(GetSpeed(p.getPenjajahType()), LEFT);
		}
	}

	private void fixPenjajahOffsetTile(Penjajah p, int dir, int xCord, int yCord) {
		switch (dir) {
		case RIGHT:
			if (xCord < 24)
				xCord++;
			break;
		case DOWN:
			if (yCord < 18)
				yCord++;
			break;
		}

		p.setPos(xCord * TILE_SIZE, yCord * TILE_SIZE);

	}

	private boolean isAtEnd(Penjajah p) {
		if(p.getX() == xEND && p.getY() == yEND)
			return true;
		return false;
	}

	private int getTileType(int x, int y) {
		return playing.getTileType(x, y);
	}

	private float getSpeedAndHeight(int dir, int pType) {
		if (dir == UP)
			return -GetSpeed(pType);
		else if (dir == DOWN)
			return GetSpeed(pType) + TILE_SIZE;

		return 0;
	}

	private float getSpeedAndWidth(int dir, int pType) {
		if (dir == LEFT)
			return -GetSpeed(pType);
		else if (dir == RIGHT)
			return GetSpeed(pType) + TILE_SIZE;

		return 0;
	}
	
	public void draw(Graphics g) {
		for(Penjajah p : penjajahs) {
			if(p.isAlive()) {
				drawPenjajah(p, g);
				drawHpBar(p, g);
			}
		}
	}

	private void drawHpBar(Penjajah p, Graphics g) {
		g.setColor(Color.RED);
		g.fillRect((int)p.getX() + 20 - (getNewBarWidth(p)/2), (int)p.getY() - 10, getNewBarWidth(p), 3);
	}
	
	private int getNewBarWidth(Penjajah p) {
		return (int)(hpBarWidth * p.getHpBarFloat());
	}

	private void drawPenjajah(Penjajah p, Graphics g) {
		if ((p instanceof LondoIreng)) {
			if(!((LondoIreng) p).isInvisible())
	        g.drawImage(penjajahImgs[p.getPenjajahType()][p.getLastDir()], (int) p.getX(), (int) p.getY(), null);
		}
		else
	        g.drawImage(penjajahImgs[p.getPenjajahType()][p.getLastDir()], (int) p.getX(), (int) p.getY(), null);

	}
	
	public ArrayList<Penjajah> getPenjajahs(){
		return penjajahs;
	}

	public void spawnPenjajah(int nextPenjajah) {
		addPenjajah(nextPenjajah);
	}

	public int getAmountAlive() {
		int size = 0;
		for(Penjajah p : penjajahs)
			if(p.isAlive())
				size++;
		return size;
	}

	public void rewardByKiling(int penjajahType) {
		playing.rewardPlayer(penjajahType);
	}
	
	public void reset() {
		penjajahs.clear();
	}
}
