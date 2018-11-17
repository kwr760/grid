package krt.grid.frame.jppf;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import krt.grid.frame.GridWebServiceException;
import krt.grid.frame.NullArgumentException;

import org.jppf.JPPFException;
import org.jppf.client.JPPFClient;
import org.jppf.client.JPPFJob;

import org.jppf.scheduling.JPPFSchedule;
import org.jppf.server.protocol.JPPFJobSLA;
import org.jppf.server.protocol.JPPFTask;


public class GridController extends Observable implements Runnable, Observer {
	public GridController(){
		client = new org.jppf.client.JPPFClient();
		currentJob = null;
		lastSubmitTime  = new Date();
		results = new ArrayList<GridResultsCollector>();
		createNewJob();
	}
	
	public GridController(JPPFClient gridClient, ArrayList<GridResultsCollector> results){
		client = new org.jppf.client.JPPFClient();
		currentJob = null;
		lastSubmitTime  = new Date();
		results = new ArrayList<GridResultsCollector>();
		createNewJob();
	}
	
	private static final int MAX_RUN_TIME = 1000 * 60 * 120;
	
	private JPPFClient client;
	private JPPFJob currentJob;
	private Date lastSubmitTime;
	private List<GridResultsCollector> results;
	
	private void createNewJob(){
		if(currentJob == null){
			currentJob = new JPPFJob();
			currentJob.setBlocking(false);
			currentJob.setId("A Bunch Of Tasks" + lastSubmitTime.toString());
			JPPFJobSLA sla = new JPPFJobSLA();
			JPPFSchedule jobExpirationSchedule = new JPPFSchedule(MAX_RUN_TIME);
			sla.setJobExpirationSchedule(jobExpirationSchedule);
			currentJob.setJobSLA(sla);
		}
	}
	
	private void submitJobToGrid(){
		Date current = new Date();
		if(currentJob.getTasks().size() >= 1){
			try {
				GridResultsCollector collector = new GridResultsCollector(currentJob.getTasks().size());
				currentJob.setResultListener(collector);
				client.submit(currentJob);
				currentJob = null;
				createNewJob();
				results.add(collector);
				
				collector.addObserver(this);
			} catch (Exception exception){
			}
			lastSubmitTime = current;
		}
	}
	
	public void submitTask(Runnable runner) throws GridWebServiceException, NullArgumentException, IllegalArgumentException{
		if (runner == null){
			throw new NullArgumentException("runner");
		} else if (! (runner instanceof Serializable) ){
			throw new IllegalArgumentException("The argument runner must support the serializable interface.");
		}
		
		try {
			synchronized(this){
				currentJob.addTask(runner);
			}
		
		} catch (JPPFException exception) {
			throw new GridWebServiceException("Unable to submit task to grid job.", exception);
		
		} 
	}
	
	public boolean areAnyJobsComplete(){
		boolean result = false;
		
		for(GridResultsCollector collector : this.results){
			if(collector.isComplete()){
				result = true;
			};
		}
		return result;
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		GridResultsCollector collector = (GridResultsCollector)arg0;
		collector.deleteObservers();
		if(collector.isComplete()){
			this.results.remove(collector);
		}
	
		List<JPPFTask> tasks = collector.getCollector().waitForResults();
		for(JPPFTask task : tasks){
			if(task.getException() == null){
				setChanged();
				notifyObservers(task.getTaskObject());
			}
		}
	}

	@Override
	public void run() {
		if(this.currentJob != null) {
			synchronized(this){
				submitJobToGrid();
			}
		}
	}
}
