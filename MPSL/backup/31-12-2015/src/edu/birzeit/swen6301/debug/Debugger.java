/**
 * 
 */
package edu.birzeit.swen6301.debug;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

import edu.birzeit.swen6301.model.Manifest;
import edu.birzeit.swen6301.model.Mirror;
import edu.birzeit.swen6301.model.ParseException;
import edu.birzeit.swen6301.model.Segment;
import edu.birzeit.swen6301.model.StreamException;
import edu.birzeit.swen6301.util.LogHandler;

/**
 * @author Salah.Hussein
 *
 */
public class Debugger {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Debugger debugger = new Debugger();
		
		//	Start Mirror Debugging	
		LogHandler.writeEvent("*********************	Start Mirror Debugging	*********************");
		debugger.debugMirror();
		LogHandler.writeEvent("*********************	End Mirror Debugging	*********************");
		
		//	Start Segment Debugging
		LogHandler.writeEvent("*********************	Start Segment Debugging	*********************");
		debugger.debugSegment();
		LogHandler.writeEvent("*********************	End Segment Debugging	*********************");
		
		//	Start Manifest Debugging
		LogHandler.writeEvent("*********************	Start Manifest Debugging	*********************");
		debugger.debugManifest();
		LogHandler.writeEvent("*********************	End Manifest Debugging	*********************");
	}

	/**
	 * Used to debug data fetching for segment by Mirror class.
	 */
	public void debugMirror() {
		Mirror mirror = new Mirror();
		
		try {
			byte[] fileBytes = mirror.download("https://wordpress.org/plugins/about/readme.txt");
			new FileOutputStream("D:\\Salah.Hussein\\MasterProgram\\SWEN6301\\project\\readme.txt").write(fileBytes);
			LogHandler.writeEvent("\"D:\\Salah.Hussein\\MasterProgram\\SWEN6301\\project\\readme.txt\" File is downloaded successfully");
		} catch (StreamException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Used to debug data fetching for available first mirror segment by Segment class.
	 */
	public void debugSegment() {
		Segment segment = new Segment();
		
		try {
			/*
			 * Test segment file: D:\Salah.Hussein\MasterProgram\SWEN6301\project\segment.txt
			 * sfasn
			 * file://localhost/D:/Salah.Hussein/MasterProgram/SWEN6301/project/test.txt
			 * asdnasd
			 * 
			 */
			
			Path p = FileSystems.getDefault().getPath("", "D:\\Salah.Hussein\\MasterProgram\\SWEN6301\\project\\segment.txt");
			segment.bind(new String(Files.readAllBytes(p)));	// Read segment.txt for debugging input.
			segment.download();
			LogHandler.writeEvent("\"D:\\Salah.Hussein\\MasterProgram\\SWEN6301\\project\\segment.txt\" Segment is downloaded successfully");
		} catch (StreamException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Used to debug data fetching for certain manifest url by using Manifest class.
	 */
	public void debugManifest() {
		Manifest manifest = new Manifest();
		
		try {
			/*
			 * http://demo.optimal.ps/nadeem/project/main.segments
			 * 
			 * OR
			 * 
			 *	sfasn
			 *	file://localhost/D:/Salah.Hussein/MasterProgram/SWEN6301/project/test.txt
			 *	asdnasd
			 *	**
			 *	file://localhost/D:/Salah.Hussein/MasterProgram/SWEN6301/project/manifest.segments
			 *	**
			 *	file://localhost/D:/Salah.Hussein/MasterProgram/SWEN6301/project/test.txt
			 *	  **  
			 *	http://demo.optimal.ps/nadeem/project/main.segments
			 * 
			 */
			
			manifest.loadAndBind("file://localhost/D:/Salah.Hussein/MasterProgram/SWEN6301/project/manifest.txt");	// Read manifest.txt for debugging input.
			//manifest.loadAndBind("http://demo.optimal.ps/nadeem/project/main.segments");	// Read manifest.txt for debugging input.
			byte[] fileBytes = manifest.download();
			new FileOutputStream("D:\\Salah.Hussein\\MasterProgram\\SWEN6301\\project\\fetched_manifest.txt").write(fileBytes);
			LogHandler.writeEvent("\"D:\\Salah.Hussein\\MasterProgram\\SWEN6301\\project\\manifest.txt\" Manifest is downloaded successfully");
		} catch (StreamException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
