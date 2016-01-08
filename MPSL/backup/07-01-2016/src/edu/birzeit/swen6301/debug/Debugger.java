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

import edu.birzeit.swen6301.model.IPart;
import edu.birzeit.swen6301.model.Manifest;
import edu.birzeit.swen6301.model.Mirror;
import edu.birzeit.swen6301.model.ParseException;
import edu.birzeit.swen6301.model.Segment;
import edu.birzeit.swen6301.model.StreamException;
import edu.birzeit.swen6301.util.DataFormatter;
import edu.birzeit.swen6301.util.LogHandler;
import edu.birzeit.swen6301.util.MultiPartStream;

/**
 * @author Salah.Hussein
 *
 */
public class Debugger {
    private static final int BUFFER_SIZE = 4096;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Debugger debugger = new Debugger();
		/*
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
		*/
		
		//	Start MultiPartStream Debugging
		LogHandler.writeEvent("*********************	Start MultiPartStream Debugging	*********************");
		debugger.debugMultiPartStream();
		LogHandler.writeEvent("*********************	End MultiPartStream Debugging	*********************");
		
	}

	/**
	 * Used to debug data fetching for segment by Mirror class.
	 */
	public void debugMirror() {
		Mirror mirror = new Mirror();
		
		try {
			byte[] fileBytes = mirror.download("https://wordpress.org/plugins/about/readme.txt");
			new FileOutputStream("output/readme.txt").write(fileBytes);
			LogHandler.writeEvent("\"output/readme.txt\" File is downloaded successfully");
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
			 * file://localhost/D:/Salah.Hussein/MasterProgram/SWEN6301/project/MPSL/input/test.txt
			 * asdnasd
			 * 
			 */
			
			Path p = FileSystems.getDefault().getPath("", "input/segment.txt");
			segment.bind(new String(Files.readAllBytes(p)));	// Read segment.txt for debugging input.
			byte[] fileBytes = segment.download();
			new FileOutputStream("output/fetched_segment.txt").write(fileBytes);
			LogHandler.writeEvent("\"input/segment.txt\" Segment is downloaded successfully");
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
			 *	file://localhost/D:/Salah.Hussein/MasterProgram/SWEN6301/project/MPSL/input/test.txt
			 *	asdnasd
			 *	**
			 *	file://localhost/D:/Salah.Hussein/MasterProgram/SWEN6301/project/MPSL/input/manifest.segments
			 *	**
			 *	file://localhost/D:/Salah.Hussein/MasterProgram/SWEN6301/project/MPSL/input/test.txt
			 *	  **  
			 *	http://demo.optimal.ps/nadeem/project/main.segments
			 * 
			 */
			
			manifest.loadAndBind("file://localhost/D:/Salah.Hussein/MasterProgram/SWEN6301/project/MPSL/input/manifest.txt");	// Read manifest.txt for debugging input.
			//manifest.loadAndBind("http://demo.optimal.ps/nadeem/project/main.segments");	// Read manifest.txt for debugging input.
			byte[] fileBytes = manifest.downloadAllNested();
			new FileOutputStream("output/fetched_manifest.txt").write(fileBytes);
			LogHandler.writeEvent("\"input/manifest.txt\" Manifest is downloaded successfully");
		} catch (StreamException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Used to debug data fetching for certain manifest url by using MultiPartStream class.
	 */
	public void debugMultiPartStream() {
		
		try {
			/*
			 * http://demo.optimal.ps/nadeem/project/main.segments
			 * 
			 * OR
			 * 
			 *	sfasn
			 *	file://localhost/D:/Salah.Hussein/MasterProgram/SWEN6301/project/MPSL/input/test.txt
			 *	asdnasd
			 *	**
			 *	file://localhost/D:/Salah.Hussein/MasterProgram/SWEN6301/project/MPSL/input/manifest.segments
			 *	**
			 *	file://localhost/D:/Salah.Hussein/MasterProgram/SWEN6301/project/MPSL/input/test.txt
			 *	  **  
			 *	http://demo.optimal.ps/nadeem/project/main.segments
			 * 
			 */
			
			byte[] fileBytes = new byte[0]; //manifest.downloadAllNested();
			
			//MultiPartStream multiPartStream = new MultiPartStream("http://demo.optimal.ps/nadeem/project/main.segments");
			MultiPartStream multiPartStream = new MultiPartStream("file://localhost/D:/Salah.Hussein/MasterProgram/SWEN6301/project/MPSL/input/main_manifest.segments");
			IPart first = multiPartStream.nextPart();
			IPart part = first;
			
			// from first to last
			while (part != null) {
				try {
					
					if (part.isEndLess()) {		// End of iteration.
						LogHandler.writeEvent("=============================		EndLess		=============================");
					}
					
					byte[] data = part.download();
					fileBytes = DataFormatter.merge(fileBytes, data);		// Download part's data and merge it into manifest data.
					// Write event in log: download successfully.
					LogHandler.writeEvent(new String(data));
					LogHandler.writeEvent("Part event: "+ part.getUrl()+ " downloaded successfully.");
				} catch (StreamException e) {
					// Write error in log.
					LogHandler.writeError("Part error: ("+ part.getUrl()+ ") download failure, "+ e.getMessage());
				}		
					
				part = multiPartStream.nextPart();
			}
			
			new FileOutputStream("output/fetched_manifest_fromFirstToLast.txt").write(fileBytes);
			LogHandler.writeEvent("\"output/manifest.txt\" all parts are downloaded from first to last successfully");
			
			Manifest manifest = new Manifest();
			manifest = new Manifest();
			manifest.loadAndBind("file://localhost/D:/Salah.Hussein/MasterProgram/SWEN6301/project/MPSL/input/main_manifest.segments");	// Read manifest.txt for debugging input.
			fileBytes = new byte[0]; //manifest.downloadAllNested();
			IPart last = manifest.lastOf(manifest);

			// from last to first
			while (last != null) {
				try {
					byte[] data = last.download();
					fileBytes = DataFormatter.merge(fileBytes, data);		// Download part's data and merge it into manifest data.
					// Write event in log: download successfully.
					LogHandler.writeEvent(new String(data));
					LogHandler.writeEvent("Part event: "+ last.getUrl()+ " downloaded successfully.");
				} catch (StreamException e) {
					// Write error in log.
					LogHandler.writeError("Part error: ("+ last.getUrl()+ ") download failure, "+ e.getMessage());
				}		
				last = last.previous();
			}
			
			new FileOutputStream("output/fetched_manifest_FromLastToFirst.txt").write(fileBytes);
			LogHandler.writeEvent("\"output/manifest.txt\" all parts are downloaded from last to first successfully");
		
			fileBytes = new byte[0]; //manifest.downloadAllNested();
			//MultiPartStream 
			multiPartStream = new MultiPartStream("http://1click.ps/files/presentation.txt-seq.segments");
	        int bytesRead = -1;
	        byte[] buffer = new byte[BUFFER_SIZE];
	        fileBytes = new byte[0];
	        
	        while ((bytesRead = multiPartStream.read(buffer)) != -1) {
	        	fileBytes = DataFormatter.merge(fileBytes, buffer, bytesRead);
	        	//System.out.println(new String(fileBytes));
	        }
			
			new FileOutputStream("output/MultiPartStream.txt").write(fileBytes);
			LogHandler.writeEvent("\"output/manifest.txt\" all parts are downloaded from last to first successfully");
		} catch (StreamException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
