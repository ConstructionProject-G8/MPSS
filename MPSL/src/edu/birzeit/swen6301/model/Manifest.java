package edu.birzeit.swen6301.model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import edu.birzeit.swen6301.model.ParseException;
import edu.birzeit.swen6301.util.LogHandler;
import edu.birzeit.swen6301.util.MultiPartStream;
import edu.birzeit.swen6301.model.StreamException;

/**
 * Encapsulate info of manifest file.
 * @author
 */
public class Manifest implements IPart {

	public List<Segment> segments;
	public String url;
	
	
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
	 * 
	 * @return 
	 */
	public String getMimeType() { 
		// TODO Auto-generated method
		return null;
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
	 * Used to bind segments lines into URLs' mirrors.
	 * @param lines 
	 * @throws ParseException
	 */
	public void bind(String[] lines) throws ParseException {
		// Segments lines loop.
		for (int i=0; i < lines.length; i++) {
			this.addSegment(new Segment(lines[i]));		// Add mirror.
			// Write event in log.
			LogHandler.writeEvent("Mirror event: "+ lines[i]+ " inserted successfully.");
		}
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
