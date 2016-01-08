package edu.birzeit.swen6301.bl;

import edu.birzeit.swen6301.model.Manifest;
import edu.birzeit.swen6301.model.ParseException;
import edu.birzeit.swen6301.model.StreamException;
import edu.birzeit.swen6301.model.Mirror;

public class ManifestParser {

	/**
	 * 
	 */
	public Manifest mainManifest;
	/**
	 * 
	 */
	public String segmentSpliter = "**";
	/**
	 * Getter of mainManifest
	 */
	public Manifest getMainManifest() {
	 	 return mainManifest; 
	}
	/**
	 * Setter of mainManifest
	 */
	public void setMainManifest(Manifest mainManifest) { 
		 this.mainManifest = mainManifest; 
	}
	/**
	 * Getter of segmentSpliter
	 */
	public String getSegmentSpliter() {
	 	 return segmentSpliter; 
	}
	/**
	 * Setter of segmentSpliter
	 */
	public void setSegmentSpliter(String segmentSpliter) { 
		 this.segmentSpliter = segmentSpliter; 
	}
	/**
	 * 
	 * @param url 
	 * @return 
	 * @throws ParseException
	 */
	public Manifest parse(String url) throws ParseException { 
		// TODO Auto-generated method
		return null;
	 }
	/**
	 * 
	 * @param url 
	 * @return 
	 * @throws StreamException
	 */
	public String loadManifest(String url) throws StreamException { 
		// TODO Auto-generated method
		return null;
	 }
	/**
	 * 
	 * @return 
	 */
	public Mirror getMirrorsHasManifest() { 
		// TODO Auto-generated method
		return null;
	 } 

}
