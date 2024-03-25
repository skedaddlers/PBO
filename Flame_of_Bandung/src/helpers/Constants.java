package helpers;

import java.awt.Color;

public class Constants {
	
	public static class SoundIndex{
		public static final int MAIN = 0;
		public static final int CUTSCENES = 1;
		public static final int PLAY = 2;
		public static final int WIN = 3;
		public static final int LOST = 4;

	}
	public static class Sizes{
		public static final int GAME_WIDTH = 1210;
		public static final int GAME_HEIGHT = 720;
		public static final int TILE_SIZE = 40;
		public static final int ACTIONBAR_WIDTH = 250;
		public static final int ACTIONBAR_XSTART = 960;
	} 
	
	public static class Direction {
		public static final int LEFT = 0;
		public static final int UP = 1;
		public static final int RIGHT = 2;
		public static final int DOWN = 3;
	}
	
	public static class Traps{
		public static final int PAKU = 0;
		public static final int LUMPUR = 1;
		public static final int LUBANG = 2;
		
		public static String GetTrapName(int trapType) {
			switch(trapType) {
			case PAKU:
				return "Paku";
			case LUMPUR:
				return "Lumpur";
			case LUBANG:
				return "Lubang";
			}
			return "";
		}

		public static int GetTrapCost(int trapType) {
			switch(trapType) {
			case PAKU:
				return 125;
			case LUMPUR:
				return 60;
			case LUBANG:
				return 200;
			}
			return 0;
		}
		
		public static int GetDamage(int trapType) {
			switch(trapType) {
			case PAKU:
				return 50;
			case LUMPUR:
				return 0;
			case LUBANG:
				return 0;
			}
			return 0;
		}
		
		public static int GetUsage(int trapType) {
			switch(trapType) {
			case PAKU:
				return 5;
			case LUMPUR:
				return 10;
			case LUBANG:
				return 3;
			}
			return 0;
		}
	}
	
	public static class Penjajahs {
		public static final int TENTARA = 0;
		public static final int TANK = 1;
		public static final int LONDO_IRENG = 2;
		
		public static long GetLondoIrengInvisTime() {
			return 7000;
		}
		
		public static int GetMoneyForKill(int penjajahType) {
			switch (penjajahType) {
			case TENTARA:
				return 20;
			case TANK:
				return 50;
			case LONDO_IRENG:
				return 25;
			}
			return 0;
		}
		
		public static float GetSpeed(int penjajahType) {
			switch (penjajahType) {
			case TENTARA:
				return 1.6f;
			case TANK:
				return 0.5f;
			case LONDO_IRENG:
				return 2.5f;
			}
			return 0;
		}
		
		public static int GetStartHp(int penjajahType) {
			switch (penjajahType) {
			case TENTARA:
				return 300;
			case TANK:
				return 1500;
			case LONDO_IRENG:
				return 125;
			}
			return 0;
		}
		
		public static int GetLiveLost(int penjajahType) {
			switch (penjajahType) {
			case TENTARA:
				return 1;
			case TANK:
				return 5;
			case LONDO_IRENG:
				return 1;
			}
			return 0;
		}
	}
	
	public static class Colours{
		public static final Color DARK_YELLOW = new Color(255, 243, 67);
		public static final Color LIGHT_BLUE = new Color(96, 145, 231);
		public static final Color LIGHT_YELLOW = new Color(255, 255, 168);
	}
	
	public static class Pejuangs {
		public static final int PRAJURIT = 0;
		public static final int JAWARA = 1;
		public static final int ORATOR = 2;
		
		public static int GetPejuangCost(int pejuangType) {
			switch (pejuangType) {
			case PRAJURIT:
				return 300;
			case JAWARA:
				return 550;
			case ORATOR:
				return 700;
			}
			return 0;
		}
		
		public static String GetName(int pejuangType) {
			switch (pejuangType) {
			case PRAJURIT:
				return "Prajurit";
			case JAWARA:
				return "Jawara";
			case ORATOR:
				return "Orator";
			}
			return "";
		}
		
		public static int GetStartDmg(int pejuangType) {
			switch (pejuangType) {
			case PRAJURIT:
				return 40;
			case JAWARA:
				return 85;
			case ORATOR:
				return 0;
			}
			return 0;
		}
		
		public static float GetAtkSpd(int pejuangType) {
			switch (pejuangType) {
			case PRAJURIT:
				return 70;
			case JAWARA:
				return 100;
			case ORATOR:
				return 0;
			}
			return 0;
		}
		
		public static float GetDefaultRange(int pejuangType) {
			switch (pejuangType) {
			case PRAJURIT:
				return 130;
			case JAWARA:
				return 70;
			case ORATOR:
				return 100;
			}
			return 0;
		}
		
		public static int GetPejuangUpgradeCost(int pejuangType, int tier) {
			switch (tier) {
			case 0:
				switch (pejuangType) {
				case PRAJURIT:
					return 180;
				case JAWARA:
					return 240;
				case ORATOR:
					return 260;
				}
			case 1:
				switch (pejuangType) {
				case PRAJURIT:
					return 250;
				case JAWARA:
					return 300;
				case ORATOR:
					return 340;
				}
			}
			
			return 0;
		}
		
		public static int GetPejuangUpgradeDamage(int pejuangType, int tier) {
			switch (tier) {
			case 0:
				switch (pejuangType) {
				case PRAJURIT:
					return 15;
				case JAWARA:
					return 24;
				}
			case 1:
				switch (pejuangType) {
				case PRAJURIT:
					return 20;
				case JAWARA:
					return 30;

				}
			}
			
			return 0;
		}
		
		public static int GetPejuangUpgradeRange(int pejuangType, int tier) {
			switch (tier) {
			case 0:
				switch (pejuangType) {
				case PRAJURIT:
					return 10;
				case JAWARA:
					return 5;
				}
			case 1:
				switch (pejuangType) {
				case PRAJURIT:
					return 15;
				case JAWARA:
					return 7;

				}
			}
			
			return 0;
		}
		
		public static float GetOratorUpgradeDmgBuff(int tier) {
			switch(tier) {
			case 0:
				return 0.2f;
			case 1:
				return 0.15f;
			}
			return 0;
	    }

	    public static float GetOratorUpgradeRangeBuff(int tier) {
	    	switch(tier) {
			case 0:
				return 0.0005f;
			case 1:
				return 0.0025f;
			}
			return 0;	    	
	    }
	    
	    public static float GetOratorUpgradeAtkSpdBuff(int tier) {
	    	switch(tier) {
			case 1:
				return 0.1f;
			}
			return 0;	    	
	    }
		
	    public static float GetOratorDamageBuff() {
	    	return 1.2f;
	    }

	    public static float GetOratorRangeBuff() {
	    	return 1.2f;
	    }
	    
	    public static float GetJawaraAOE() {
	    	return 80;
	    }
	

	}
	
	public static class Projectiles{
		public static final int BULLET = 0;
		public static final int SWING = 1;

		
		public static float GetSpeed(int type) {
			switch(type) {
			case BULLET:
				return 40f;
			default:
				return 0f;
			}
		}
	}
	
	public static class Tiles{
		public static final int OTHER_TILE = 0;
		public static final int GRASS_TILE = 1;
		public static final int ROAD_TILE = 2;

	}
}
