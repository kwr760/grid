package krt.grid.manager.service;

import krt.grid.manager.TaskQueue;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

public class ManagerModule extends AbstractModule {
	@Override 
	public void configure(){
		bind(TaskQueue.class).in(Singleton.class);
	}
}

