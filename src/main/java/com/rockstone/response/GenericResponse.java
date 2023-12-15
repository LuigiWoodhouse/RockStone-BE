package com.rockstone.response;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class GenericResponse<T> implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	private int statusCode;
	private String message;
	private transient T data;
	
	public GenericResponse(int code, String msg) {
		statusCode = code;
		message = msg;
	}
	
	public GenericResponse(int code, String msg, T data) {
		statusCode = code;
		message = msg;
		this.data = data;
	}
	
	
}
