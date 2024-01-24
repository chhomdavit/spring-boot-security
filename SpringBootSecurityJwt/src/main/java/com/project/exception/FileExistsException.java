package com.project.exception;

public class FileExistsException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8206776621598095124L;

	public FileExistsException(String message) {
		super(message);
	}
}
