package scenes;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import helpers.LoadImage;
import main.Game;
import ui.MyButton;

import static helpers.Constants.Colours.*;
import static helpers.Constants.Sizes.*;
import static main.GameStates.*;

public class Settings extends GameScene implements SceneMethods {

	private MyButton bMenu, bMute;
	private BufferedImage img;
	
	private Rectangle soundBarBound;
	private int xPos = GAME_WIDTH / 2;

	public Settings(Game game) {
		super(game);
		initButtons();
		img = LoadImage.getMainMenu();
		this.soundBarBound = new Rectangle(GAME_WIDTH/2 - 180, 310, 360, 20);
	}

	private void initButtons() {
		bMenu = new MyButton("Menu", 505, 600, 200, 40);
		bMute = new MyButton("Mute", GAME_WIDTH/2 - 50, GAME_HEIGHT/2 + 40, 100, 40);
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(img, 0, 0, null);
		
		g.setColor(new Color(20, 21, 22, 220));
		g.fillRect(GAME_WIDTH/2 - 250, 190, 500, 350);
		
		g.setColor(new Color(255, 255, 255, 180));
		g.drawRect(GAME_WIDTH/2 - 250, 190, 500, 350);
		
		drawButtons(g);
		drawVolumeBar(g);

	}

	private void drawVolumeBar(Graphics g) {
		g.setColor(new Color(253, 204, 13));
		g.fillRect(GAME_WIDTH/2 - 200, 300, 400, 40);
		
		g.setColor(Color.WHITE);
		g.fillRect(GAME_WIDTH/2 - 180, 310, 360, 20);
		
		g.setColor(Color.WHITE);
		g.setFont(new Font("Monospaced", Font.BOLD, 50));
		String vol = new String("Volume Bar");
		int x = g.getFontMetrics().stringWidth(vol);
		g.drawString("Volume Bar", GAME_WIDTH/2 - x/2, 250);

		g.setColor(LIGHT_BLUE);
		g.fillOval(xPos - 25, 320 - 25, 50, 50);
		
		g.setColor(DARK_YELLOW);
		g.fillOval(xPos - 18, 320 - 18, 36, 36);

	}

	private void drawButtons(Graphics g) {
		bMenu.draw(g);
		game.getPlaying().getActionBar().drawButtonFeedback(g, bMenu);
		bMute.draw(g);
		game.getPlaying().getActionBar().drawButtonFeedback(g, bMute);

	}

	@Override
	public void mouseClicked(int x, int y) {
		if (bMenu.getBounds().contains(x, y))
			SetGameState(MENU);
		else if(bMute.getBounds().contains(x, y))
			muteSound();
		else if(soundBarBound.getBounds().contains(x, y))
			changeVolume(x);

	}

	private void muteSound() {
		game.getSound().changeMuted();;
		bMute.changeMuted();
	}

	private void changeVolume(int x) {
		xPos = x;
		game.getSound().setMinusVolume((float) (60 - ((float) (x - 335)/360)*60));
	}

	@Override
	public void mouseMoved(int x, int y) {
		bMenu.setMouseOver(false);
		bMute.setMouseOver(false);
		if (bMenu.getBounds().contains(x, y))
			bMenu.setMouseOver(true);
		else if(bMute.getBounds().contains(x, y))
			bMute.setMouseOver(true);

	}

	@Override
	public void mousePressed(int x, int y) {
		if (bMenu.getBounds().contains(x, y))
			bMenu.setMousePressed(true);
		else if(bMute.getBounds().contains(x, y))
			bMute.setMousePressed(false);
		else if(bMute.getBounds().contains(x, y)) 
			changeVolume(x);
	}

	@Override
	public void mouseReleased(int x, int y) {
		resetButtons();
	}

	private void resetButtons() {
		bMenu.resetBooleans();
		bMute.resetBooleans();
	}

	@Override
	public void mouseDragged(int x, int y) {
		if (soundBarBound.getBounds().contains(x, y)) {
			changeVolume(x);
		}
	}

}
