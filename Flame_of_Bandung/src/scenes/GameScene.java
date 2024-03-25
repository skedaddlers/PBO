package scenes;

import main.Game;

public class GameScene {

	protected Game game;
	protected int aniIndex;
	protected int ANI_SPEED = 25;
	protected int tick;

	public GameScene(Game game) {
		this.game = game;
	}

	public Game getGame() {
		return game;
	}

	protected void updateTick() {
		tick++;
		if (tick >= ANI_SPEED) {
			tick = 0;
			aniIndex++;
			if (aniIndex >= 4)
				aniIndex = 0;
		}
	}
}
