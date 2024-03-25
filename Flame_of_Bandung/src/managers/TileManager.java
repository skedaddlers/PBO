package managers;

import static helpers.Constants.Sizes.*;
import static helpers.Constants.Tiles.*;

import java.awt.image.BufferedImage;

import java.util.ArrayList;

import helpers.LoadImage;
import helpers.Utilz;
import objects.Tile;

public class TileManager {

	public Tile GRASS, WATER, ROAD, TREE, ROCK;
	private BufferedImage atlas;
	public ArrayList<Tile> tiles = new ArrayList<>();

	public TileManager() {

		loadAtalas();
		createTiles();

	}

	private void createTiles() {

		int id = 0;
		tiles.add(GRASS = new Tile(getSprite(2, 1), id++, GRASS_TILE));
		tiles.add(ROCK = new Tile(Utilz.buildImg(getImgs(2, 1, 8, 9)), id++, OTHER_TILE));
		tiles.add(ROAD = new Tile(getSprite(9, 1), id++, ROAD_TILE));
		tiles.add(TREE = new Tile(Utilz.buildImg(getImgs(2, 1, 14, 6)), id++, OTHER_TILE));
		tiles.add(ROCK = new Tile(Utilz.buildImg(getImgs(2, 1, 9, 9)), id++, OTHER_TILE));
		tiles.add(ROCK = new Tile(Utilz.buildImg(getImgs(2, 1, 10, 9)), id++, OTHER_TILE));		
//		tiles.add(ROAD = new Tile(getSprite(9, 1), id++, END_TILE));
//		tiles.add(ROAD = new Tile(getSprite(9, 1), id++, START_TILE));
	}

	private BufferedImage[] getImgs(int x1, int y1, int x2, int y2) {
		
		return new BufferedImage[] {getSprite(x1,y1), getSprite(x2,y2)};
	}
	private void loadAtalas() {

		atlas = LoadImage.getSpriteAtlas();

	}

	public Tile getTile(int id) {
		return tiles.get(id);
	}

	public BufferedImage getSprite(int id) {
		return tiles.get(id).getSprite();
	}

	private BufferedImage getSprite(int xCord, int yCord) {
		return atlas.getSubimage(xCord * TILE_SIZE, yCord * TILE_SIZE, TILE_SIZE, TILE_SIZE);
	}

}
