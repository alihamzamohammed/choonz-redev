package com.qa.choonz.frontend.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Login {

	private WebElement loginBtn;
	private WebElement uNameTxt;
	private WebElement passwordTxt;
	private SignupPage SP;

	private WebDriver driver;

	public Login(WebDriver driver) {
		this.driver = driver;
		SP = new SignupPage(driver);
	}

	private void getElements() {
		new WebDriverWait(driver, 10).until(webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
		new WebDriverWait(driver, 5).until(ExpectedConditions.textToBePresentInElement(driver.findElement(By.xpath("//*[@id=\"info-box\"]")), "User created"));
		loginBtn = driver.findElement(By.xpath("/html/body/wrapper/main/div/div/div/form/button"));
		uNameTxt = driver.findElement(By.id("UName"));
		System.out.println("display me " + uNameTxt.isDisplayed());
		passwordTxt = driver.findElement(By.id("Password"));
		System.out.println("------------------ getting elements ----------------------");
	}

	public void CreateAccount(String username, String Password, String CPassword, String URL) {
		SP.FullSignUp(username, Password, CPassword, URL + "/signup.html");// waits until login is loaded

	}

	public void LoadLoginPage(String URL) {
		driver.get(URL + "/login.html");
	}

	public void EnterCredentials(String uName, String password) {

		// new WebDriverWait(driver,
		// 5).until(ExpectedConditions.textToBePresentInElement(driver.findElement(By.xpath("//*[@id=\"info-box\"]")),
		// "User created"));//waity until page loaded
		getElements();
		uNameTxt.clear();
		uNameTxt.click();// might need to be clicked before i send keys
		uNameTxt.sendKeys(uName);
		passwordTxt.clear();
		passwordTxt.click();
		passwordTxt.sendKeys(password);
		new WebDriverWait(driver, 5).until(ExpectedConditions.attributeContains(uNameTxt, "value", uName));
		new WebDriverWait(driver, 5).until(ExpectedConditions.attributeContains(passwordTxt, "value", password));
	}

	public void SubmitForm() {
		loginBtn.submit();
	}
}
