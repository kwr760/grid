package krt.grid.objects;

public class Result {
	public enum Status { Success, Failed };
	
	public Result(long a, Status b, String c) { }; 
	
	public int getStatus() {return 0;};
	public int getDetails() {return 0;};
	public int getJobID() {return 0;}

	public void setJobID(long l) {
	}

	public void setDetails(String string) {
	}

	public void setStatus(Status success) {
	}
	
}
