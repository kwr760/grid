package krt.grid.data.objects;
    
public class FileStatus {
    private byte fileStatusID;
    private String fileStatusName;

    public FileStatus() {
        super();
    }
    
    public FileStatus(byte fileStatusID) {
        super();
        this.fileStatusID = fileStatusID;
    }
    
    public FileStatus(byte fileStatusID, String fileStatusName) {
        super();
        this.fileStatusID = fileStatusID;
        this.fileStatusName = fileStatusName;

    }
    
    public byte getFileStatusID() {
        return fileStatusID;
    }
    public void setFileStatusID(byte newValue) {
        this.fileStatusID = newValue;
    }

    public String getFileStatusName() {
        return fileStatusName;
    }
    public void setFileStatusName(String newValue) {
        this.fileStatusName = newValue;
    }
}