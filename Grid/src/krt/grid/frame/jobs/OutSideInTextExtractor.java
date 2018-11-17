package krt.grid.frame.jobs;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import krt.grid.frame.Task.ITextExtractor;


import net.bitform.api.secure.SecureOptions;
import net.bitform.api.secure.SecureRequest;
import net.bitform.api.secure.SecureResponse;

public class OutSideInTextExtractor implements ITextExtractor, Serializable {
	private static final long serialVersionUID = 8126389181188751139L;
	private boolean skipped = false;
	
	@Override
	public boolean isSkipped() {
		return skipped;
	}

	@Override
	public int extract(File in, File out, int maxPageCount) throws IOException, Exception {
		if (in == null || out == null){
			throw new IllegalArgumentException("Input and output files must be provided.");
		} else if (!in.exists()){
			throw new IllegalArgumentException("The provided input file does not exist. " + in.getName());
		} else if (maxPageCount < 0){
			throw new IllegalArgumentException("The maxPageCount can not be negative.  It must be a positive value or 0 for no limit.");
		}
		
		int pageCount = 0;
		
		if(maxPageCount != 0 && pageCount > maxPageCount){
        	System.out.println("Skipping file, max page count (" + maxPageCount + ") exceeded ("+ pageCount +").");
        	skipped = true;
		} else {
			
			SecureRequest request = new SecureRequest();
			request.setOption(SecureOptions.SourceDocument, in);
			request.setOption(SecureOptions.JustAnalyze, true);
			request.setOption(SecureOptions.OutputType, SecureOptions.OutputTypeOption.ToText);
			request.setOption(SecureOptions.ToTextEncoding, SecureOptions.ToTextEncodingOption.UTF8);
			request.setOption(SecureOptions.ResultDocument, out);
			
			request.execute();
			
			SecureResponse response = request.getResponse();
			
			if (!response.getResult(SecureOptions.WasProcessed)){
				throw new Exception("The document failed extraction by CleanContent but did not generate an error.");
			}
		}
		return pageCount;
	}
}
