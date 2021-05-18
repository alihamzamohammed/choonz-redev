package com.qa.choonz.runner;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

// import cucumber.api.CucumberOptions;
// import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(plugin = { "pretty", "html:target/htmlReports/cucumber-report.html" }, features = {
                "src/test/resources/cucumber/Login.feature" }, monochrome = true, glue = "com.qa.choonz.tests")
public class Runner {

}
