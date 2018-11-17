package krt.grid.api.frame;

public class GridFactory {
	private static GridAPI instance;
	  
	public static void setInstance(GridAPI gridClient) {
		instance = gridClient;
	}
	public static void setInstance(GridAPI gridClient, String host) {
		instance = gridClient;
		instance.setHost(host);
	}
	public static void setInstance(String host) {
		if (instance == null) {
			instance = new JPPFGrid();
		}
		instance.setHost(host);
	}
	public static void resetInstance() {
		instance = null;
	}
	public static GridAPI getInstance() {
		if (instance == null) {
			instance = new JPPFGrid();
		}
    
		return instance;
	}
}
