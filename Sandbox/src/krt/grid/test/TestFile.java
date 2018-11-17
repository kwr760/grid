package krt.grid.test;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.junit.Assert;

public class TestFile {
	public void testFileCreate() throws Exception {
		System.out.println("*** Create a new File with JSON ***");
		String json = "{" + "\"fileID\":2," + "\"pathHash\":\"PathHash\","
				+ "\"hashSHA1\":\"HashSHA1\"," + "\"hashMD5\":\"HashMD5\","
				+ "\"mediaPath\":\"MediaPath\","
				+ "\"mediaFilename\":\"MediaFilename\","
				+ "\"internalPath\":\"InternalPath\"," + "\"fileTypeID\":1,"
				+ "\"fileSizeBytes\":1024," + "\"fileStatusID\":1,"
				+ "\"sourceID\":1," + "\"fileSetID\":1,"
				+ "\"documentOwners\":\"documentOwners\","
				+ "\"workOrderID\":1," + "\"directoryID\":1,"
				+ "\"metaData\":\"MetaData\"" + "}";

		URL postUrl = new URL("http://localhost:8080/Sandbox/files");
		HttpURLConnection connection = (HttpURLConnection) postUrl
				.openConnection();
		connection.setDoOutput(true);
		connection.setInstanceFollowRedirects(false);
		connection.setRequestMethod("PUT");
		connection.setRequestProperty("Content-Type", "application/json");
		OutputStream os = connection.getOutputStream();
		os.write(json.getBytes());
		os.flush();
		Assert.assertEquals(HttpURLConnection.HTTP_CREATED,
				connection.getResponseCode());
		System.out
				.println("Location: " + connection.getHeaderField("Location"));
		connection.disconnect();
	}

	public void testFileUpdate() throws Exception {
		System.out.println("*** Update a File with JSON ***");
		String json = "{" + "\"pathHash\": \"PathHash\","
				+ "\"hashSHA1\" : \"HashSHA1\"," + "\"hashMD5\" : \"HashMD5\","
				+ "\"mediaPath\" : \"MediaPath\","
				+ "\"mediaFilename\" : \"MediaFilename\","
				+ "\"internalPath\" : \"InternalPath\","
				+ "\"documentOwners\" : \"documentOwners\","
				+ "\"metaData\" : \"MetaData\"," + "\"fileTypeID\" : 1,"
				+ "\"fileSizeBytes\" : 1024," + "\"fileStatusID\" : 1,"
				+ "\"sourceID\" : 1," + "\"fileSetID\" : 1,"
				+ "\"workOrderID\" : 1," + "\"directoryID\" : 1" + "}";

		URL postUrl = new URL("http://localhost:8080/Sandbox/files/1");
		HttpURLConnection connection = (HttpURLConnection) postUrl
				.openConnection();
		connection.setDoOutput(true);
		connection.setInstanceFollowRedirects(false);
		connection.setRequestMethod("POST");
		connection.setRequestProperty("Content-Type", "application/json");
		OutputStream os = connection.getOutputStream();
		os.write(json.getBytes());
		os.flush();
		Assert.assertEquals(HttpURLConnection.HTTP_CREATED,
				connection.getResponseCode());
		System.out
				.println("Location: " + connection.getHeaderField("Location"));
		connection.disconnect();
	}

	public void testFileGet() throws Exception {
		System.out.println("*** Get a File with JSON ***");

		URL postUrl = new URL("http://localhost:8080/Sandbox/files/1");
		HttpURLConnection connection = (HttpURLConnection) postUrl
				.openConnection();
		connection.setInstanceFollowRedirects(false);
		connection.setRequestMethod("GET");
		connection.setRequestProperty("Content-Type", "application/json");
		Assert.assertEquals(HttpURLConnection.HTTP_CREATED,
				connection.getResponseCode());
		System.out
				.println("Location: " + connection.getHeaderField("Location"));
		connection.disconnect();
	}

	public void testFileDelete() throws Exception {
		System.out.println("*** Delete a File with JSON ***");

		URL postUrl = new URL("http://localhost:8080/Sandbox/files/1");
		HttpURLConnection connection = (HttpURLConnection) postUrl
				.openConnection();
		connection.setDoOutput(true);
		connection.setInstanceFollowRedirects(false);
		connection.setRequestMethod("DELETE");
		connection.setRequestProperty("Content-Type", "application/json");
		Assert.assertEquals(HttpURLConnection.HTTP_CREATED,
				connection.getResponseCode());
		System.out
				.println("Location: " + connection.getHeaderField("Location"));
		connection.disconnect();
	}
}
