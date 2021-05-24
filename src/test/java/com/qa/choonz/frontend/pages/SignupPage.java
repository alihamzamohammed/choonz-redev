package com.qa.choonz.frontend.pages;

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
		new WebDriverWait(driver, 5).until(ExpectedConditions.urlContains("login"));
	}

	public void SubmitFormNoRedirect() {
		SignUpBtn.click();
		new WebDriverWait(driver, 5).until(ExpectedConditions.elementToBeClickable(SignUpBtn));
	}

	public void LoadURL(String URL) {
		driver.get(URL);
		new WebDriverWait(driver, 5).until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/wrapper/main/div/div/div/form/button")));
		getElements();
	}

	public void enterPassword(String password) {
		PasswordTxt.sendKeys(password);
	}

	public void enterCPassword(String password) {
		ConfirmPasswordTxt.sendKeys(password);
	}
}
