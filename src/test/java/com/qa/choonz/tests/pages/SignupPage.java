package com.qa.choonz.tests.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SignupPage {

	private WebElement SignUpBtn;
	private WebElement UsernameTxt;
	private WebElement PasswordTxt;
	private WebElement ConfirmPasswordTxt;

	private WebDriver driver;

	public SignupPage(WebDriver driver) {
		this.driver = driver;
	}

	public void getElements() {
		SignUpBtn = driver.findElement(By.xpath("/html/body/wrapper/main/div/div/div/form/button"));
		UsernameTxt = driver.findElement(By.id("UName"));
		PasswordTxt = driver.findElement(By.id("Password"));
		ConfirmPasswordTxt = driver.findElement(By.id("CPassword"));
	}

	public void FullSignUp(String username, String password, String cPassword, String URL) {
		LoadURL(URL);
		SignUp(username, password, cPassword);
		SubmitForm();
	}

	public void SignUp(String username, String password, String cPassword) {
		UsernameTxt.sendKeys(username);
		PasswordTxt.sendKeys(password);
		ConfirmPasswordTxt.sendKeys(cPassword);
	}

	public void SubmitForm() {
		SignUpBtn.click();
		new WebDriverWait(driver, 5).until(ExpectedConditions.urlContains("login"));// wait
																					// until
																					// page
																					// has
																					// loaded
		// wait for login page to load first
	}

	public void SubmitFormNoRedirect() {
		SignUpBtn.click();// diffrent from above this wont redirect because it fails
		new WebDriverWait(driver, 5).until(ExpectedConditions.elementToBeClickable(SignUpBtn));// wait for page to
																								// refresh because it
																								// failed
	}

	public void LoadURL(String URL) {
		driver.get(URL);
		// wait for signupbtn to be clickable
		new WebDriverWait(driver, 5).until(
				ExpectedConditions.elementToBeClickable(By.xpath("/html/body/wrapper/main/div/div/div/form/button")));// wait
																														// until
																														// page
																														// has
																														// loaded
		getElements();
	}

	public void enterPassword(String password) {
		PasswordTxt.sendKeys(password);
	}

	public void enterCPassword(String password) {
		ConfirmPasswordTxt.sendKeys(password);
	}
}
