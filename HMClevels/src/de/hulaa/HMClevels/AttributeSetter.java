package de.hulaa.HMClevels;

public class AttributeSetter {
	
	
	public static double setPlayerHealth(int level) {

//		if (level < 11) {
//			return level + 4;
//		} else if (level == 11) {
//			return 16;
//		} else if (level > 11) {
//
//			double health = 0.0007D * ((Math.pow(level, 4))) - 0.0548D* (Math.pow(level, 3)) + 1.6334D * (Math.pow(level, 2))- 13.156D * (level) + 20.914D;
//			int healthRounded = (int) Math.round(health);
//			return healthRounded;
//
//		}
		
		if (level > 0) {
			return (0.0061D * ((Math.pow(level, 3))) - 0.1166D* ((Math.pow(level, 2))) + 2.7317D * level + 1.3647D);
		} else {
			return 3.0D;
		}
		
		

	}

	public static double setPlayerAD(int level) {

		//double ad = (0.0008 * (Math.pow(level, 3)))- (0.0074D * (Math.pow(level, 2))) + (0.5532 * level)+ (0.8596D);
		double ad =	(2D*(Math.pow(10, -8))*(Math.pow(level, 5)))+(2*(Math.pow(10, -5))*(Math.pow(level, 4)))-(0.0007D*(Math.pow(level, 3)))+(0.0358D*(Math.pow(level, 2)))+(0.1317D*level)+(0.8057D);
		return ad;

	}

}
