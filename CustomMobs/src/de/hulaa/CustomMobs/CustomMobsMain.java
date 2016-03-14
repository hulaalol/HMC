package de.hulaa.CustomMobs;

import net.minecraft.server.v1_9_R1.EntityLiving;
import net.minecraft.server.v1_9_R1.EntitySkeleton;
import net.minecraft.server.v1_9_R1.GenericAttributes;
import net.minecraft.server.v1_9_R1.World;

import java.lang.reflect.Constructor;
import java.util.Timer;
import java.util.TimerTask;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_9_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_9_R1.entity.CraftEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;



public class CustomMobsMain extends JavaPlugin implements Listener {
	

	public void onEnable() {

		System.out.println("[HCM] Hulaa Custom Mobs 1.0 loaded");
		CustomEntityType.registerEntities();
		//spawnAdditionalMobs();


		Timer getOnlinePlayers = new Timer();
		getOnlinePlayers.schedule(new TimerTaskGetOnlinePlayers(), (long) 0, (long) 30000);

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
	


	
	public boolean onCommand(CommandSender sender, Command cmd, String cmdlabel, String[] args) {
		Player player = (Player) sender;



		if (cmd.getName().equalsIgnoreCase("cspawn")) {

			
			String requestedMob = args[0].toLowerCase();
			Location loc = player.getLocation();
			Location spawnloc = new Location(player.getWorld(),loc.getX(),loc.getY(),(loc.getZ()+10));
			
			String entity = null;
			
			switch(requestedMob){
			
			case "customzombie":
			CustomEntityZombie customzombie = new CustomEntityZombie(((CraftWorld) player.getWorld()).getHandle());
			entity="customzombie";
			AdditionalMobSpawner.spawnEntity(customzombie,loc);
			break;
			
			case "customskeleton":
			CustomEntitySkeleton customskeleton = new CustomEntitySkeleton(((CraftWorld) player.getWorld()).getHandle());
			entity="customskeleton";
			AdditionalMobSpawner.spawnEntity(customskeleton,loc);
			break;
			
			case "nextanguard":
			NextanGuard nextanguard = new NextanGuard(((CraftWorld) player.getWorld()).getHandle());
			entity="nextanguard";
			AdditionalMobSpawner.spawnEntity(nextanguard,loc);
			break;
			
			case "mountnextanguard":
			NextanHorse horse = new NextanHorse(((CraftWorld) player.getWorld()).getHandle());
			AdditionalMobSpawner.spawnEntity(horse,loc);
			NextanGuard nextanguardrider = new NextanGuard(((CraftWorld) player.getWorld()).getHandle());
			AdditionalMobSpawner.spawnEntity(nextanguardrider,loc);
			
			horse.rider = nextanguardrider;
			nextanguardrider.mount = horse;
			nextanguardrider.isMounted = true;
			
			nextanguardrider.startRiding(horse);
			entity="mountednextanguard";
			default:
			//do nothing;

			}
			
			if(entity!=null){
				player.sendMessage("§2[HMC] §3"+entity+ " spawned! ");
				return true;	
			}
			
			
		}
		
		return true;
		
	}
	
	
	
	
}
	
	
	

