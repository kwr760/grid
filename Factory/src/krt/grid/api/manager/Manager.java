package krt.grid.api.manager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;

import krt.grid.api.manager.ManagerAPI;
import krt.grid.objects.Result;

import org.codehaus.jackson.map.ObjectMapper;


public class Manager implements ManagerAPI {
	private String host = "http://localhost:8080";
    ObjectMapper mapper = new ObjectMapper();
	
    public void setHost(String host) {
    	this.host = host;
    }
    
	public void	test() throws Exception{
		try {
			URL postUrl = new URL(host + "/Manager/test");
			HttpURLConnection connection = (HttpURLConnection) postUrl.openConnection();
			connection.setDoOutput(true);
			connection.setInstanceFollowRedirects(false);
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "application/json");
			connection.getResponseCode();
			connection.disconnect();
		}
		catch (MalformedURLException mue) {
			throw new Exception(mue.getMessage());
		}
		catch (IOException ioe) {
			throw new Exception(ioe.getMessage());
		}
	}
	public void	reset() throws Exception{
		try {
			URL postUrl = new URL(host + "/Manager/reset");
			HttpURLConnection connection = (HttpURLConnection) postUrl.openConnection();
			connection.setDoOutput(true);
			connection.setInstanceFollowRedirects(false);
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "application/json");
			connection.getResponseCode();
			connection.disconnect();
		}
		catch (MalformedURLException mue) {
			throw new Exception(mue.getMessage());
		}
		catch (IOException ioe) {
			throw new Exception(ioe.getMessage());
		}
	}
	public void	halt() throws Exception{
		try {
			URL postUrl = new URL(host + "/Manager/halt");
			HttpURLConnection connection = (HttpURLConnection) postUrl.openConnection();
			connection.setDoOutput(true);
			connection.setInstanceFollowRedirects(false);
			connection.setRequestMethod("DELETE");
			connection.setRequestProperty("Content-Type", "application/json");
			connection.getResponseCode();
			connection.disconnect();
		}
		catch (MalformedURLException mue) {
			throw new Exception(mue.getMessage());
		}
		catch (IOException ioe) {
			throw new Exception(ioe.getMessage());
		}
	}
	public String version() throws Exception {
		String result;
		try {
			URL postUrl = new URL(host + "/Manager/version");
			HttpURLConnection connection = (HttpURLConnection) postUrl.openConnection();
			connection.setDoOutput(true);
			connection.setInstanceFollowRedirects(false);
			connection.setRequestMethod("GET");
			connection.setRequestProperty("Content-Type", "application/json");
			connection.getResponseCode();
			result = getResponse(connection);
			connection.disconnect();
		}
		catch (MalformedURLException mue) {
			throw new Exception(mue.getMessage());
		}
		catch (IOException ioe) {
			throw new Exception(ioe.getMessage());
		}
		return result;
	}
	public void sendResults(LinkedList<Result> results) throws Exception {
		try {
			URL postUrl = new URL(host + "/Manager/documents/results");
			HttpURLConnection connection = (HttpURLConnection) postUrl.openConnection();
			connection.setDoOutput(true);
			connection.setInstanceFollowRedirects(false);
			connection.setRequestMethod("PUT");
			connection.setRequestProperty("Content-Type", "application/json");
			OutputStream os = connection.getOutputStream();
			os.write(mapper.writeValueAsBytes(results));
			os.flush();
			connection.getResponseCode();
			connection.disconnect();
		}
		catch (MalformedURLException mue) {
			throw new Exception(mue.getMessage());
		}
		catch (IOException ioe) {
			throw new Exception(ioe.getMessage());
		}
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
		    URL postUrl = new URL(host + "/Manager/Status/");
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
