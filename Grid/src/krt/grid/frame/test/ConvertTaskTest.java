package krt.grid.frame.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import krt.grid.frame.Task.ConvertTask;
import krt.grid.objects.Task;

import org.junit.Before;
import org.junit.Test;


public class ConvertTaskTest {
	private Task task;
	
	@Before
	public void setUp() throws Exception {
		task = new Task();
		task.setDestinationPath("\\\\dimension\\Output");
		task.setDocumentID(1001);
		task.setWorkItemID(2);
		task.setSourcePath("\\\\dimension\\docs\\0\\0\\0\\0\\0\\0\\0\\0\\0\\0\\2\\2\\");
		List<String> tasks = new ArrayList<String>();
		tasks.add("pdf_convert");
		task.setProcessTasks(tasks);
		Map<String,String> processingOptions = new HashMap<String,String>();
		processingOptions.put("SpeakerNotes", "false");
		processingOptions.put("MaxPages", "50");
		task.setProcessingOptions(processingOptions);
		task.setCallback("http://localhost:8080");
	}
	
	@Test
	public void IsLoadTheProperties() throws Exception {
		ConvertTask cTask = new ConvertTask(task);
		String Prop = cTask.getJPPFConfigFilePath();
		System.out.println("Properties: " + Prop);
	}

	@Test
	public void IsConvertGood() throws Exception {
		ConvertTask cTask = new ConvertTask(task);
		String Prop = cTask.getJPPFConfigFilePath();
		System.out.println("Properties: " + Prop);
		System.out.println("Source: " + cTask.getNativeSourceFile());
		System.out.println("Destination: " + cTask.getOutputFile());
	}
}
