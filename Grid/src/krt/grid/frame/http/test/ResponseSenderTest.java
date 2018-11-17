package krt.grid.frame.http.test;

import krt.grid.frame.http.ResponseSender;
import krt.grid.objects.Result;
import krt.grid.objects.Result.Status;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class ResponseSenderTest {
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testSubmitResponse() {
		ResponseSender sender = new ResponseSender();
		Result result = new Result(1L,Status.Success,"");
		
		sender.submitResponse(result, "http://localhost:1068");
	}
}
