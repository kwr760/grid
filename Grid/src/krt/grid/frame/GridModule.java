package krt.grid.frame;

import java.util.*;
import java.util.concurrent.*;

import com.google.inject.*;

public class GridModule extends AbstractModule implements Provider<ScheduledExecutorService>{
	@Override 
	public void configure(){
		bind(TaskBroker.class).in(Singleton.class);
		bind(new TypeLiteral<Queue<BrokerItem>>() {}).toInstance( new LinkedList<BrokerItem>());
		bind(new TypeLiteral<Map<String, BrokerItem>>() {}).toInstance( new HashMap<String, BrokerItem>());
		bind(ScheduledExecutorService.class).toProvider(GridModule.class);
	}

	@Override 
	public ScheduledExecutorService get() {
		return Executors.newScheduledThreadPool(10);
	}
}
