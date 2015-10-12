package de.hulaa.CustomMobs3;

public class CustomEntitySkeletonAttributeSetter {
	
	public static int setSkeletonHealth(int level) {

		if (level < 11) {
			return level + 4;
		} else if (level == 11) {
			return 16;
		} else if (level > 11) {

			double health = 0.0007D * ((Math.pow(level, 4))) - 0.0548D* (Math.pow(level, 3)) + 1.6334D * (Math.pow(level, 2))- 13.156D * (level) + 20.914D;
			int healthRounded = (int) Math.round(health);
			return healthRounded;

		}

		return 1;
	}


}
