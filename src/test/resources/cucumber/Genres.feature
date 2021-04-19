Feature: Check genres page

Scenario: Search for an genre
	Given I load the genres page
	When I enter an genre to search
	When I submit the search
	Then Check the genre was added

Scenario: Search for a genre that dose not exist
	Given I load the genres page
	When I enter an genre to search
	When I submit the search
	Then Check the genre was not shown

Scenario: Check that genres were added
	Given I load the genres page 
	Then check that genres have been added

Scenario: Check that genres redirect
	Given I load the genres page
	When I click an genre 
	Then The genres page should appear

Scenario: Create an album 
	Given I load the genres page
	When I load the create album modal
	When I enter the new album information
	Then Check the genre was created
	
Scenario: Create without entering data
	Given I have loaded the genres page
	When I click the create btn
	When The create modal loads
	When I enter no information
	Then I should be notified that I need to enter some information