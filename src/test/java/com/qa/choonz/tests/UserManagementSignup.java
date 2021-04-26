// package com.qa.choonz.tests;

// import static org.assertj.core.api.Assertions.assertThat;

// import java.util.concurrent.TimeUnit;

// import com.qa.choonz.tests.pages.SignupPage;
// import com.relevantcodes.extentreports.ExtentReports;
// import com.relevantcodes.extentreports.ExtentTest;

// import org.openqa.selenium.TimeoutException;
// import org.openqa.selenium.WebDriver;
// import org.openqa.selenium.chrome.ChromeDriver;
// import org.openqa.selenium.chrome.ChromeOptions;

// import io.cucumber.java.After;
// import io.cucumber.java.Before;
// import io.cucumber.java.en.Given;
// import io.cucumber.java.en.Then;
// import io.cucumber.java.en.When;

// public class UserManagementSignup {

// private static WebDriver driver;

// // reports
// private static ExtentReports extent;
// private static ExtentTest testt;

// // Get page
// private static SignupPage SP;

// private String URL;

// @Before // runs before each scenario
// public void init() {

// URL = "http://localhost:8082";

// // call setup before every scenario
// setup();

// driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
// // try to load the page within 30 seconds
// }

// public void setup() {
// extent = new ExtentReports("src/test/resources/reports/report1.html",
// true);// this will be called three times
// // replacing itself
// // so need diffrent names probably best to use a counter

// // setup the chrome driver
// System.setProperty("webdriver.chrome.driver",
// "src/test/resources/drivers/chromedriver.exe");
// ChromeOptions cOptions = new ChromeOptions();
// cOptions.addArguments("--no-sandbox");

// cOptions.setHeadless(false);// wanna set it for now
// // disable cookies
// cOptions.setCapability("profile.default_content_setting_values.cookies", 2);
// cOptions.setCapability("network.cookie.cookieBehavior", 2);
// cOptions.setCapability("profile.block_third_party_cookies", true);
// driver = new ChromeDriver(cOptions);
// driver.manage().window().maximize();
// try {
// SP = new SignupPage(driver);
// } catch (TimeoutException e) {
// System.out.println("Page: " + URL + " did not load within 30 seconds!");
// }

// }

// // Scenario 1/2/3 given
// @Given("^I load the signup page$")
// public void i_load_the_signup_page() {
// SP.LoadURL(URL);
// }

// // scenario 1
// @When("^I enter the signup information$")
// public void i_enter_the_signup_information() {
// SP.FullSignUp("TestUsername", "securePassword", "securePassword",
// "/signup.html");
// }

// @When("^I click submit$")
// public void i_click_submit() {
// SP.SubmitForm();

// }

// @Then("^I should be notified of account creation$")
// public void i_should_be_notified_of_account_creation() {
// // when the form is submitted it will redirect to login if everything was
// fine
// assertThat(driver.getCurrentUrl()).isEqualTo("http://localhost:8082/login.html?signup=true");
// // this checks that i have logged in and it went well
// }

// // scenario 3
// @When("^I enter a password$")
// public void i_enter_a_password() {
// SP.enterPassword("password1");
// }

// @When("^I confirm with wrong password$")
// public void i_confirm_with_wrong_password() {
// SP.enterCPassword("password2");
// }

// @Then("^I should be notified that the passwords didnt match$")
// public void i_should_be_notified_that_the_passwords_didnt_match() {
// assertThat(driver.getCurrentUrl()).isEqualTo("http://localhost:8082/signup.html?signup=error");
// }

// @After
// public void teardown() {
// driver.quit();
// // end the test and delete the resources
// extent.flush();
// extent.close();
// }
// }
