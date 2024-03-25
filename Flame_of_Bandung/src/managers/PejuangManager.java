package managers;

import static helpers.Constants.Pejuangs.*;
import static helpers.Constants.Sizes.*;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Float;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import helpers.Constants;
import helpers.LoadImage;
import objects.Jawara;
import objects.Orator;
import objects.Pejuang;
import objects.Prajurit;
import penjajah.LondoIreng;
import penjajah.Penjajah;
import scenes.Playing;


public class PejuangManager {

	private Playing playing;
	private BufferedImage[] pejuangImgs;
	private ArrayList<Pejuang> pejuangs = new ArrayList<>();
	private int totalPejuang = 0;
	private int attackStartTime = 15;
	
	public PejuangManager(Playing playing) {
		this.playing = playing;
		
		loadPejuangImgs();
	}

	private void loadPejuangImgs() {
		pejuangImgs = new BufferedImage[3];
		BufferedImage atlas = LoadImage.getPejuangAtlas();
		for(int i = 0; i < 3; i++) {
			pejuangImgs[i] = atlas.getSubimage(i * TILE_SIZE, 0, TILE_SIZE, TILE_SIZE);
		}
	}

	public void addPejuang(Pejuang selectedPejuang, int xPos, int yPos) {
		switch(selectedPejuang.getPejuangType()) {
		case PRAJURIT:
			pejuangs.add(new Prajurit(xPos, yPos, totalPejuang++));
			break;
		case JAWARA:
			pejuangs.add(new Jawara(xPos, yPos, totalPejuang++));
			break;
		case ORATOR:
			pejuangs.add(new Orator(xPos, yPos, totalPejuang++));
			break;
		}
	}
	
	public Pejuang getPejuangAt(int x, int y) {

		for(Pejuang p : pejuangs) {
			if(p.getX() == x && p.getY() == y)
				return p;
		}
		return null;
	}
	
	public void draw(Graphics g) {
		for(Pejuang p : pejuangs) {
			Graphics2D g2d = (Graphics2D) g;
			g2d.drawImage(pejuangImgs[p.getPejuangType()], p.getX(), p.getY(), null);
			if (p instanceof Jawara && ((Jawara) p).isAttacking()) {
				drawSlash(g, (Jawara)p);
                attackStartTime--;
                if (attackStartTime == 0) {
                	attackStartTime = 15;
                    ((Jawara) p).setAttacking(false);
                }
            }
		}
	}
	
	private void drawSlash(Graphics g, Jawara j) {
	    Graphics2D g2d = (Graphics2D) g.create();
	    g2d.rotate(Math.toRadians(j.getRot()), j.getX() + TILE_SIZE / 2, j.getY() + TILE_SIZE / 2);
	    g2d.drawImage(j.getAttackSprite(), j.getX() - TILE_SIZE/2, j.getY() - TILE_SIZE/2, null);
	    g2d.dispose();
	}


	public void update() {
		for(Pejuang p : pejuangs) {
			p.update();
			if(p.getPejuangType() == ORATOR)
				applyOratorBuff((Orator) p);
			else
				attackPenjajahInRange(p);
		}
	}
	

	public void upgradePejuang(Pejuang p, int cost) {
		if(p instanceof Orator) {
			((Orator)p).upgradeOrator(cost);
			for(Pejuang pj : pejuangs) {
				if(p != pj && inBuffRange(pj, (Orator)p)) {
					pj.resetBuff();
				}
			}
		}
		else
			p.upgrade(cost);
	}
	
	private void applyOratorBuff(Orator orator) {
	    for (Pejuang p : pejuangs) {
	        if (p != orator && inBuffRange(p, orator)) {
	            if (!(p instanceof Orator)) {
	                if (p.isBuffed() == false) {
	                    orator.applyBuff(p);
	                    p.buffApplied();
	                }
	            }
	        }
	    }
	}


	private boolean inBuffRange(Pejuang p, Orator orator) {
		int range = helpers.Utilz.GetHypoDistance(p.getX(), p.getY(), orator.getX(), orator.getY());
		return range < orator.getRange();	
	}

	private void attackPenjajahInRange(Pejuang p) {
		for(Penjajah ph : playing.getPenjajahManager().getPenjajahs()) {
			if(ph.isAlive()) {
				if(isPenjajahInRange(p, ph)) {
					if(p.isOffCd()) {
						if(ph instanceof LondoIreng) {
							if(!((LondoIreng) ph).isInvisible()) {
								playing.dealDmgToPenjajah(p, ph);
								p.resetCd();
							}
						}
						else {
							if(p instanceof Jawara) {
								int xDist = (int) (p.getX() - ph.getX());
								int yDist = (int) (p.getY() - ph.getY());
								float arcValue = (float) Math.atan(yDist / (float)xDist);
								float rotate = (float) Math.toDegrees(arcValue);
								if(xDist < 0)
									rotate += 180;
								((Jawara)p).setRot(rotate);
							}
							playing.dealDmgToPenjajah(p, ph);
							p.resetCd();
						}
					}
				}
				else {
			
				}
			}
		}
	}
	

	public void removePejuang(Pejuang p) {
		for(int i = 0; i < pejuangs.size(); i++)
			if(pejuangs.get(i).getId() == p.getId())
				pejuangs.remove(i);
	}


	private boolean isPenjajahInRange(Pejuang p, Penjajah pj) {
		int range = helpers.Utilz.GetHypoDistance(p.getX(), p.getY(), pj.getX(), pj.getY());
		return range < p.getRange();
	}

	public BufferedImage[] getPejuangImgs() {
		return pejuangImgs;
	}

	public void dealJawaraAOE(Pejuang jawara, Penjajah phDealt) {
		Point2D.Float AOESource = new Point2D.Float(phDealt.getX(), phDealt.getY());
		for(Penjajah p : playing.getPenjajahManager().getPenjajahs()) {
			if(inAOERange(p, AOESource)) {
				((Jawara)jawara).setAttacking(true);
				p.dmgDealt(jawara.getDamage());
			}
		}
		
	}

	private boolean inAOERange(Penjajah p, Float sc) {
		int range = helpers.Utilz.GetHypoDistance(p.getX(), p.getY(), sc.x, sc.y);
		return range < helpers.Constants.Pejuangs.GetJawaraAOE();
	}

	public void reset() {
		pejuangs.clear();
		totalPejuang = 0;
		attackStartTime = 15;
	}


}
