Feature: Check navbar

Scenario: Check Choonz
	Given I have loaded a page with the navbar
	When I click choonz
	Then The home page should load

Scenario: Check Home
	Given I have loaded a page with the navbar
	When I click Home
	Then The home page should load

Scenario: Check Artists
	Given I have loaded a page with the navbar
	When I click Artists
	Then The Artists page should load
	
Scenario: Check Albums
	Given I have loaded a page with the navbar
	When I click Albums
	Then The Albums page should load
	
Scenario: Check Genres
	Given I have loaded a page with the navbar
	When I click Genres
	Then The Genres page should load
	
Scenario: Check Playlist
	Given I have loaded a page with the navbar
	When I click Playlist
	Then The Playlist page should load
	
Scenario: Check Tracks
	Given I have loaded a page with the navbar
	When I click Tracks
	Then The Tracks page should load
	
Scenario: Check Login
	Given I have loaded a page with the navbar
	When I click Login
	Then The Login page should load
	
Scenario: Check Sign Up
	Given I have loaded a page with the navbar
	When I click Sign Up
	Then The Sign Up page should load
		