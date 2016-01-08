package edu.birzeit.swen6301.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.io.*;
import java.net.MalformedURLException;

import edu.birzeit.swen6301.model.StreamException;
import edu.birzeit.swen6301.util.LogHandler;
import edu.birzeit.swen6301.model.ParseException;

/**
 * To encapsulate and organize all related specifications (links) of certain segment text.<p>
 * <p>
 * Main Functions:<p>
 * 1. Split lines as text array of URLs.<p>
 * 2. Bind URLs as mirror objects into attached list.<p>
 * 3. Open stream of first available and valid mirror (URL).<p>
 * <p>
 * @author
 */
public class Segment implements IPart, Comparator, Comparable {
	
	private int rank;
	private boolean endLess;
	private List<Mirror> mirrors;
	private byte[] bytes;
	private Mirror openedMirror;
	private IPart parentPart;
	private IPart nextPart;
	private IPart previousPart;
	
	public Segment() {
	}
	
	public Segment(IPart parent) {
		this.setParentPart(parent);
	}
	
	public Segment(String segmentStr, IPart parent) throws ParseException, StreamException {
		this(parent);
		this.bind(segmentStr);
	}
	
	public Segment(IPart parent, String segmentStr, boolean mainManifestContents) throws ParseException, StreamException {
		this(parent);
		this.bind(segmentStr, mainManifestContents);
	}
	
	public Segment(IPart parent, String[] lines) throws ParseException, StreamException {
		this(parent);
		this.bind(lines);
	}

	/**
	 * @return the rank
	 */
	public int getRank() {
		return rank;
	}

	/**
	 * @return the opendMirror
	 */
	public Mirror getOpenedMirror() {
		return openedMirror;
	}

	/**
	 * @param opendMirror the opendMirror to set
	 */
	public void setOpenedMirror(Mirror opendMirror) {
		this.openedMirror = opendMirror;
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
	 * @param rank the rank to set
	 */
	public void setRank(int rank) {
		this.rank = rank;
	}
	
	/**
	 * Getter of manifest
	 */
	public Manifest getManifest() {
		if (this.getOpenedMirror() == null) return null;
		else return this.getOpenedMirror().getManifest();
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
		if (this.getOpenedMirror() == null) return null;
		else return this.getOpenedMirror().getUrl();
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
		if (this.getOpenedMirror() == null) return null;
		else return this.getOpenedMirror().getMimeType();
	}
	
	/**
	 * Used to bind segments lines into URLs' mirrors.
	 * @param segments 
	 * @throws ParseException
	 */
	public void bind(String[] segmentUrls) throws ParseException, StreamException {
		this.bind(segmentUrls, false);
	}
	
	/**
	 * Used to bind segments lines into URLs' mirrors.
	 * 
	 * @param segmentUrls
	 * @param mainManifestContents
	 * @throws ParseException
	 */
	public void bind(String[] segmentUrls, boolean mainManifestContents) throws ParseException, StreamException {
		// Segments' lines loop.
		for (int i=0; i < segmentUrls.length; i++) {
			try {
				Mirror mirror = new Mirror(segmentUrls[i]);
				mirror.setParentPart(this);
				this.addMirror(mirror);		// Add mirror.
				// Write event in log.
				LogHandler.writeEvent("Mirror event: "+ segmentUrls[i]+ " data bind operation is done successfully.");
				
				if (this.getOpenedMirror() == null) {	//If mirror still isn't opened.
					mirror.setPreviousPart(this);
					mirror.openStream(mainManifestContents);		// Open mirror.
					this.setOpenedMirror(mirror);
					// Write event in log: download successfully.
					LogHandler.writeEvent("Mirror event: "+ mirror.getUrl()+ " is opened successfully.");
				}
			} catch (StreamException e) {
				String msg = "Mirror stream error: ("+ segmentUrls[i]+ ") opening is failed, "+ e.getMessage();
				// Write error in log.
				LogHandler.writeError(msg);
				// If first used link and by manifest only.
				if (mainManifestContents) throw new StreamException(msg, this, e.getStackTrace());
			} catch (MalformedURLException e) {
				String msg = "Mirror stream error: ("+ segmentUrls[i]+ ") opening is failed, "+ e.getMessage();
				// Write error in log.
				LogHandler.writeError(msg);
				// If first used link and by manifest only.
				if (mainManifestContents) throw new ParseException(msg, this, e.getStackTrace());
			}
		}
	}
	
	/**
	 * Used to bind segment string into URLs' mirrors.
	 * @param segmentStr 
	 * @throws ParseException
	 */
	public void bind(String segmentStr) throws ParseException, StreamException {
		this.bind(segmentStr, false);
	}
	
	/**
	 * Used to bind segment string into URLs' mirrors.
	 * 
	 * @param segmentStr
	 * @param mainManifestContents
	 * @throws ParseException
	 */
	public void bind(String segmentStr, boolean mainManifestContents) throws ParseException, StreamException { 
		String segmentUrls[] = segmentStr.split("\\r?\\n");		// Split string of segment into array of string.
		this.bind(segmentUrls, mainManifestContents);
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
	 * @return 
	 */
	public boolean isEmpty() { 
		return (this.getBytes() == null || this.getBytes().length == 0);
	 }
	
	/**
	 * 
	 * Used to download first available mirror by this segment.
	 * 
	 * @return
	 * @throws StreamException
	 * @throws  
	 */
	public byte[] download() throws StreamException {
		if (this.getOpenedMirror() == null) return new byte[0];
		
		return this.getOpenedMirror().download();
	}
	
	/**
	 * 
	 * Used to download all nested segments by this mirror.
	 * 
	 * @return
	 * @throws StreamException
	 */
	public byte[] downloadAllNested() throws StreamException {
		if (this.getOpenedMirror() == null) return new byte[0];
		
		return this.getOpenedMirror().downloadAllNested();
	}

	public InputStream openStream() throws StreamException, ParseException { 
		if (this.getOpenedMirror() == null) return null;
		
		return this.getOpenedMirror().getInputStream();
	}
	
	/**
	 * Used to close input stream and disconnect (http or url) connection of this segment.
	 */
	@Override
	public void closeStream() {
		if (this.getManifest() != null) this.getManifest().closeStream();
		if (this.getOpenedMirror() != null) this.getOpenedMirror().closeStream();
	}

	@Override
	public int compare(Object segment1, Object segment2) {
		return ((Segment) segment2).compareTo(segment1);
	}

	@Override
	public int compareTo(Object segment) {
		return (((Segment) segment).getRank() - this.getRank());
	}

	@Override
	public IPart parent() {
		return this.parentPart;
	}

	@Override
	public IPart next() {
		if (this.getManifest() == null) return this.nextPart;
		
		return this.getManifest().next();
	}

	@Override
	public IPart previous() {
		return this.previousPart;
	}
}
