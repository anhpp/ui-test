package org.exoplatform.selenium;

import org.apache.log4j.Level;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

public class TestReportListener extends TestListenerAdapter {

	private void logResult(ITestResult result) {
        Level level = Level.INFO;
        String message = "";
        switch (result.getStatus()) {
        case ITestResult.STARTED:
        	message = "TEST STARTED";
        	break;
        case ITestResult.FAILURE:
        	level = Level.ERROR;
        	message = "TEST ERROR";
        	break;
        case ITestResult.SKIP:
        	message = "TEST SKIPPED";
        	break;
        case ITestResult.SUCCESS_PERCENTAGE_FAILURE:
        	level = Level.WARN;
        	break;
        case ITestResult.SUCCESS:
        	message = "TEST SUCCESS";
        	break;
        }
        TestLogger.log(message, level);
    }

    @Override
    public void onTestStart(ITestResult result) {
    	logResult(result);
    }

    @Override
    public void onTestFailure(ITestResult result) {
		String sMethodName = result.getMethod().getMethodName();	
    	Utils.captureScreen(sMethodName + ".PNG");
    	logResult(result);
    }

    @Override
    public void onTestSkipped(ITestResult result) {
    	String sClassName = result.getTestClass().getName();
    	Utils.captureScreen(sClassName + ".PNG");
    	logResult(result);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
    	logResult(result);
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
    	logResult(result);
    }
}