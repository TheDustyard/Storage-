package com.dusterthefirst.storageplus;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.inventory.Inventory;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.IndexOptions;
import org.mongodb.morphia.annotations.Indexed;
import org.mongodb.morphia.annotations.Property;

@Entity(value = "Users", noClassnameStored = true)
public class DatabaseUser {

    @Id
    public int id;

    @Indexed(options = @IndexOptions(unique = true))
    public String uuid;

	@Indexed
    public String username;

    public String ip;

    public long connectionTime;

    @Property("ip_history")
    public List<Integer> ipHistory = new ArrayList<>();

    @Property("name_history")
    public List<String> nameHistory = new ArrayList<>();
    
    public List<Inventory> backpacks = new ArrayList<>();
    
    public void setId(int id) {
		this.id = id;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public void setConnectionTime(long connectionTime) {
		this.connectionTime = connectionTime;
	}
}
