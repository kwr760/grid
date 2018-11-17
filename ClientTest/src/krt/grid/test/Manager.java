package krt.grid.test;

import java.util.LinkedList;

import krt.grid.api.manager.ManagerAPI;
import krt.grid.api.manager.ManagerFactory;
import krt.grid.objects.Result;
import krt.grid.objects.Result.Status;

import org.junit.Assert;
import org.junit.Test;


public class Manager {
	ManagerAPI tm = ManagerFactory.getInstance();
	@Test
	public void testHalt() throws Exception
	{
	    System.out.println("*** Halt the process queue ***");
		tm.halt();
	}
	@Test
	public void testVersion() throws Exception
	{
	    System.out.println("*** Version the process queue ***");
		String version = tm.version();
		Assert.assertEquals("0.0.0.0", version);
	}
	@Test
	public void testSendResults() throws Exception
	{
	    System.out.println("*** Send Results ***");
	    Result result = new Result((long)1000, Status.Success, "Success");
	    LinkedList<Result> results = new LinkedList<Result>();
	    results.add(result);
		tm.sendResults(results);
	}
}
