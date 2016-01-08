package edu.birzeit.swen6301.model;

import java.io.*;

import edu.birzeit.swen6301.model.StreamException;

public interface IPart {

	/**
	 * 
	 * @return 
	 */
	public String getUrl();

	/**
	 * 
	 * @return
	 */
	public String getMimeType();

	/**
	 * 
	 * @return 
	 * @throws StreamException
	 */
	public InputStream openStream() throws StreamException;

	/**
	 * 
	 * @return 
	 */
	public boolean isEndLess(); 

}
