package scenes;

import java.awt.Color;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;

import helpers.LoadImage;
import main.Game;
import ui.MyButton;

import static helpers.Constants.Colours.*;
import static helpers.Constants.Sizes.*;
import static main.GameStates.*;
import static helpers.Constants.SoundIndex.*;


public class Menu extends GameScene implements SceneMethods {

	private MyButton bPlaying, bSettings, bQuit;
	private BufferedImage img;
	
	public Menu(Game game) {
		super(game);
		initButtons();
		img = LoadImage.getMainMenu();
	}

	private void initButtons() {

		int w = 200;
		int h = w / 3;
		int x = GAME_WIDTH / 2 - w / 2;
		int y = 230;
		int yOffset = 150;

		bPlaying = new MyButton("Play", x, y, w, h);
		bSettings = new MyButton("Settings", x, y + yOffset * 1, w, h);
		bQuit = new MyButton("Quit", x, y + yOffset * 2, w, h);

	}

	@Override
	public void render(Graphics g) {
		g.drawImage(img, 0, 0, null);

		drawButtons(g);

	}

	private void drawButtons(Graphics g) {
		bPlaying.draw(g);
		bSettings.draw(g);
		bQuit.draw(g);

	}

	@Override
	public void mouseClicked(int x, int y) {

		if (bPlaying.getBounds().contains(x, y)) {
			if(game.getPlaying().getIsUpdated()) {
				game.playMusic(PLAY);
				SetGameState(PLAYING);
			}
			else {
				game.playMusic(CUTSCENES);
				SetGameState(CUTSCENE);
			}
		}
		else if (bSettings.getBounds().contains(x, y))
			SetGameState(SETTINGS);
		else if (bQuit.getBounds().contains(x, y))
			System.exit(0);
	}

	@Override
	public void mouseMoved(int x, int y) {
		bPlaying.setMouseOver(false);
		bSettings.setMouseOver(false);
		bQuit.setMouseOver(false);

		if (bPlaying.getBounds().contains(x, y))
			bPlaying.setMouseOver(true);
		else if (bSettings.getBounds().contains(x, y))
			bSettings.setMouseOver(true);
		else if (bQuit.getBounds().contains(x, y))
			bQuit.setMouseOver(true);

	}

	@Override
	public void mousePressed(int x, int y) {

		if (bPlaying.getBounds().contains(x, y))
			bPlaying.setMousePressed(true);
		else if (bSettings.getBounds().contains(x, y))
			bSettings.setMousePressed(true);
		else if (bQuit.getBounds().contains(x, y))
			bQuit.setMousePressed(true);

	}

	@Override
	public void mouseReleased(int x, int y) {
		resetButtons();
	}

	private void resetButtons() {
		bPlaying.resetBooleans();
		//bEdit.resetBooleans();
		bSettings.resetBooleans();
		bQuit.resetBooleans();

	}

	@Override
	public void mouseDragged(int x, int y) {
		// TODO Auto-generated method stub

	}

}