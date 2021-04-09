Feature: Check track page

Scenario: Check track Information
	Given I load the track page 
	Then Check for the relevant track information

Scenario: Edit track
	Given I load the track page
	When I Load the edit track modal
	When I enter the new track information
	Then Check the track information has updated

Scenario: Delete track
	Given I load the track page
	When I click delete track
	When I confirm deletion
	Then Check the track was deleted

Scenario: Check songs have loaded
	Given I load the track page
	Then Check that songs have been loaded

Scenario: Check that the songs redirect
	Given I have loaded the track page
	When I click the load song button
	Then the songs page should appear

Scenario: Edit without entering data
	Given I have loaded the Track page
	When I click the edit btn
	When The edit modal loads
	When I enter no information
	Then I should be notified that I need to enter some information
	