package de.hulaa.CustomMobs3;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.bukkit.entity.EntityType;

import net.minecraft.server.v1_9_R1.BiomeBase;
import net.minecraft.server.v1_9_R1.BiomeBase.BiomeMeta;
import net.minecraft.server.v1_9_R1.Biomes;
import net.minecraft.server.v1_9_R1.EntityInsentient;
import net.minecraft.server.v1_9_R1.EntitySkeleton;
import net.minecraft.server.v1_9_R1.EntityTypes;
import net.minecraft.server.v1_9_R1.EnumCreatureType;

public enum CustomEntityType2 {

	CUSTOMSKELETON("MacGyver",51,EntityType.SKELETON,EntitySkeleton.class,CustomEntitySkeleton.class);
	
	
	private String name;
	private int id;
	private EntityType entityType;
	private Class<? extends EntityInsentient> nmsClass;
	private Class<? extends EntityInsentient> customClass;
	
	private static ArrayList<BiomeBase> biomes = new ArrayList<>();
	
	
	private CustomEntityType2(String name, int id, EntityType type,
			Class<? extends EntityInsentient> nmsClass, Class<? extends EntityInsentient> customClass){
	this.name = name;
	this.id = id;
	this.entityType = type;
	this.nmsClass = nmsClass;
	this.customClass = customClass;
	}


	public String getName() {
		return name;
	}


	public int getId() {
		return id;
	}


	public EntityType getEntityType() {
		return entityType;
	}


	public Class<? extends EntityInsentient> getNmsClass() {
		return nmsClass;
	}


	public Class<? extends EntityInsentient> getCustomClass() {
		return customClass;
	}
	
	
	
	public static void registerEntities(){
		
		for(CustomEntityType2 entity: values()){
			register(entity.getCustomClass(),entity.getName(), entity.getId());
		}
		
		
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
		biomes.add(Biomes.k);
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
		
		for(BiomeBase biome: biomes){
			
			List<BiomeMeta> monsterlist = new ArrayList<>();
			monsterlist = biome.getMobs(EnumCreatureType.MONSTER);
			
			monsterlist.clear();
			
			monsterlist.add(new BiomeMeta(CustomEntitySkeleton.class,100,5,15));
			
			
			
		}

		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static void register(Class customEntity, String name, int id){
		
		try{
		((Map) getPrivateStatic(EntityTypes.class, "c")).put(name,customEntity);
		
		((Map) getPrivateStatic(EntityTypes.class, "d")).put(customEntity, name);
		
		((Map) getPrivateStatic(EntityTypes.class, "e")).put(Integer.valueOf(id),customEntity);
		
		((Map) getPrivateStatic(EntityTypes.class, "f")).put(customEntity,Integer.valueOf(id));
		
		((Map) getPrivateStatic(EntityTypes.class, "g")).put(name,Integer.valueOf(id));
		
		}catch(Exception exc){ // BIG NO GO!!!
			
		}
		

	}

	
	private static Object getPrivateStatic(Class clazz, String f) throws Exception{
	Field field = clazz.getDeclaredField(f);
	field.setAccessible(true);
	return field.get(null);
	}
		
		
		
		
		
		
	}
	
	
	

