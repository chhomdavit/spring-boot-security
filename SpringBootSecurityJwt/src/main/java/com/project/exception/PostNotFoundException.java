package com.project.exception;

public class PostNotFoundException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3774631635783721094L;
	 
	public PostNotFoundException(String message) {
		super(message);
	}

}
