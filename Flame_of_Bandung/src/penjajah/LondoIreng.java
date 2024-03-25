package penjajah;

import static helpers.Constants.Penjajahs.LONDO_IRENG;

import managers.PenjajahManager;

public class LondoIreng extends Penjajah{
	
	private boolean invisible;
    private long invisibleStartTime;

	public LondoIreng(float x, float y, int id, PenjajahManager pm) {
		super(x, y, id, LONDO_IRENG, pm);
		invisible = true;
        invisibleStartTime = System.currentTimeMillis();
	}

	public boolean isInvisible() {
		return invisible;
	}

	public long getInvisibleStartTime() {
		return invisibleStartTime;
	}

	public void setVisible(boolean b) {
		invisible = false;
	}

}
