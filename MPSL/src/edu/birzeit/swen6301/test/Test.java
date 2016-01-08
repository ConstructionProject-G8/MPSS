/**
 * 
 */
package edu.birzeit.swen6301.test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

import edu.birzeit.swen6301.model.Mirror;
import edu.birzeit.swen6301.model.ParseException;
import edu.birzeit.swen6301.model.StreamException;

/**
 * @author Salah.Hussein
 *
 */
public class Test {
	 public static String convertFiletoString(File file)
	  throws IOException {
	      int len;
	      char[] chr = new char[4096];
	      final StringBuffer buffer = new StringBuffer();
	      final FileReader reader = new FileReader(file);
	      try {
	          while ((len = reader.read(chr)) > 0) {
	              buffer.append(chr, 0, len);
	          }
	      } finally {
	          reader.close();
	      }
	      return buffer.toString();
	  }


	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Mirror mirror = new Mirror();
		
		try {
			byte[] fileBytes = mirror.download("https://wordpress.org/plugins/about/readme.txt");
			
			System.out.println(new String(fileBytes));
			//new FileOutputStream("D:\\readme.txt").write(fileBytes);
			//System.out.println(convertFiletoString(new File("D:\\readme.txt")));
		} catch (StreamException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
