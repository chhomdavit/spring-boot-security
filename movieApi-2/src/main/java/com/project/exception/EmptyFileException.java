package com.project.exception;

@SuppressWarnings("serial")
public class EmptyFileException extends Throwable {

	public EmptyFileException(String message) {
		super(message);
	}
}
