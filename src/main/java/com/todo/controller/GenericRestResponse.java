package com.todo.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GenericRestResponse<T> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9133219992259902371L;
	
	private List<String> errorMessages = new ArrayList<String>();
	private T content;
	
	public static final GenericRestResponse<String> SUCCESS = new GenericRestResponse<String>("success");
	
	public GenericRestResponse(T content) {
		this.content = content;
	}
	
	public T getContent() {
		return content;
	}
	
	public void setContent(T content) {
		this.content = content;
	}
	
	public boolean getHasError() {
		return !errorMessages.isEmpty();
	}
	
	public void addErrorMessage(String errorMessage) {
		errorMessages.add(errorMessage);
	}
	
	public List<String> getErrorMessage() {
		return errorMessages;
	}
}
