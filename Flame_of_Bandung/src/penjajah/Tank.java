package penjajah;

import static helpers.Constants.Penjajahs.TANK;

import managers.PenjajahManager;

public class Tank extends Penjajah{

	public Tank(float x, float y, int id, PenjajahManager pm) {
		super(x, y, id, TANK, pm);
	}

}
