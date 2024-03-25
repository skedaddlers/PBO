package objects;

import static helpers.Constants.Traps.LUMPUR;


public class Lumpur extends Trap{

	public float slow = 0.5f;
	
	public Lumpur(int x, int y, int id) {
		super(x, y, id, LUMPUR);
		// TODO Auto-generated constructor stub
	}
	
	public float getSlow() {
		return slow;
	}

}
