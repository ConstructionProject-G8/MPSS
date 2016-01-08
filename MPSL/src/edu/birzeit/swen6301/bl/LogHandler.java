package edu.birzeit.swen6301.bl;

public class LogHandler {

	/**
	 * 
	 */
	public String eventLogPath;
	/**
	 * 
	 */
	public String errorLogPath;
	/**
	 * Getter of eventLogPath
	 */
	public String getEventLogPath() {
	 	 return eventLogPath; 
	}
	/**
	 * Setter of eventLogPath
	 */
	public void setEventLogPath(String eventLogPath) { 
		 this.eventLogPath = eventLogPath; 
	}
	/**
	 * Getter of errorLogPath
	 */
	public String getErrorLogPath() {
	 	 return errorLogPath; 
	}
	/**
	 * Setter of errorLogPath
	 */
	public void setErrorLogPath(String errorLogPath) { 
		 this.errorLogPath = errorLogPath; 
	}
	/**
	 * 
	 */
	public void writeError() { 
		// TODO Auto-generated method
	 }
	/**
	 * 
	 */
	public void writeEvent() { 
		// TODO Auto-generated method
	 } 

}
