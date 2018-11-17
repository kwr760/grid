package krt.grid.data.objects;

public class File {
	private long fileID;
	private String hashSHA1;
	private String hashMD5;
	private int fileTypeID;
	private long fileSizeBytes;
	private byte fileStatusID;
	private long directoryID;

	public File() {
		super();
	}

	public File(long fileID) {
		super();
		this.fileID = fileID;
	}

	public File(long fileID, String hashSHA1, String hashMD5, String mediaPath,
			String mediaFileName, String internalPath, int fileTypeID,
			long fileSizeBytes, byte fileStatusID, long sourceID,
			long fileSetID, String documentOwners, long workOrderID,
			long directoryID, String metaData) {
		super();
		this.fileID = fileID;
		this.hashSHA1 = hashSHA1;
		this.hashMD5 = hashMD5;
		this.fileTypeID = fileTypeID;
		this.fileSizeBytes = fileSizeBytes;
		this.fileStatusID = fileStatusID;
		this.directoryID = directoryID;
	}

	public long getFileID() {
		return fileID;
	}
	public void setFileID(long newValue) {
		this.fileID = newValue;
	}

	public String getHashSHA1() {
		return hashSHA1;
	}
	public void setHashSHA1(String newValue) {
		this.hashSHA1 = newValue;
	}

	public String getHashMD5() {
		return hashMD5;
	}
	public void setHashMD5(String newValue) {
		this.hashMD5 = newValue;
	}

	public int getFileTypeID() {
		return fileTypeID;
	}
	public void setFileTypeID(int newValue) {
		this.fileTypeID = newValue;
	}

	public long getFileSizeBytes() {
		return fileSizeBytes;
	}
	public void setFileSizeBytes(long newValue) {
		this.fileSizeBytes = newValue;
	}

	public byte getFileStatusID() {
		return fileStatusID;
	}
	public void setFileStatusID(byte newValue) {
		this.fileStatusID = newValue;
	}

	public long getDirectoryID() {
		return directoryID;
	}
	public void setDirectoryID(long newValue) {
		this.directoryID = newValue;
	}
}