package krt.grid.frame.Task;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;


import java.io.*;
import java.util.*;

import krt.grid.frame.ITask;
import krt.grid.frame.NativeFile;
import krt.grid.frame.jobs.OutSideInTextExtractor;
import krt.grid.objects.Task;

public class ExtractionTask implements ITask, Runnable, Serializable {
    
	private static final long serialVersionUID = -383957222862428301L;
	
	private static final String PADDING = "AAA";
    private String _source, _destination;
    private boolean _success, _done;
    private int _pageCount;
    private Date _startTime;
    private boolean _skipExistingTextFiles, _skipped;
    private ITextExtractor _extractor;
    private int _maxPageCount;
    private String _errorMessage;
    private Exception _errorException;
	Date timeExecuted;
	Date timeCreated;
	long documentID;
	Task task;

	private static final int MAX_PAGE_COUNT = 1000;
	private static final String TXT_EXT = ".txt";
	private static final String DOCINFO_FILE = "DocInfo.json";

	
	@Override
	public long getJobID() {
		return documentID;
	}

	public void setDocumentID(long documentID) {
		this.documentID = documentID;
	}

	public long getTimeSpan(){
		return timeExecuted.getTime() - timeCreated.getTime();
	}

	public boolean isExisting() {
		return _isExisting;
	}

	public boolean isSkipExistingTextFiles() {
		return _skipExistingTextFiles;
	}

	private boolean _isExisting;
    
    public String getSource(){
    	return _source;
    }
    
    public String getDestination(){
    	return _destination;
    }

	public String getErrorMessage() {
		return _errorMessage;
	}
	
	public Exception get_errorException() {
		return _errorException;
	}

    public boolean isDone() {
        return _done;
    }

    public boolean isSkipped(){
    	return _skipped;
    }
    
    public int getPageCount() {
        return _pageCount;
    }

    public Date getStartTime() {
        return _startTime;
    }

    public boolean isSuccess() {
        return _success;
    }
    
	public ExtractionTask(Task task)
	{
		this.task = task;
		this.setDocumentID(task.getJobID());
    	_skipExistingTextFiles = true;
    	_source = ""; 				//task.getSourcePath();
        _destination = ""; 			//task.getDestinationPath();
        _success = false;
        _skipped = false;
        _pageCount = 0;
        _done = false;
        _maxPageCount = MAX_PAGE_COUNT;
        _extractor = new OutSideInTextExtractor();
        _errorException = null;
        _errorMessage = null;
	}
    
    public void run() {
        System.out.println("Extraction Start!!");

        try{
            _startTime = new Date();
            _pageCount = extractText(this.task.getSourcePath(), this.task.getDestinationPath(), false);
            _success = true;
        } catch (Exception e) {
            _success = false;
            _errorMessage = "Conversion failed for " + _source + "\n"+ e;
            _errorException = e;
        }
        _done = true;
        System.out.println("Extraction Completed!!");
    }
    
    private int extractText(String inputFile, String outputFile, boolean skipExistingTextFiles) throws IOException, Exception {
        int pageCount = 0;
        
        GetDocumentFileInfo(inputFile);
        
		System.out.println("Input path, output path are required.");
		System.out.println("Usage: PdfExport InputPath OutputPath "+
											 "ConfigurationFile [Timeout(in milliseconds)]");
		System.out.println("Input Path: " + this.getSource());
		System.out.println("Output Path: " + this.getDestination());
        
        File outFile = new File(this.getDestination());
        
        if (outFile.exists() && skipExistingTextFiles){
        	_skipped = true;
        	_isExisting = true;
        	System.out.println("Skipping file " + org.apache.commons.io.FilenameUtils.getName(this.getSource()) + " the text file exists.");
        	return pageCount;
        }
        
        // Copy the input file to a local directory before we work on it.
        File nasInputFile = new File(this.getSource());
        
        File tempFile = File.createTempFile(
        		org.apache.commons.io.FilenameUtils.getBaseName(nasInputFile.getName()) + PADDING, 
        		org.apache.commons.io.FilenameUtils.getExtension(nasInputFile.getName()),
        		org.apache.commons.io.FileUtils.getTempDirectory());
        
        //File tempFile = new File(tempDir, nasInputFile.getName());
        org.apache.commons.io.FileUtils.copyFile(nasInputFile, tempFile);
        
        // A little user entertainment.
        System.out.println("Extracting from " + org.apache.commons.io.FilenameUtils.getName(this.getSource()) + " (" + tempFile.length()/1024 + " KB)");
        
        // Creating a text directory if it doesn't exist.
        File directory = new File(outputFile); //, outputFile.lastIndexOf(File.separator)));
        System.out.println("Create Folder: " + directory);
        
        if(!directory.exists()){
            directory.mkdirs();
        }
        try	{
        	pageCount = _extractor.extract(tempFile, outFile, _maxPageCount);
        	_skipped = _extractor.isSkipped();
        } finally {
            org.apache.commons.io.FileUtils.deleteQuietly(tempFile);
        }

        return pageCount;
    }

	@Override
	public Task getTask() {
		return this.task;
	}

	// Get the process Task from the Main Task...
	@Override
	public List<String> getTasks() {
		return task.getProcessTasks();
	}
	private void GetDocumentFileInfo(String sourcePath){
	
		System.out.println("Getting Document Info");
		ObjectMapper mapper = new ObjectMapper();
		
		String docInfoJSN = sourcePath + "\\" + DOCINFO_FILE;
		System.out.println(docInfoJSN);
		FileInputStream fileStream; 
		String jsonString = "";
		try {
			fileStream = new FileInputStream(docInfoJSN);

			java.io.BufferedInputStream bis = new java.io.BufferedInputStream(fileStream);
			java.io.InputStreamReader isr = new java.io.InputStreamReader(bis);
			java.io.BufferedReader br = new java.io.BufferedReader(isr);
			String strContent = "";
			try {
				while ((strContent = br.readLine()) != null) {
					jsonString += strContent;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		
		NativeFile nDoc = new NativeFile();
	    try {
			nDoc = mapper.readValue(jsonString, NativeFile.class);
	    	_source = (nDoc.getInternalPath());
	
			File sFile = new File(this.getSource());
	
			int iIdx = sFile.getName().lastIndexOf(".");
			int iLen = 0;
			if (iIdx != 0) {
				iLen = sFile.getName().length() - iIdx;
			}
			if (iIdx > 0 && iIdx <= sFile.getName().length() - iLen ) {      
				_destination = this.task.getDestinationPath() + "\\" +  sFile.getName().substring(0, iIdx) + TXT_EXT;      
			}  
			
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
