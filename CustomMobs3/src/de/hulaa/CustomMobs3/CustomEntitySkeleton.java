package de.hulaa.CustomMobs3;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.EntityExperienceOrb;
import net.minecraft.server.v1_8_R3.EntitySkeleton;
import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.GenericAttributes;
import net.minecraft.server.v1_8_R3.World;

import java.lang.Integer;

public class CustomEntitySkeleton extends EntitySkeleton {

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
				Location loc = new Location(BukkitWorld, this.locX, this.locY,
						this.locZ);

				for (int numberOfOrbs = 10; numberOfOrbs > 0; numberOfOrbs--) {
					Bukkit.getServer().broadcastMessage(
							"[BETA] Skeleton xp orb distribution");
					Entity orb = new CustomEntityExperienceOrb(this.world);
					AdditionalMobSpawner.spawnEntity(orb, loc);
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

		this.setCustomName("Skeleton <" + level + ">");

		double attackDamage = (2D + (0.5D * level));
		double maxHealth = CustomEntitySkeletonAttributeSetter
				.setSkeletonHealth(level);
		double mvmnt = (0.1D * (level / 8) + 0.1D);
		double followRange = (15D);
		double KnockbackResist = 0.1D + (level / 30);

		this.getAttributeInstance(GenericAttributes.maxHealth).setValue(
				maxHealth);
		this.getAttributeInstance(GenericAttributes.ATTACK_DAMAGE).setValue(
				attackDamage);
		this.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(
				0.2D);
		this.getAttributeInstance(GenericAttributes.FOLLOW_RANGE).setValue(
				16.0D + level);
		this.getAttributeInstance(GenericAttributes.c)
				.setValue(KnockbackResist);
	}

	public int getLevel() {
		Double level = ((this
				.getAttributeInstance(GenericAttributes.FOLLOW_RANGE)
				.getValue()) - 16.0D);
		return level.intValue();
	}

}