package krt.grid.frame.jppf;

import java.util.List;
import java.util.Observable;

import org.jppf.client.JPPFResultCollector;
import org.jppf.client.event.TaskResultEvent;
import org.jppf.client.event.TaskResultListener;
import org.jppf.server.protocol.JPPFTask;

public class GridResultsCollector extends Observable implements  TaskResultListener{
	private JPPFResultCollector collector;
	private int startSize, currentSize;

	public GridResultsCollector(int count) {
		startSize = count;
		currentSize = 0;
		collector = new JPPFResultCollector(count);	
	}
	
	public GridResultsCollector(int count, JPPFResultCollector results){
		startSize = count;
		currentSize = 0;
		collector = results;
	}
	
	public int getStartSize() {
		return startSize;
	}

	public int getCurrentSize() {
		return currentSize;
	}

	public boolean isComplete(){
		return getCurrentSize() >= getStartSize();
	}
	
	@Override
	public void  resultsReceived(TaskResultEvent event) {
		collector.resultsReceived(event);
		
		if(event.getThrowable() == null){
			List<JPPFTask> tasks = event.getTaskList();
			currentSize += tasks.size();		
		} else {
			currentSize = 0;
		}
		
		if(this.isComplete()){
			this.setChanged();
			this.notifyObservers(this);
		}
	}

	public JPPFResultCollector getCollector() {
		return collector;
	}
}

