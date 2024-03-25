package objects;

import static helpers.Constants.Pejuangs.*;
public abstract class Pejuang {
	private int x, y, id, pejuangType, cdTick, damage;
	private float range, atkSpeed;
	protected int moneySpent = 0;
	protected int tier = 0 ;
	private float speed;
	private boolean buffed = false;

	public Pejuang(int x, int y, int id, int pejuangType) {
		super();
		this.x = x;
		this.y = y;
		this.id = id;
		this.pejuangType = pejuangType;
		
		setDefaultDmg();
		setDefaultRange();
		setDefaultCd();
	}

	public void update() {
		cdTick++;
	}
	
	private void setDefaultDmg() {
		damage = helpers.Constants.Pejuangs.GetStartDmg(pejuangType);
	}
	
	private void setDefaultRange() {
		range = helpers.Constants.Pejuangs.GetDefaultRange(pejuangType);
	}
	
	private void setDefaultCd() {
		atkSpeed = helpers.Constants.Pejuangs.GetAtkSpd(pejuangType);
	}
	
	public int getTier() {
		return tier;
	}
	
	public boolean isOffCd() {
		return cdTick > atkSpeed;
	}
	
	public void resetCd() {
		cdTick = 0;
	}

	public void upgrade(int cost) {
		this.damage += helpers.Constants.Pejuangs.GetPejuangUpgradeDamage(pejuangType, tier);
		this.range += helpers.Constants.Pejuangs.GetPejuangUpgradeRange(pejuangType, tier);
		tier++;
		moneySpent += cost;
	}
	
	public int getMoneySpent() {
		return moneySpent;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getId() {
		return id;
	}

	public int getPejuangType() {
		return pejuangType;
	}

	public int getDamage() {
		return damage;
	}

	public float getRange() {
		return range;
	}
	
    public void setDamage(int dmg) {
        damage = dmg;
    }

    public void setRange(float rng) {
        range = rng;
    }

	public float getAtkSpeed() {
		return atkSpeed;
	}
	
	public boolean isBuffed() {
		return buffed;
	}
	
	public void resetBuff() {
		this.buffed = false;
	}

	public void buffApplied() {
		this.buffed  = true;
	}

	public void setAtkSpd(int i) {
		this.atkSpeed = i;
	}


}
