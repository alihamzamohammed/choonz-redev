Feature: Check artists page

Scenario: Search for an album
	Given I load the artists page
	When I enter an album to search
	When I submit the search
	Then Check the album was added

Scenario: Check that artists were added
	Given I load the artists page 
	Then check that artists have been added

Scenario: Check that artists redirect
	Given I load the artists page
	When I click an album 
	Then The artists page should appear

Scenario: Create an album 
	Given I load the artists page
	When I load the create album modal
	When I enter the new album information
	Then Check the album was created
	
	