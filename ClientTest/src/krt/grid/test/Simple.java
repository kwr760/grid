package krt.grid.test;

import krt.grid.api.manager.ManagerAPI;
import krt.grid.api.manager.ManagerFactory;

public class Simple extends Thread {
	private static ManagerAPI tm = ManagerFactory.getInstance();
	private int id;
	
	public Simple(int id) {
		this.id = id;
	}
	
	public static void main(String[] args) {
		
  		try {
			tm.test();
			
			for (int i = 0; i < 25; i++){
				Simple test = new Simple(i+1);
				test.start();
			}
		}
		catch (Exception rce) {
			rce.printStackTrace();
		}
	}
		
	public void run() {
		try {
			System.out.println("id:  " + id);
		}
		catch (Exception rce) {
			rce.printStackTrace();
		}
	}	
}
