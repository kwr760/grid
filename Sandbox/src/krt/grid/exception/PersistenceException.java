package krt.grid.exception;

public class PersistenceException  extends Exception {
	private static final long serialVersionUID = 5153946628945954787L;

	public PersistenceException() {
	}
	
	public PersistenceException(String msg) {
		super(msg);
	}
}
