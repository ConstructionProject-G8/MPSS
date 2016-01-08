package edu.birzeit.swen6301.test;

import java.io.FileNotFoundException;

import java.io.IOException;

import edu.birzeit.swen6301.model.Manifest;
import edu.birzeit.swen6301.model.Mirror;
import edu.birzeit.swen6301.model.ParseException;
import edu.birzeit.swen6301.model.Segment;


public class Read {
	/*<p>
	 * <b>
	 * The aim of this method to check the program with segments file without redundancy 
	 * so the mainfest file (.segment ) , so the segment file contains on the paths of 3 part of the file 
	 * segment name= firsTest.segments .
	 * actual result save on the file firstOriginal.txt
	 *  </b>
	 *
	**
	 * Returns an Image object that can then be painted on the screen. 
	 * The url argument must specify an absolute {@link URL}. The name
	 * argument is a specifier that is relative to the url argument. 
	 * <p>
	 * This method always returns immediately, whether or not the 
	 * image exists. When this applet attempts to draw the image on
	 * the screen, the data will be loaded. The graphics primitives 
	 * that draw the image will incrementally paint on the screen. 
	 *
	 * @param  url  an absolute URL giving the base location of the image
	 * @param  name the location of the image, relative to the url argument
	 * @return      the image at the specified URL
	 * @see         Image
	 */
	public static void main (String args[]) throws ParseException, FileNotFoundException, IOException
	{
		Manifest manifest = new Manifest();
		String actualFileContent="";
		String expectedFileContent="";
			
			byte[] fileBytes = null;
			try {
				 manifest.loadAndBind("http://demo.optimal.ps/nadeem/project/first/firstOriginal.txt");
				 fileBytes= manifest.downloadAllNested();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
			
		
			actualFileContent=new String(fileBytes);
			
		
			//expectedFileContent=convertFiletoString(new File("D:\\invalid_expected.txt"));
			
		System.out.println("===="+actualFileContent);
}
}
