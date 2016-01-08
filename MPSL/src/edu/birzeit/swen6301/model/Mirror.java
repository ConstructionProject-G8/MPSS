package edu.birzeit.swen6301.model;

import java.io.*;
import java.net.*;

import edu.birzeit.swen6301.model.StreamException;
import edu.birzeit.swen6301.util.DataFormatter;
import edu.birzeit.swen6301.util.LogHandler;

/**
 * This class used to package data part (mirror) and encapsulate its function.<p>
 * <p>
 * Main Functions:<p>
 * 1. Open data stream from valid URL.<p>
 * 2. If URL is manifest, then it will be included as new attached manifest and so on recursively like a tree.<p>
 *  <p>
 */
public class Mirror implements IPart {
    private static final int BUFFER_SIZE = 4096;

	private URL URL;	
	private String mimeType;
	private long contentLength;
	private String url;
	private Segment segment;
	private Manifest manifest;
	private boolean endLess;

	private HttpURLConnection httpConn;
	private URLConnection urlConn;
	private InputStream inputStream;
	private byte[] bytes;
	private IPart parentPart;
	private IPart previousPart;
	private IPart nextPart;
	
	public Mirror() {
		
	}
	
	public Mirror(String url) throws MalformedURLException {
		this.setUrl(url);
	}

	/**
	 * 
	 * @return 
	 */
	public InputStream getInputStream() {
	 	 return this.inputStream; 
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
	 * @param parentPart the parentPart to set
	 */
	public void setParentPart(IPart parentPart) {
		this.parentPart = parentPart;
	}

	/**
	 * @param nextPart the nextPart to set
	 */
	public void setNextPart(IPart nextPart) {
		this.nextPart = nextPart;
	}

	/**
	 * @param previousPart the previousPart to set
	 */
	public void setPreviousPart(IPart previousPart) {
		this.previousPart = previousPart;
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
		return ((this.getMimeType() != null && this.getMimeType().equalsIgnoreCase(Manifest.CONTENT_TYPE)) || 
				(this.getUrl() != null && this.getUrl().toLowerCase().endsWith(".segments")));
	 }
	
	/**
	 * 
	 * @return 
	 */
	public boolean isEndLess() { 
		return this.endLess;
	}

	/**
	 * @param endLess the endLess to set
	 */
	public void setEndLess(boolean endLess) {
		this.endLess = endLess;
	}
	
	/**
	 * Used to download segment by this mirror.
	 */
	public byte[] download(String url) throws StreamException, ParseException {
		try {
			this.setUrl(url);
			this.openStream();
		} catch (MalformedURLException e) {
			throw new ParseException(e.getMessage(), this, e.getStackTrace());
		}
		
		return this.download();
	}
	
	/**
	 * 
	 * Used to download segment by this mirror.
	 * 
	 * @return
	 * @throws StreamException
	 */
	public byte[] download() throws StreamException {
        try {
    		//inputStream.mark(Integer.MAX_VALUE);
	        int bytesRead = -1;
	        byte[] buffer = new byte[BUFFER_SIZE];
	        bytes = new byte[0];
	        while ((bytesRead = inputStream.read(buffer)) != -1) bytes = DataFormatter.merge(bytes, buffer, bytesRead);
	        //inputStream.reset();		// Reset stream before downloading again.
		} catch (IOException e) {
			throw new StreamException(e.getMessage()+ ", "+ e.getCause(), this, e.getStackTrace());
		} catch (Exception e) {
			throw new StreamException(e.getMessage()+ ", "+ e.getCause(), this, e.getStackTrace());
		} finally {
			//this.closeStream();
		}
		
		return bytes;
	}
	
	/**
	 * 
	 * Used to download all nested segments by this mirror.
	 * 
	 * @return
	 * @throws StreamException
	 */
	public byte[] downloadAllNested() throws StreamException {
	    if (this.hasManifest() && this.getManifest() != null) bytes = this.getManifest().downloadAllNested();
	    else bytes = this.download();
		
		return bytes;
	}
	
	/**
	 * Used to open http stream of this segment by this mirror.
	 * @throws StreamException
	 * @throws ParseException 
	 */
	public InputStream openStream() throws StreamException, ParseException {
		return this.openStream(false);
	}
	
	/**
	 * Used to open http stream of this segment by this mirror.
	 * 
	 * @param openManifestContents
	 * @return
	 * @throws StreamException
	 * @throws ParseException
	 */
	public InputStream openStream(boolean openManifestContents) throws StreamException, ParseException { 
		int responseCode;
        
		try {
			urlConn = this.getURL().openConnection();
			httpConn = (HttpURLConnection) urlConn;
	        responseCode = httpConn.getResponseCode();
	        this.setContentLength(httpConn.getContentLengthLong());
 
	        // always check HTTP response code first
	        if (responseCode == HttpURLConnection.HTTP_OK) {
	        	this.setMimeType(httpConn.getContentType());
	        	
		        if (this.hasManifest() && !openManifestContents) {	// If url refer to other manifest file and manifest contents inn't intended.
		        	inputStream = openManifestStream();
		        } else inputStream = httpConn.getInputStream();		// If url refer to other part of file.
	        } else {
				throw new StreamException("HTTP access is failed.", this);
	        }
		} catch (IOException e) {
			throw new StreamException(e.getMessage()+ ", "+ e.getCause(), this, e.getStackTrace());
		} catch (ClassCastException e) {
			LogHandler.writeError("Mirror error: invalid http URL format for ("+ this.getUrl()+ "), "+ e.getMessage());
			
            try {
		        if (this.hasManifest() && !openManifestContents) {	// If url refer to other manifest file and manifest contents inn't intended.
		        	inputStream = openManifestStream();
		        } else inputStream = urlConn.getInputStream();	// If url refer to other part of file.
			} catch (IOException e1) {
				throw new StreamException("URL access is failed.", this);
			}
		}
		
		return inputStream;
	}
	
	/**
	 * Used to open input stream for the next part as Manifest part.
	 * 
	 * @return
	 * @throws StreamException
	 * @throws ParseException
	 */
	private InputStream openManifestStream() throws StreamException, ParseException {
		Manifest manifest = (Manifest) this.findParent(this);

		if (manifest == null) manifest = new Manifest(this.getUrl(), this.parent());
		else this.previous().setEndLess(true);

		manifest.setPreviousPart(this.previous());
    	this.setManifest(manifest);
    	
    	return manifest.openStream();
	}

	/**
	 * Used to find manifest if already done as parent to be like loop.
	 * 
	 * @param part
	 * @return
	 */
	public IPart findParent(IPart part) {
		if (part == null) return null;
		
		IPart parent = part;
		
		while (true) {
			
			if (parent != part && part.getUrl().equals(parent.getUrl())) return parent;
			else if (parent.parent() == null) return null;
			 
			parent = parent.parent();
		}
	}
	
	/**
	 * Used to close input stream and disconnect (http or url) connection of this segment.
	 */
	public void closeStream() {
        try {
			if (inputStream != null) inputStream.close();
			LogHandler.writeEvent("Mirror event: Input stream of ("+ this.getUrl()+ ") is closed successfully.");
			if (httpConn != null) httpConn.disconnect();
			LogHandler.writeEvent("Mirror event: Http connection of ("+ this.getUrl()+ ") is disconnected successfully.");
		} catch (IOException e) {
			LogHandler.writeError("Mirror stream error: Stream("+ this.getUrl()+ ") closing is failed, "+ e.getMessage());
		}
	}

	@Override
	public IPart parent() {
		return this.parentPart;
	}

	@Override
	public IPart next() {
		if (this.getManifest() == null) return nextPart;
		
		return this.getManifest().next();
	}

	@Override
	public IPart previous() {
		return this.previousPart;
	}
}
