package edu.birzeit.swen6301.model;

public class ParseException extends Exception {

	/**
	 * 
	 */
	public String message;
	/**
	 * 
	 */
	public IPart failedPart;
	
	public ParseException(String message, IPart failedPart) {
		this.setMessage(message);
		this.setFailedPart(failedPart);
	}
	
	public ParseException(String message, IPart failedPart, StackTraceElement[] stackTrace) {
		this(message, failedPart);
		this.setStackTrace(stackTrace);
	}
	
	/**
	 * Getter of messge
	 */
	public String getMessage() {
	 	 return message; 
	}
	/**
	 * Setter of messge
	 */
	public void setMessage(String messge) { 
		 this.message = messge; 
	}
	/**
	 * Getter of ipart
	 */
	public IPart getFailedPart() {
	 	 return failedPart; 
	}
	/**
	 * Setter of ipart
	 */
	public void setFailedPart(IPart ipart) { 
		 this.failedPart = ipart; 
	}
	/**
	 * 
	 */
	public void segmentTrace() { 
		// TODO Auto-generated method
	 } 

}
