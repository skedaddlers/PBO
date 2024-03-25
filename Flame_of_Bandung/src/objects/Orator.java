package objects;

import static helpers.Constants.Pejuangs.ORATOR;

import helpers.Constants;

public class Orator extends Pejuang{
	
	private int bonusDamage = 0;
	private float bonusRange = 0;
	private float bonusAtkSpd = 1.0f;
	
	public Orator(int x, int y, int id) {
		super(x, y, id, ORATOR);
        bonusDamage = (int)Constants.Pejuangs.GetOratorDamageBuff();
        bonusRange = Constants.Pejuangs.GetOratorRangeBuff();	
    }

	public void applyBuff(Pejuang p) {
        p.setDamage(p.getDamage() * bonusDamage);
        p.setRange(p.getRange() * bonusRange);	
        p.setAtkSpd((int)(p.getAtkSpeed() * bonusAtkSpd));
	}
	
	public void upgradeOrator(int cost) {
		this.bonusDamage += (int)Constants.Pejuangs.GetOratorUpgradeDmgBuff(tier);
		this.bonusRange += Constants.Pejuangs.GetOratorUpgradeRangeBuff(tier);
		this.bonusAtkSpd -= Constants.Pejuangs.GetOratorUpgradeAtkSpdBuff(tier);
		tier++;
		moneySpent += cost;		
	}

	

}
