Feature: Check login page

	Scenario: Login with existing account
		Given I have created an account
		When I load the Login Page
		When I enter the login credentials
		When I submit the form
		Then I should be redirected after logging in

	Scenario: Login with an account that dose not exist
		Given I have created an account
		When I load the Login Page
		When I enter incorrect Login credentials
		When I submit the form
		Then I should be notified that this failed
