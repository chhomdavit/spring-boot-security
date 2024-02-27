package com.project.dto;

public class ResponseObjectDto {

	private String status;
	private String message;
	private Object data;
	
	public ResponseObjectDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ResponseObjectDto(String status, String message, Object data) {
		super();
		this.status = status;
		this.message = message;
		this.data = data;
	}
	

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "ResponseObjectDto [status=" + status + ", message=" + message + ", data=" + data + "]";
	}
	
}

