package com.roy.process.test;

import static org.mockito.Mockito.mock;
import java.net.URISyntaxException;
import javax.servlet.http.HttpServletResponse;
import junit.framework.Assert;
import krt.grid.manager.TaskQueue;

import org.codehaus.jackson.map.ObjectMapper;
import org.jboss.resteasy.core.Dispatcher;
import org.jboss.resteasy.mock.MockDispatcherFactory;
import org.jboss.resteasy.mock.MockHttpRequest;
import org.jboss.resteasy.mock.MockHttpResponse;
import org.junit.Before;
import org.junit.Test;


public class MockManagerTest {
	ObjectMapper mapper = new ObjectMapper();
	Dispatcher dispatcher;
	TaskQueue mockedQueue;
	
	@Before
	public void setUp() throws Exception {
		dispatcher = MockDispatcherFactory.createDispatcher();
		mockedQueue = mock(TaskQueue.class);
	}

	@Test
	public void getVersionTest() throws URISyntaxException {
		MockHttpRequest request = MockHttpRequest.get("/version");
	    MockHttpResponse response = new MockHttpResponse();

	    dispatcher.invoke(request, response);

	    Assert.assertEquals("Response returned was " + response.getStatus(),HttpServletResponse.SC_OK, response.getStatus());
	    Assert.assertEquals("Content returned was " + response.getContentAsString(),"0.0.0.0", response.getContentAsString());
	}
}
