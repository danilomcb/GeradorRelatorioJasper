package br.com.projetobase.report.exception;

public class ServiceOrderNotFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ServiceOrderNotFoundException() {
	}

	public ServiceOrderNotFoundException(String message) {
		super(message);
	}
}
