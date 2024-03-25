package managers;

import static helpers.Constants.Pejuangs.*;
import static helpers.Constants.Projectiles.*;
import static helpers.Constants.Sizes.*;

import java.awt.Graphics;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import helpers.LoadImage;
import objects.Pejuang;
import objects.Projectile;
import penjajah.Penjajah;
import scenes.Playing;


public class ProjectileManager {

	private Playing playing;
	private ArrayList<Projectile> projectiles = new ArrayList<>();
	private BufferedImage[] projImgs;
	private int projId = 0;
	
	public ProjectileManager(Playing playing) {
		this.playing = playing;
		importImgs();
	}
	
	private void importImgs() {
		BufferedImage bullet = LoadImage.getProjectileAtlas();
		projImgs = new BufferedImage[2];
		projImgs[0] = bullet.getSubimage(0, 0, TILE_SIZE, TILE_SIZE);
	}
	
	public void newProj(Pejuang p, Penjajah ph) {
		int type = getProjType(p);
		
		int xDist = (int) (p.getX() - ph.getX());
		int yDist = (int) (p.getY() - ph.getY());
		int totalDist = Math.abs(xDist) + Math.abs(yDist);
		
		float xDiv = (float)Math.abs(xDist)/totalDist;
		float xSpeed = xDiv * helpers.Constants.Projectiles.GetSpeed(type);
		float ySpeed = (1-xDiv) * helpers.Constants.Projectiles.GetSpeed(type);

		if(p.getX() > ph.getX())
			xSpeed *= -1;
		if(p.getY() > ph.getY())
			ySpeed *= -1;
		
		float arcValue = (float) Math.atan(yDist / (float)xDist);
		float rotate = (float) Math.toDegrees(arcValue);
		if(xDist < 0)
			rotate += 180;
		projectiles.add(new Projectile(p.getX() + TILE_SIZE/2, p.getY() + TILE_SIZE/2, xSpeed, ySpeed, p.getDamage(), rotate, projId++, type));
	}
	
	private int getProjType(Pejuang p) {
		switch(p.getPejuangType()) {
			case PRAJURIT:
				return BULLET;
			default:
				return 0;
		}
	}

	public void draw(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		for(Projectile pr : projectiles)
			if(pr.isActive()) {
				g2d.translate(pr.getPos().x, pr.getPos().y);
				g2d.rotate(Math.toRadians(180));
				g2d.rotate(Math.toRadians(pr.getRotation()));
				g2d.drawImage(projImgs[pr.getProjectileType()], -TILE_SIZE/2, -TILE_SIZE/2, null);
				g2d.rotate(-Math.toRadians(pr.getRotation()));
				g2d.translate(-pr.getPos().x, -pr.getPos().y);
			}
	}
	
	public void update() {
		for(Projectile pr : projectiles)
			if(pr.isActive()) {
				pr.move();
				if(isProjHitting(pr)) {
					pr.setActive(false);
				}
				else if(isProjOutOfBounds(pr)){
					pr.setActive(false);
				}
			}
	}
	
	private boolean isProjOutOfBounds(Projectile pr) {
		return (pr.getPos().x < 0 && pr.getPos().y < 0 && pr.getPos().y > GAME_HEIGHT && pr.getPos().x > GAME_WIDTH);
	}

	private boolean isProjHitting(Projectile pr) {
		for(Penjajah ph : playing.getPenjajahManager().getPenjajahs()) {
			if(ph.isAlive()) {	
				if(ph.getBounds().contains(pr.getPos())) {
					ph.dmgDealt(pr.getDmg());
					return true;
				}
			}
		}
		return false;
	}

	public void reset() {
		projectiles.clear();
		projId = 0;
	}

}
