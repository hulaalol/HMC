package de.hulaa.CustomMobs;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import net.minecraft.server.v1_9_R1.Entity;
import net.minecraft.server.v1_9_R1.EntityExperienceOrb;
import net.minecraft.server.v1_9_R1.EntityHorse;
import net.minecraft.server.v1_9_R1.EntityPlayer;
import net.minecraft.server.v1_9_R1.EntityZombie;
import net.minecraft.server.v1_9_R1.EnumParticle;
import net.minecraft.server.v1_9_R1.GenericAttributes;
import net.minecraft.server.v1_9_R1.World;

public class NextanGuard extends EntityZombie {

	public static double healthModifier=1.0D;					// HP modifier
	public static double adModifier = 1.0D;						// AD modifier
	public static double XPModifier = 1.0D; //implement this!
	
	public int MobLevel=1;												// standard MobLevel, is set to nearest Player from SpawnPoint
	public ItemStack[] equipment= new ItemStack[6];						// equipment generated depending on level
	boolean isLevelScaled = false; 								// is set to true after level-scaling
	boolean isMounted = false;
	
	public EntityHorse mount;
	
	public NextanGuard(World world) {
		super(world);
	}
	
	public NextanGuard(World world, Integer level){
		super(world);
		this.MobLevel = level.intValue();
	}

	/* 
	 * When this is spawned and uses his move-Method the first time, he becomes level-scaled
	 * When done, isLevelScaled is set to true, preventing to spam the level-scaling-process
	 */
	@Override
	public void move(double d0, double d1, double d2) {
		super.move(d0, d1, d2);
		
		if(!isLevelScaled){
		isLevelScaled = true;
		this.persistent=true;
		
		ArrayList<Player> playerList = TimerTaskGetOnlinePlayers.players;
		
		if(TimerTaskGetOnlinePlayers.players.size()>0){
	
			Location spawnLoc = new Location(this.getWorld().getWorld(),this.locX,this.locY,this.locZ);
			Player closestPlayer=null;
			double smallestDistance= Double.MAX_VALUE;
			double distance = 0.0D;
			closestPlayer = playerList.get(0);
			
			for(Player p : playerList){
				distance = p.getLocation().distanceSquared(spawnLoc);
				
				if(distance<smallestDistance){
					smallestDistance = distance;
					closestPlayer = p;
			}
			}
			
			MobLevel = AdditionalMobSpawner.getSpawnLevel(closestPlayer.getLevel());

			//filter other Zombie Types
			this.setBaby(false);
			if(this.isVillager()){
				this.die();
			}else{
				//scale the stats
				this.scaleStats();

				//set the name tag
				double health = this.getAttributeInstance(GenericAttributes.maxHealth).getValue();
				double ad =this.getAttributeInstance(GenericAttributes.ATTACK_DAMAGE).getValue();
				this.setCustomName("Nextan Guard <" + this.MobLevel + "> "+String.format("HP:%.0f AD:%.0f", health, ad));
		
				//generate the Equipment
				this.equipment[0] = EquipmentDataBase.WEAPONS[17];
				
				//20% Chance to Dual-Wield - else --> Shield
				if(Math.random()>0.8){
					this.equipment[5] = 	EquipmentDataBase.WEAPONS[17];
				}else{
					this.equipment[5] = EquipmentDataBase.OFFHAND[0];
				}
				//all Gold-Armor
				this.equipment[1]=EquipmentDataBase.BOOTS[3];
				this.equipment[2]=EquipmentDataBase.LEGGINGS[3];
				this.equipment[3]=EquipmentDataBase.CHESTPLATE[3];
				this.equipment[4]=EquipmentDataBase.HELMET[3];

				//equip the Mob
				EquipmentGenerator.equipMob(this, this.equipment);		
				//adjust Mob-AD depending on Weapon received
				EquipmentGenerator.adjustAD(this, this.equipment[0]);
				
			}	
		}
		}
		
		
	}
	
	public void scaleStats() {
		//scale
		this.getAttributeInstance(GenericAttributes.maxHealth).setValue(healthModifier * GenericAttributesFunction.healthPoints[MobLevel]);
		//heal
		this.setHealth((float) (GenericAttributesFunction.healthPoints[MobLevel]));
	
		this.getAttributeInstance(GenericAttributes.ATTACK_DAMAGE).setValue(GenericAttributesFunction.attackDamage[MobLevel]);
		this.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(0.35D);
		this.getAttributeInstance(GenericAttributes.FOLLOW_RANGE).setValue(16.0D);
		this.getAttributeInstance(GenericAttributes.c).setValue(0.5D); //Knockback-Resistance?
	}
	


	@Override
	protected void bC() {

		++this.deathTicks;
		if (this.deathTicks == 20) {
			int i;

			if (!this.world.isClientSide
					&& (this.lastDamageByPlayerTime > 0 || this
							.alwaysGivesExp()) && this.bd()
					&& this.world.getGameRules().getBoolean("doMobLoot")) {
				i = this.getExpValue(this.killer);

				while (i > 0) {
					int j = EntityExperienceOrb.getOrbValue(i);

					i -= j;
					// this.world.addEntity(new EntityExperienceOrb(this.world,
					// this.locX, this.locY, this.locZ, j));
				}

				World world = this.getWorld();
				org.bukkit.World BukkitWorld = world.getWorld();
				
				if (this.killer instanceof EntityPlayer){

					Player player = (Player) this.killer.getBukkitEntity();
					int playerLevel = player.getLevel();
					
					Location loc = new Location(BukkitWorld, this.locX, this.locY,this.locZ);

					for (int orbQuantity = CustomEntityExperienceOrb.getOrbQuantity(playerLevel, MobLevel); orbQuantity > 0; orbQuantity--) {
						Bukkit.getServer().broadcastMessage("[BETA] Zombie xp orb ("+orbQuantity +") distribution");
						
						Entity orb = new CustomEntityExperienceOrb(this.world, this.MobLevel, playerLevel);
						AdditionalMobSpawner.spawnEntity(orb, CustomEntityExperienceOrb.orbLoc(loc,2));
						
					}
					
				}
				

			}

			this.die();

		      for (i = 0; i < 20; i++)
		      {
		        double d0 = this.random.nextGaussian() * 0.02D;
		        double d1 = this.random.nextGaussian() * 0.02D;
		        double d2 = this.random.nextGaussian() * 0.02D;
		        
		        this.world.addParticle(EnumParticle.EXPLOSION_NORMAL, this.locX + this.random.nextFloat() * this.width * 2.0F - this.width, this.locY + this.random.nextFloat() * this.length, this.locZ + this.random.nextFloat() * this.width * 2.0F - this.width, d0, d1, d2, new int[0]);
		      }
		}

	}

	public int getLevel() {
		return MobLevel;
	}


}