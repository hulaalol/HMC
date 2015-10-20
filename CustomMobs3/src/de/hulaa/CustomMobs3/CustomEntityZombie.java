package de.hulaa.CustomMobs3;

import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.EntityExperienceOrb;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.EntityZombie;
import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.GenericAttributes;
import net.minecraft.server.v1_8_R3.World;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CustomEntityZombie extends EntityZombie {

	public static double sharedHealthModifier=1.0D;						// HP modifier
	public static double sharedAdModifier = 1.0D;						// AD modifier
	private static double [][] stats = new double[75][2]; 				// [level][columns] col0=hp col1=ad
	
	public int MobLevel=1;												// standard MobLevel, is set to nearest Player from SpawnPoint
	public ItemStack[] equipment= new ItemStack[5];						// equipment generated depending on level
	
	
	private boolean isLevelScaled = false; 								// is set to true after level-scaling
	
	static{																//when class is loaded, initialize stats for 75 levels
		
		for(int i=1; i<stats.length; i++){	
			stats[i][0]=sharedHealthModifier*GenericAttributesFunction.mobHealth(i);
			stats[i][1]=sharedAdModifier*GenericAttributesFunction.mobAd(i);
			
		}
	}
	
	
	public CustomEntityZombie(World world) {
		super(world);
	}
	
	
	
	/* 
	 * When mob is spawned and uses his move-Method the first time, he becomes level-scaled
	 * When done, isLevelScaled is set to true, preventing to spam the level-scaling-process
	 */
	@Override
	public void move(double d0, double d1, double d2) {
		super.move(d0, d1, d2);
		
		if(!isLevelScaled){
		
		//get spawnLocation
		Location spawnLoc = new Location (this.world.getWorld(),this.locX, this.locY, this.locZ);
		
		//get Players Online
		Player [] players = Bukkit.getOnlinePlayers().toArray(new Player[Bukkit.getOnlinePlayers().size()]);
		
		
		//search for closest Player to the SpawnLocation
		Player closestPlayer=null;
		double smallestDistance=0.0D;
		for(int i=0; i<players.length; i++){

		double distance = players[i].getLocation().distanceSquared(spawnLoc);
		
		if(i==0){
		smallestDistance = distance;
		closestPlayer = players[0];
		}else{
			
			if (distance<smallestDistance){
				smallestDistance = distance;
				closestPlayer = players[i];
			}
		}
		}
		

		
		if(closestPlayer==null){
			//if no Player is found, spawn level 1 Mob
			MobLevel = AdditionalMobSpawner.getSpawnLevel(MobLevel);
		}else{
			//else calculate MobLevel depending on closestPlayer level
			MobLevel = AdditionalMobSpawner.getSpawnLevel(closestPlayer.getLevel());
		}

		//scale the stats
		this.scaleStats();
	
		//set the name tag
		double health = this.getAttributeInstance(GenericAttributes.maxHealth).getValue();
		double ad =this.getAttributeInstance(GenericAttributes.ATTACK_DAMAGE).getValue();
		this.setCustomName("Zombie <" + MobLevel + "> "+String.format("HP:%.0f AD:%.0f", health, ad));
		
		//generate the Equipment
		equipment = EquipmentGenerator.generateEquipment(MobLevel);
		//unequip the NMS-spawn Weapon
		this.setEquipment(0, null);
		//equip the Mob
		for (int i = 0; i < 5; i++) {

			if (equipment[i] != null) {
				net.minecraft.server.v1_8_R3.ItemStack equip = CraftItemStack.asNMSCopy(equipment[i]);
				this.setEquipment(i, equip);
			}
		}
		
		//adjust Mob-AD depending on Weapon received
		EquipmentGenerator.adjustAD(this, equipment[0]);

		isLevelScaled = true;
		
		//announce Spawn
		//Bukkit.getServer().broadcastMessage("[BETA] CreatureSpawnEvent Zombie<"+MobLevel+">@"+closestPlayer.getDisplayName()+" Loc:"+Utils.announceLocation(spawnLoc));
		}
	}

	@Override
	protected void initAttributes() {
		super.initAttributes();
	}
	
	public void scaleStats() {
		//scale
		this.getAttributeInstance(GenericAttributes.maxHealth).setValue(stats[MobLevel][0]);
		//heal
		this.setHealth((float) (stats[MobLevel][0]));
	
		this.getAttributeInstance(GenericAttributes.ATTACK_DAMAGE).setValue(stats[MobLevel][1]);
		this.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(0.35D);
		this.getAttributeInstance(GenericAttributes.FOLLOW_RANGE).setValue(16.0D);
		this.getAttributeInstance(GenericAttributes.c).setValue(0.5D);
	}
	


	@Override
	protected void aZ() {

		++this.deathTicks;
		if (this.deathTicks == 20) {
			int i;

			if (!this.world.isClientSide
					&& (this.lastDamageByPlayerTime > 0 || this
							.alwaysGivesExp()) && this.ba()
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