package de.hulaa.CustomMobs3;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_9_R1.entity.CraftPlayer;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.inventory.HorseInventory;

import net.minecraft.server.v1_9_R1.Entity;
import net.minecraft.server.v1_9_R1.EntityExperienceOrb;
import net.minecraft.server.v1_9_R1.EntityHorse;
import net.minecraft.server.v1_9_R1.EntityMonster;
import net.minecraft.server.v1_9_R1.EntityPlayer;
import net.minecraft.server.v1_9_R1.EntityZombie;
import net.minecraft.server.v1_9_R1.EnumHorseType;
import net.minecraft.server.v1_9_R1.EnumParticle;
import net.minecraft.server.v1_9_R1.GenericAttributes;
import net.minecraft.server.v1_9_R1.PacketPlayOutMount;
import net.minecraft.server.v1_9_R1.World;

public class NextanHorse extends EntityHorse {

	public static double healthModifier=0.3D;					// HP modifier
	public static double XPModifier = 0.3D; 					//implement this!
	
	public int MobLevel=1;												// standard MobLevel, is set to nearest Player from SpawnPoint
	private boolean isLevelScaled = false; 								// is set to true after level-scaling
	private boolean riderDead = false;
	
	
	public EntityMonster rider;
	
	public NextanHorse(World world) {
		super(world);
	}
	
	public NextanHorse(World world, Integer level){
		super(world);
		this.MobLevel = level.intValue();
	}
	
	/* 
	 * When mob is spawned and uses his move-Method the first time, he becomes level-scaled
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
			
			//scale the stats
			this.setType(EnumHorseType.ZOMBIE);
			this.scaleStats();

			//set the name tag
			double health = this.getAttributeInstance(GenericAttributes.maxHealth).getValue();
			this.setCustomName("Nextan Horse <" + MobLevel + "> "+String.format("HP:%.0f", health));

			Horse horse = (Horse) this.getBukkitEntity();
			horse.setAdult();
			HorseInventory inventory = horse.getInventory();
			inventory.setSaddle(EquipmentDataBase.HORSE_EQUIP[0]);
			
		}
		}else{
			if((!(riderDead)) && this.getBukkitEntity().isEmpty()){
				
				riderDead=true;
				
				if(this.rider.killer instanceof EntityPlayer){
//					CraftPlayer player = (CraftPlayer) this.rider.killer.getBukkitEntity();
//					player.playSound(player.getLocation(),Sound.BLOCK_NOTE_BASS , 10F, 10F);
				}else{
					this.die();
					
				}
			}else{
				PacketPlayOutMount packet = new PacketPlayOutMount(this);
				for(Player p: TimerTaskGetOnlinePlayers.players){
					((CraftPlayer)p).getHandle().playerConnection.sendPacket(packet);
				}
			}
		}

}
	

	@Override
	protected void initAttributes() {
		super.initAttributes();
	}
	
	public void scaleStats() {
		//scale
		this.getAttributeInstance(GenericAttributes.maxHealth).setValue(healthModifier * GenericAttributesFunction.healthPoints[MobLevel]);
		//heal
		this.setHealth((float) (GenericAttributesFunction.healthPoints[MobLevel]));
	
		this.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(0.35D);
		this.getAttributeInstance(GenericAttributes.FOLLOW_RANGE).setValue(16.0D);
		this.getAttributeInstance(GenericAttributes.c).setValue(1.0D); //Knockback-Resistance?
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
