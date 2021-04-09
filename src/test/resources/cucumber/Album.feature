Feature: Check Album Page

Scenario: Check Album Information
	Given I load the album page 
	Then Check for the relevant album information

Scenario: Edit Album
	Given I load the album page
	When I Load the edit album modal
	When I enter the new album information
	Then Check the album information has updated

Scenario: Delete Album
	Given I load the artist page
	When I click delete album
	When I confirm deletion
	Then Check the album was deleted

Scenario: Check songs have loaded
	Given I load the album page
	Then Check that songs have been loaded

Scenario: Check that the songs redirect
	Given I have loaded the album page
	When I click the load song button
	Then the songs page should appear

Scenario: Check artist link
	Given I have loaded the album page
	When I click the artist link
	Then the artist page should load

Scenario: Edit without entering data
	Given I have loaded the album page
	When I click the edit btn
	When The edit modal loads
	When I enter no information
	Then I should be notified that I need to enter some information



	