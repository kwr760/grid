package krt.grid.frame.Task;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import krt.grid.frame.ITask;
import krt.grid.frame.NativeFile;
import krt.grid.frame.jobs.PdfExport;
import krt.grid.objects.Task;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;


@SuppressWarnings("unused")
public class ConvertTask implements ITask, Runnable, Serializable {
	private static final long serialVersionUID = -1831708370151890493L;
	Date timeExecuted;
	Date timeCreated;
	long documentID;
	Task task;
    private String _errorMessage;
    private Exception _errorException;
	private String jppfConfigFilePath = "";
	private boolean _success, _done;
    private String outputFile = "";
    private String nativeSourceFile = "";
    
	private static final String JPPFConfigFile = "JPPFConfig.cfg";
	private static final String JPPFConfigPathKey = "JPPFConfigPath";
	private static final String PROP_FILE="gridwebservice.properties";
	private static final String DOCINFO_FILE = "DocInfo.json";
	private static final String PDF_EXT = ".pdf";

	public ConvertTask(Task task)
	{
		timeCreated = new Date();
		this.task = task;
		this.setDocumentID(task.getJobID());
	    _errorMessage = "";
	    _errorException = null;
		jppfConfigFilePath = "";
	    _success = false;
	    _done = false;
		String jppfFilePath = "C:\\Configuration\\" + JPPFConfigFile;
		this.setJPPFConfigFilePath(jppfFilePath);
	}
	
	private void GetDocumentFileInfo(String sourcePath){
		System.out.println("Getting Document Info");
		ObjectMapper mapper = new ObjectMapper();
		
		String docInfoJSN = sourcePath + "\\" + DOCINFO_FILE;
		System.out.println(docInfoJSN);
		FileInputStream fileStream; 
		String jsonString = "";
		java.io.BufferedInputStream bis;
		java.io.InputStreamReader isr;
		java.io.BufferedReader br;
		try {
			fileStream = new FileInputStream(docInfoJSN);
			bis = new java.io.BufferedInputStream(fileStream);
			isr = new java.io.InputStreamReader(bis);
			br = new java.io.BufferedReader(isr);
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
		bis = null;
		isr = null;
		br = null;
		
		NativeFile nDoc = new NativeFile();
		
	    try {
			nDoc = mapper.readValue(jsonString, NativeFile.class);
			this.setNativeSourceFile(nDoc.getInternalPath());
			
			File sFile = new File(this.getNativeSourceFile());

			int iIdx = sFile.getName().lastIndexOf(".");
			int iLen = 0;
			if (iIdx != 0) {
				iLen = sFile.getName().length() - iIdx;
			}
			if (iIdx > 0 && iIdx <= sFile.getName().length() - iLen ) {      
				this.setOutputFile(this.task.getDestinationPath() + "\\" +  sFile.getName().substring(0, iIdx) + PDF_EXT);      
			}  
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void setJPPFConfigFilePath(String jppfCfgPath){
		this.jppfConfigFilePath = jppfCfgPath;
	}

	public String getJPPFConfigFilePath(){
		return this.jppfConfigFilePath;
	}

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
	
	@Override
	public boolean isSuccess(){
		return timeExecuted != null;
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
	
	@Override
	public void run() {
		timeExecuted = new Date();
		
		System.out.println("Convert: " + getTimeSpan());

		try
		{
			ConvertPdfFile();
			
		} catch (Exception ioE){
            _success = false;
            _errorMessage = "Conversion failed for " + task.getSourcePath() + "\n"+ ioE;
            _errorException = ioE;
			System.out.println("Convert Completed with Error:" + ioE.getMessage());
		}
	}
	
	private void ConvertPdfFile() {
	
		String cfg = this.getJPPFConfigFilePath();
	    GetDocumentFileInfo(task.getSourcePath());
		String ifp = this.getNativeSourceFile();  
		String ofp = this.getOutputFile();  

		int timeout = 10000;
		
		PdfExport ex;
		try {
			System.out.println("Input path, output path and configuration file are required.");
			System.out.println("Usage: PdfExport InputPath OutputPath "+
												 "ConfigurationFile [Timeout(in milliseconds)]");
			System.out.println();
			System.out.println("Config File: " + cfg);
			ex = new PdfExport(cfg);
			
			ex.convert(ifp, ofp, timeout);
			
			System.out.println("Conversion Completed!!!");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
            _success = false;

            _errorMessage = "Conversion failed for " + task.getSourcePath() + "\n"+ e;
            _errorException = e;
			System.out.println("Convert Completed with Error:" + e.getMessage());
		} catch (IOException ioe) {
			ioe.printStackTrace();
            _success = false;

            _errorMessage = "Conversion failed for " + task.getSourcePath() + "\n"+ ioe;
            _errorException = ioe;
			System.out.println("Convert Completed with Error:" + ioe.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
            _success = false;

            _errorMessage = "Conversion failed for " + task.getSourcePath() + "\n"+ e;
            _errorException = e;
			System.out.println("Convert Completed with Error:" + e.getMessage());
		}
	}

	@Override
	public Task getTask() {
		return this.task;
	}

	public void setOutputFile(String outputFile) {
		this.outputFile = outputFile;
	}

	public String getOutputFile() {
		return outputFile;
	}
	
	public void setNativeSourceFile(String nativeSourceFile) {
		this.nativeSourceFile = nativeSourceFile;
	}

	public String getNativeSourceFile() {
		return nativeSourceFile;
	}

	@Override
	public List<String> getTasks() {
		return task.getProcessTasks();
	}
}
