package edu.birzeit.swen6301.util;

import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LogHandler {
	private static final Logger LOGGER = Logger.getLogger("LogHandler");
	private static LogHandler logHandler;	// To be singleton.

	private String eventLogPath;
	private String errorLogPath;
	private Handler consoleHandler = null;
	private Handler fileHandler  = null;
	
	public LogHandler() {
        //Creating consoleHandler and fileHandler
        consoleHandler = new ConsoleHandler();
        
        try {
			fileHandler  = new FileHandler("debug_log.html");

			//Assigning handlers to LOGGER object
            LOGGER.addHandler(consoleHandler);
            LOGGER.addHandler(fileHandler);
            fileHandler.setFormatter(new LogFormatter());

            LOGGER.config("Configuration done.");

            //Setting levels to handlers and LOGGER
            consoleHandler.setLevel(Level.ALL);
            fileHandler.setLevel(Level.ALL);
            LOGGER.setLevel(Level.ALL);

            //Console handler removed
            //LOGGER.removeHandler(consoleHandler);
            
            LOGGER.log(Level.FINE, "Finer logged");
		} catch (SecurityException e) {
            LOGGER.log(Level.SEVERE, "Error occur in FileHandler.", e);
            e.printStackTrace();
		} catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error occur in FileHandler.", e);
            e.printStackTrace();
		}
        
        LOGGER.finer("MaltipartStream LOGGER handler completed.");
	}

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
	public static void writeError(String msg) {
		if (logHandler == null) logHandler = new LogHandler();
		
		logHandler.log(Level.SEVERE, msg);
	}
	/**
	 * 
	 */
	public static void writeEvent(String msg) { 
		if (logHandler == null) logHandler = new LogHandler();
		
		logHandler.log(Level.INFO, msg);
	} 
	
	/**
	 * 
	 */
	public void log(Level level, String msg) { 
		LOGGER.log(level, msg);
	} 

}
