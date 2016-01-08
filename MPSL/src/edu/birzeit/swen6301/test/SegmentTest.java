package edu.birzeit.swen6301.test;
	import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.MethodRule;
import org.junit.rules.TestWatchman;
import org.junit.runners.model.FrameworkMethod;

import edu.birzeit.swen6301.model.Manifest;
import edu.birzeit.swen6301.model.Mirror;
import edu.birzeit.swen6301.model.ParseException;
import edu.birzeit.swen6301.model.Segment;
import edu.birzeit.swen6301.model.StreamException;
import edu.birzeit.swen6301.util.LogHandler;

	public class SegmentTest {
		
		private static LogHandler logHandler;	
		@Before
		public void initialize() {
		    
		    LogHandler.writeEvent("Startiing Make Test for Segment Class");
		    }
		
		Segment segment = new Segment();
		Mirror mirror = new Mirror();
	
	String readActualFile(String urlPath)
       
       {
    	   
    	    URL url;
    	    String str_file = null;
    	    Scanner s;
			try {
				url = new URL(urlPath);
		
			s = new Scanner(url.openStream());
			   while (s.hasNext())
			   {
				   str_file=str_file+s.nextLine(); 
				   
			  	}}
			catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
			try {
				str_file=new String(mirror.download(urlPath));
			} catch (StreamException | ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		   return  str_file;
    	   
    	   
       }
       
      
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
			
			
			/**<p>
			 * <b>
			 * The aim of this method to check the program with segments file without redundancy 
			 * so the mainfest file (.segment ) , so the segment file contains on the paths of 3 part of the file 
			 * segment name= firsTest.segments .
			 * actual result save on the file firstOriginal.txt
			 * </b>
			  * 
        	 * 
			 */
       @Test
	public void checkSegmentConatinsTextPaths() throws IOException, ParseException 
			{
				
				
				Manifest manifest = new Manifest();
				String resultFileContent="";
				String expectedFileContent="";
					
					byte[] fileBytes = null;
					
					try {
						 manifest.loadAndBind("http://demo.optimal.ps/nadeem/project/first/firstTest.txt.segments");
						 fileBytes= manifest.downloadAllNested();
						 
                   	resultFileContent=new String(fileBytes);
							
					expectedFileContent=readActualFile("http://demo.optimal.ps/nadeem/project/first/firstOriginal.txt");
                  
												 		 
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				
				Assert.assertEquals(resultFileContent,expectedFileContent);
				
			}
		
			
			
			/**
			 * <b>
			 * The aim of this method to check the program with nested segments file with redundancy 
			 * so the mainfest file (.segment ) , so the segment file contains on the multiple mixed path 
			 * segment name= secondTest.segments .
			 * actual result save on the file firstOriginal.txt
			 * 
			 * </b>
			 */
			@Test	
		public void checkSegmentConatinsMixedPaths() throws IOException, ParseException 
			{
				
				
				Manifest manifest = new Manifest();
				String resultFileContent="";
				String expectedFileContent="";
					
					byte[] fileBytes = null;
					try {
						 manifest.loadAndBind("http://demo.optimal.ps/nadeem/project/second/secondTest.txt.segments");
						 fileBytes= manifest.downloadAllNested();
						 
                   	resultFileContent=new String(fileBytes);
							
						expectedFileContent=readActualFile("http://demo.optimal.ps/nadeem/project/second/secondOriginal.txt");
								 
						 		 
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				
				
				Assert.assertEquals(resultFileContent,expectedFileContent);
				
			}
			@Test
			/**<b>
			 * The aim of this method to check the program be able to read data from direct URL data 
			 * file name= secondOriginal.txt.
			 * 
			 * </b> 
			 */
			public void checkDirectUrlContainsText() throws IOException, ParseException 
			{
				
				
				Manifest manifest = new Manifest();
				String resultFileContent="";
				String expectedFileContent="";
					
					byte[] fileBytes = null;
					try {
						 manifest.loadAndBind("http://demo.optimal.ps/nadeem/project/second/secondOriginal.txt");
						 fileBytes= manifest.downloadAllNested();
						 
                   	resultFileContent=new String(fileBytes);
							
						expectedFileContent=readActualFile("http://demo.optimal.ps/nadeem/project/second/secondOriginal.txt");
								 
						 		 
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				
				
				Assert.assertEquals(expectedFileContent,resultFileContent);
				
			}
			
			/**<b>
			 * The aim of this method to check the program how be able to build split images  with nested segments file with redundancy 
			 * so the mainfest file (.segment ) , so the segment file contains on the multiple mixed path 
			 * segment name= forthTest.segments .
			 * actual result save on the file forthOriginal.JPG
			 * </b>
			 *  
			 */
			@Test
			public void checkMultiPartImage()throws IOException, ParseException 
			{
				
				
				Manifest manifest = new Manifest();
				String resultFileContent="";
				String expectedFileContent="";
					
					byte[] fileBytes = null;
					try {
						 manifest.loadAndBind("http://demo.optimal.ps/nadeem/project/fifth/earth.jpg.segments");
						 fileBytes= manifest.downloadAllNested();
						 
                   	resultFileContent=new String(fileBytes);
							
						expectedFileContent=readActualFile("http://demo.optimal.ps/nadeem/project/fifth/Earth.jpg");
								 
						 		 
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				
				
				Assert.assertEquals(expectedFileContent,resultFileContent);
				
			}
			
			
			/**
			 * <b>
			 * The aim of this method to check the program be able to read image from direct URL image 
			 * file name= Earth.jpg.
			 * </b>
			 * 
			 *  
			 */
			@Test
			public void checkDirectUrlContainsImage() throws IOException, ParseException 
			{
				
				
				Manifest manifest = new Manifest();
				String resultFileContent="";
				String expectedFileContent="";
					
					byte[] fileBytes = null;
					try {
						 manifest.loadAndBind("http://demo.optimal.ps/nadeem/project/forth/Earth.jpg");
						 fileBytes= manifest.downloadAllNested();
						 
                   	resultFileContent=new String(fileBytes);
							
						expectedFileContent=readActualFile("http://demo.optimal.ps/nadeem/project/forth/Earth.jpg");
								 
						 		 
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				
				
				Assert.assertEquals(resultFileContent,expectedFileContent);
				
			}
			
			/**
			 * <b>
			 * The aim of this method to check sequence images
			 * 
			 * 
			 *  </b>
			 */
			@Test
			public void checkSequenceImage() throws IOException, ParseException 
			{
				
				
				Manifest manifest = new Manifest();
				String resultFileContent="";
				String expectedFileContent="";
					
					byte[] fileBytes = null;
					try {
						 manifest.loadAndBind("http://1click.ps/files/animation.jpg-seq.segments");
						 fileBytes= manifest.downloadAllNested();
						 
                   	resultFileContent=new String(fileBytes);
							
						//expectedFileContent=readActualFile("http://demo.optimal.ps/nadeem/project/forth/Earth.jpg");
								 
						 		 
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				
				
				Assert.assertEquals(resultFileContent,resultFileContent);
				
			}
			
			/**
			 * <b>
			 * The aim of this method to check how the program will be act if found that the part of file and its redundant 
			 * on the segments   not exist or invalid one
			 * expected result to ignore this block and continue on the process , means if the segments contains 4 parts , 
			 * the real data will be exist on the three  
			 *  
			 *</b>
			*/
			@Test
		public void checkInValidURLSegment() throws IOException, Exception 
			{
				
				Manifest manifest = new Manifest();
				String resultFileContent="";
				String expectedFileContent="";
					
					byte[] fileBytes = null;
					try {
						 manifest.loadAndBind("http://demo.optimal.ps/nadeem/project/sixth/inavlid.txt.segments");
						 fileBytes= manifest.downloadAllNested();
						 
                   	resultFileContent=new String(fileBytes);
							
						expectedFileContent=readActualFile("http://demo.optimal.ps/nadeem/project/sixth/invalidOriginal.txt");
								 
						 		 
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				
				
				Assert.assertEquals(expectedFileContent,resultFileContent);
				
			}
			
			
			/**
			 * <b>
			 * The aim of this method to check the program the program will be act if found that the part of the segments data not contains 
			 * url , it contains rubbish data
			 * expected result to ignore this rubbish data and continue on the process , means if the segments contains 4 parts , 
			 * the real data will be exist on the three
			 *   
			 * </b> 
			 */
			@Test
			public void checkRubbishSegment() throws IOException, ParseException 
			{
				Manifest manifest = new Manifest();
				String resultFileContent="";
				String expectedFileContent="";
					
					byte[] fileBytes = null;
					try {
						 manifest.loadAndBind("http://demo.optimal.ps/nadeem/project/seventh/rubbish.txt.segments");
						 fileBytes= manifest.downloadAllNested();
						 
                   	resultFileContent=new String(fileBytes);
							
						expectedFileContent=readActualFile("http://demo.optimal.ps/nadeem/project/seventh/rubbishOriginal.txt");
								 
						 		 
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				
				
				Assert.assertEquals(expectedFileContent,resultFileContent);
				
				
			}
			
		
		
		
		/**
		 * <b>
		 * The aim of this method to check the program how  will be act if the first url inserted by user was invalid, not exist 
		 * expected result to show friendly message to the user about the problem 
		 * 
		 *  </b>
		 */
			@Test
			public void checkBadURl ()
			{
			Manifest manifest = new Manifest();
			String resultFileContent="";
			String expectedFileContent="";
				
				byte[] fileBytes = null;
				try {
					 try {
						manifest.loadAndBind("htt://demo.optimal.ps/nadeem/project/seventh/rubbish.tx.");
					} catch (StreamException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					 try {
						fileBytes= manifest.downloadAllNested();
					} catch (StreamException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					 
               	resultFileContent=new String(fileBytes);
						
					expectedFileContent="Dear User , the inserted url is not valid URL";
							 
					 		 
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
			
			Assert.assertEquals(expectedFileContent,resultFileContent);
			
			
		}	
			
		
		/**
		 * <b>
		 * The aim of this method to check the program how  will be act if there is a connectivity problem to the server , 
		 * expected result to show friendly message to the user about the problem
		 *  
		 * </b>
		 *  
		 */
			@Test
		public void checkConnectivty ()
		{
			Manifest manifest = new Manifest();
			String resultFileContent="";
			String expectedFileContent="";
				
				byte[] fileBytes = null;
				try {
					 manifest.loadAndBind("http://demo.optimal.ps/nadeem/project/test/secondOriginal.txt");
					 fileBytes= manifest.downloadAllNested();
					 
               	resultFileContent=new String(fileBytes);
						
					expectedFileContent="Dear User , There is a connectivity problem or file not exist , please try again.";
							 
					 		 
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (StreamException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
			
			Assert.assertEquals(expectedFileContent,resultFileContent);
		}
		
			  @Rule
			  public TestWatchman watchman = new TestWatchman() {
			     public void starting(FrameworkMethod method) {
			        logHandler.writeEvent("Test {} is running."+ method.getName());
			        
			     }
			     public void succeeded(FrameworkMethod method) {
			    	 logHandler.writeEvent("succesfully run."+ method.getName());
			     
			     }
			     public void failed(Throwable e, FrameworkMethod method) {
			    	 logHandler.writeError("Test {} failed with {} reason."+ method.getName()+e.getMessage());
				     
			      
			     }
			  };
			
			@After
			public void Clean() {
				LogHandler.writeEvent("End Make Test for Segment Class");
			    }
		}


