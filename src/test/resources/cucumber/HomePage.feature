Feature: Check the home page

Scenario Outline: Check search bar works with diffrent options
	Given I load the home page
	When I change search bar to <Option>
	When I enter <SearchOption>
	When I submit the search box
	Then <Option> Information should be loaded
Examples:
	|Albums|Test Album|
	|Artists|Test Artist|
	|Genre|Test Genre|
	|Playlists|Test Playlist|
	|Tracks|Test Track|
	|All|Test Anything|

Scenario: Check that albums have loaded
	Given I load the home page
	Then Albums should have loaded on the home page

Scenario: Check that playlists have loaded
	Given I load the home page
	Then Playlists should have loaded on the home page

Scenario: More albums loads albums page
	Given I load the home page
	When I click the More albums Btn
	Then Albums page should load

Scenario: More Playlists loads playlists page
	Given I load the home page
	When I click more Playlists Btn
	Then Playlists page should load

	