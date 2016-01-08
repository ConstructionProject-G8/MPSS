package edu.birzeit.swen6301.util;

import java.io.*;

import edu.birzeit.swen6301.model.Manifest;

public class MultiPartStream extends InputStream {

	/**
	 * 
	 */
	public Manifest mainManifest;

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
	 * 
	 * @param mnf 
	 * @return 
	 */
	public MultiPartStream openStream(Manifest mnf) { 
		// TODO Auto-generated method
		return null;
	 }

	/**
	 * 
	 */
	public int read() {
		return 0; 
		// TODO Auto-generated method
	 } 

}
