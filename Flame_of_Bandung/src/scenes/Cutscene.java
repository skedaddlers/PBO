package scenes;

import java.awt.Graphics;

import java.awt.image.BufferedImage;

import helpers.LoadImage;
import main.Game;
import ui.MyButton;

import static helpers.Constants.Sizes.*;
import static helpers.Constants.SoundIndex.*;
import static main.GameStates.*;

public class Cutscene extends GameScene implements SceneMethods{
	
	private BufferedImage[] cutscenes;
	private int cutsIndex = 1;
	private MyButton bNext;
	
	public Cutscene(Game game) {
		super(game);
		
		loadImages();
		bNext = new MyButton("Lanjut", 10, 10, 100, 40);
	}

	private void loadImages() {
		cutscenes = new BufferedImage[6];
		for(int i = 1; i < 6; i++) {
			cutscenes[i] = LoadImage.getCutscene(i);
		}
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(cutscenes[cutsIndex], 0, 0, null);
		bNext.draw(g);
	}

	public void resetIndex() {
		cutsIndex = 1;	
	}

	@Override
	public void mouseClicked(int x, int y) {
		if(bNext.getBounds().contains(x, y)) {
			if(cutsIndex + 1 < 6)
				cutsIndex++;
			else {
				resetIndex();
				game.playMusic(PLAY);
				SetGameState(PLAYING);
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
