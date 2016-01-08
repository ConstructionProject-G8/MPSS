package edu.birzeit.swen6301.model;

import java.io.*;
import java.util.*;
import edu.birzeit.swen6301.util.*;

/**
 * Encapsulate info of manifest file.
 * @author
 */
public class Manifest implements IPart {
	public static final String CONTENT_TYPE = "text/segments-manifest";

	private List<Segment> segments;
	private String url;
	private byte[] bytes;
	
	int rank = 0;
	String segmentStr = "";
	
	public Manifest() {
		
	}
	
	public Manifest(String url) throws StreamException, ParseException {
		this.loadAndBind(url);
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
	 * @param url 
	 * @throws ParseException
	 * @throws StreamException 
	 */
	public void loadAndBind(String url) throws ParseException, StreamException { 
		this.bind(this.load(url));
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
		rank = 0;
		segmentStr = "";
		
		// Manifests lines loop.
		for (int i=0; i < lines.length; i++) {
			if (lines[i].trim().equals("**") && !segmentStr.trim().equals("")) this.bindSegment();	// If End of segment.
			else segmentStr = segmentStr+ lines[i]+ System.lineSeparator();
		}
		// 	Last segment.
		if (!segmentStr.trim().equals("")) this.bindSegment();
	
	}
	
	/**
	 * Handle segment binder.
	 * @param segmentStr
	 * @param rank
	 */
	public void bindSegment() {
		Segment segment = new Segment();
		
		try {
			segment.bind(segmentStr);
			segment.setRank(rank);
			this.addSegment(segment);	// Add segment.
			// Write event in log.
			LogHandler.writeEvent("Manifest event: (Segment#"+ segment.getRank()+ " in ["+ this.getUrl()+ "] manifest) data bind operation is done successfully.");
			segmentStr = "";	// Reset segment string variable.
			rank++;		// Set next rank.
		} catch (ParseException e) {
			LogHandler.writeError("Manifest error: (Segment#"+ segment.getRank()+ " in ["+ this.getUrl()+ "] manifest) data bind operation is failed, "+ e.getMessage());
		}
	}
	
	/**
	 * Used to download contents of this manifest.
	 * @throws ParseException 
	 */
	public String load(String url) throws StreamException, ParseException {
		this.setUrl(url);
		
		return this.load();
	}
	
	/**
	 * Used to download contents of this manifest.
	 * @throws ParseException 
	 */
	public String load() throws StreamException, ParseException {
		String contents = null;
		
		contents = new String(new Segment(this.getUrl()).download(false));		// Download manifest contents.
		// Write event in log: download successfully.
		LogHandler.writeEvent("Manifest event: "+ this.getUrl()+ " manifest contents is loaded successfully.");
		
		return contents;
	}
	
	/**
	 * Used to download all segments of this manifest.
	 */
	public byte[] download() throws StreamException {
		bytes = new byte[0];
		Segment segment = new Segment();
		this.getSegments().sort(segment);
		
		// Mirrors loop.
		for (int i=0; i < getSegments().size(); i++) {
			try {
				segment = getSegments().get(i);
				bytes = DataFormatter.merge(bytes, segment.download());		// Download segment data and merge it into manifest data.
				// Write event in log: download successfully.
				LogHandler.writeEvent("Segment event: "+ segment.getUrl()+ " downloaded successfully.");
			} catch (StreamException e) {
				// Write error in log.
				LogHandler.writeError("Segment error: ("+ segment.getUrl()+ ") download failure, "+ e.getMessage());
			}		
		}
		
		return bytes;
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
