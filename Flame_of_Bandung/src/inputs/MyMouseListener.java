package inputs;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import main.Game;
import main.GameStates;

public class MyMouseListener implements MouseListener, MouseMotionListener {

	private Game game;

	public MyMouseListener(Game game) {
		this.game = game;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		switch (GameStates.gameState) {
		case MENU:
			game.getMenu().mouseDragged(e.getX(), e.getY());
			break;
		case PLAYING:
			game.getPlaying().mouseDragged(e.getX(), e.getY());
			break;
		case SETTINGS:
			game.getSettings().mouseDragged(e.getX(), e.getY());
			break;
		case CUTSCENE:
			game.getCutscene().mouseDragged(e.getX(), e.getY());
			break;
		case VICTORY:
			game.getVictory().mouseDragged(e.getX(), e.getY());
			break;
		default:
			break;
		}

	}

	@Override
	public void mouseMoved(MouseEvent e) {
		switch (GameStates.gameState) {
		case MENU:
			game.getMenu().mouseMoved(e.getX(), e.getY());
			break;
		case PLAYING:
			game.getPlaying().mouseMoved(e.getX(), e.getY());
			break;
		case SETTINGS:
			game.getSettings().mouseMoved(e.getX(), e.getY());
			break;
		case GAMEOVER:
			game.getGameOver().mouseMoved(e.getX(), e.getY());
			break;
		case CUTSCENE:
			game.getCutscene().mouseMoved(e.getX(), e.getY());
			break;
		case VICTORY:
			game.getVictory().mouseMoved(e.getX(), e.getY());
			break;
		default:
			break;

		}

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1) {

			switch (GameStates.gameState) {
			case MENU:
				game.getMenu().mouseClicked(e.getX(), e.getY());
				break;
			case PLAYING:
				game.getPlaying().mouseClicked(e.getX(), e.getY());
				break;
			case SETTINGS:
				game.getSettings().mouseClicked(e.getX(), e.getY());
				break;
			case GAMEOVER:
				game.getGameOver().mouseClicked(e.getX(), e.getY());
				break;
			case CUTSCENE:
				game.getCutscene().mouseClicked(e.getX(), e.getY());
				break;
			case VICTORY:
				game.getVictory().mouseClicked(e.getX(), e.getY());
				break;
			default:
				break;

			}

		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		switch (GameStates.gameState) {
		case MENU:
			game.getMenu().mousePressed(e.getX(), e.getY());
			break;
		case PLAYING:
			game.getPlaying().mousePressed(e.getX(), e.getY());
			break;
		case SETTINGS:
			game.getSettings().mousePressed(e.getX(), e.getY());
			break;
		case GAMEOVER:
			game.getGameOver().mousePressed(e.getX(), e.getY());
			break;
		case CUTSCENE:
			game.getCutscene().mousePressed(e.getX(), e.getY());
			break;
		case VICTORY:
			game.getVictory().mousePressed(e.getX(), e.getY());
			break;
		default:
			break;

		}

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		switch (GameStates.gameState) {
		case MENU:
			game.getMenu().mouseReleased(e.getX(), e.getY());
			break;
		case PLAYING:
			game.getPlaying().mouseReleased(e.getX(), e.getY());
			break;
		case SETTINGS:
			game.getSettings().mouseReleased(e.getX(), e.getY());
			break;
		case GAMEOVER:
			game.getGameOver().mouseReleased(e.getX(), e.getY());
			break;
		case CUTSCENE:
			game.getCutscene().mouseReleased(e.getX(), e.getY());
			break;
		case VICTORY:
			game.getVictory().mouseReleased(e.getX(), e.getY());
			break;
		default:
			break;

		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

}
