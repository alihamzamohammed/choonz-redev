Feature: Check playlist page

Scenario: Check playlist Information
	Given I load the playlist page 
	Then Check for the relevant playlist information

Scenario: Edit playlist
	Given I load the playlist page
	When I Load the edit playlist modal
	When I enter the new playlist information
	Then Check the playlist information has updated

Scenario: Delete playlist
	Given I load the playlist page
	When I click delete playlist
	When I confirm deletion
	Then Check the playlist was deleted

Scenario: Check songs have loaded
	Given I load the playlist page
	Then Check that songs have been loaded

Scenario: Check that the songs redirect
	Given I have loaded the playlist page
	When I click the load song button
	Then the songs page should appear

Scenario: Edit without entering data
	Given I have loaded the playlist page
	When I click the edit btn
	When The edit modal loads
	When I enter no information
	Then I should be notified that I need to enter some information
	