package de.hulaa.CustomMobs;

import de.hulaa.CustomMobs.GenericAttributesFunction;

public abstract class GenericAttributesFunction {
	
	
	public static int maxlevel = 100;
	public static double [] healthPoints = new double[maxlevel];
	public static double [] attackDamage = new double[maxlevel];
	public static double [] attackSpeed = new double[maxlevel];
	public static double [] armor = new double[maxlevel];
	
	static{																//when class is loaded, initialize stats till maxlevel
		for(int level=1; level<maxlevel; level++){
			healthPoints[level]=GenericAttributesFunction.mobHealth(level);
			attackDamage[level]=GenericAttributesFunction.mobAd(level);
		}
	}

	public static double mobHealth(int level){
		return (3D*(Math.pow(10, -6))*(Math.pow(level, 5)))+(0.0003D*(Math.pow(level, 4)))-(0.0088D*(Math.pow(level, 3)))+(0.7675D*(Math.pow(level, 2)))+(2.4377D*level)+(2.7618D);
	}
	
	public static double playerHealth(int level) {

		if (level >= 0) {
			return (0.0061D * ((Math.pow(level, 3))) - 0.1166D* ((Math.pow(level, 2))) + 2.7317D * level + 20.3647D);
		} else {
			return 3.0D;
		}

	}
	
	public static double mobAd(int mobLevel){
		
		double percentageModifier = 3.1078D*(Math.pow(Math.E, (0.0233*mobLevel)))/100D;
		return 2*percentageModifier*playerHealth(mobLevel);

	}
	
}
	


