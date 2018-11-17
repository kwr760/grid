package krt.grid.frame.jobs;

import com.outsideinsdk.*;
import java.io.*;
import java.security.*;
import java.util.*;

public class PdfExport implements Serializable
{
	private static final long serialVersionUID = 3895129281307667383L;
	private static final String INPUTPATHKEY = "inputpath";
	private static final String OUTPUTPATHKEY = "outputpath";
	private static final String OUTPUTIDKEY = "outputid";

	Properties configProps = new Properties();

	public PdfExport(String cfp)
		throws FileNotFoundException, IOException
	{
		parseConfig(cfp);
	}

	public void parseConfig(String cfp)
		throws FileNotFoundException, IOException
	{
		File cff = new File(cfp);

		if (!cff.exists() || !cff.isFile() || !cff.canRead())
		{
			throw(new InvalidParameterException("Configuration file unreadable."));
		}

		BufferedReader cfr = new BufferedReader(new FileReader(cff));

		String line;
		while ((line = cfr.readLine()) != null)
		{
			processLine(line);
		}
	}

	private void processLine(String l)
	{
		int indPound = l.indexOf('#');

		String line = (indPound == -1) ? l.trim() : l.substring(0, indPound).trim();

		if (line.length() != 0)
		{
			StringTokenizer stl = new StringTokenizer(line);
            String key = stl.nextToken();
            String value = stl.nextToken();
            while(stl.hasMoreTokens())
            {
              value +=" " + stl.nextToken();
            }
            configProps.setProperty(key, value);
		}
	}

	public void convert(String ifp, String ofp, long timeout) throws Exception
	{
		String oid = configProps.getProperty(OUTPUTIDKEY);

		System.out.println("Input Path: "+ifp);
		System.out.println("Output Path: " +ofp);
		System.out.println("Output ID: "+oid);
		
		configProps.remove(INPUTPATHKEY);
		configProps.remove(OUTPUTPATHKEY);
		
		File iff = new File(ifp);
		File [] iffa;
		if (iff.isDirectory())
			iffa = iff.listFiles();
		else
		{
			iffa = new File[1];
			iffa[0] = iff;
		}

		File off = new File(ofp);
		String path = off.getAbsolutePath();
	    int sep = path.lastIndexOf(System.getProperty("file.separator"));
		File resultDir = new File(path.substring(0, sep));
		System.out.println("resultDir Value:  " + resultDir.toString());
		System.out.println("resultDir Is Directory:  " + resultDir.isDirectory());
		System.out.println("resultDir Exists:  " + resultDir.exists());
		
		if (!resultDir.exists()) 
			resultDir.mkdir();

		System.out.println("call OutsideIn Export");

		Export e = new Export(configProps);
		
		System.out.println("off Is Directory:" + off.isDirectory());
		System.out.println("iffa Value: " + iffa[0].toString());
		if (off.isDirectory())
		{
			String ext = "." + oid.substring(3);
			for (int i=0; i<iffa.length; i++)
			{
				String ifn = iffa[i].toString();
				String ofn = ofp + File.separator + iffa[i].getName() + ext;
				System.out.println("Converting "+ifn+" to "+ofn);
				ExportStatusCode result = e.convert(ifn, ofn, oid, timeout);
	            if (result.getCode() == ExportStatusCode.SCCERR_OK.getCode())
	            {
					   System.out.println("Conversion Success!");
	            }
	            else {
	               System.out.println("Conversion Error: " + result);
	            }
			}
		}
		else
		{
			System.out.println("off Is Directory:" + off.isDirectory());
			for (int i=0; i<iffa.length; i++)
			{
				ExportStatusCode result = e.convert(iffa[i].toString(), ofp, oid, timeout);
	            if (result.getCode() == ExportStatusCode.SCCERR_OK.getCode())
	            {
					   System.out.println("Conversion Successful!");
	            }
	            else {
	               System.out.println("Conversion Error: " + result);
	               throw new Exception("Convertion Failed: " + result);
	            }
			}
		}
	}
}
