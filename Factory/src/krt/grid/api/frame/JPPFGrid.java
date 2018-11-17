package krt.grid.api.frame;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import krt.grid.objects.Task;

import org.codehaus.jackson.map.ObjectMapper;


public class JPPFGrid implements GridAPI {
	private String host = "http://localhost:8080";
    ObjectMapper mapper = new ObjectMapper();
	
    public void setHost(String host) {
    	this.host = host;
    }

	public JPPFGrid() {
	}
    
	public boolean submitTask(Task task)
	{
		boolean result = false;
	
		try {
		    URL postUrl = new URL(host + "/Grid/submitTask");
			HttpURLConnection connection = (HttpURLConnection) postUrl.openConnection();
			connection.setDoOutput(true);
			connection.setInstanceFollowRedirects(false);
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "application/json");
			OutputStream os = connection.getOutputStream();
			os.write(mapper.writeValueAsBytes(task));
			os.flush();
			connection.getResponseCode();
			String response = getResponse(connection);
			result = new Boolean(response);
			connection.disconnect();
		}
		catch (MalformedURLException mue)
		{
			System.out.println(mue.getMessage());
		}
		catch (IOException ioe)
		{
			System.out.println(ioe.getMessage());
		}
		
		return result;
	}
	
	private String getResponse(HttpURLConnection connection) throws IOException {
		BufferedReader rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		StringBuilder sb = new StringBuilder();
		
		String line;
		while ((line = rd.readLine()) != null) {
			sb.append(line);
			sb.append("\n");
		}
		rd.close();
		return sb.toString();
	}

	@Override
	public String Status() {
		try {
		    URL postUrl = new URL(host + "/Grid/Status/");
			HttpURLConnection connection = (HttpURLConnection)postUrl.openConnection();
			connection.setInstanceFollowRedirects(false);
			connection.setRequestMethod("GET");
			connection.setRequestProperty("Content-Type", "application/json");
			connection.getResponseCode();
			String s = getResponse(connection);
			connection.disconnect();

			return s;
		}
		catch (MalformedURLException mue) {
		}
		catch (IOException ioe) {
		}
		
		return "error in status call";
	}
}
