package krt.grid.frame;

public class GridWebServiceException extends Exception {
	private static final long serialVersionUID = 2839350689888688886L;

	public GridWebServiceException(){super();}
	
	public GridWebServiceException(Throwable cause){super(cause);}
	
	public GridWebServiceException(String message, Throwable cause){super(message, cause);}
	
	public GridWebServiceException(String message){super(message);}
}
