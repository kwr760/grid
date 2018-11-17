package krt.grid.frame.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.LinkedList;
import java.util.logging.Logger;

import krt.grid.api.manager.ManagerAPI;
import krt.grid.api.manager.ManagerFactory;
import krt.grid.objects.Result;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;


public class ResponseSender {
	private Logger logger;

	public void submitResponse(Result item, String callback){
		String callbackUrl = callback;
		ObjectMapper mapper = new ObjectMapper();
		String json = "";
		try {
			json = mapper.writeValueAsString(item);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		
		try{
		    URL url = new URL(callbackUrl);
		    URLConnection conn = url.openConnection();
		    conn.setDoOutput(true);
		    OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
		    wr.write(json);
		    wr.flush();
	
		    BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		    wr.close();
		    rd.close();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("End TASK MANAGER Call..");
	}

	public void submitResponseByFactory(Result item, String callback){
		ManagerAPI tm = ManagerFactory.getInstance();
        LinkedList<Result> results = new LinkedList<Result>();
        results.add(item);
        try {
			tm.sendResults(results);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		logger.info("End Call back To TASK MANAGER ");
	}
}
