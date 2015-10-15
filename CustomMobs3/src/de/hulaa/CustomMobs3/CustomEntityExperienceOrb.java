package de.hulaa.CustomMobs3;

import net.minecraft.server.v1_8_R3.EntityExperienceOrb;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.World;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class CustomEntityExperienceOrb extends EntityExperienceOrb{
	
	

	public CustomEntityExperienceOrb(World world) {
		super(world);
		Bukkit.getServer().broadcastMessage("[BETA] Custom XP Orb spawned!");
		// TODO Auto-generated constructor stub
	}
	
	
	@Override
    public void d(EntityHuman entityhuman) {
        if (!this.world.isClientSide) {
            if (this.c == 0 && entityhuman.bp == 0) {
                entityhuman.bp = 2;
                this.world.makeSound(entityhuman, "random.orb", 0.1F, 0.5F * ((this.random.nextFloat() - this.random.nextFloat()) * 0.7F + 1.8F));
                entityhuman.receive(this, 1);
                
                
                
                org.bukkit.entity.Player player = (Player) entityhuman.getBukkitEntity();
                int level = player.getLevel();
                
                if(level <50){
                
                float totalExpF = (float) entityhuman.expTotal;
                
                
                float mobExp = getOrbPercentageValue(level);

                player.setExp(entityhuman.exp + mobExp);

            	player.sendMessage("float: "+entityhuman.exp);
            	
            	
            	entityhuman.expTotal = Math.round(totalExpF);
            	player.sendMessage("total: "+entityhuman.expTotal);
            	
                
//                if(currentExp>=(float) entityhuman.getExpToLevel())
//                {
//                	entityhuman.expLevel++;
//                	entityhuman.exp = 0.0f;
//                }else{
//                	entityhuman.exp +=mobExp;  
//                	//   / (float) entityhuman.getExpToLevel();
//                	player.sendMessage(""+entityhuman.exp);
//                }
                
                
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


	public static float getOrbPercentageValue(int playerLevel) {
		// =(EXP(-0,061*level))*0,106
		Double value = (Math.pow(Math.E, ((-0.061D)*playerLevel)))*0.0106D;
		return value.floatValue();
	}
	
	public static int getOrbValue(int i){
		return 1;
	}
	
	
	
	

}
