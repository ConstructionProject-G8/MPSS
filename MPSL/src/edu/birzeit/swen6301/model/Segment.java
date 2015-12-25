package edu.birzeit.swen6301.model;

import java.util.ArrayList;
import java.util.List;
import java.io.*;
import java.net.MalformedURLException;

import edu.birzeit.swen6301.model.StreamException;
import edu.birzeit.swen6301.util.LogHandler;
import edu.birzeit.swen6301.model.ParseException;

public class Segment implements IPart {
	
	private String url;
	private String mimeType;
	private Manifest manifest;
	private List<Mirror> mirrors;
	private byte[] bytes;
	
	public Segment() {
		
	}
	
	public Segment(String segmentStr) throws ParseException {
		this.bind(segmentStr);
	}
	
	public Segment(String[] lines) throws ParseException {
		this.bind(lines);
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
	 * Getter of mirrors
	 */
	public List<Mirror> getMirrors() {
	 	 return mirrors; 
	}
	/**
	 * Setter of mirrors
	 */
	public void setMirrors(List<Mirror> mirrors) { 
		 this.mirrors = mirrors; 
	}

	/**
	 * Getter of bytes
	 */
	public byte[] getBytes() {
		return bytes;
	}
	/**
	 * Setter of bytes
	 */
	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}

	/**
	 * 
	 * @return 
	 */
	public String getUrl() { 
		return url;
	 }
	/**
	 * Used to add new Mirror into mirrors list.
	 * @param mirror 
	 */
	public void addMirror(Mirror mirror) { 
		if (mirrors == null) mirrors = new ArrayList<Mirror>();
		
		mirrors.add(mirror);
	 }
	
	/**
	 * 
	 * @return 
	 */
	public String getMimeType() { 
		return mimeType;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	} 
	
	/**
	 * Used to bind segments lines into URLs' mirrors.
	 * @param segments 
	 * @throws ParseException
	 */
	public void bind(String[] segmentUrls) throws ParseException {
		// Segments lines loop.
		for (int i=0; i < segmentUrls.length; i++) {
			try {
				this.addMirror(new Mirror(segmentUrls[i]));		// Add mirror.
				// Write event in log.
				LogHandler.writeEvent("Mirror event: "+ segmentUrls[i]+ " inserted successfully.");
			} catch (MalformedURLException e) {
				// Write error in log.
				LogHandler.writeError("Mirror error: invalid URL ("+ segmentUrls[i]+ "), "+ e.getMessage()+ ", "+ e.getCause());
				//throw new ParseException(e.getMessage()+ ", "+ e.getCause(), this, e.getStackTrace());
			}
		}
	}
	
	/**
	 * Used to bind segment string into URLs' mirrors.
	 * @param segmentStr 
	 * @throws ParseException
	 */
	public void bind(String segmentStr) throws ParseException { 
		String segmentUrls[] = segmentStr.split("\\r?\\n");		// Split string of segment into array of string.
		this.bind(segmentUrls);
	}
	
	/**
	 * 
	 * @return 
	 */
	public boolean isEndLess() { 
		return false;
	 }
	
	/**
	 * 
	 * @return 
	 */
	public boolean isEmpty() { 
		return (this.getBytes() == null || this.getBytes().length == 0);
	 }
	
	/**
	 * Used to download first available segment by this mirror.
	 */
	public byte[] download() throws StreamException {
		// Mirrors loop.
		for (int i=0; i < getMirrors().size(); i++) {
			try {
				bytes = getMirrors().get(i).download();		// Download mirror.
				// Write event in log: download successfully.
				LogHandler.writeEvent("Mirror event: "+ getMirrors().get(i).getUrl()+ " downloaded successfully.");
				break;
			} catch (StreamException e) {
				// Write error in log.
				LogHandler.writeError("Mirror error: ("+ getMirrors().get(i).getUrl()+ ") download failure, "+ e.getMessage()+ ", "+ e.getCause());
			}		
		}
		
		return bytes;
	}

	public InputStream openStream() throws StreamException { 
		return null;
	}
}
