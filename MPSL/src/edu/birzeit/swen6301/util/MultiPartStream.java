package edu.birzeit.swen6301.util;

import java.io.*;

import edu.birzeit.swen6301.model.IPart;
import edu.birzeit.swen6301.model.Manifest;
import edu.birzeit.swen6301.model.ParseException;
import edu.birzeit.swen6301.model.StreamException;

/**
 * To realize all related parts for certain manifest's URL as linked list.<p>
 * <p>
 * Main Functions:<p>
 * 1. Extends java.io.InputStream class.<p>
 * 2. Use manifest object to access series of opened streams as one linked list.<p>
 * 3. Override read method: Retrieve blocks for certain part one by one,
 *    and then go to the other blocks in the next part and so on until finishing all parts.<p>
 * 4. Sequancial file reading: Retrieve parts sequentially from the prepared linked list.<p>
 * <p>
 * @author
 */
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
	 * Used to return current instance of this class.
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
		byte[] bytes = new byte[1];
		this.read(bytes);
		
		return Byte.toUnsignedInt(bytes[0]);
	 } 

	/**
	 * Used to read all parts sequancially block by block.
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
						//this.part.openStream().mark(Integer.MAX_VALUE);		// Buffering Stream.
						inputStream = this.part.openStream();
					} 
				} else {
					this.part = null;
					return -1;	// EOF
				}
			}
			
	        if ((bytesRead = inputStream.read(buffer)) == -1) {  // End of part.
	        	inputStream = null;
	        	//this.part.openStream().reset();		// Reset stream before downloading again.	// Reset Stream.
	        	return this.read(buffer);
	        }
		} catch (StreamException e) {
        	throw new IOException(e.getMessage());
        } catch (ParseException e) {
        	throw new IOException(e.getMessage());
		}
        
		return bytesRead;
	 }
	
	/**
	 * Used to close open streams in all related parts serially one by one.
	 */
	@Override
	public void close() {
		if (this.firstPart != null) this.firstPart.closeStream();
	}
}
