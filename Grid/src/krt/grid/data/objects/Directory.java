package krt.grid.data.objects;
    
public class Directory {
    private long directoryID;
    private String directoryName;
    private String fullPath;

    public Directory() {
        super();
    }
    
    public Directory(long directoryID) {
        super();
        this.directoryID = directoryID;
    }
    
    public Directory(long directoryID, long mediaID, Long directoryID_Parent, String directoryName, String fullPath) {
        super();
        this.directoryID = directoryID;
        this.directoryName = directoryName;
        this.fullPath = fullPath;
    }
    
    public long getDirectoryID() {
        return directoryID;
    }
    public void setDirectoryID(long newValue) {
        this.directoryID = newValue;
    }

    public String getDirectoryName() {
        return directoryName;
    }
    public void setDirectoryName(String newValue) {
        this.directoryName = newValue;
    }

    public String getFullPath() {
        return fullPath;
    }
    public void setFullPath(String newValue) {
        this.fullPath = newValue;
    }
}