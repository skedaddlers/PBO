package objects;

public abstract class Trap {

	private int x, y, id;
	private int damage;
	private int usage;
	private int trapType;
	private boolean active = true;
	private int cooldown = 20;
	private int intervalTick = 0;
	
	public Trap(int x, int y,int id, int type) {
		this.x = x;
		this.y = y;
		this.id = id;
		this.trapType = type;
		
		setDefaultUsage();
		setDefaultDamage();
		
	}

	private void setDefaultDamage() {
		damage = helpers.Constants.Traps.GetDamage(trapType);
	}
	

	private void setDefaultUsage() {
		usage = helpers.Constants.Traps.GetUsage(trapType);
	}

	public void update() {
		intervalTick++;
	}

	public boolean isOffCd() {
		return intervalTick > cooldown;
	}
	
	public void resetTick() {
		intervalTick = 0;
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

	public int getDamage() {
		return damage;
	}

	public int getUsage() {
		return usage;
	}

	public int getTrapType() {
		return trapType;
	}

	public void setTrapType(int trapType) {
		this.trapType = trapType;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public void updateUsage() {
		usage--;
		if(usage == 0) {
			active = false;
		}
	}


}
