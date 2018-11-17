package krt.grid.frame.jppf.test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.jppf.client.JPPFResultCollector;
import org.jppf.client.event.TaskResultEvent;
import org.jppf.server.protocol.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.*;

import krt.grid.frame.jppf.GridResultsCollector;


public class GridResultsCollectorTest {
	GridResultsCollector gridCollector;
	JPPFResultCollector collector;
	
	@Before
	public void setUp() throws Exception {
		JPPFResultCollector collector = mock(JPPFResultCollector.class);
		gridCollector = new GridResultsCollector(1, collector);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void WhenAllTaskHaveBeenRecievedIsCompleteShouldBeTrue() {
		List<JPPFTask> list = new LinkedList<JPPFTask>();
		list.add(mock(JPPFTask.class));
		TaskResultEvent event = new TaskResultEvent(list);
		gridCollector.resultsReceived(event);
		
		boolean expected = true;
		boolean actual = gridCollector.isComplete();
		assertEquals(expected, actual);
	}

	@Test
	public void WhenAllTaskAreNotRecievedIsCompleteShouldBeFalse() {
		boolean expected = false;
		boolean actual = gridCollector.isComplete();
		assertEquals(expected, actual);
	}
	
	@Test
	public void WhenAllTaskHaveBeenRecievedObserversShouldBeNotified() {
		List<JPPFTask> list = new LinkedList<JPPFTask>();
		list.add(mock(JPPFTask.class));
		TaskResultEvent event = new TaskResultEvent(list);
		
		Observer mockObserver = mock(Observer.class);
		gridCollector.addObserver(mockObserver);
		
		gridCollector.resultsReceived(event);
		
		verify(mockObserver).update(eq(gridCollector), anyObject());
	}
	
	@Test
	public void IfAnExceptionIsRecievedThenTheCountsShouldReset() {
		List<JPPFTask> list = new LinkedList<JPPFTask>();
		list.add(mock(JPPFTask.class));
		TaskResultEvent event = new TaskResultEvent(list);
		
		gridCollector.resultsReceived(event);
		
		assertEquals(1, gridCollector.getCurrentSize());
		
		TaskResultEvent exceptionEvent = new TaskResultEvent(new Exception());
		gridCollector.resultsReceived(exceptionEvent);
		
		assertEquals(0, gridCollector.getCurrentSize());
	}
}
