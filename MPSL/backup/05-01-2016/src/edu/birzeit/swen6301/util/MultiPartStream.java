package edu.birzeit.swen6301.util;

import java.io.*;

import edu.birzeit.swen6301.model.IPart;
import edu.birzeit.swen6301.model.Manifest;
import edu.birzeit.swen6301.model.ParseException;
import edu.birzeit.swen6301.model.StreamException;

public class MultiPartStream extends InputStream {

    private InputStream inputStream;
	private IPart part;
	private IPart firstPart;
	
	public MultiPartStream(IPart firstPart) {
		this.firstPart = firstPart;
	}
	
	public MultiPartStream(String url) throws StreamException, ParseException {
		this.firstPart = new Manifest(url, null);
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
		throw new RuntimeException("read() operation isn't implemented yet.");
	 } 

	/**
	 * Used to read all parts serially block by block.
	 * 
	 * @param buffer
	 * @return
	 * @throws IOException 
	 */
	@Override
	public int read(byte[] buffer) throws IOException {
        int bytesRead = -1;
        
		try {
			if (inputStream == null) {		// End of part.
				if (this.nextPart() != null) {
					if (this.part.openStream() == null) {
						buffer = new byte[0];
						return 0;
					} else {
						//this.part.openStream().mark(Integer.MAX_VALUE);
						inputStream = this.part.openStream();
					} 
				} else {
					this.part = null;
					return -1;	// EOF
				}
			}
			
	        if ((bytesRead = inputStream.read(buffer)) == -1) {  // End of part.
	        	inputStream = null;
	        	//this.part.openStream().reset();		// Reset stream before downloading again.
	        	return this.read(buffer);
	        }
		} catch (StreamException e) {
        	throw new IOException(e.getMessage());
        } catch (ParseException e) {
        	throw new IOException(e.getMessage());
		}
        
		return bytesRead;
	 } 

}
