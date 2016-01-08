package edu.birzeit.swen6301.util;

import java.io.*;

import edu.birzeit.swen6301.model.IPart;

public class MultiPartStream extends InputStream {

	private IPart part;
	private IPart firstPart;
	
	public MultiPartStream(IPart firstPart) {
		this.firstPart = firstPart;
	}
	
	/**
	 * Move to next part.
	 * @return
	 */
	public IPart nextPart() {
		if (this.part == null) this.part = this.firstPart;
		else this.part = this.part.next();
		
		return this.part;
	}

	/**
	 * 
	 * @param mnf 
	 * @return 
	 */
	public InputStream openStream() {
		return this;
	 }

	/**
	 * Used to read byte by byte.
	 */
	@Override
	public int read() throws IOException {
		return 0; 
		// TODO Auto-generated method
	 } 

	/**
	 * Used to read byte by byte.
	 * 
	 * @param buffer
	 * @return
	 * @throws IOException 
	 */
	@Override
	public int read(byte[] buffer) throws IOException {
		return 0; 
		// TODO Auto-generated method
	 } 

}
