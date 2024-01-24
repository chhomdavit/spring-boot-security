package com.project.exception;

public class NoResultsFoundException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 4921041016415132402L;

	public NoResultsFoundException(String message) {
        super(message);
    }
}
