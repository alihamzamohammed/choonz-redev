package com.qa.choonz.frontend.tests;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.concurrent.TimeUnit;

import com.qa.choonz.frontend.pages.Login;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import io.github.bonigarcia.seljup.SeleniumJupiter;

@ExtendWith(SeleniumJupiter.class)
public class UserManagmentLogin {

	private WebDriver driver;


	private boolean AccountCreated = false;
	// Get page
	private static Login LP;

	private String URL;

	@Before // runs before each scenario
	public void init() {

		// call setup before every scenario
		URL = "http://localhost:8082";

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
			LP = new Login(driver);
		} catch (TimeoutException e) {
			System.out.println("Page: " + URL + " did not load within 30 seconds!");
		}

		driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
		driver.get(URL);
		// try to load the page within 30 seconds
	}

	@Given("^I have created an account$")
	public void i_have_created_an_account() {
		assertThat(driver.getCurrentUrl()).contains(URL);
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
		assertThat(driver.findElement(By.xpath("//*[@id=\"info-box\"]")).getText()).isEqualTo("User created");
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
		driver.quit();
		// end the test and delete the resources
	}
}
