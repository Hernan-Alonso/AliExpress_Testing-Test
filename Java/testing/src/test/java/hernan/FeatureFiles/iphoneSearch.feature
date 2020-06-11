Feature: Search iPhone sold quantity
	User must search for iPhone list. Go to the second page of the results and check if the second iPhone published it has quantity of 1 or more sold.
	
Scenario: Searching iPhone
	Given User navigates to Aliexpress website
	And User types in the searchbar iPhone
	When User clicks on the search button
	Then User should see iPhone search result page
	When User click in the Next button
	Then User should see second page of iPhone list
	And Second ad should have sold an item
	