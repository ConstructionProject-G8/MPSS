package edu.birzeit.swen6301.test;
	import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.birzeit.swen6301.model.Mirror;
import edu.birzeit.swen6301.model.ParseException;
import edu.birzeit.swen6301.model.Segment;
import edu.birzeit.swen6301.util.LogHandler;

	public class SegmentTest {
		
		private static LogHandler logHandler;	
		@Before
		public void initialize() {
		    
		    LogHandler.writeEvent("Startiing Make Test for Segment Class");
		    }
		
		Segment segment = new Segment();
		Mirror mirror = new Mirror();
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

			@Test
			public void checkValidURLSegment() throws IOException, ParseException 
			{
				String actualFileContent="";
				String expectedFileContent="";
					
					byte[] fileBytes = null;
					try {
						fileBytes = mirror.download("http://demo.optimal.ps/nadeem/project/invalid.segments");
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					new FileOutputStream("D:\\result.txt").write(fileBytes);
					
					segment.bind(convertFiletoString(new File("D:\\result.txt")));	// Read segment.txt for debugging input.
					segment.download();
					new FileOutputStream("D:\\segment.txt").write(fileBytes);
					actualFileContent=convertFiletoString(new File("D:\\segment.txt"));
					expectedFileContent=convertFiletoString(new File("D:\\invalid_expected.txt"));
					
				
				
				Assert.assertEquals(expectedFileContent,actualFileContent);
				
			}
			public void checkInValidURLSegment() throws IOException, Exception 
			{
				String actualFileContent="";
			String expectedFileContent="";
				
				byte[] fileBytes = null;
				try {
					fileBytes = mirror.download("http://demo.optimal.ps/nadeem/project/invalid.segments");
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				new FileOutputStream("D:\\result.txt").write(fileBytes);
				
				segment.bind(convertFiletoString(new File("D:\\result.txt")));	// Read segment.txt for debugging input.
				segment.download();
				new FileOutputStream("D:\\segment.txt").write(fileBytes);
				actualFileContent=convertFiletoString(new File("D:\\segment.txt"));
				expectedFileContent=convertFiletoString(new File("D:\\invalid_expected.txt"));
				
				
				
				Assert.assertEquals(expectedFileContent,actualFileContent);
				
			}
			public void checkRubbishSegment() throws IOException, ParseException 
			{
				String actualFileContent="";
				String expectedFileContent="";
					
					byte[] fileBytes = null;
					try {
						fileBytes = mirror.download("http://demo.optimal.ps/nadeem/project/rubbish.segments");
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					new FileOutputStream("D:\\result.txt").write(fileBytes);
					
					segment.bind(convertFiletoString(new File("D:\\result.txt")));	// Read segment.txt for debugging input.
					segment.download();
					new FileOutputStream("D:\\segment.txt").write(fileBytes);
					actualFileContent=convertFiletoString(new File("D:\\segment.txt"));
					expectedFileContent=convertFiletoString(new File("D:\\rubbish_expected.txt"));
					
					
					
					Assert.assertEquals(expectedFileContent,actualFileContent);
				
				Assert.assertEquals(expectedFileContent,actualFileContent);
				
			}
			
			  @Test
			 
			public void checkBadURl ()
			{
				
			}
			
			@Test
			public void checkConnectivty ()
			{
				
			}
			
			@After
			public void Clean() {
				LogHandler.writeEvent("End Make Test for Segment Class");
			    }
		}


