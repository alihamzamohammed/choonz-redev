package com.qa.choonz.frontend.tests;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.concurrent.TimeUnit;

import com.qa.choonz.frontend.pages.SignupPage;

import org.junit.jupiter.api.extension.ExtendWith;
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
public class UserManagementSignup {

    private WebDriver driver;


    // Get page
    private static SignupPage SP;

    private String URL;

    @Before // runs before each scenario
    public void init() {

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
            SP = new SignupPage(driver);
        } catch (TimeoutException e) {
            System.out.println("Page: " + URL + " did not load within 30 seconds!");
        }

        driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
        driver.get(URL);
        // try to load the page within 30 seconds
    }

    // Scenario 1/2/3 given
    @Given("^I load the signup page$")
    public void i_load_the_signup_page() {
        assertThat(driver.getCurrentUrl()).contains(URL);
        System.out.println(URL);
        SP.LoadURL(URL + "/signup.html");
    }

    // scenario 1
    @When("^I enter the signup information$")
    public void i_enter_the_signup_information() {
       SP.SignUp("TestUsername", "securePassword", "securePassword");
    }

    @When("^I click submit$")
    public void i_click_submit() {
        SP.SubmitForm();

    }

    @Then("^I should be notified of account creation$")
    public void i_should_be_notified_of_account_creation() {
        assertThat(driver.getCurrentUrl()).isEqualTo("http://localhost:8082/login.html?signup=true");
    }

    // scenario 3
    @When("^I enter a password$")
    public void i_enter_a_password() {
        SP.enterPassword("password1");
    }

    @When("^I confirm with wrong password$")
    public void i_confirm_with_wrong_password() {
        SP.enterCPassword("password2");
        SP.SubmitFormNoRedirect();
    }

    @Then("^I should be notified that the passwords didnt match$")
    public void i_should_be_notified_that_the_passwords_didnt_match() {
        assertThat(driver.getCurrentUrl()).isEqualTo("http://localhost:8082/signup.html?incorrect=true");
    }

    @After
    public void teardown() {
        driver.quit();
        // end the test and delete the resources
    }
}
