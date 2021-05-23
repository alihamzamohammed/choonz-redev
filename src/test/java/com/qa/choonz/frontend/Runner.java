package com.qa.choonz.frontend;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(plugin = { "pretty", "html:target/htmlReports/cucumber-report.html" }, features = {
                "src/test/resources/cucumber/Login.feature", "src/test/resources/cucumber/Signup.feature" }, monochrome = true, glue = "com.qa.choonz.frontend")
public class Runner {

}
