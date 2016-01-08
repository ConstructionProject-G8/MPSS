package edu.birzeit.swen6301.model;

import java.io.*;

import edu.birzeit.swen6301.model.StreamException;

public interface IPart {

	/**
	 * 
	 * @return next part.
	 */
	public IPart next();

	/**
	 * 
	 * @return previous part.
	 */
	public IPart previous();

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
	 * Used to open input stream for this part.
	 * 
	 * @return 
	 * @throws StreamException
	 * @throws ParseException 
	 */
	public InputStream openStream() throws StreamException, ParseException;
	
	/**
	 * Used to fetch bytes for this part.
	 * 
	 * @return
	 * @throws StreamException
	 * @throws ParseException
	 */
	public byte[] download() throws StreamException, ParseException;

	/**
	 * @param nextPart the next part to set
	 */
	public void setNextPart(IPart nextPart);

	/**
	 * @param previousPart the previous part to set
	 */
	public void setPreviousPart(IPart previousPart);

	/**
	 * 
	 * @return 
	 */
	public boolean isEndLess(); 

}
