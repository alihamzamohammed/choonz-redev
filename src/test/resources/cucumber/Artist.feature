Feature: Check artist page

Scenario: Check Artist Information
	Given I load the artist page 
	Then Check for the relevant artist information

Scenario: Edit Artist
	Given I load the artist page
	When I Load the edit artist modal
	When I enter the new artist information
	Then Check the artist information has updated

Scenario: Delete artist
	Given I load the artist page
	When I click delete artist
	When I confirm deletion
	Then Check the artist was deleted

Scenario: Check songs have loaded
	Given I load the artist page
	Then Check that songs have been loaded

Scenario: Check that the songs redirect
	Given I have loaded the artist page
	When I click the load song button
	Then the songs page should appear

Scenario: Edit without entering data
	Given I have loaded the Artist page
	When I click the edit btn
	When The edit modal loads
	When I enter no information
	Then I should be notified that I need to enter some information