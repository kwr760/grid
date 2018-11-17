package krt.grid.manager;

import java.io.Serializable;

import krt.grid.api.frame.GridFactory;
import krt.grid.api.frame.MockGrid;
import krt.grid.manager.jbpm.StartFlow;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class TaskQueue extends Thread implements Runnable, Serializable {
	private static final long serialVersionUID = 69292397831002051L;
	
	private final long WAIT_TIME = 15000;
	private boolean shuttingDown = false;
	@SuppressWarnings("unused")
	private StartFlow documentFlow;

	@Inject
	public TaskQueue() {
			this.documentFlow = new StartFlow();
			start();
			System.out.println("Parent Thread");
	}
	
	public void halt() {
		System.out.println("Process Queue Halt called.");
		this.documentFlow = new StartFlow();
	}
	public void test() {
		System.out.println("Set the Process Queue to be tested.");
		GridFactory.setInstance(new MockGrid());
	}
	public void reset() {
		System.out.println("Reset the Process Queue from testing.");
		GridFactory.resetInstance();
	}
	
	public void run() {
		try {
			while (!shuttingDown) {
				Thread.sleep(WAIT_TIME);
			}
		} catch (InterruptedException ie) {
			ie.printStackTrace();
		}
	}
}


