package krt.grid.frame;

import java.util.LinkedList;
import java.util.Map;
import java.util.Observable;
import java.util.Queue;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.util.Observer;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import krt.grid.frame.Task.ConvertTask;
import krt.grid.frame.Task.ExtractionTask;
import krt.grid.frame.Task.SimpleTask;
import krt.grid.frame.http.ResponseSender;
import krt.grid.frame.jppf.GridController;
import krt.grid.objects.Result;
import krt.grid.objects.Task;
import krt.grid.objects.Result.Status;

@Singleton
public class TaskBroker implements Runnable, Observer{
	private Queue<BrokerItem> taskQueue;
	private Map<String, BrokerItem> runningTasks;
	private Queue<BrokerItem> completedQueue;
	private GridController gridController;
	private ScheduledExecutorService threadPool;
	
	@Inject
	public TaskBroker(Queue<BrokerItem> taskQueue, Map<String, BrokerItem> taskHolder, 
			Queue<BrokerItem> completedQueue, GridController gridController, 
			ScheduledExecutorService threadPool){
		this.taskQueue = taskQueue;
		this.runningTasks = taskHolder;
		this.completedQueue = completedQueue;
		this.completedQueue = new LinkedList<BrokerItem>();
		this.gridController = gridController;
		this.gridController.addObserver(this);
		this.threadPool = threadPool;
		
		threadPool.scheduleAtFixedRate(this, 0, 5, TimeUnit.SECONDS);
		threadPool.scheduleAtFixedRate(this.gridController, 0, 5, TimeUnit.SECONDS);
	}
	
	public ScheduledExecutorService getThreadPool() {
		return threadPool;
	}

	public boolean taskItemExists(Long jobID)
	{
		for (BrokerItem tItem : taskQueue)
		{
			if (tItem.getGridTask().getJobID() == jobID) 
			{
				return true;
			}
		}
		return false;
	}
	
	public boolean taskItemExistsInCompleted(Long jobID)
	{
		for (BrokerItem tItem : completedQueue)
		{
			if (tItem.getGridTask().getJobID() == jobID) 
			{
				return true;
			}
			
		}
		
		return false;
	}
		
	public boolean cancelTask(Long jobID)
	{
		boolean result = false;
		for (BrokerItem tItem : taskQueue)
		{
			if (tItem.getGridTask().getJobID() == jobID) 
			{
				taskQueue.remove(tItem);
				result = true;
			}
		}
		
		return result;
	}
	
	public TaskStatus getTaskStatus(long documentID){
		TaskStatus status = TaskStatus.NotFound;
		
		if(this.taskItemExists(documentID)){
			status = TaskStatus.Queued;
		} else if(this.runningTasks.containsKey(documentID)) {
			status = TaskStatus.Running;
		} else if(this.taskItemExistsInCompleted(documentID)) {
			status = TaskStatus.Completed;
		}
		return status;
	}
	
	public boolean submitTask(Task taskItem)throws NullArgumentException{
		if(taskItem == null){
			throw new NullArgumentException("taskItem");
		}
		
		boolean result = false;
		
		BrokerItem item = null;
		try{
			item = new BrokerItem(taskItem);
			item.setStatus(TaskStatus.Start);
			result = taskQueue.add(item);
			item.setStatus(TaskStatus.Queued);
		} catch (Exception e){
			if (item != null){
				item.setStatus(TaskStatus.Failed);
				item.setFailureReason(e);
				
				if(taskQueue.contains(item)){
					taskQueue.remove(item);
				}
				
				completedQueue.add(item);
			}
			return false;
		}

		return result;
	}

	public void run() {
		if(!this.taskQueue.isEmpty()){
			submitItemsToGrid(this.taskQueue);
		}
		if(!this.completedQueue.isEmpty()){
			returnResultsToTaskManager(this.completedQueue);
		}
	}

	private void returnResultsToTaskManager(Queue<BrokerItem> completedQueue) {
		ResponseSender sender = new ResponseSender();
		Result result = new Result(0, null, null);
		
		while(!completedQueue.isEmpty()){
		
			BrokerItem item = completedQueue.remove();
			
			result.setJobID(item.getGridTask().getJobID());
			
			String details = "";
			if(item.getFailureReason() != null) {
				details = item.getFailureReason().toString();
			}
			result.setDetails(details);
			
			result.setStatus(item.getStatus() == TaskStatus.Failed ? Status.Failed : Status.Success);
			result.setJobID(item.getGridTask().getJobID());
			
			sender.submitResponseByFactory(result, item.getGridTask().getCallback());
		}
	}

	private void submitItemsToGrid(Queue<BrokerItem> taskQueue) {
		while (!taskQueue.isEmpty()){
			BrokerItem brokerItem = taskQueue.remove();
			Task task = brokerItem.getGridTask();

			for (int i = 0; i < task.getProcessTasks().size(); i++) {
				try {
					if (task.getProcessTasks().get(i).equals("text_extract"))
					{
						this.gridController.submitTask(new ExtractionTask(task));
						
					}
					else if (task.getProcessTasks().get(i).equals("pdf_convert")) {
						this.gridController.submitTask(new ConvertTask(task));
					}
					else
					{
						this.gridController.submitTask(new SimpleTask(task));
					}
					
					brokerItem.setStatus(TaskStatus.Running);
					this.runningTasks.put(task.getJobID() + task.getProcessTasks().get(0).toString(), brokerItem);
					
				} catch (Exception e) {
					brokerItem.setStatus(TaskStatus.Failed);
					brokerItem.setFailureReason(e);
					this.completedQueue.add(brokerItem);
				}
				finally{}
			}
		}
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		ITask task = (ITask) arg1;
		
		BrokerItem  item = this.runningTasks.remove(task.getJobID()+ task.getTasks().get(0).toString());
		
		if(item != null){
					
			item.setStatus(task.isSuccess()?TaskStatus.Completed : TaskStatus.Failed);
			this.completedQueue.add(item);
		} 
	}
	
	public String getStatus() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("Task Queue Length: " + this.taskQueue.size() + "\n");
		sb.append("Running Tasks Queue Length: " + this.runningTasks.size() + "\n");
		sb.append("Completed Tasks Queue Length: " + this.completedQueue.size() + "\n");
				
		return sb.toString();
	}
}


