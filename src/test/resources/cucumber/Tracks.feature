Feature: Check tracks page

Scenario: Search for an album
	Given I load the tracks page
	When I enter an album to search
	When I submit the search
	Then Check the album was added

Scenario: Check that tracks were added
	Given I load the tracks page 
	Then check that tracks have been added

Scenario: Check that tracks redirect
	Given I load the tracks page
	When I click an album 
	Then The tracks page should appear

Scenario: Create an album 
	Given I load the tracks page
	When I load the create album modal
	When I enter the new album information
	Then Check the album was created
	
Scenario: Create without entering data
	Given I have loaded the tracks page
	When I click the create btn
	When The create modal loads
	When I enter no information
	Then I should be notified that I need to enter some information