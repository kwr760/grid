package krt.grid.api.manager;

import krt.grid.api.manager.Manager;
import krt.grid.api.manager.ManagerAPI;

public class ManagerFactory {
	private static ManagerAPI instance;
  
	public static void setInstance(ManagerAPI taskManagerClient) {
		instance = taskManagerClient;
	}

	public static void setInstance(ManagerAPI taskManagerClient, String host) {
		instance = taskManagerClient;
		instance.setHost(host);
	}

	public static void setInstance(String host) {
		if (instance == null) {
			instance = new Manager();
		}
		instance.setHost(host);
	}
	
	public static ManagerAPI getInstance() {
		if (instance == null) {
			instance = new Manager();
		}
    
		return instance;
	}
}
