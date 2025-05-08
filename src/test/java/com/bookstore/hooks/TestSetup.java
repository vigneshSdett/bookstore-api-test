package com.bookstore.hooks;

import com.bookStore.utils.ExtentReportUtil;
import com.bookStore.utils.ServerManager;

import io.cucumber.java.BeforeAll;

public class TestSetup {

@BeforeAll
public static void globalSetup() {
    ExtentReportUtil.initReport();  // Always initialize first
    ExtentReportUtil.createTest("FastAPI Server Setup");

    ServerManager.startServer(); // This should now work and log to report
}
}