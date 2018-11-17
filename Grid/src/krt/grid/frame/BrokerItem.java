package krt.grid.frame;

import java.util.*;

import krt.grid.objects.Task;


public class BrokerItem {
	private Task gridTask;
	private Map<TaskStatus, Date> timeStamps;
	private TaskStatus status;
	private Throwable failureReason;
	
	public BrokerItem(){
		this.gridTask = null;
		this.timeStamps = new HashMap<TaskStatus, Date>();
		this.status = TaskStatus.Nothing;
		this.failureReason = null;
	}
	
	public BrokerItem(Task taskItem){
		this.gridTask = taskItem;
		this.timeStamps = new HashMap<TaskStatus, Date>();
		this.status = TaskStatus.Nothing;
		this.failureReason = null;
	}
	
	public Throwable getFailureReason() {
		return failureReason;
	}

	public void setFailureReason(Throwable failureReason) {
		this.failureReason = failureReason;
	}

	public Task getGridTask() {
		return gridTask;
	}
	
	public void setGridTask(Task taskItem) {
		this.gridTask = taskItem;
		
	}

	public Map<TaskStatus, Date> getTimeStamps() {
		return timeStamps;
	}

	public void setTimeStamps(Map<TaskStatus, Date> timeStamps) {
		this.timeStamps = timeStamps;
	}

	public TaskStatus getStatus() {
		return status;
	}

	public void setStatus(TaskStatus status) {
		this.status = status;
		timeStamps.put(status, new Date());
	}
}


