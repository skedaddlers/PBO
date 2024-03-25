package helpers;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Utilz {
	public static BufferedImage buildImg(BufferedImage[] imgs) {
		int w = imgs[0].getWidth();
		int h = imgs[0].getHeight();
		
		BufferedImage newImg = new BufferedImage(w, h, imgs[0].getType());
		Graphics2D g2d = newImg.createGraphics();
		
		for(BufferedImage i : imgs) {
			g2d.drawImage(i, 0, 0, null);
		}
		g2d.dispose();
		return newImg;
	}
	
	public static int GetHypoDistance(float x1, float y1, float x2, float y2) {
		float deltaX = Math.abs(x1-x2);
		float deltaY = Math.abs(y1-y2);
		return (int) Math.hypot(deltaX, deltaY);
	}
}
