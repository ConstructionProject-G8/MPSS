package edu.birzeit.swen6301.model;

import java.io.IOException;

public class StreamException extends IOException {

	/** */
	private IPart failedPart;
	/** */
	private String message;
	
	public StreamException() {
	}
	
	public StreamException(String message, IPart failedPart) {
		this.setMessage(message);
		this.setFailedPart(failedPart);
	}
	
	public StreamException(String message, IPart failedPart, StackTraceElement[] stackTrace) {
		this(message, failedPart);
		this.setStackTrace(stackTrace);
	}
	
	/**
	 * Getter of failedPart
	 */
	public IPart getFailedPart() {
	 	 return failedPart; 
	}
	/**
	 * Setter of failedPart
	 */
	public void setFailedPart(IPart failedPart) { 
		 this.failedPart = failedPart; 
	}
	
	/**
	 * Getter of message
	 */
	public String getMessage() {
	 	 return message; 
	}
	/**
	 * Setter of message
	 */
	public void setMessage(String message) { 
		 this.message = message; 
	}
	
	/**
	 * 
	 */
	public void segmentTrace() { 
		// TODO Auto-generated method
	 } 

}
