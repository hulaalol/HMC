package de.hulaa.CustomMobs3;

import net.minecraft.server.v1_8_R3.DifficultyDamageScaler;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.EntityExperienceOrb;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.EntitySkeleton;
import net.minecraft.server.v1_8_R3.GenericAttributes;
import net.minecraft.server.v1_8_R3.World;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CustomEntitySkeleton extends EntitySkeleton {

	public int MobLevel=1;
	public ItemStack[] equipment= new ItemStack[5];
	public static double sharedHealthModifier=1.0D;
	public static double sharedAdModifier = 1.0D;
	private boolean isLevelScaled = false;
	
	
	public CustomEntitySkeleton(World world) {
		super(world);
		
		

		//int level = this.getLevel();
		
		//UNEQUIP BOW
		
		
		// EQUIPMENT PROCESS
				


		
		
		//Bukkit.getServer().broadcastMessage("[BETA] Skeleton spawn Level:"+this.getLevel()+" HP:"+this.getAttributeInstance(GenericAttributes.maxHealth).getValue());
		//Bukkit.getServer().broadcastMessage("[BETA] Skeleton spawn Level:"+this.getLevel()+" AD:"+this.getAttributeInstance(GenericAttributes.ATTACK_DAMAGE).getValue());
	
	}
	
	
	
	/* (non-Javadoc)
	 * @see net.minecraft.server.v1_8_R3.Entity#move(double, double, double)
	 */
	@Override
	public void move(double d0, double d1, double d2) {
		super.move(d0, d1, d2);
		
		if(!isLevelScaled){
			
		Location spawnLoc = new Location (this.world.getWorld(),this.locX, this.locY, this.locZ);
		
		Player [] players = Bukkit.getOnlinePlayers().toArray(new Player[Bukkit.getOnlinePlayers().size()]);
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
			MobLevel = AdditionalMobSpawner.getSpawnLevel(MobLevel);
		}else{
			MobLevel = AdditionalMobSpawner.getSpawnLevel(closestPlayer.getLevel());
		}
		
		this.changeAttributes();
	
		double health = this.getAttributeInstance(GenericAttributes.maxHealth).getValue();
		double ad =this.getAttributeInstance(GenericAttributes.ATTACK_DAMAGE).getValue();
		
		this.setCustomName("Skeleton <" + MobLevel + "> "+String.format("HP:%.0f AD:%.0f", health, ad));
		
		
		equipment = EquipmentGenerator.generateEquipment(MobLevel);
		
		this.setEquipment(0, null);

		for (int i = 0; i < 5; i++) {

			if (equipment[i] != null) {
				net.minecraft.server.v1_8_R3.ItemStack equip = CraftItemStack.asNMSCopy(equipment[i]);
				this.setEquipment(i, equip);
			}
		}
		
		EquipmentGenerator.adjustAD(this, equipment[0]);
		

		//super.dropEquipment(true, 1);

		isLevelScaled = true;
		
		
		Bukkit.getServer().broadcastMessage("[BETA] CreatureSpawnEvent Skeleton<"+MobLevel+">@"+closestPlayer.getDisplayName()+" Loc:"+Utils.announceLocation(spawnLoc));
		}
	}
	

//	@Override
//	  protected void a(DifficultyDamageScaler difficultydamagescaler)
//	  {
//	    super.a(difficultydamagescaler);
//	   this.setEquipment(0, null);
//	   
//	   net.minecraft.server.v1_8_R3.ItemStack weapon = CraftItemStack.asNMSCopy(equipment[0]);
//	   this.setEquipment(0, weapon);
//
//	  }
	
	
	
	@Override
	protected void initAttributes() {
		super.initAttributes();

		this.getAttributeInstance(GenericAttributes.maxHealth).setValue(20.0D);
		this.getAttributeInstance(GenericAttributes.ATTACK_DAMAGE).setValue(2.0D);
		this.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(0.2D);
		this.getAttributeInstance(GenericAttributes.FOLLOW_RANGE).setValue(16.0D);
		this.getAttributeInstance(GenericAttributes.c).setValue(0.5D);
		
		
	}
	
	public void changeAttributes() {
		
		//scale
		this.getAttributeInstance(GenericAttributes.maxHealth).setValue(sharedHealthModifier * GenericAttributesFunction.mobHealth(MobLevel));
		//heal
		this.setHealth((float) this.getAttributeInstance(GenericAttributes.maxHealth).getValue());
	
		this.getAttributeInstance(GenericAttributes.ATTACK_DAMAGE).setValue(sharedAdModifier * GenericAttributesFunction.mobAd(MobLevel));
		this.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(0.2D);
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
						Bukkit.getServer().broadcastMessage("[BETA] Skeleton xp orb ("+orbQuantity +") distribution");
						
						Entity orb = new CustomEntityExperienceOrb(this.world, this.MobLevel, playerLevel);
						AdditionalMobSpawner.spawnEntity(orb, CustomEntityExperienceOrb.orbLoc(loc,3));
						
					}
					
				}
				

			}

			this.die();

			// for (i = 0; i < 20; ++i) {
			// double d0 = this.random.nextGaussian() * 0.02D;
			// double d1 = this.random.nextGaussian() * 0.02D;
			// double d2 = this.random.nextGaussian() * 0.02D;
			//
			// this.world.addParticle("explode", this.locX + (double)
			// (this.random.nextFloat() * this.width * 2.0F) - (double)
			// this.width, this.locY + (double) (this.random.nextFloat() *
			// this.length), this.locZ + (double) (this.random.nextFloat() *
			// this.width * 2.0F) - (double) this.width, d0, d1, d2);
			// }
		}

	}
	
	
	public int getLevel() {
		Double level = ((this.getAttributeInstance(GenericAttributes.FOLLOW_RANGE).getValue()) - 16.0D);
		return level.intValue();
	}


}