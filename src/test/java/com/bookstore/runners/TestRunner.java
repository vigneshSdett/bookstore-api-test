package com.bookstore.runners;

import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
    features = "src/test/resources/features",
    glue = {"com.bookstore.stepdefs", "com.bookstore.hooks"},
    plugin = {"pretty", "html:target/cucumber-reports", "json:target/cucumber.json"},
    monochrome = true,
    tags = "@regression"
)
public class TestRunner {
}