package krt.grid.api.manager;

import java.util.LinkedList;

import krt.grid.objects.Result;

public interface ManagerAPI {
	public void setHost(String host); 
	public void	test() throws Exception;
	public void	reset() throws Exception;
	public void	halt() throws Exception;
	public String version() throws Exception;
	public void sendResults(LinkedList<Result> results) throws Exception;
	public String Status();
}
