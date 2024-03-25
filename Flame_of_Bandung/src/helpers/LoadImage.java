package helpers;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import javax.imageio.ImageIO;

public class LoadImage {

	public static BufferedImage getSpriteAtlas() {
		BufferedImage img = null;
		InputStream is = LoadImage.class.getClassLoader().getResourceAsStream("sprite.png");

		try {
			img = ImageIO.read(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return img;
	}
	
	public static BufferedImage getPenjajahAtlas() {
		BufferedImage img = null;
		InputStream is = LoadImage.class.getClassLoader().getResourceAsStream("penjajah.png");
		try {
			img = ImageIO.read(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return img;
	}
	
	public static BufferedImage getPejuangAtlas() {
		BufferedImage img = null;
		InputStream is = LoadImage.class.getClassLoader().getResourceAsStream("Pejuang.png");
		try {
			img = ImageIO.read(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return img;
	}
	
	public static BufferedImage getProjectileAtlas() {
		BufferedImage img = null;
		InputStream is = LoadImage.class.getClassLoader().getResourceAsStream("Obstacles.png");
		try {
			img = ImageIO.read(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return img;
	}
	
	public static BufferedImage getTrapAtlas() {
		BufferedImage img = null;
		InputStream is = LoadImage.class.getClassLoader().getResourceAsStream("Obstacles.png");
		try {
			img = ImageIO.read(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return img;
	}
	
	public static BufferedImage getJawaraSlashImg() {
		BufferedImage img = null;
		InputStream is = LoadImage.class.getClassLoader().getResourceAsStream("JawaraAtk.png");
		try {
			img = ImageIO.read(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return img;
	}
	
	public static BufferedImage getCutscene(int index) {
		BufferedImage img = null;
		String path = "Cutscene" + Integer.toString(index) + ".png";
		InputStream is = LoadImage.class.getClassLoader().getResourceAsStream(path);
		try {
			img = ImageIO.read(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return img;
	}

	public static BufferedImage getVictory(int i) {
		BufferedImage img = null;
		String path = "WinScene" + Integer.toString(i) + ".png";
		InputStream is = LoadImage.class.getClassLoader().getResourceAsStream(path);
		try {
			img = ImageIO.read(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return img;
	}

	public static BufferedImage getMainMenu() {
		BufferedImage img = null;
		InputStream is = LoadImage.class.getClassLoader().getResourceAsStream("Settings.png");
		try {
			img = ImageIO.read(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return img;	
	}

	public static BufferedImage getLost() {
		BufferedImage img = null;
		InputStream is = LoadImage.class.getClassLoader().getResourceAsStream("LostScene.png");
		try {
			img = ImageIO.read(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return img;	
	}

}

