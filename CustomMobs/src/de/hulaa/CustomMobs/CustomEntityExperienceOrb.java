package de.hulaa.CustomMobs;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import net.minecraft.server.v1_9_R1.EntityExperienceOrb;
import net.minecraft.server.v1_9_R1.EntityHuman;
import net.minecraft.server.v1_9_R1.SoundCategory;
import net.minecraft.server.v1_9_R1.SoundEffects;
import net.minecraft.server.v1_9_R1.World;

public class CustomEntityExperienceOrb extends EntityExperienceOrb{
	
	private int mobLevel;
	private int killerLevel;
	private int killerCollectorDifference;
	
	private static double[] XPvalue = new double[100];
	static{
		for(int level=0; level<100; level++){
			XPvalue[level]= Math.pow(Math.E, ((-0.061D)*level))*0.0106D;
		}
	}
	

	public CustomEntityExperienceOrb(World world) {
		super(world);
		//Bukkit.getServer().broadcastMessage("[BETA] Custom XP Orb spawned!");
		
	}
	
	public CustomEntityExperienceOrb(World world, int mobLevel){
		super(world);
		this.mobLevel = mobLevel;
	}
	
	public CustomEntityExperienceOrb(World world, int mobLevel, int killerLevel)
	{
		super(world);
		this.mobLevel = mobLevel; 
		this.killerLevel = killerLevel;
		this.killerCollectorDifference = 0;
	}
	
	
	@Override
    public void d(EntityHuman entityhuman) {
        if (!this.world.isClientSide) {
            if (this.c == 0 && entityhuman.by == 0) { //bp in 1.8 updated
                entityhuman.by = 2;
                this.world.a(null, entityhuman.locX, entityhuman.locY, entityhuman.locZ, SoundEffects.bi, SoundCategory.PLAYERS, 0.1F, 0.5F * ((this.random.nextFloat() - this.random.nextFloat()) * 0.7F + 1.8F));
                entityhuman.receive(this, 1);
                
                
                
                org.bukkit.entity.Player collectingPlayer = (Player) entityhuman.getBukkitEntity();
                int collectingPlayerLevel = collectingPlayer.getLevel();
                
                this.killerCollectorDifference = (this.killerLevel - collectingPlayerLevel);
                
                if(collectingPlayerLevel <50 && (killerCollectorDifference<10)){
                
                float totalExpF = (float) entityhuman.expTotal;
                float mobExp = getOrbPercentageValue(collectingPlayerLevel, this.mobLevel);

                collectingPlayer.setExp(entityhuman.exp + mobExp);
                
                if(mobExp!=0.0F){
                collectingPlayer.sendMessage("§a+"+ String.format("%.8f", (mobExp*100)) +"% XP");
                }
            	

            	entityhuman.expTotal = Math.round(totalExpF);

                for(totalExpF+=mobExp; entityhuman.exp>=1.0F; entityhuman.exp/=(float)entityhuman.getExpToLevel()){
                	entityhuman.exp = (entityhuman.exp - 1.0F) * (float) entityhuman.getExpToLevel();
                	entityhuman.expTotal = Math.round(totalExpF);
                	entityhuman.levelDown(1);
                }

                }
  
                	this.die();	
            }
        }
    }
	
	public static int getOrbQuantity(int playerLevel, int mobLevel){
		
		int difference = playerLevel-mobLevel;
		double dice = Math.random();
		
		
		switch(difference){
		
		case -10:
		case -9:
		case -8:
		return 12;
		
		case -7:
		return 10;
		
				
		case -6:
		if (dice > 0.2D){
			return 9;
		}else{
			return 8;
		}
			
		case -5:
		if(dice > 0.5D){
			return 8;
		}else{
			return 7;
		}
		
		case -4:
		if(dice > 0.7D){
			return 7;
		}else{
			return 6;
		}
		
		case -3:
		if(dice> 0.8D){
			return 6;
		}else{
			return 5;
		}
		
		default:
		return 5;
		
		}
		
	}


	public static float getOrbPercentageValue(int playerLevel, int mobLevel) {

		float fvalueXP = Double.valueOf(XPvalue[playerLevel]).floatValue();
		
		int difference = playerLevel-mobLevel;
		
		if(difference<=0){   //same level or higher
			
			switch(difference){
			
			case 0:
			return fvalueXP;
			case -1:
			return (fvalueXP*1.05F);
			case -2:
			return (fvalueXP*1.15F);
			case -3:
			return (fvalueXP*1.30F);
			case -4:
			return (fvalueXP*1.45F);
			case -5:
			return (fvalueXP*1.60F);
			case -6:
			return (fvalueXP*1.70F);
			case -7:
			return (fvalueXP*1.85F);
			case -8:
			return (fvalueXP*1.95F);
			default:
			return (fvalueXP*2.0F);
			}
			
		}else{ //lower level
			
			switch(difference){
			case 4:
			return (fvalueXP*0.4F);
			case 3:
			return (fvalueXP*0.6F);
			case 2:
			return (fvalueXP*0.8F);
			case 1:
			return (fvalueXP*0.9F);
			default:
			return 0.0F;
			}
			
		}

	}
	
	public static int getOrbValue(int i){
		return 1;
	}
	
	public static Location orbLoc(Location loc, int splashRadius){
		
		double[] locXZ ={loc.getX(), loc.getZ()};

		for(int i=0; i<2; i++){
		int sign = Utils.randInt(1, 2);
		locXZ[i] += (Math.pow(-1, sign))*(splashRadius*Math.random());
		}
		
		loc.setX(locXZ[0]);
		loc.setZ(locXZ[1]);
		loc.setY(loc.getY()+(Math.random()*2));

		return loc;
	}
	
	
	
	

}
