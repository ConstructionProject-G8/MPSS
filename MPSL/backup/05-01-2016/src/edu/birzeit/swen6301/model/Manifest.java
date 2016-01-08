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
	
	private IPart parentPart;
	private IPart nextPart;
	private IPart previousPart;
	private List<Segment> segments;
	private String url;
	private byte[] bytes;
	private boolean endLess;
	
	int rank = 0;
	String segmentStr = "";
	
	public Manifest() {
		
	}
	
	public Manifest(String url, IPart parent) throws StreamException, ParseException {
		this.setParentPart(parent);
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
	 * Used to sort segments according to assigned rank.
	 */
	public boolean sortSegments() {
		if (this.getSegments() == null) return false;
		else this.getSegments().sort(new Segment());
		
		return true;
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
		//System.out.println(this.load(url));
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
			segment.setParentPart(this);
			segment.bind(segmentStr);
			segment.setRank(rank);
			this.addSegment(segment);	// Add segment.
			
			if (rank == 0) segment.setPreviousPart(this.previous());	// If first segment in this manifest.
			else {
				IPart lastPart = lastOf(this.getSegments().get(rank-1));
				
				if (lastPart != null) {
					segment.setPreviousPart(lastPart);
					lastPart.setNextPart(segment);
				}
			}
			
			// Write event in log.
			LogHandler.writeEvent("Manifest event: (Segment#"+ segment.getRank()+ " in ["+ this.getUrl()+ "] manifest) data bind operation is done successfully.");
			segmentStr = "";	// Reset segment string variable.
			rank++;		// Set next rank.
		} catch (ParseException e) {
			LogHandler.writeError("Manifest error: (Segment#"+ segment.getRank()+ " in ["+ this.getUrl()+ "] manifest) data bind operation is failed, "+ e.getMessage());
		}
	}

	/**
	 * Used to get last part of the chain that started by passed part.
	 * And also used assign previous part for whole chain that is started by the passed part.
	 * 
	 * @param part
	 * @return
	 */
	public IPart lastOf(IPart part) {
		if (part == null) return null;
		IPart last = part;
		IPart previous = part;
		
		while (true) {
			if (last.isEndLess()) return null;				// If recursive loop.
			else if (last.next() == null) return last;		// If last reached.
			last = last.next();
			last.setPreviousPart(previous);
			previous = last;
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
		
		LogHandler.writeEvent("Manifest event: Loading ("+ this.getUrl()+ ") manifest contents is started.");
		contents = new String(new Segment(this.getUrl(), true).download());	// Download manifest contents.
		// Write event in log: download successfully.
		LogHandler.writeEvent("Manifest event: Loading ("+ this.getUrl()+ ") manifest contents is completed successfully.");
		
		return contents;
	}
	
	/**
	 * Used to download all segments of this manifest.
	 * 
	 * @return
	 * @throws StreamException
	 */
	public byte[] download() throws StreamException {
		if (this.getSegments() == null || this.getSegments().get(0) == null) return new byte[0]; 	// If empty.
		
		return this.getSegments().get(0).download();
	}
	
	/**
	 * Used to download all nested segments by this mirror.
	 * 
	 * @param loadManifestContents
	 * @return
	 * @throws StreamException
	 */
	public byte[] downloadAllNested() throws StreamException {
		bytes = new byte[0];
		
		if (getSegments() == null) return bytes;	// If Empty.
		
		Segment segment = new Segment();
		this.sortSegments();
		
		// Mirrors loop.
		for (int i=0; i < getSegments().size(); i++) {
			try {
				segment = getSegments().get(i);
				bytes = DataFormatter.merge(bytes, segment.downloadAllNested());		// Download segment data and merge it into manifest data.
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
		return this.endLess;
	}

	/**
	 * @param endLess the endLess to set
	 */
	public void setEndLess(boolean endLess) {
		this.endLess = endLess;
	}

	/**
	 * 
	 * @param segment 
	 */
	public void addSegment(Segment segment) { 
		if (segments == null) {		// First segment = current first part.
			segments = new ArrayList<Segment>();
		}
		
		segments.add(segment);
	 }

	/**
	 * @param previousPart the previousPart to set
	 */
	public void setPreviousPart(IPart previousPart) {
		this.previousPart = previousPart;
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
	 * 
	 * @return InputStream for the current part.
	 * @throws StreamException
	 * @throws ParseException 
	 */
	public InputStream openStream() throws StreamException, ParseException { 
		if (this.getSegments() == null || this.getSegments().get(0) == null) return null;
		
		return this.getSegments().get(0).openStream();
	}

	@Override
	public IPart parent() {
		return this.parentPart;
	}

	@Override
	public IPart next() {
		if (this.getSegments() == null) return null;
		return this.getSegments().get(1);
	}

	@Override
	public IPart previous() {
		return this.previousPart;
	}

}
