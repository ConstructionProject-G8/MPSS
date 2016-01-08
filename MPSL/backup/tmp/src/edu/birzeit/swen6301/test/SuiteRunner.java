package edu.birzeit.swen6301.test;


import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import edu.birzeit.swen6301.util.LogHandler;

public class SuiteRunner {
	private static LogHandler logHandler;	
	public static void main(String[] args) {

		Result result = JUnitCore.runClasses(TestSuite.class);
		for (Failure fail : result.getFailures()) {
			System.out.println(fail.toString());
		}
		if (result.wasSuccessful()) {
			  LogHandler.writeEvent("All tests finished successfully...");
			
		}
	}
}
