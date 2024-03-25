package main;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;

import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import inputs.KeyboardListener;
import inputs.MyMouseListener;
import managers.TileManager;
import scenes.Cutscene;
import scenes.GameOver;
import static helpers.Constants.SoundIndex.*;
import scenes.Menu;
import scenes.Playing;
import scenes.Settings;
import scenes.Victory;
import sound.Sound;


public class Game extends JFrame implements Runnable {

	private GameScreen gameScreen;
	private Thread gameThread;

	private final double FPS_SET = 120.0;
	private final double UPS_SET = 60.0;

	// Classes
	private Render render;
	private Menu menu;
	private Playing playing;
	private Settings settings;
	private GameOver gameOver;
	private Cutscene cutscene;
	private Victory victory;

	private TileManager tileManager;
	
	private Sound sound;

	public Game() {

		initClasses();

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				int choose = JOptionPane.showConfirmDialog(null,
				"Do you really want to exit Flame of Bandung?", "Closing Flame of Bandung", 
				JOptionPane.YES_NO_OPTION,
				JOptionPane.INFORMATION_MESSAGE);
				if (choose == JOptionPane.YES_OPTION) {
					e.getWindow().dispose();
					System.exit(1);
				} else {
					setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
				}
			 }
		 });
		
		sound = new Sound();
		playMusic(MAIN);
		setResizable(false);
		setTitle("Flame of Bandung");
		add(gameScreen);
		pack();
	    setLocationRelativeTo(null); // Center the JFrame
		setVisible(true);
		
		

	}


	public void playMusic(int i) {
		if(sound.getMusicPlayingNow() != i) {
			if(sound.isPlay())
				sound.stop();
			sound.setMusic(i);
			sound.play();
			sound.loop();
		}
	}


	private void initClasses() {
		tileManager = new TileManager();
		render = new Render(this);
		gameScreen = new GameScreen(this);
		menu = new Menu(this);
		playing = new Playing(this);
		settings = new Settings(this);
		gameOver = new GameOver(this);
		cutscene = new Cutscene(this);
		victory = new Victory(this);
	}

	private void start() {
		gameThread = new Thread(this) {
		};

		gameThread.start();
	}

	private void updateGame() {
		switch (GameStates.gameState) {
		case MENU:
			break;
		case PLAYING:
			playing.update();
			break;
		case SETTINGS:
			break;
		case CUTSCENE:
			break;
		default:
			break;
		
		}
	}

	public static void main(String[] args) {

		Game game = new Game();
		game.gameScreen.initInputs();
		game.start();

	}

	@Override
	public void run() {

		double timePerFrame = 1000000000.0 / FPS_SET;
		double timePerUpdate = 1000000000.0 / UPS_SET;

		long lastFrame = System.nanoTime();
		long lastUpdate = System.nanoTime();
		long lastTimeCheck = System.currentTimeMillis();

		int frames = 0;
		int updates = 0;

		long now;

		while (true) {
			now = System.nanoTime();

			// Render
			if (now - lastFrame >= timePerFrame) {
				repaint();
				lastFrame = now;
				frames++;
			}

			// Update
			if (now - lastUpdate >= timePerUpdate) {
				updateGame();
				lastUpdate = now;
				updates++;
			}

			if (System.currentTimeMillis() - lastTimeCheck >= 1000) {
				System.out.println("FPS: " + frames + " | UPS: " + updates);
				frames = 0;
				updates = 0;
				lastTimeCheck = System.currentTimeMillis();
			}

		}

	}

	// Getters and setters
	public Render getRender() {
		return render;
	}

	public Menu getMenu() {
		return menu;
	}

	public Playing getPlaying() {
		return playing;
	}

	public Settings getSettings() {
		return settings;
	}
	
	public Cutscene getCutscene() {
		return cutscene;
	}

	public GameOver getGameOver() {
		return gameOver;
	}
	
	public Victory getVictory() {
		return victory;
	}
	
	public TileManager getTileManager() {
		return tileManager;
	}


	public Sound getSound() {
		return sound;
	}

}