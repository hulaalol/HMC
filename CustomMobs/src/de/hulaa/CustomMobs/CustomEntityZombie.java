package de.hulaa.CustomMobs;

import net.minecraft.server.v1_9_R1.Entity;
import net.minecraft.server.v1_9_R1.EntityExperienceOrb;
import net.minecraft.server.v1_9_R1.EntityPlayer;
import net.minecraft.server.v1_9_R1.EntityZombie;
import net.minecraft.server.v1_9_R1.EnumItemSlot;
import net.minecraft.server.v1_9_R1.EnumParticle;
import net.minecraft.server.v1_9_R1.GenericAttributes;
import net.minecraft.server.v1_9_R1.World;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_9_R1.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CustomEntityZombie extends EntityZombie {

	public static double healthModifier=1.0D;					// HP modifier
	public static double adModifier = 1.0D;						// AD modifier
	public static double XPModifier = 1.0D; //implement this!
	
	public int MobLevel=1;												// standard MobLevel, is set to nearest Player from SpawnPoint
	public ItemStack[] equipment= new ItemStack[6];						// equipment generated depending on level
	
	private boolean isLevelScaled = false; 								// is set to true after level-scaling
	
	
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
			
			org.bukkit.World BukkitWorld = this.getWorld().getWorld();
			
			if(BukkitWorld.getEnvironment().equals(org.bukkit.World.Environment.NORMAL)){
				
				//get spawnLocation
				Location spawnLoc = new Location (this.world.getWorld(),this.locX, this.locY, this.locZ);
				
				//get Players Online
				Player [] players = Bukkit.getOnlinePlayers().toArray(new Player[Bukkit.getOnlinePlayers().size()]);
				List<Player> playersList = new ArrayList<Player>(Arrays.asList(players)); 
				List<Player> filteredList = new ArrayList<Player>(); 
				
				//search for closest Player to the SpawnLocation
				Player closestPlayer=null;
				double smallestDistance= Double.MAX_VALUE;
				double distance = 0.0D;
				closestPlayer = players[0];
				
				//measure only to players that are in NORMAL World
				playersList.stream()
				.filter(p -> p.getWorld().getEnvironment().equals(org.bukkit.World.Environment.NORMAL))
				.forEach(p -> {filteredList.add(p);});
					
				for(Player p : filteredList){
					distance = p.getLocation().distanceSquared(spawnLoc);
					
					if(distance<smallestDistance){
						smallestDistance = distance;
						closestPlayer = p;
					}


			if(closestPlayer==null){
				//if no Player is found, spawn level 1 Mob
				MobLevel = AdditionalMobSpawner.getSpawnLevel(MobLevel);
			}else{
				//else calculate MobLevel depending on closestPlayer level
				MobLevel = AdditionalMobSpawner.getSpawnLevel(closestPlayer.getLevel());
			}
				
			}
		
			

		//scale the stats
		this.scaleStats();
	
		//set the name tag
		double health = this.getAttributeInstance(GenericAttributes.maxHealth).getValue();
		double ad =this.getAttributeInstance(GenericAttributes.ATTACK_DAMAGE).getValue();
		this.setCustomName("Zombie <" + MobLevel + "> "+String.format("HP:%.0f AD:%.0f", health, ad));
		
		//generate the Equipment
		equipment = EquipmentGenerator.generateEquipment(MobLevel);
		//equip the Mob
		EquipmentGenerator.equipMob(this, equipment);
		//adjust Mob-AD depending on Weapon received
		EquipmentGenerator.adjustAD(this, equipment[0]);
		
		//announce Spawn
		isLevelScaled = true;
		//Bukkit.getServer().broadcastMessage("[BETA] CreatureSpawnEvent Zombie<"+MobLevel+">@"+closestPlayer.getDisplayName()+" Loc:"+Utils.announceLocation(spawnLoc));
		}}
	}

	public void scaleStats() {
		//scale
		this.getAttributeInstance(GenericAttributes.maxHealth).setValue(healthModifier*GenericAttributesFunction.healthPoints[MobLevel]);
		//heal
		this.setHealth((float) (GenericAttributesFunction.healthPoints[MobLevel]));
	
		this.getAttributeInstance(GenericAttributes.ATTACK_DAMAGE).setValue(adModifier*GenericAttributesFunction.attackDamage[MobLevel]);
		
		this.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(0.3D);
		this.getAttributeInstance(GenericAttributes.FOLLOW_RANGE).setValue(32.0D);
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

				//while loop not needed?
				while (i > 0) {
					int j = EntityExperienceOrb.getOrbValue(i);
					i -= j;
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