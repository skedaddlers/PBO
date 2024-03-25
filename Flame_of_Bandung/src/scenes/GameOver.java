package scenes;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import helpers.LoadImage;
import main.Game;
import ui.MyButton;

import static helpers.Constants.Sizes.*;
import static main.GameStates.*;


public class GameOver extends GameScene implements SceneMethods{

	private MyButton bReplay, bMenu, bNext;
	private BufferedImage[] img;
	private int index = 0;
	
	public GameOver(Game game) {
		super(game);
		initButtons();
		img = new BufferedImage[2];
		img[0] = LoadImage.getLost();
		img[1] = LoadImage.getMainMenu();
		bNext = new MyButton("Lanjut", 10, 10, 100, 40);

	}

	private void initButtons() {

		int w = 200;
		int h = w / 3;
		int x = GAME_WIDTH / 2 - w / 2;
		int y = 250;
		int yOffset = 150;

		bMenu = new MyButton("Menu", x, y, w, h);
		bReplay = new MyButton("Replay", x, y + yOffset * 1, w, h);

		
	}
	
	@Override
	public void render(Graphics g) {
		if(index == 0) {
			g.drawImage(img[0], 0, 0, null);
			bNext.draw(g);
		}
		else {
			g.drawImage(img[1], 0, 0, null);
			bMenu.draw(g);
			bReplay.draw(g);
		}
	}

	@Override
	public void mouseClicked(int x, int y) {
		if(index == 0) {
			if(bNext.getBounds().contains(x, y)) {
				index++;
			}	
		}else {
			if(bMenu.getBounds().contains(x, y)) {
				game.getPlaying().resetAll();	
				game.getCutscene().resetIndex();
				SetGameState(MENU);
			}
			else if(bReplay.getBounds().contains(x, y))
				replayGame();
		}
	}

	private void replayGame() {
		//reset
		game.getPlaying().resetAll();
		game.getCutscene().resetIndex();
		index = 0;
		SetGameState(CUTSCENE);
	}

	@Override
	public void mouseMoved(int x, int y) {
		if(index == 0) {
			bNext.setMouseOver(false);
			
			if(bNext.getBounds().contains(x, y))
				bNext.setMouseOver(true);
		}
		else {
			bMenu.setMouseOver(false);
			bReplay.setMouseOver(false);
			
			if(bMenu.getBounds().contains(x, y))
				bMenu.setMouseOver(true);
			else if(bReplay.getBounds().contains(x, y))
				bReplay.setMouseOver(true);
		}
	}

	@Override
	public void mousePressed(int x, int y) {
		if(index == 0) {
			if(bNext.getBounds().contains(x, y))
				bNext.setMousePressed(true);
		}
		else {
			if(bMenu.getBounds().contains(x, y))
				bMenu.setMousePressed(true);
			else if(bReplay.getBounds().contains(x, y))
				bReplay.setMousePressed(true);		
		}
	}

	@Override
	public void mouseReleased(int x, int y) {
		if(index == 0) {
			bNext.resetBooleans();
		}
		else {
			bMenu.resetBooleans();
			bReplay.resetBooleans();
		}
	}

	@Override
	public void mouseDragged(int x, int y) {
		// TODO Auto-generated method stub
		
	}

}
