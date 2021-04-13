Feature: Check genre page

Scenario: Check genre Information
	Given I load the genre page 
	Then Check for the relevant genre information

Scenario: Edit genre
	Given I load the genre page
	When I Load the edit genre modal
	When I enter the new genre information
	Then Check the genre information has updated

Scenario: Delete genre
	Given I load the genre page
	When I click delete genre
	When I confirm deletion
	Then Check the genre was deleted

Scenario: Check songs have loaded
	Given I load the genre page
	Then Check that songs have been loaded

Scenario: Check that the songs redirect
	Given I have loaded the genre page
	When I click the load song button
	Then the songs page should appear

Scenario: Edit without entering data
	When I click the edit btn
	When The edit modal loads
	When I enter no information
	Then I should be notified that I need to enter some information