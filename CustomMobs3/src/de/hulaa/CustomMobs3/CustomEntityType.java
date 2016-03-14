package de.hulaa.CustomMobs3;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.bukkit.entity.EntityType;

import net.minecraft.server.v1_9_R1.BiomeBase;
import net.minecraft.server.v1_9_R1.EntityInsentient;
import net.minecraft.server.v1_9_R1.EntityPig;
import net.minecraft.server.v1_9_R1.EntitySheep;
import net.minecraft.server.v1_9_R1.EntitySkeleton;
import net.minecraft.server.v1_9_R1.EntitySlime;
import net.minecraft.server.v1_9_R1.EntitySpider;
import net.minecraft.server.v1_9_R1.EntitySquid;
import net.minecraft.server.v1_9_R1.EntityTypes;
import net.minecraft.server.v1_9_R1.EntityWitch;
import net.minecraft.server.v1_9_R1.BiomeBase.BiomeMeta;
import net.minecraft.server.v1_9_R1.Biomes;
import net.minecraft.server.v1_9_R1.EntityBat;
import net.minecraft.server.v1_9_R1.EntityChicken;
import net.minecraft.server.v1_9_R1.EntityCow;
import net.minecraft.server.v1_9_R1.EntityCreeper;
import net.minecraft.server.v1_9_R1.EntityEnderman;
import net.minecraft.server.v1_9_R1.EntityHorse;
import net.minecraft.server.v1_9_R1.EntityZombie;
import net.minecraft.server.v1_9_R1.EnumCreatureType;


	public enum CustomEntityType {
		//Temporary, cause else it will throw some errors, you will see for yourself if u remove it :p
		CUSTOMSKELETON("Skeleton",51,EntityType.SKELETON,EntitySkeleton.class,CustomEntitySkeleton.class),
		CUSTOMZOMBIE("Zombie",54,EntityType.ZOMBIE,EntityZombie.class,CustomEntityZombie.class),
		NEXTANGUARD("Nextan-Guard",54,EntityType.ZOMBIE, EntityZombie.class, NextanGuard.class),
		NEXTANHORSE("Nextan-Horse",100,EntityType.HORSE,EntityHorse.class,NextanHorse.class);
		//CUSTOMSKELETON1("Skeleton1",51,EntityType.SKELETON,EntitySkeleton.class,CustomEntitySkeleton1.class),
		//CUSTOMSKELETON2("Skeleton2",51,EntityType.SKELETON,EntitySkeleton.class,CustomEntitySkeleton2.class);
		
		
		private String name;
		private int id;
		private EntityType entityType;
		private Class<? extends EntityInsentient> nmsClass;
		private Class<? extends EntityInsentient> customClass;
		private static ArrayList<BiomeBase> biomes = new ArrayList<>();
		
		
		
		private CustomEntityType(/*The name of the NMS entity*/String name, /*The entity id*/int id, /*The entity type*/EntityType entityType, /*The origional NMS class*/ Class<? extends EntityInsentient> nmsClass, /*The custom class*/ Class<? extends EntityInsentient> customClass) {
		this.name = name;
		this.id = id;
		this.entityType = entityType;
		this.nmsClass = nmsClass;
		this.customClass = customClass;
		}
		 
		public String getName() {
		return name;
		}
		 
		public int getID() {
		return id;
		}
		 
		public EntityType getEntityType() {
		return entityType;
		}
		 
		public Class<? extends EntityInsentient> getNMSClass() {
		return nmsClass;
		}
		 
		public Class<? extends EntityInsentient> getCustomClass() {
		return customClass;
		}
		
		
		public static void registerEntities(){
			
			//Register all entities in Server
			for (CustomEntityType entity : values())
				a(entity.getCustomClass(), entity.getName(), entity.getID());
			
			//create a List of all possible Biomes
			biomes.add(Biomes.a);
			biomes.add(Biomes.b);
			biomes.add(Biomes.c);
			biomes.add(Biomes.d);
			biomes.add(Biomes.e);
			biomes.add(Biomes.f);
			biomes.add(Biomes.g);
			biomes.add(Biomes.h);
			biomes.add(Biomes.i);
			biomes.add(Biomes.j);
			//biomes.add(Biomes.k); --> THE END!
			biomes.add(Biomes.l);
			biomes.add(Biomes.m);
			biomes.add(Biomes.n);
			biomes.add(Biomes.o);
			biomes.add(Biomes.p);
			biomes.add(Biomes.q);
			biomes.add(Biomes.r);
			biomes.add(Biomes.s);
			biomes.add(Biomes.t);
			biomes.add(Biomes.u);
			biomes.add(Biomes.v);
			biomes.add(Biomes.w);
			biomes.add(Biomes.x);
			biomes.add(Biomes.y);
			biomes.add(Biomes.z);
			biomes.add(Biomes.A);
			biomes.add(Biomes.B);
			biomes.add(Biomes.C);
			biomes.add(Biomes.D);
			biomes.add(Biomes.E);
			biomes.add(Biomes.F);
			biomes.add(Biomes.G);
			biomes.add(Biomes.H);
			biomes.add(Biomes.I);
			biomes.add(Biomes.J);
			biomes.add(Biomes.K);
			biomes.add(Biomes.L);
			biomes.add(Biomes.M);
			biomes.add(Biomes.N);
			biomes.add(Biomes.O);
			biomes.add(Biomes.P);
			biomes.add(Biomes.Q);
			biomes.add(Biomes.R);
			biomes.add(Biomes.S);
			biomes.add(Biomes.T);
			biomes.add(Biomes.U);
			biomes.add(Biomes.V);
			biomes.add(Biomes.W);
			biomes.add(Biomes.X);
			biomes.add(Biomes.Y);
			biomes.add(Biomes.Z);
			biomes.add(Biomes.aa);
			biomes.add(Biomes.ab);
			biomes.add(Biomes.ac);
			biomes.add(Biomes.ad);
			biomes.add(Biomes.ae);
			biomes.add(Biomes.af);
			biomes.add(Biomes.ag);
			biomes.add(Biomes.ah);
			biomes.add(Biomes.ai);
			biomes.add(Biomes.aj);
			biomes.add(Biomes.ak);
			
			//replace the Monster-List & Animal-List, in every Biome
			for(BiomeBase b : biomes){
				List<BiomeMeta> monsterlist= new ArrayList<>();
				monsterlist = b.getMobs(EnumCreatureType.MONSTER);
				monsterlist.clear();
//				monsterlist.add(new BiomeMeta(CustomEntityZombie.class,50,5,20));
//				monsterlist.add(new BiomeMeta(CustomEntitySkeleton.class,100,4,4));
				monsterlist.add(new BiomeMeta(NextanGuard.class,100,3,5));
				
				List<BiomeMeta> animallist= new ArrayList<>();
				animallist = b.getMobs(EnumCreatureType.CREATURE);
				animallist.clear();

			}
		}
		
		
		public static void unregisterEntities(){
			
			//Remove Class References
			for (CustomEntityType entity : values()) {
				try {
				((Map) getPrivateStatic(EntityTypes.class, "d")).remove(entity.getCustomClass());
				} catch (Exception e) {
				e.printStackTrace();
				}
				 
				try {
				((Map) getPrivateStatic(EntityTypes.class, "f")).remove(entity.getCustomClass());
				} catch (Exception e) {
				e.printStackTrace();
				}
				}
				 
			
				//Re-register Custom Classes
				for (CustomEntityType entity : values())
				try {
				a(entity.getNMSClass(), entity.getName(), entity.getID());
				} catch (Exception e) {
				e.printStackTrace();
				}
				
				//Update Biome-Spawn Lists
				if(biomes.size()<=62){
					//ERROR
				}else{
					for(BiomeBase b: biomes){
						List<BiomeMeta> monsterlist= new ArrayList<>();
						monsterlist = b.getMobs(EnumCreatureType.MONSTER);
						monsterlist.clear();
						monsterlist.add(new BiomeMeta(EntitySpider.class, 100, 4, 4));
						monsterlist.add(new BiomeMeta(EntityZombie.class, 100, 4, 4));
						monsterlist.add(new BiomeMeta(EntitySkeleton.class, 100, 4, 4));
						monsterlist.add(new BiomeMeta(EntityCreeper.class, 100, 4, 4));
						monsterlist.add(new BiomeMeta(EntitySlime.class, 100, 4, 4));
						monsterlist.add(new BiomeMeta(EntityEnderman.class, 10, 1, 4));
						monsterlist.add(new BiomeMeta(EntityWitch.class, 5, 1, 1));
						
						List<BiomeMeta> animallist= new ArrayList<>();
						animallist = b.getMobs(EnumCreatureType.CREATURE);
						animallist.clear();
						animallist.add(new BiomeMeta(EntitySheep.class, 12, 4, 4));
						animallist.add(new BiomeMeta(EntityPig.class, 10, 4, 4));
						animallist.add(new BiomeMeta(EntityChicken.class, 10, 4, 4));
						animallist.add(new BiomeMeta(EntityCow.class, 8, 4, 4));
						
						List<BiomeMeta> wateranimallist= new ArrayList<>();
						wateranimallist = b.getMobs(EnumCreatureType.WATER_CREATURE);
						wateranimallist.clear();
						wateranimallist.add(new BiomeMeta(EntitySquid.class, 10, 4, 4));
						
						List<BiomeMeta> ambientlist= new ArrayList<>();
						ambientlist = b.getMobs(EnumCreatureType.AMBIENT);
						ambientlist.clear();
						ambientlist.add(new BiomeMeta(EntityBat.class, 10, 8, 8));

					}
				}
			
			
			
			
			
		}
		

		/*Set data into the entitytypes class from NMS*/
		@SuppressWarnings({ "unchecked", "rawtypes" })
		private static void a(Class paramClass, String paramString, int paramInt) {
		try {
		((Map) getPrivateStatic(EntityTypes.class, "c")).put(paramString, paramClass);
		((Map) getPrivateStatic(EntityTypes.class, "d")).put(paramClass, paramString);
		((Map) getPrivateStatic(EntityTypes.class, "e")).put(Integer.valueOf(paramInt), paramClass);
		((Map) getPrivateStatic(EntityTypes.class, "f")).put(paramClass, Integer.valueOf(paramInt));
		((Map) getPrivateStatic(EntityTypes.class, "g")).put(paramString, Integer.valueOf(paramInt));
		} catch (Exception exc) {
		}

}
		@SuppressWarnings("rawtypes")
		private static Object getPrivateStatic(Class clazz, String f) throws Exception {
		Field field = clazz.getDeclaredField(f);
		field.setAccessible(true);
		return field.get(null);
		}
		
	}