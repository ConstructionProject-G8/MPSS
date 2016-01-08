package edu.birzeit.swen6301.model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import edu.birzeit.swen6301.model.ParseException;
import edu.birzeit.swen6301.util.DataFormatter;
import edu.birzeit.swen6301.util.LogHandler;
import edu.birzeit.swen6301.util.MultiPartStream;
import edu.birzeit.swen6301.model.StreamException;

/**
 * Encapsulate info of manifest file.
 * @author
 */
public class Manifest implements IPart {
	public static final String CONTENT_TYPE = "text/segments-manifest";

	private List<Segment> segments;
	private String url;
	private byte[] bytes;
	
	public Manifest(String url) {
		this.setUrl(url);
	}
	
	/**
	 * Getter of segments
	 */
	public List<Segment> getSegments() {
	 	 return segments; 
	}
	/**
	 * Setter of segments
	 */
	public void setSegments(List<Segment> segments) { 
		 this.segments = segments; 
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
	 */
	public void setUrl(String url) { 
		 this.url = url; 
	}

	/**
	 * @return the bytes
	 */
	public byte[] getBytes() {
		return bytes;
	}

	/**
	 * @param bytes the bytes to set
	 */
	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
	} 

	/**
	 * 
	 * @return 
	 */
	public String getMimeType() { 
		return CONTENT_TYPE;
	 }
	/**
	 * 
	 * @param manifestStr 
	 * @throws ParseException
	 */
	public void bind(String manifestStr) throws ParseException { 
		String lines[] = manifestStr.split("\\r?\\n");		// Split string of segment into array of string.
		this.bind(lines);
	}
	
	/**
	 * Used to bind manifest lines into segments.
	 * @param lines 
	 * @throws ParseException
	 */
	public void bind(String[] lines) {
		int rank = 0;
		String segmentStr = "";
		
		// Manifests lines loop.
		for (int i=0; i < lines.length; i++) {
			if (lines[i].equals("**") && !segmentStr.trim().equals("")) {	// If End of segment.
				Segment segment = new Segment();
				
				try {
					segment.bind(segmentStr);
					segment.setRank(rank);
					this.addSegment(segment);	// Add mirror.
					// Write event in log.
					LogHandler.writeEvent("Segment event: (Segment#"+ segment.getRank()+ " in ["+ this.getUrl()+ "] manifest) data bind operation is done successfully.");
					segmentStr = "";	// Reset segment string variable.
					rank++;		// Set next rank.
				} catch (ParseException e) {
					LogHandler.writeError("Segment error: (Segment#"+ segment.getRank()+ " in ["+ this.getUrl()+ "] manifest) data bind operation is failed, "+ e.getMessage());
				}
			} else {
				segmentStr = segmentStr+ lines[i]+ System.lineSeparator();
			}
		}
	}
	
	/**
	 * Used to download all segment in this manifest.
	 */
	public byte[] download() throws StreamException {
		Segment segment = new Segment();
		this.getSegments().sort(segment);
		
		// Mirrors loop.
		for (int i=0; i < getSegments().size(); i++) {
			try {
				segment = getSegments().get(i);
				DataFormatter.merge(bytes, segment.download());		// Download segment.
				this.setUrl(segment.getUrl());
				// Write event in log: download successfully.
				LogHandler.writeEvent("Mirror event: "+ segment.getUrl()+ " downloaded successfully.");
			} catch (StreamException e) {
				// Write error in log.
				LogHandler.writeError("Mirror error: ("+ segment.getUrl()+ ") download failure, "+ e.getMessage());
			}		
		}
		
		return null;//bytes;
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
	 * @param is 
	 * @throws StreamException
	 */
	public void openStream(MultiPartStream is) throws StreamException { 
		// TODO Auto-generated method
	 }
	/**
	 * 
	 * @param segment 
	 */
	public void addSegment(Segment segment) { 
		if (segments == null) segments = new ArrayList<Segment>();
		
		segments.add(segment);
	 }
	/**
	 * 
	 * @return 
	 * @throws StreamException
	 */
	public InputStream openStream() throws StreamException { 
		// TODO Auto-generated method
		return null;
	 }

}
