Feature: Check playlists page

Scenario: Search for an playlist
	Given I load the playlists page
	When I enter an playlist to search
	When I submit the search
	Then Check the playlist was added
	
Scenario: Search for an playlist that dose not exist
	Given I load the playlists page
	When I enter an playlist to search
	When I submit the search
	Then Check the playlist was not shown

Scenario: Check that playlists were added
	Given I load the playlists page 
	Then check that playlists have been added

Scenario: Check that playlists redirect
	Given I load the playlists page
	When I click an playlist 
	Then The playlists page should appear

Scenario: Create an playlist 
	Given I load the playlists page
	When I load the create album modal
	When I enter the new playlist information
	Then Check the playlist was created
	
Scenario: Create without entering data
	Given I have loaded the playlists page
	When I click the create btn
	When The create modal loads
	When I enter no information
	Then I should be notified that I need to enter some information