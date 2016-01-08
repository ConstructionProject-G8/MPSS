package edu.birzeit.swen6301.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.io.*;
import java.net.MalformedURLException;

import edu.birzeit.swen6301.model.StreamException;
import edu.birzeit.swen6301.util.LogHandler;
import edu.birzeit.swen6301.model.ParseException;

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
	
	public Segment(String segmentStr) throws ParseException {
		this.bind(segmentStr);
	}
	
	public Segment(String segmentStr, boolean openManifestContents) throws ParseException {
		this.bind(segmentStr, openManifestContents);
	}
	
	public Segment(String[] lines) throws ParseException {
		this.bind(lines);
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
	public void bind(String[] segmentUrls) throws ParseException {
		this.bind(segmentUrls, false);
	}
	
	/**
	 * Used to bind segments lines into URLs' mirrors.
	 * 
	 * @param segmentUrls
	 * @param openManifestContents
	 * @throws ParseException
	 */
	public void bind(String[] segmentUrls, boolean openManifestContents) throws ParseException {
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
					mirror.openStream(openManifestContents);		// Open mirror.
					this.setOpenedMirror(mirror);
					// Write event in log: download successfully.
					LogHandler.writeEvent("Mirror event: "+ mirror.getUrl()+ " is opened successfully.");
				}
			} catch (StreamException e) {
				// Write error in log.
				LogHandler.writeError("Mirror stream error: ("+ segmentUrls[i]+ ") opening is failed, "+ e.getMessage());
			} catch (MalformedURLException e) {
				// Write error in log.
				LogHandler.writeError("Mirror error: invalid URL ("+ segmentUrls[i]+ "), "+ e.getMessage());
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
		this.bind(segmentStr, false);
	}
	
	/**
	 * Used to bind segment string into URLs' mirrors.
	 * 
	 * @param segmentStr
	 * @param openManifestContents
	 * @throws ParseException
	 */
	public void bind(String segmentStr, boolean openManifestContents) throws ParseException { 
		String segmentUrls[] = segmentStr.split("\\r?\\n");		// Split string of segment into array of string.
		this.bind(segmentUrls, openManifestContents);
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
