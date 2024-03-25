package objects;

import static helpers.Constants.Pejuangs.JAWARA;

import java.awt.Image;
import java.awt.image.BufferedImage;

import helpers.LoadImage;

public class Jawara extends Pejuang{
	private boolean isAttacking = false;
	BufferedImage atkSprite = LoadImage.getJawaraSlashImg();
	private float swingRot;
	
	public Jawara(int x, int y, int id) {
		super(x, y, id, JAWARA);
	}

	public void setAttacking(boolean attacking) {
        isAttacking = attacking;
    }
	
	public void setRot(float rot) {
		this.swingRot = rot;
	}

	public float getRot() {
		return swingRot;
	}
	
	public BufferedImage getAttackSprite() {
		return atkSprite;
	}

	public boolean isAttacking() {
		return isAttacking;
	}

}
