package krt.grid.frame;

import java.io.Serializable;

public class NativeFile implements Serializable {
	private static final long serialVersionUID = -1791455681980957940L;
	private long fileSizeBytes;
	private int fileID;
	private int fileTypeID;
	private String hashMD5;
	private String hashSHA1;
	private String internalPath;

	public void setFileSizeBytes(long fileSizeBytes) {
		this.fileSizeBytes = fileSizeBytes;
	}
	public long getFileSizeBytes() {
		return fileSizeBytes;
	}
	public void setFileID(int fileID) {
		this.fileID = fileID;
	}
	public int getFileID() {
		return fileID;
	}
	public void setFileTypeID(int fileTypeID) {
		this.fileTypeID = fileTypeID;
	}
	public int getFileTypeID() {
		return fileTypeID;
	}
	public void setHashMD5(String hashMD5) {
		this.hashMD5 = hashMD5;
	}
	public String getHashMD5() {
		return hashMD5;
	}
	public void setHashSHA1(String hashSHA1) {
		this.hashSHA1 = hashSHA1;
	}
	public String getHashSHA1() {
		return hashSHA1;
	}
	public void setInternalPath(String internalPath) {
		this.internalPath = internalPath;
	}
	public String getInternalPath() {
		return internalPath;
	}
}