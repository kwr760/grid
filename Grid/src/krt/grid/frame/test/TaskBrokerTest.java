package krt.grid.frame.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ScheduledExecutorService;

import junit.framework.Assert;
import krt.grid.frame.BrokerItem;
import krt.grid.frame.NullArgumentException;
import krt.grid.frame.TaskBroker;
import krt.grid.frame.TaskStatus;
import krt.grid.objects.Task;

import org.junit.*;
import org.mockito.Mockito;


public class TaskBrokerTest {
	private Task task;
	private TaskBroker broker;
	
	private Queue<BrokerItem> taskQueue;
	private HashMap<String, BrokerItem> runningTasks;
	private Queue<BrokerItem> completedQueue;
	
	@Before
	public void Setup(){
		task = new Task();
		task.setDestinationPath("//dimension//output/PdfTest.pd");
		task.setDocumentID(1001);
		task.setSourcePath("//dimension/input/PdfTest.txt");
		List<String> tasks = new ArrayList<String>();
		tasks.add("pdf_convert");
		task.setProcessTasks(tasks);
		Map<String,String> processingOptions = new HashMap<String,String>();
		processingOptions.put("SpeakerNotes", "false");
		processingOptions.put("MaxPages", "50");
		task.setProcessingOptions(processingOptions);
		
		this.completedQueue = new LinkedList<BrokerItem>();
		this.taskQueue = new LinkedList<BrokerItem>();
		this.runningTasks = new HashMap<String, BrokerItem>();
		ScheduledExecutorService threadpool = Mockito.mock(ScheduledExecutorService.class);
		broker = new TaskBroker(taskQueue, runningTasks, completedQueue, null, threadpool);
	}
	
	@Test
	public void whenATaskItemIsSubmittedItIsQueuedAndTrueIsReturned() throws NullArgumentException{
		boolean expected = true;
		boolean actual;
		
		actual = broker.submitTask(task);
		Assert.assertEquals("submitTask did not return true on submited task item.", expected, actual);
	}
	
	@Test
	public void whenATaskItemIsSubmittedAndTheQueuedFailsFalseIsReturned() throws NullArgumentException{
		boolean expected = false;
		boolean actual;
		actual = broker.submitTask(task);
		Assert.assertEquals("submitTask did not return false on submited task item.", expected, actual);
	}
	
	@Test
	public void ifATaskIsInTheQueueItShouldBeSubmittedToTheGrid(){
		Assert.fail("Not Implemented");
	}
	
	@Test
	public void whenTaskIsSubmittedToTheGridACopyShouldBeHeldUntilComplete(){
		Assert.fail("Not Implemented");
	}
	
	@Test
	public void whenTaskIsSubmittedToTheGridATimeStampShouldBeStored(){
		Assert.fail("Not Implemented");
	}
	
	@Test
	public void whenTaskIsCompleteSendStatusWebService(){
		Assert.fail("Not Implemented");
	}
	
	@Test
	public void whenTaskIsCompleteRemoveTheSavedItem(){
		Assert.fail("Not Implemented");
	}
	
	@Test
	public void whenATaskIsCanceledAndIsInTheQueueJustDeleteIt(){
		Assert.fail("Not Implemented");
	}
	
	@Test
	public void whenATaskIsCanceledAndIsInTheHoldingBinReturnFalse(){
		Assert.fail("Not Implemented");
	}
	
	@Test
	public void ifGetStatusIsCalledAndTheTaskIsInTheSubmitQueueThenReturnQueued() throws NullArgumentException{
		TaskStatus expected = TaskStatus.Queued;
		
		broker.submitTask(task);
		TaskStatus actual = broker.getTaskStatus(task.getJobID());
		
		Assert.assertEquals("Expected value " + expected + "was not recieved. Actual: " + actual,expected, actual);
	}
	
	@Test
	public void ifGetStatusIsCalledAndTheTaskIsMissingThenReturnNotFound(){
		TaskStatus expected = TaskStatus.NotFound;
		TaskStatus actual = broker.getTaskStatus(task.getJobID());
		
		Assert.assertEquals("Expected value " + expected + "was not recieved. Actual: " + actual,expected, actual);
	}
	
	@Test
	public void ifGetStatusIsCalledAndTheTaskIsInTheRunningMapThenReturnRunning(){
		TaskStatus expected = TaskStatus.Running;
		BrokerItem item = new BrokerItem(task);
		this.runningTasks.put(task.getJobID() + task.getProcessTasks().get(0).toString(), item);
		TaskStatus actual = broker.getTaskStatus(task.getJobID());
		
		Assert.assertEquals("Expected value " + expected + "was not recieved. Actual: " + actual,expected, actual);
	}
	
	@Test
	public void ifGetStatusIsCalledAndTheTaskIsInTheCompletedQueueThenReturnComplete(){
		TaskStatus expected = TaskStatus.Completed;
		BrokerItem item = new BrokerItem(task);
		this.completedQueue.add(item);
		TaskStatus actual = broker.getTaskStatus(task.getJobID());
		
		Assert.assertEquals("Expected value " + expected + "was not recieved. Actual: " + actual,expected, actual);
	}
}
