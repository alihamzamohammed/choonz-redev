Feature: Check Albums page

Scenario: Search for an album
	Given I load the albums page
	When I enter an album to search
	When I submit the search
	Then Check the album was added
	
Scenario: Search for an album that dose not exist
	Given I load the playlists page
	When I enter an album to search
	When I submit the search
	Then Check the album was not shown

Scenario: Check that Albums were added
	Given I load the albums page 
	Then check that albums have been added

Scenario: Check that Albums redirect
	Given I load the albums page
	When I click an album 
	Then The albums page should appear

Scenario: Create an album 
	Given I load the albums page
	When I load the create album modal
	When I enter the new album information
	Then Check the album was created

Scenario: Create without entering data
	Given I have loaded the albums page
	When I click the create btn
	When The create modal loads
	When I enter no information
	Then I should be notified that I need to enter some information
	