package com.qa.choonz.tests;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.concurrent.TimeUnit;

import com.qa.choonz.tests.pages.Login;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;

@CucumberContextConfiguration
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
public class UserManagmentLogin {

	private static WebDriver driver;

	// reports
	private static ExtentReports extent;
	private static ExtentTest test;

	private boolean AccountCreated = false;
	// Get page
	private static Login LP;

	private String URL;

	@Before // runs before each scenario
	public void init() {

		// call setup before every scenario
		URL = "http://localhost:8082";

		setup();

		driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
		// try to load the page within 30 seconds
	}

	public void setup() {
		extent = new ExtentReports("src/test/resources/reports/reportLogin.html", true);

		// setup the chrome driver
		System.setProperty("webdriver.chrome.driver", "src/test/resources/drivers/chromedriver");
		ChromeOptions cOptions = new ChromeOptions();
		cOptions.addArguments("--no-sandbox");
		cOptions.setHeadless(true);
		// disable cookies
		cOptions.setCapability("profile.default_content_setting_values.cookies", 2);
		cOptions.setCapability("network.cookie.cookieBehavior", 2);
		cOptions.setCapability("profile.block_third_party_cookies", true);
		driver = new ChromeDriver(cOptions);

		try {
			System.out.println(URL);
			LP = new Login(driver);
		} catch (TimeoutException e) {
			System.out.println("Page: " + URL + " did not load within 30 seconds!");
		}

	}

	@Given("^I have created an account$")
	public void i_have_created_an_account() {
		if (!AccountCreated) {
			LP.CreateAccount("Testusername1", "Testpassword1", "Testpassword1", URL);// this redirects to login page
			AccountCreated = true;
		} else {
			LP.LoadLoginPage(URL);// if we already created then load this instead
		}
	}

	@When("^I load the Login Page$")
	public void i_load_the_Login_Page() {
		// assert that
		assertThat(driver.getCurrentUrl()).isEqualTo(URL + "/login.html?signup=true");
	}

	@When("^I enter the login credentials$")
	public void i_enter_the_login_credentials() {
		System.out.println("Entering credentials-------------");
		LP.EnterCredentials("Testusername1", "Testpassword1");
	}

	@Then("^I should be redirected after logging in$")
	public void i_should_be_redirected_after_logging_in() {
		assertThat(driver.getCurrentUrl()).isEqualTo(URL + "/index.html");
	}

	@When("^I enter incorrect Login credentials$")
	public void i_enter_incorrect_Login_credentials() {
		LP.EnterCredentials("FailedUsername", "FailedPassword");
	}

	@When("^I submit the form$")
	public void i_submit_the_form() {
		LP.SubmitForm();// page waits for refresh
	}

	@Then("^I should be notified that this failed$")
	public void i_should_be_notified_that_this_failed() {
		assertThat(driver.findElement(By.xpath("//*[@id=\"info-box\"]")).getText())
				.isEqualTo("Your details were incorrect");
		// an alert box was made that shows the login failed
	}

	@After
	public void teardown() {
		driver.close();
		// end the test and delete the resources
		extent.flush();
		extent.close();
	}
}
