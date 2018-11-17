package krt.grid.data.objects;
    
public class FileType {
    private int fileTypeID;
    private String fileTypeName;

    public FileType() {
        super();
    }
    
    public FileType(int fileTypeID) {
        super();
        this.fileTypeID = fileTypeID;
    }
    
    public FileType(int fileTypeID, String fileTypeName, long vendorFileTypeID) {
        super();
        this.fileTypeID = fileTypeID;
        this.fileTypeName = fileTypeName;
    }
    
    public int getFileTypeID() {
        return fileTypeID;
    }
    public void setFileTypeID(int newValue) {
        this.fileTypeID = newValue;
    }

    public String getFileTypeName() {
        return fileTypeName;
    }
    public void setFileTypeName(String newValue) {
        this.fileTypeName = newValue;
    }
}