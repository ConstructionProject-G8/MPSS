package edu.birzeit.swen6301.model;

import java.io.*;
import java.net.*;

import edu.birzeit.swen6301.model.StreamException;

/**
 * This class used to package data part (segment) and encapsulate its function.
 *  
 */
public class Mirror implements IPart {
    private static final int BUFFER_SIZE = 4096;

    /** URL object */
	private URL URL;	
	/** content type */
	private String mimeType;
	private long contentLength;
	/** Mirror URL address. */
	private String url;
	private Segment segment;
	private Manifest manifest;

	private HttpURLConnection httpConn;
	private InputStream inputStream;
	/** Data bytes */
	private byte[] bytes;
	
	public Mirror() {
		
	}
	
	public Mirror(String url) throws MalformedURLException {
		this.setUrl(url);
	}

	/**
	 * 
	 * @return 
	 */
	public String getMimeType() {
	 	 return mimeType; 
	}
	/**
	 * Setter of mimeType
	 */
	public void setMimeType(String mimeType) { 
		 this.mimeType = mimeType; 
	}
	
	/**
	 * 
	 * @return URL
	 */
	public URL getURL() {
		return URL;
	}
	public void setURL(URL uRL) {
		URL = uRL;
	} 
	
	/**
	 * 
	 * @return 
	 */
	public byte[] getBytes() {
		return bytes;
	}
	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}
	
	/**
	 * 
	 * @return 
	 */
	public long getContentLength() {
		return contentLength;
	}
	public void setContentLength(long contentLength) {
		this.contentLength = contentLength;
	}
	
	/**
	 * 
	 * @return 
	 */
	public String getUrl() {
	 	 return url; 
	}
	/**
	 * Setter of url
	 * @throws MalformedURLException 
	 */
	public void setUrl(String url) throws MalformedURLException { 
		this.url = url;
		this.setURL(new URL(this.getUrl()));
	}
	
	/**
	 * Getter of segment
	 */
	public Segment getSegment() {
	 	 return segment; 
	}
	/**
	 * Setter of segment
	 */
	public void setSegment(Segment segment) { 
		 this.segment = segment; 
	}
	
	/**
	 * Getter of manifest
	 */
	public Manifest getManifest() {
	 	 return manifest; 
	}
	/**
	 * Setter of manifest
	 */
	public void setManifest(Manifest manifest) { 
		 this.manifest = manifest; 
	}
	
	/**
	 * 
	 * @return 
	 */
	public boolean hasManifest() { 
		// TODO Auto-generated method
		return false;
	 }
	
	/**
	 * 
	 * @return 
	 */
	public boolean isEndLess() { 
		// TODO Auto-generated method
		return false;
	}
	
	/**
	 * Used to download segment by this mirror.
	 */
	public byte[] download(String url) throws StreamException, ParseException {
		try {
			this.setUrl(url);
		} catch (MalformedURLException e) {
			throw new ParseException(e.getMessage()+ ", "+ e.getCause(), this, e.getStackTrace());
		}
		
		return this.download();
	}
	
	
	/**
	 * Used to download segment by this mirror.
	 */
	public byte[] download() throws StreamException {
        int bytesRead = -1;
        byte[] buffer = new byte[BUFFER_SIZE];
        bytes = new byte[0];
        openStream();
        
        try {
			while ((bytesRead = inputStream.read(buffer)) != -1) {
				byte[] tmp = new byte[bytes.length+bytesRead];
				System.arraycopy(bytes, 0, tmp, 0, bytes.length);	// Copy bytes array to temporary array.
				System.arraycopy(buffer, 0, tmp, bytes.length, bytesRead);	// Copy fetched bytes into temporary array.
				bytes = tmp;	// Put new expanded array into bytes array.
			}
		} catch (IOException e) {
			throw new StreamException(e.getMessage()+ ", "+ e.getCause(), this, e.getStackTrace());
		}
		
		this.closeStream();
		
		return bytes;
	}
	
	/**
	 * Used to open http stream of this segment by this mirror.
	 */
	public InputStream openStream() throws StreamException { 
		int responseCode;
        
		try {
			httpConn = (HttpURLConnection) this.getURL().openConnection();
	        responseCode = httpConn.getResponseCode();
	        this.setContentLength(httpConn.getContentLengthLong());
 
	        // always check HTTP response code first
	        if (responseCode == HttpURLConnection.HTTP_OK) {
	        	this.setMimeType(httpConn.getContentType());
	            inputStream = httpConn.getInputStream();
	        } else {
				throw new StreamException("HTTP is not OK.", this);
	        }
		} catch (IOException e) {
			throw new StreamException(e.getMessage()+ ", "+ e.getCause(), this, e.getStackTrace());
		}

		return inputStream;
	}
	
	/**
	 * Used to close http stream of this segment.
	 */
	private void closeStream() {
        try {
			if (inputStream != null) inputStream.close();
			if (httpConn != null) httpConn.disconnect();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
