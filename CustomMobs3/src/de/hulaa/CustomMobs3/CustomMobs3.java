package de.hulaa.CustomMobs3;

import net.minecraft.server.v1_8_R3.EntityLiving;
import net.minecraft.server.v1_8_R3.EntitySkeleton;
import net.minecraft.server.v1_8_R3.GenericAttributes;
import net.minecraft.server.v1_8_R3.World;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class CustomMobs3 extends JavaPlugin implements Listener {
	

	public void onEnable() {

		System.out.println("[HCM] Hulaa Custom Mobs 1.0 loaded");
		CustomEntityType.registerEntities();
		//spawnAdditionalMobs();
		

		// getServer().getPluginManager().registerEvents(this, this);
		// in Case there are some events added

	}

	public void onDisable() {
		CustomEntityType.unregisterEntities();
		System.out.println("[HCM] Unregistering Entities....");

	}

//	@EventHandler
//	public void EntityLostHealthEvent(EntityDamageEvent event) {
//
//		EntityLiving mob = (EntityLiving) event.getEntity();
//
//		if (!(mob instanceof Player)) {
//
//			if (mob.getCustomName() != null) {
//				
//				
//				double maxHealth = mob.getAttributeInstance(GenericAttributes.maxHealth).getValue();
//
//				int updateHealth = (int) Math.round(maxHealth-event.getDamage());
//				
//				
//				String oldCustomName = mob.getCustomName();
//				String[] oldCustomNameSplitted = oldCustomName.split(" "); // index 3
//
//				String newCustomName = "";
//				for (int i = 0; i < oldCustomNameSplitted.length; i++) {
//
//					if (i == 3) {
//						newCustomName += updateHealth + " ";
//					} else {
//						newCustomName += oldCustomNameSplitted[i] + " ";
//					}
//
//				}
//
//				mob.setCustomName(newCustomName);
//
//			}
//
//		}
//
//	}

	
	
	
	
	public void spawnAdditionalMobs() {

		new BukkitRunnable() {
			public void run() {

				Player [] players = Bukkit.getOnlinePlayers().toArray(new Player[Bukkit.getOnlinePlayers().size()]);
				boolean PlayersOnline = players.length > 0;

				if (PlayersOnline) {

					int PlayerIndex = Utils.randInt(0, players.length - 1);
					Location spawn = AdditionalMobSpawner.getSpawnLocation(players[PlayerIndex]);
					World world = ((CraftWorld) players[PlayerIndex].getWorld()).getHandle();

					if (spawn != null) {
						int PlayerLevel = players[PlayerIndex].getLevel();
						int spawnLevel = AdditionalMobSpawner.getSpawnLevel(PlayerLevel);


//						if (spawnLevel <= 2 && spawnLevel>0){
//
//							EntitySkeleton skelett = AdditionalMobSpawner.spawnSkeleton(world, spawnLevel);
//							skelett.setCustomName("CMob <"+spawnLevel+">");
//							AdditionalMobSpawner.spawnEntity(skelett, spawn);
//							Bukkit.getServer().broadcastMessage("[BETA] CustomSkeleton <"+spawnLevel+"> @"+Utils.announceLocation(spawn));
//
//
//						}else {

							EntityLiving skelett = new CustomEntitySkeleton(((CraftWorld) players[PlayerIndex].getWorld()).getHandle());
							AdditionalMobSpawner.spawnEntity(skelett, spawn);
							Bukkit.getServer().broadcastMessage("[BETA] "+ skelett.getCustomName()+" @"+Utils.announceLocation(spawn));

						//}
					}
				}
			}
		}.runTaskTimer(this, 240, 240);
	}
	
	
//	@EventHandler(priority = EventPriority.HIGH)
//	public void onEntitySpawn(CreatureSpawnEvent event){
//		Bukkit.getServer().broadcastMessage("[BETA] onEntitySpawn triggered");
//		
//		//SpawnReason reason = event.getSpawnReason();
//			
//		
//			Boolean isCustomSkeleton= event.getEntity() instanceof EntitySkeleton;
//			
//			if (isCustomSkeleton==true){
//			
//				Location SpawnLocation = event.getLocation();
//				Bukkit.getServer().broadcastMessage("[BETA] CreatureSpawnEvent modified! Location:" +SpawnLocation.getBlockX()+" / "+SpawnLocation.getBlockZ());
//				
//				
//				double distanceCandidate = 0D;
//				double smallestDistance = 10000000000000D;
//				Player nearestPlayer = null;
//				
//				for (Player player : Bukkit.getOnlinePlayers()){
//					Location PlayerLocation = player.getLocation();
//					distanceCandidate = SpawnLocation.distance(PlayerLocation);
//					
//					if (distanceCandidate < smallestDistance)
//					{
//						smallestDistance = distanceCandidate;
//						nearestPlayer = player;
//					}
//					
//				}
				
				
				
				
				//CustomEntitySkeleton skelett = new CustomEntitySkeleton(((CraftWorld) world).getHandle());
				//skelett.setSpawnLevel(nearestPlayer.getLevel());
	
	

	
	
//	if (spawnLevel == 1) {
//
//		EntityLiving skelett = new CustomEntitySkeleton1(((CraftWorld) players[PlayerIndex].getWorld()).getHandle());
//
//		skelett.setCustomName("ASpawn Skeleton <1>");
//
//		net.minecraft.server.v1_8_R3.ItemStack weapon = CraftItemStack.asNMSCopy(new ItemStack(Material.BOW, 1));
//
//		skelett.setEquipment(0, weapon);
//		CustomEntitySkeleton.spawnEntity(skelett, spawn);
//
//		Bukkit.getServer().broadcastMessage("[BETA] Additional LVL 1 Spawn @"+Utils.announceLocation(spawn));
//						
//
//	} else if (spawnLevel == 2) {
//
//		EntityLiving skelett = new CustomEntitySkeleton2(((CraftWorld) players[PlayerIndex].getWorld()).getHandle());
//
//		skelett.setCustomName("ASpawn Skeleton <2>");
//
//		CustomEntitySkeleton.spawnEntity(skelett, spawn);
//
//		Bukkit.getServer().broadcastMessage("[BETA] Additional LVL 2 Spawn @"+Utils.announceLocation(spawn));

//	} else {
//
//		EntityLiving skelett = new CustomEntitySkeleton(((CraftWorld) players[PlayerIndex].getWorld()).getHandle());
//
//		Utils.spawnEntity(skelett, spawn);
//
//		String CustomName = skelett.getCustomName();
//		skelett.setCustomName(CustomName + "A");
//
//		Bukkit.getServer().broadcastMessage("[BETA] Additional LVL X Spawn @"+Utils.announceLocation(spawn));
	

				

	}
	
	
	

