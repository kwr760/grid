package krt.grid.frame.test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Matchers.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import junit.framework.Assert;
import krt.grid.frame.GridService;
import krt.grid.frame.NullArgumentException;
import krt.grid.frame.TaskBroker;
import krt.grid.frame.TaskStatus;
import krt.grid.objects.Task;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.jboss.resteasy.core.Dispatcher;
import org.jboss.resteasy.mock.MockDispatcherFactory;
import org.jboss.resteasy.mock.MockHttpRequest;
import org.jboss.resteasy.mock.MockHttpResponse;
import org.junit.Before;
import org.junit.Test;


public class GridServiceTest {

	Dispatcher dispatcher;
	TaskBroker mockedBroker;
	@Before
	public void setUp() throws Exception {
		dispatcher = MockDispatcherFactory.createDispatcher();
	    mockedBroker = mock(TaskBroker.class);
	    dispatcher.getRegistry().addSingletonResource(new GridService(mockedBroker));
	}

	@Test
	public void getVersionTest() throws URISyntaxException {
		MockHttpRequest request = MockHttpRequest.get("/GridService/getVersion");
	    MockHttpResponse response = new MockHttpResponse();

	    dispatcher.invoke(request, response);

	    Assert.assertEquals("Response returned was " + response.getStatus(),HttpServletResponse.SC_OK, response.getStatus());
	    Assert.assertEquals("Content returned was " + response.getContentAsString(),"1.0.0.0", response.getContentAsString());
	}
	
	@Test
	public void cancelTaskTest() throws URISyntaxException {
		MockHttpRequest request = MockHttpRequest.post("/GridService/cancelTask/1");
	    MockHttpResponse response = new MockHttpResponse();

	    when(mockedBroker.cancelTask(1L)).thenReturn(true);
	    
	    dispatcher.invoke(request, response);
	    
	    assertEquals("Response returned was " + response.getStatus(),HttpServletResponse.SC_OK, response.getStatus());
	    assertEquals("Content returned was " + response.getContentAsString(),"true", response.getContentAsString().toLowerCase());
	}

	@Test
	public void checkHealthTest() throws URISyntaxException {
		MockHttpRequest request = MockHttpRequest.get("/GridService/checkHealth");
	    MockHttpResponse response = new MockHttpResponse();

	    String expected = "{\"Status\":\"Running\",\"name\":\"GridService\",\"version\":\"1.0.0.0\"}";
	    dispatcher.invoke(request, response);
	    
	    assertEquals("Response returned was " + response.getStatus(),HttpServletResponse.SC_OK, response.getStatus());
	    assertEquals("Content returned was " + response.getContentAsString(),expected, response.getContentAsString());
	}
	
	@Test
	public void submitTaskTest() throws URISyntaxException, JsonParseException, JsonMappingException, IOException, NullArgumentException {
		MockHttpRequest request = MockHttpRequest.post("/GridService/submitTask");
		String jsonRequest = "{\"documentID\":1001,\"sourcePath\":\"//Server/path/source.pdf\",\"destinationPath\":\"//Server/path/file.txt\",\"processingOptions\":{\"MaxPages\":\"50\",\"SpeakerNotes\":\"false\"},\"processTasks\":[\"text_extract\"]}";
		
		request.contentType("application/json");
		request.content(jsonRequest.getBytes());
	    MockHttpResponse response = new MockHttpResponse();
	    
	    when(mockedBroker.submitTask(isA(Task.class))).thenReturn(true);
	    
	    dispatcher.invoke(request, response);
	    
	    verify(mockedBroker).submitTask(isA(Task.class));
	    
	    assertEquals("Response returned was " + response.getStatus(),HttpServletResponse.SC_OK, response.getStatus());
	    assertEquals("Content returned was " + response.getContentAsString(),"true", response.getContentAsString().toLowerCase());
	}
	
	@Test
	public void taskStatusTest() throws URISyntaxException {
		MockHttpRequest request = MockHttpRequest.get("/GridService/taskStatus/1");
		
		when(mockedBroker.getTaskStatus(1L)).thenReturn(TaskStatus.NotFound);
		
		MockHttpResponse response = new MockHttpResponse();

	    String expected = TaskStatus.NotFound.toString();

	    dispatcher.invoke(request, response);
	    
	    assertEquals("Response returned was " + response.getStatus(),HttpServletResponse.SC_OK, response.getStatus());
	    assertEquals("Content returned was " + response.getContentAsString(),expected, response.getContentAsString());
	}

	@Test
	public void multipleTasksStatusTest() throws URISyntaxException, JsonGenerationException, JsonMappingException, IOException {
		
		List<Long> ids = new ArrayList<Long>();
		ids.add(1L);
		ids.add(2L);
		
		when(mockedBroker.getTaskStatus(1L)).thenReturn(TaskStatus.NotFound);
		when(mockedBroker.getTaskStatus(2L)).thenReturn(TaskStatus.Running);
		
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(ids);  
		
		MockHttpRequest request = MockHttpRequest.get("/GridService/taskStatus");
		
		request.contentType("application/json");
		request.content(json.getBytes());
	    
		MockHttpResponse response = new MockHttpResponse();

	    String expected = "{\"1\":\"NotFound\",\"2\":\"Running\"}";
	    
	    dispatcher.invoke(request, response);
	    
	    assertEquals("Response returned was " + response.getStatus(),HttpServletResponse.SC_OK, response.getStatus());
	    assertEquals("Content returned was " + response.getContentAsString(),expected, response.getContentAsString());
	}
}
