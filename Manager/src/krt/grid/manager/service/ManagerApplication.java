package krt.grid.manager.service;

import java.util.Set;
import java.util.HashSet;

import javax.ws.rs.core.Application;

import krt.grid.manager.service.Manager;
import krt.grid.manager.service.ManagerModule;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class ManagerApplication extends Application {
	private Set<Object> singeltons = new HashSet<Object>();
	private Set<Class<?>> empty = new HashSet<Class<?>>();
	
	public ManagerApplication(){
		Injector injector = Guice.createInjector(new ManagerModule());
		this.singeltons.add(injector.getInstance(Manager.class));
	} 
	
	public Set<Class<?>> getClasses(){
		return empty;
	}
	
	public Set<Object> getSingletons(){
		return this.singeltons;
	}
}
