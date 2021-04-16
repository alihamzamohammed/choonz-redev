package com.qa.choonz.runner;
import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
@RunWith(Cucumber.class)
@CucumberOptions(
plugin = {"pretty"},//"html:target/htmlReports/cucumber-report.html"
features = {"src/test/resources/cucumber"},
monochrome = true,
glue="com.qa.choonz.tests")
public class Runner {

}
