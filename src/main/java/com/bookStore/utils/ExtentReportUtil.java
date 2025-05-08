package com.bookStore.utils;

import com.aventstack.extentreports.*;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;


public class ExtentReportUtil {
    private static ExtentReports extent;
    private static ExtentTest test;

    public static void initReport() {
        if (extent == null) {
            ExtentSparkReporter reporter = new ExtentSparkReporter("target/extent-report.html");
            extent = new ExtentReports();
            extent.attachReporter(reporter);
        }
    }

    public static void createTest(String testName) {
        test = extent.createTest(testName);
    }

    public static void step(String status, String details) {
        if (test != null) {
            switch (status.toUpperCase()) {
                case "INFO":
                    test.info(details);
                    break;
                case "PASS":
                    test.pass(details);
                    break;
                case "FAIL":
                    test.fail(details);
                    break;
                default:
                    test.info(details);
            }
        }
    }
    
    public static void step(Status status, String details) {
    	step(status.toString(), details);
    }

    public static void flushReport() {
        if (extent != null) {
            extent.flush();
        }
    }
    
    public static void logValidation(String title, Object expected, Object actual, String expectedDesc, String actualDesc, boolean pass) {
        if (pass) {
            test.pass("✅ " + title + "\nExpected: " + expected + " - " + expectedDesc + "\nActual: " + actual + " - " + actualDesc);
        } else {
            test.fail("❌ " + title + "\nExpected: " + expected + " - " + expectedDesc + "\nActual: " + actual + " - " + actualDesc);
        }
    }
}
