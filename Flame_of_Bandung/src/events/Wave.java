package events;

import java.util.ArrayList;

public class Wave {
	
	private ArrayList<Integer> penjajahList;

	public Wave(ArrayList<Integer> penjajahList) {
		this.penjajahList = penjajahList;
	}
	
	public ArrayList<Integer> getPenjajahList(){
		return penjajahList;
	}
}
