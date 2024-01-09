package com.project.exception;

@SuppressWarnings("serial")
public class FileExistsException extends RuntimeException{

	public FileExistsException(String message) {
		super(message);
	}
}
