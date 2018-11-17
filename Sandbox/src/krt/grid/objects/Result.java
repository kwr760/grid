package krt.grid.objects;

import java.io.Serializable;

public class Result implements Serializable {
	private static final long serialVersionUID = -6481493584823511478L;
	
	public enum Status {
		Success,
		Failed
	}

	public Result(long jobID, Status status,
			String failureDetails) {
		super();
		this.jobID = jobID;
		this.status = status;
		this.failureDetails = failureDetails;
	}
	
	private long jobID;
	private Status status;
	private String failureDetails;
	
	public long getJobID() {
		return jobID;
	}

	public void setJobID(long jobID) {
		this.jobID = jobID;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getFailureDetails() {
		return failureDetails;
	}

	public void setFailureDetails(String failureDetails) {
		this.failureDetails = failureDetails;
	}
}
