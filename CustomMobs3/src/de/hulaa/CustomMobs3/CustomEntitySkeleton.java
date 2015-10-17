package de.hulaa.CustomMobs3;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import net.minecraft.server.v1_8_R3.AttributeBase;
import net.minecraft.server.v1_8_R3.AttributeMapBase;
import net.minecraft.server.v1_8_R3.AttributeModifiable;
import net.minecraft.server.v1_8_R3.AttributeRanged;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.EntityExperienceOrb;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.EntityLiving;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.EntitySkeleton;
import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.GenericAttributes;
import net.minecraft.server.v1_8_R3.IAttribute;
import net.minecraft.server.v1_8_R3.World;

import java.lang.Integer;

public class CustomEntitySkeleton extends EntitySkeleton {
	
	public int MobLevel;

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.minecraft.server.v1_8_R3.EntityLiving#aZ()
	 */
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
						Bukkit.getServer().broadcastMessage("[BETA] Skeleton xp orb distribution");
						
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

	public CustomEntitySkeleton(World world) {
		super(world);

		int level = this.getLevel();

		// EQUIPMENT PROCESS

		ItemStack[] equipment = EquipmentGenerator.generateEquipment(level);
		for (int i = 0; i < 5; i++) {

			if (equipment[i] != null) {
				net.minecraft.server.v1_8_R3.ItemStack equip = CraftItemStack
						.asNMSCopy(equipment[i]);
				super.setEquipment(i, equip);
			}
		}

		super.dropEquipment(true, 1);
	
	}

	@Override
	protected void initAttributes() {

		super.initAttributes();

		int level = AdditionalMobSpawner.spawnLevel();
		MobLevel = level;

		this.setCustomName("Skeleton <" + level + ">");

//		double attackDamage = (2D + (0.5D * level));
//		double mvmnt = (0.1D * (level / 8) + 0.1D);
//		double followRange = (15D);
		double KnockbackResist = 0.1D + (level / 30);


		this.getAttributeInstance(GenericAttributes.maxHealth).setValue(calcMaxHealth(level));
		this.getAttributeInstance(GenericAttributes.ATTACK_DAMAGE).setValue(calcAD(level));
		this.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(0.2D);
		this.getAttributeInstance(GenericAttributes.FOLLOW_RANGE).setValue(16.0D + level);
		this.getAttributeInstance(GenericAttributes.c).setValue(KnockbackResist);
	}

	public int getLevel() {
		Double level = ((this.getAttributeInstance(GenericAttributes.FOLLOW_RANGE).getValue()) - 16.0D);
		return level.intValue();
	}
	
	
	public static double calcMaxHealth(int level) {

//		if (level < 11) {
//			return (double)(level + 4);
//		} else if (level == 11) {
//			return 16.0D;
//		} else if (level > 11) {
//
//			double health = 0.0007D * ((Math.pow(level, 4))) - 0.0548D* (Math.pow(level, 3)) + 1.6334D * (Math.pow(level, 2))- 13.156D * (level) + 20.914D;
			
		//= 3*(10^(-6))*(A4^5) + 0,0003*(A4^4) - 0,0088*(A4^3) + 0,7675*(A4^2) + 2,4377*(A4)+ 6,7618
			double health = (3D*(Math.pow(10, -6))*(Math.pow(level, 5)))+(0.0003D*(Math.pow(level, 4)))-(0.0088D*(Math.pow(level, 3)))+(0.7675D*(Math.pow(level, 2)))+(2.4377D*level)+(6.7618D);
		
		
			return health;

		}

	
	
	public static double calcAD (int level){
		
		double ad = 0.4977D * Math.pow(Math.E, (0.1161D*level));
		return ad;
		
	}
	
	


}