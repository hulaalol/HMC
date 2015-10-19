package de.hulaa.CustomMobs3;

import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.EntityExperienceOrb;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.EntityZombie;
import net.minecraft.server.v1_8_R3.GenericAttributes;
import net.minecraft.server.v1_8_R3.World;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CustomEntityZombie extends EntityZombie {
	
	public int MobLevel;
	public ItemStack[] equipment= new ItemStack[5];
	public static double sharedHealthModifier=1.0D;
	public static double sharedAdModifier = 1.0D;
	
	
	public CustomEntityZombie(World world) {
		super(world);
		

		int level = this.getLevel();

		// EQUIPMENT PROCESS
		equipment = EquipmentGenerator.generateEquipment(level);
		
		for (int i = 0; i < 5; i++) {

			if (equipment[i] != null) {
				net.minecraft.server.v1_8_R3.ItemStack equip = CraftItemStack.asNMSCopy(equipment[i]);
				super.setEquipment(i, equip);
			}
		}
		
		EquipmentGenerator.adjustAD(this, equipment[0]);

		//super.dropEquipment(true, 1);
		
		double health = this.getAttributeInstance(GenericAttributes.maxHealth).getValue();
		double ad =this.getAttributeInstance(GenericAttributes.ATTACK_DAMAGE).getValue();
		
		this.setCustomName("Zombie <" + MobLevel + "> "+String.format("HP:%.0f AD:%.0f", health, ad));
	
	}
	
	@Override
	protected void initAttributes() {
		super.initAttributes();
		MobLevel = AdditionalMobSpawner.spawnLevel();

		this.getAttributeInstance(GenericAttributes.maxHealth).setValue(sharedHealthModifier * GenericAttributesFunction.mobHealth(MobLevel));
		this.getAttributeInstance(GenericAttributes.ATTACK_DAMAGE).setValue(sharedAdModifier * GenericAttributesFunction.mobAd(MobLevel));
		this.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(0.2D);
		this.getAttributeInstance(GenericAttributes.FOLLOW_RANGE).setValue(16.0D);
		this.getAttributeInstance(GenericAttributes.c).setValue(0.7D);

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