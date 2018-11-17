package krt.grid.objects;

public class File {
	private long fileID;
	private String pathHash;
	private String hashSHA1;
	private String hashMD5;
	private String mediaPath;
	private String mediaFilename;
	private String internalPath;
	private long fileTypeID;
	private long fileSizeBytes;
	private long fileStatusID;
	private long sourceID;
	private long fileSetID;
	private String documentOwners;
	private long workOrderID;
	private long directoryID;
	private String metaData;
	
	public File() {
		super();
	}

	public File(long id) {
		super();
		this.setFileID(id); 
	}

	public void copy(File copyFile) {
		this.setDirectoryID(copyFile.getDirectoryID());
		this.setDocumentOwners(copyFile.getDocumentOwners());
		this.setFileID(copyFile.getFileID());
		this.setFileSetID(copyFile.getFileSetID());
		this.setFileSizeBytes(copyFile.getFileSizeBytes());
		this.setFileStatusID(copyFile.getFileStatusID());
		this.setFileTypeID(copyFile.getFileTypeID());
		this.setHashMD5(copyFile.getHashMD5());
		this.setHashSHA1(copyFile.getHashSHA1());
		this.setInternalPath(copyFile.getInternalPath());
		this.setMediaFilename(copyFile.getMediaFilename());
		this.setMediaPath(copyFile.getMediaPath());
		this.setMetaData(copyFile.getMetaData());
		this.setPathHash(copyFile.getPathHash());
		this.setSourceID(copyFile.getSourceID());
		this.setWorkOrderID(copyFile.getWorkOrderID());
	}
	
	public long getFileID() {
		return fileID;
	}

	public void setFileID(long fileID) {
		this.fileID = fileID;
	}

	public String getPathHash() {
		return pathHash;
	}

	public void setPathHash(String pathHash) {
		this.pathHash = pathHash;
	}

	public String getHashSHA1() {
		return hashSHA1;
	}

	public void setHashSHA1(String hashSHA1) {
		this.hashSHA1 = hashSHA1;
	}

	public String getHashMD5() {
		return hashMD5;
	}

	public void setHashMD5(String hashMD5) {
		this.hashMD5 = hashMD5;
	}

	public String getMediaPath() {
		return mediaPath;
	}

	public void setMediaPath(String mediaPath) {
		this.mediaPath = mediaPath;
	}

	public String getMediaFilename() {
		return mediaFilename;
	}

	public void setMediaFilename(String mediaFilename) {
		this.mediaFilename = mediaFilename;
	}

	public String getInternalPath() {
		return internalPath;
	}

	public void setInternalPath(String internalPath) {
		this.internalPath = internalPath;
	}

	public long getFileTypeID() {
		return fileTypeID;
	}

	public void setFileTypeID(long fileTypeID) {
		this.fileTypeID = fileTypeID;
	}

	public long getFileSizeBytes() {
		return fileSizeBytes;
	}

	public void setFileSizeBytes(long fileSizeBytes) {
		this.fileSizeBytes = fileSizeBytes;
	}

	public long getFileStatusID() {
		return fileStatusID;
	}

	public void setFileStatusID(long fileStatusID) {
		this.fileStatusID = fileStatusID;
	}

	public long getSourceID() {
		return sourceID;
	}

	public void setSourceID(long sourceID) {
		this.sourceID = sourceID;
	}

	public long getFileSetID() {
		return fileSetID;
	}

	public void setFileSetID(long fileSetID) {
		this.fileSetID = fileSetID;
	}

	public String getDocumentOwners() {
		return documentOwners;
	}

	public void setDocumentOwners(String documentOwners) {
		this.documentOwners = documentOwners;
	}

	public long getWorkOrderID() {
		return workOrderID;
	}

	public void setWorkOrderID(long workOrderID) {
		this.workOrderID = workOrderID;
	}

	public long getDirectoryID() {
		return directoryID;
	}

	public void setDirectoryID(long directoryID) {
		this.directoryID = directoryID;
	}

	public String getMetaData() {
		return metaData;
	}

	public void setMetaData(String metaData) {
		this.metaData = metaData;
	}
}
