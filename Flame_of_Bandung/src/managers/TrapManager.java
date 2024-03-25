package managers;

import static helpers.Constants.Penjajahs.TANK;
import static helpers.Constants.Sizes.*;
import static helpers.Constants.Traps.*;

import java.awt.Graphics;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import helpers.LoadImage;
import objects.Lubang;
import objects.Lumpur;
import objects.Paku;
import objects.Trap;
import penjajah.Penjajah;
import scenes.Playing;

public class TrapManager {
	private Playing playing;
	private BufferedImage[] trapImgs;
	private ArrayList<Trap> traps = new ArrayList<>();
	private int totalTrap = 0;
	
	public TrapManager(Playing playing) {
		this.playing = playing;
		trapImgs = new BufferedImage[3];
		loadTrapImgs();
	}

	private void loadTrapImgs() {
		BufferedImage sprite = LoadImage.getTrapAtlas();  

		trapImgs[0] = sprite.getSubimage(0, TILE_SIZE, TILE_SIZE, TILE_SIZE);
		trapImgs[1] = sprite.getSubimage(TILE_SIZE, 0, TILE_SIZE, TILE_SIZE);
		trapImgs[2] = sprite.getSubimage(TILE_SIZE, TILE_SIZE, TILE_SIZE, TILE_SIZE);

	}
	
	public void addTrap(Trap t, int x, int y) {
		switch(t.getTrapType()) {
		case PAKU:
			traps.add(new Paku(x, y, totalTrap++));
			break;
		case LUMPUR:
			traps.add(new Lumpur(x, y, totalTrap++));
			break;
		case LUBANG:
			traps.add(new Lubang(x, y, totalTrap++));
			break;
		}
	}
	
	public void update() {
		for(Trap t : traps) {
			t.update();
			if(t.isActive() && t.isOffCd()) {
				for(Penjajah p : playing.getPenjajahManager().getPenjajahs()) {
					if(p.getBounds().contains(t.getX() + 10, t.getY() + 10) && p.isAlive()) {
						switch(t.getTrapType()) {
						case PAKU:
							dealDmgToPenjajah(t, p);
							break;
						case LUMPUR:
							slowPenjajah(t, p);
							break;
						case LUBANG:
							intsaKillPenjajah(t, p);
							break;
						}
					}
					t.resetTick();
				}
			}
		}
	}
	
	private void intsaKillPenjajah(Trap t, Penjajah p) {
		if(p.getPenjajahType() != TANK) {
			p.instaKill();
			t.updateUsage();
		}
	}

	private void slowPenjajah(Trap t, Penjajah p) {
		p.causeSlow();
		t.updateUsage();
	}

	private void dealDmgToPenjajah(Trap t, Penjajah p) {
		p.dmgDealt(t.getDamage());
		t.updateUsage();
	}

	public void draw(Graphics g) {
		for(Trap t : traps) {
			if(t.isActive())
				g.drawImage(trapImgs[t.getTrapType()], t.getX(), t.getY(), null);
		}
	}

	public BufferedImage[] getTrapImgs() {
		return trapImgs;
	}

	public Trap getTrapAt(int x, int y) {
		for(Trap t : traps) {
			if(t.getX() == x && t.getY() == y)
				return t;
		}
		return null;
	}

	public void reset() {
		traps.clear();
		totalTrap = 0;
	}
}
