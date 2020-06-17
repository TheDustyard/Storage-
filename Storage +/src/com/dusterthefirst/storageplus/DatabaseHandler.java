package com.dusterthefirst.storageplus;

import java.util.List;

import org.bukkit.entity.Player;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.dao.BasicDAO;

import com.mongodb.MongoClient;

class UserDAO extends BasicDAO<DatabaseUser, String> { 
	public UserDAO(Class<DatabaseUser> entityClass, Datastore ds) {
	    super(entityClass, ds);
	}
}

public class DatabaseHandler {
	private Morphia morphia;
	private MongoClient mc;
	private Datastore datastore;
	private UserDAO userData;
	
	public DatabaseHandler() {
	    mc = new MongoClient();
	
	    morphia = new Morphia();
	    
	    morphia.map(DatabaseUser.class);
	
	    datastore = morphia.createDatastore(mc, "StoragePlus");
	    datastore.ensureIndexes();
	
	    userData = new UserDAO(DatabaseUser.class, datastore);
	}
	
	public DatabaseUser getUserByPlayer(Player player) {
	    DatabaseUser du = userData.findOne("uuid", player.getUniqueId().toString());
	    if (du == null) {
	        du = new DatabaseUser();
	        du.setUuid(player.getUniqueId().toString());
	        du.setIp(player.getAddress().getAddress().toString());
	        du.setUsername(player.getName());
	        userData.save(du);
	    }
	    return du;
	}
	 
	public void saveUser(DatabaseUser user) {
		userData.save(user);
	}
	 
	public List<DatabaseUser> getAllUsers(){
        return userData.find().asList();
    }
}
