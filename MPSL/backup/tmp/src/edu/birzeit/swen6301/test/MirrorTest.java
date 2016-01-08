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
import edu.birzeit.swen6301.model.StreamException;
import edu.birzeit.swen6301.util.LogHandler;
public class MirrorTest {
	private static LogHandler logHandler;	
	@Before
	public void initialize() {
		LogHandler.writeEvent("Startiing Make Test for Mirror Class");
	    
	    }
	
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
		public void checkFileContent() throws IOException 
		{
			String actualFileContent="";
			String ExpectedFileContent="";
			 
			try {
				byte[] fileBytes = mirror.download("http://demo.optimal.ps/nadeem/project/main.segments");
				new FileOutputStream("D:\\result.txt").write(fileBytes);
				 actualFileContent=convertFiletoString(new File("D:\\result.txt"));
				 ExpectedFileContent=convertFiletoString(new File("D:\\expected.txt"));
				 
				 
			} catch (StreamException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			Assert.assertEquals(ExpectedFileContent,actualFileContent);
			
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
			LogHandler.writeEvent("End Make Test for Mirror Class");
		    
		    }
	}


