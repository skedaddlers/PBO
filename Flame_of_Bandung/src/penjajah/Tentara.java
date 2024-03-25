package penjajah;

import static helpers.Constants.Penjajahs.TENTARA;

import managers.PenjajahManager;

public class Tentara extends Penjajah {

	public Tentara(float x, float y, int id, PenjajahManager pm) {
		super(x, y, id, TENTARA, pm);
	}

}
