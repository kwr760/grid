package krt.grid.frame.test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import krt.grid.objects.Task;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Before;
import org.junit.Test;

public class GridTaskTest {
	private Task task;
	
	@Before
	public void setUp() throws Exception {
		task = new Task();
		task.setDestinationPath("//Server/path/file.txt");
		task.setDocumentID(1001);
		task.setWorkItemID(2);
		task.setSourcePath("//Server/path/source.pdf");
		List<String> tasks = new ArrayList<String>();
		tasks.add("text_extract");
		task.setProcessTasks(tasks);
		Map<String,String> processingOptions = new HashMap<String,String>();
		processingOptions.put("SpeakerNotes", "false");
		processingOptions.put("MaxPages", "50");
		task.setProcessingOptions(processingOptions);
		task.setCallback("http://localhost:1068");
	}

	@Test
	public void jsonSerializationTest() throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		
		String json = mapper.writeValueAsString(task);
		System.out.println(json);
	    Task deserializedItem = mapper.readValue(json, Task.class);
	    
	    assertEquals(task.getJobID(), deserializedItem.getJobID());
	    assertEquals(task.getDestinationPath(), deserializedItem.getDestinationPath());
	    assertEquals(task.getSourcePath(), deserializedItem.getSourcePath());
	    assertArrayEquals(task.getProcessTasks().toArray(), deserializedItem.getProcessTasks().toArray());
	    assertArrayEquals(task.getProcessingOptions().entrySet().toArray(), deserializedItem.getProcessingOptions().entrySet().toArray());
	}
}
