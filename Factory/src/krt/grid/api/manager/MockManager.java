package krt.grid.api.manager;

import java.util.LinkedList;

import krt.grid.api.manager.ManagerAPI;
import krt.grid.objects.Result;


public class MockManager implements ManagerAPI {
    public void setHost(String host) {
    }
	public void	test() {
	}
	public void	halt() {
	}
	public void	reset() {
	}
	public String version() {
		return "0.0.0.0";
	}
	public void sendResults(LinkedList<Result> results) {
	}
	@Override
	public String Status() {
		return "Task Manager Status - not implemented";
	}
}
