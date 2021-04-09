Feature: Check login page

Scenario: Login with existing account
	Given I load the Login Page
	When I enter the login credentials
	When I submit the information
	Then I should be redirected after logging in

Scenario: Login with an account that dose not exist
	Given I load the Login Page
	When I enter incorrect Login credentials
	When I submit the form 
	Then I should be notified that this failed
	