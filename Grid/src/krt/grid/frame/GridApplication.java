package krt.grid.frame;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class GridApplication extends Application {
	private Set<Object> singeltons = new HashSet<Object>();
	private Set<Class<?>> empty = new HashSet<Class<?>>();
	
	public GridApplication(){
		Injector injector = Guice.createInjector(new GridModule());
		this.singeltons.add(injector.getInstance(GridService.class));
	} 
	
	public Set<Class<?>> getClasses(){
		return empty;
	}
	
	public Set<Object> getSingletons(){
		return this.singeltons;
	}
}
