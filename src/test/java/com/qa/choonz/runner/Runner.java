package runner;
import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
@RunWith(Cucumber.class)
@CucumberOptions(
plugin = {"pretty", "html:target/htmlReports/cucumber-report.html", "json:target/cucumber.json"},
features = {"src/test/resources/cucumber/HomePage.feature"},
monochrome = true,
glue="tests")
public class Runner {

}
