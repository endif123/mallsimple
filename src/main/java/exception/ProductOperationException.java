package exception;

public class ProductOperationException extends RuntimeException{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7516321225941628931L;

	public ProductOperationException(String msg) {
		super(msg);
	}
}
