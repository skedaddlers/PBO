package managers;

import static helpers.Constants.Penjajahs.*;

import java.util.ArrayList;
import java.util.Arrays;

import events.Wave;
import scenes.Playing;

public class WaveManager {
	
	private Playing playing;
	private ArrayList<Wave> waves = new ArrayList<>();
	private int penjajahSpawnTickLimit = 70;
	private int penjajahSpawnTick = penjajahSpawnTickLimit; 
	private int waveTickLimit = 5 * 60;
	private int waveTick = 0;
	private int penjajahIndex, waveIndex;
	
	public WaveManager(Playing playing) {
		this.playing = playing;
		createWaves();
	}
	
	public void update() {
		if(penjajahSpawnTick < penjajahSpawnTickLimit)
			penjajahSpawnTick++;
	}
	
	public void increaseWaveIndex() {
		waveIndex++;
	}
	
	public int getNextPenjajah() {
		penjajahSpawnTick = 0;
		return waves.get(waveIndex).getPenjajahList().get(penjajahIndex++);
	}

	private void createWaves() {
		waves.add(new Wave(new ArrayList<Integer>(Arrays.asList(
				TENTARA, TENTARA, TENTARA, TENTARA))));
		waves.add(new Wave(new ArrayList<Integer>(Arrays.asList(
				TENTARA, TENTARA, TENTARA, TENTARA, TENTARA, TENTARA, TENTARA))));
		waves.add(new Wave(new ArrayList<Integer>(Arrays.asList(
				TENTARA, TENTARA, TENTARA, TENTARA, TENTARA, TANK, TENTARA, TENTARA, TENTARA))));
		waves.add(new Wave(new ArrayList<Integer>(Arrays.asList(
				LONDO_IRENG, TENTARA, TENTARA, TENTARA, TENTARA, LONDO_IRENG, TENTARA, TENTARA, LONDO_IRENG))));
		waves.add(new Wave(new ArrayList<Integer>(Arrays.asList(
				TANK, TENTARA, TANK, TENTARA, TANK, TENTARA, TANK, TENTARA, TENTARA, TENTARA))));
		waves.add(new Wave(new ArrayList<Integer>(Arrays.asList(
				LONDO_IRENG, LONDO_IRENG, LONDO_IRENG, LONDO_IRENG, LONDO_IRENG, LONDO_IRENG, LONDO_IRENG))));
		waves.add(new Wave(new ArrayList<Integer>(Arrays.asList(
				TANK, TANK, TENTARA, TENTARA, TANK, TENTARA, TENTARA, TENTARA, TENTARA, TENTARA, LONDO_IRENG))));
		waves.add(new Wave(new ArrayList<Integer>(Arrays.asList(
				TENTARA, TENTARA, TENTARA, TENTARA, TENTARA, TENTARA, TENTARA, TENTARA, TENTARA, TENTARA, TENTARA,
				TENTARA, TENTARA, TENTARA, TENTARA, TENTARA, TENTARA, TENTARA, TENTARA, TENTARA, TENTARA, TENTARA))));
		waves.add(new Wave(new ArrayList<Integer>(Arrays.asList(
				TENTARA, TANK, TANK, TANK, TANK, TANK, TENTARA, TENTARA, TENTARA))));
		waves.add(new Wave(new ArrayList<Integer>(Arrays.asList(
				LONDO_IRENG, LONDO_IRENG, TENTARA, TENTARA, LONDO_IRENG, LONDO_IRENG, TENTARA, TENTARA, TENTARA, 
				LONDO_IRENG, LONDO_IRENG, TENTARA, TENTARA, LONDO_IRENG, LONDO_IRENG, TENTARA, TENTARA, LONDO_IRENG, 
				LONDO_IRENG, TENTARA, TENTARA, LONDO_IRENG, LONDO_IRENG))));
		waves.add(new Wave(new ArrayList<Integer>(Arrays.asList(
				TENTARA, TANK, TANK, LONDO_IRENG, TANK, TANK, LONDO_IRENG, TENTARA, TENTARA, TANK, TANK, LONDO_IRENG,
				TANK, TANK, TENTARA, TANK, TANK, TANK, TANK, TANK, LONDO_IRENG, LONDO_IRENG, LONDO_IRENG, LONDO_IRENG, LONDO_IRENG))));

	}
	
	public ArrayList<Wave> getWaves(){
		return waves;
	}

	public boolean isTImeForSpawn() {
		return penjajahSpawnTick >= penjajahSpawnTickLimit;
	}
	
	public boolean isThereMorePenjajahInWave() {
		return penjajahIndex < waves.get(waveIndex).getPenjajahList().size();
	}

	public boolean isThereMoreWaves() {
		return waveIndex + 1 < waves.size();
	}

	public void resetPenjajahIndex() {
		penjajahIndex = 0;
	}
	
	public int getWaveIndex() {
		return waveIndex;
	}
	
	public float getTimeLeft() {
		float ticksLeft = waveTickLimit - waveTick;
		return ticksLeft / 60.0f;
		
	}

	public void reset() {
		waves.clear();
		createWaves();
		penjajahIndex = 0;
		waveIndex = 0;
		waveTick = 0;
		penjajahSpawnTick = penjajahSpawnTickLimit; 
	}



}
