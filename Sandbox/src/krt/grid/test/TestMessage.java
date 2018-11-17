package krt.grid.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.junit.Assert;
import org.junit.Test;

public class TestMessage {
	@Test
	public void testMessageCreate() throws Exception {
		System.out.println("*** Create a new Message with JSON ***");
		String json = "{" + "\"jobID\":1," + "\"status\":1,"
				+ "\"message\":\"Success\"" + "}";

		URL postUrl = new URL("http://localhost:8080/Sandbox/messages");
		HttpURLConnection connection = (HttpURLConnection) postUrl
				.openConnection();
		connection.setDoOutput(true);
		connection.setInstanceFollowRedirects(false);
		connection.setRequestMethod("PUT");
		connection.setRequestProperty("Content-Type", "application/json");
		OutputStream os = connection.getOutputStream();
		os.write(json.getBytes());
		os.flush();
		int resp = connection.getResponseCode();
		Assert.assertEquals(HttpURLConnection.HTTP_OK, resp);
		connection.disconnect();
	}

	@Test
	public void testMessageUpdate() throws Exception {
		System.out.println("*** Update a Message with JSON ***");
		String json = "{" + "\"jobID\":1," + "\"status\":1,"
				+ "\"message\":\"Success\"" + "}";

		URL postUrl = new URL("http://localhost:8080/Sandbox/messages/1");
		HttpURLConnection connection = (HttpURLConnection) postUrl
				.openConnection();
		connection.setDoOutput(true);
		connection.setInstanceFollowRedirects(false);
		connection.setRequestMethod("POST");
		connection.setRequestProperty("Content-Type", "application/json");
		OutputStream os = connection.getOutputStream();
		os.write(json.getBytes());
		os.flush();
		Assert.assertEquals(HttpURLConnection.HTTP_OK,
				connection.getResponseCode());
		connection.disconnect();
	}

	@Test
	public void testMessageGet() throws Exception {
		System.out.println("*** Get a Message with JSON ***");

		URL postUrl = new URL("http://localhost:8080/SimpleService/messages/version");
		HttpURLConnection connection = (HttpURLConnection) postUrl
				.openConnection();
		connection.setInstanceFollowRedirects(false);
		connection.setRequestMethod("GET");
		connection.setRequestProperty("Content-Type", "application/json");
		int resp = connection.getResponseCode();
		Assert.assertEquals(404, resp);
		if (100 == resp) {
			String response = getResponse(connection);
			Assert.assertEquals(
					"{\"message\":\"Success\",\"status\":1,\"jobID\":1}",
					response);
		}
		connection.disconnect();
	}

	@Test
	public void testMessageDelete() throws Exception {
		System.out.println("*** Delete a Message with JSON ***");

		URL postUrl = new URL("http://localhost:8080/Sandbox/messages/1");
		HttpURLConnection connection = (HttpURLConnection) postUrl
				.openConnection();
		connection.setInstanceFollowRedirects(false);
		connection.setRequestMethod("DELETE");
		connection.setRequestProperty("Content-Type", "application/json");
		Assert.assertEquals(HttpURLConnection.HTTP_OK,
				connection.getResponseCode());
		connection.disconnect();
	}

	private String getResponse(HttpURLConnection connection) throws IOException {
		InputStream is = connection.getInputStream();
		BufferedReader rd = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();

		String line;
		while ((line = rd.readLine()) != null) {
			sb.append(line);
		}
		rd.close();
		System.out.println(sb);
		return sb.toString();
	}
}
