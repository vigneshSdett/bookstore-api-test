package com.bookStore.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import com.bookStore.config.ConfigReader;

import io.restassured.RestAssured;

public class ServerManager {
    private static Process serverProcess;

    public static void startServer() {
    	
    	if (System.getenv("CI") != null) {
    	    System.out.println("CI environment detected — skipping FastAPI server startup.");
    	    ExtentReportUtil.step("INFO", "CI mode: FastAPI server is managed by GitHub Actions.");
    	    return;
    	}
        try {
        	
        	ExtentReportUtil.step("INFO", "----------Server startup---------");
            ProcessBuilder pb = new ProcessBuilder("cmd.exe", "/c", "uvicorn main:app --reload");
            String projectRoot = System.getProperty("user.dir"); // Points to bookstore-tests
            File backendDir = new File(projectRoot + File.separator + "bookstore-main" + File.separator + "bookstore");
            pb.directory(backendDir);


            serverProcess = pb.start(); // Save process for later shutdown

            BufferedReader reader = new BufferedReader(new InputStreamReader(serverProcess.getInputStream()));
            new Thread(() -> {
                String line;
                try {
                    while ((line = reader.readLine()) != null) {
                        System.out.println("SERVER LOG: " + line);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();

            // ✅ Wait until FastAPI is up by checking `/docs`
            boolean serverUp = false;
            int maxRetries = 10;
            int delayMillis = 1000;

            for (int i = 0; i < maxRetries; i++) {
                if (isServerRunning()) {
                    serverUp = true;
                    break;
                }
                Thread.sleep(delayMillis);
            }

            if (!serverUp) {
            	ExtentReportUtil.step("Fail", "Server started but not responding on http://127.0.0.1:8000");
                throw new RuntimeException("Server started but not responding on http://127.0.0.1:8000");
            }

            ExtentReportUtil.step("PASS", "FastAPI Server is up and ready!");

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Server startup failed.", e);
        }
    }

    public static boolean isServerRunning() {
        try {
            RestAssured.baseURI = ConfigReader.getBaseUri();
            io.restassured.response.Response response = RestAssured
                .given()
                .header("Content-Type", "application/json")
                .get("/docs"); // or replace with "/signup" if you have that implemented
            return response.getStatusCode() == 200;
        } catch (Exception e) {
            return false;
        }
    }

    public static void stopServer() {
        if (serverProcess != null) {
            serverProcess.destroy();
            System.out.println("🛑 FastAPI Server stopped.");
        }
    }
}
