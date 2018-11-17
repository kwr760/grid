package krt.grid.frame.Task;

import java.io.File;

public interface ITextExtractor {
	boolean isSkipped();
	int extract(File in, File out, int maxPageCount) throws Exception;
}
