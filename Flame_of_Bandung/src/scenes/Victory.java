package scenes;

import static helpers.Constants.Sizes.GAME_HEIGHT;
import static helpers.Constants.SoundIndex.*;
import static helpers.Constants.Sizes.GAME_WIDTH;
import static main.GameStates.*;
import static main.GameStates.SetGameState;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import helpers.LoadImage;
import main.Game;
import ui.MyButton;

public class Victory extends GameScene implements SceneMethods{
	
	private BufferedImage[] victories;
	private int vicIndex = 1;
	private MyButton bNext;

	public Victory(Game game) {
		super(game);

		loadImages();
		bNext = new MyButton("Lanjut", 10, 10, 100, 40);
	}

	private void loadImages() {
		victories = new BufferedImage[3];
		for(int i = 1; i < 3; i++) {
			victories[i] = LoadImage.getVictory(i);
		}
	}

	public void resetIndex() {
		vicIndex = 1;	
	}
	
	@Override
	public void render(Graphics g) {
		g.drawImage(victories[vicIndex], 0, 0, null);
		bNext.draw(g);
	}

	@Override
	public void mouseClicked(int x, int y) {
		if(bNext.getBounds().contains(x, y)) {
			if(vicIndex + 1 < 3)
				vicIndex++;
			else {
				game.getPlaying().resetAll();
				resetIndex();
				game.playMusic(MAIN);
				SetGameState(MENU);
			}
		}
	}

	@Override
	public void mouseMoved(int x, int y) {
		bNext.setMouseOver(false);
		
		if(bNext.getBounds().contains(x, y))
			bNext.setMouseOver(true);
	}

	@Override
	public void mousePressed(int x, int y) {
		if(bNext.getBounds().contains(x, y))
			bNext.setMousePressed(true);			
	}

	@Override
	public void mouseReleased(int x, int y) {
		bNext.resetBooleans();
	}

	@Override
	public void mouseDragged(int x, int y) {
		// TODO Auto-generated method stub
		
	}

}
