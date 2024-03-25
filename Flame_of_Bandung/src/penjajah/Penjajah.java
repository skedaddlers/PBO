package penjajah;

import static helpers.Constants.Direction.*;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import helpers.LoadImage;
import managers.PenjajahManager;

public abstract class Penjajah {
	
	private float x, y;
	private Rectangle bounds;
	private BufferedImage[] sprites;
	private int hp;
	private int maxHp;
	private int ID;
	private int penjajahType;
	private int lastDir;
	private PenjajahManager pManager;
	protected int slowTickLimit = 300;
	protected int slowTick = slowTickLimit;
	protected boolean alive = true;
	public Penjajah(float x, float y, int id, int type, PenjajahManager pm) {
		this.x = x;
		this.y = y;
		this.ID = id;
		this.penjajahType = type;
		this.pManager = pm;
		sprites = new BufferedImage[4];
		bounds = new Rectangle((int)x, (int)y, 40, 40);
		lastDir = -1;
		setStartHp();
	}
	
	private void setStartHp() {
		hp = helpers.Constants.Penjajahs.GetStartHp(penjajahType);
		maxHp = hp;
	}

	public void move(float speed, int dir) {
		lastDir = dir;
		
		if(slowTick < slowTickLimit) {
			slowTick++;
			speed *= 0.5f;
		}
		switch (dir) {
		case LEFT:
			this.x -= speed;
			break;
		case UP:
			this.y -= speed;
			break;
		case RIGHT:
			this.x += speed;
			break;
		case DOWN:
			this.y += speed;
			break;
		}
		updateHitbox();
	}
	
	private void updateHitbox() {
		bounds.x = (int) x;
		bounds.y = (int) y;
	}


	public void causeSlow() {
		slowTick = 0;
	}
	
	public void dmgDealt(int dmg) {
		this.hp -= dmg;
		if(hp <= 0) {
			alive = false;
			pManager.rewardByKiling(penjajahType);
		}
	}

	public void kill() {
		
		alive = false;
		hp = 0;
	}
	
	public float getHpBarFloat() {
		return (float)hp / maxHp;
	}
	
	public void setPos(int x, int y) {
		// Don't use this one for moving the enemy.
		this.x = x;
		this.y = y;
	}
	
	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public Rectangle getBounds() {
		return bounds;
	}

	public int getHp() {
		return hp;
	}

	public int getID() {
		return ID;
	}

	public int getPenjajahType() {
		return penjajahType;
	}
	
	public int getLastDir() {
		return lastDir;
	}
	
	public boolean isAlive() {
		return alive;
	}

	public void instaKill() {
		alive = false;
	}



}
