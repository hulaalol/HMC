package me.hulaa.info;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

public enum Towns {
	ARROWFALL(15,80,-2705,"Arrowfall"),
	LAGOONA(-371,80,-2993,"Lagoona"),
	ORODOM(-378,80,-2334,"Orodom"),
	JIBJOBMOUNTAINS(-438,83,-5939,"JibjobMountains"),
	SAFRANA(21476,159,21344,"Safrana");
	
	public String name;
	public int x;
	public int y;
	public int z;

	Towns(int x, int y, int z, String name){
		this.x = x;
		this.y = y;
		this.z = z;
		this.name = name;
	}

}
