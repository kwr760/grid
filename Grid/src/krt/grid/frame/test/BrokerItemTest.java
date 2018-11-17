package krt.grid.frame.test;

import static org.junit.Assert.*;
import krt.grid.frame.BrokerItem;
import krt.grid.frame.TaskStatus;

import org.junit.Test;


public class BrokerItemTest {
	@Test
	public void ifYouChangeAStatusTheATimestampShouldBeSet() {
		BrokerItem brokerItem = new BrokerItem();
	    brokerItem.setStatus(TaskStatus.Start);
	    brokerItem.setStatus(TaskStatus.Running);
	    
	    assertTrue(brokerItem.getTimeStamps().containsKey(TaskStatus.Start));
	    assertTrue(brokerItem.getTimeStamps().containsKey(TaskStatus.Running));
	}

	@Test
	public void ifYouMakeANewInstanceTheTaskStatusShouldBeNothing() {
		BrokerItem brokerItem = new BrokerItem();
	    
	    assertTrue(brokerItem.getStatus() == TaskStatus.Nothing);
	}
}
