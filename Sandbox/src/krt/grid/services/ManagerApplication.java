package krt.grid.services;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

public class ManagerApplication extends Application {
	private Set<Object> singletons = new HashSet<Object>();
	private Set<Class<?>> empty = new HashSet<Class<?>>();
	
	public ManagerApplication() {
		singletons.add(new FilesResource());
		singletons.add(new MessagesResource());
	} 
	
    public Set<Class<?>> getClasses() {
        return empty;
    }
	
	public Set<Object> getSingletons() {
		return singletons;
	}
}
