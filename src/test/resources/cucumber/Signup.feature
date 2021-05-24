Feature: Check Signup Page

Scenario: Check whether user can sign in with correct information
	Given I load the signup page
	When I enter the signup information
	When I click submit
	Then I should be notified of account creation

Scenario: Signup with bad information
	Given I load the signup page
	When I enter the incorrect information
	When I submit the form
	Then I should be notified that the signup failed

Scenario: Diffrent passwords
	Given I load the signup page
	When I enter a password
	When I confirm with wrong password
	Then I should be notified that the passwords didnt match
