#Author: your.email@your.domain.com
#Keywords Summary :
#Feature: List of scenarios.
#Scenario: Business rule through list of steps with arguments.
#Given: Some precondition step
#When: Some key actions
#Then: To observe outcomes or validation
#And,But: To enumerate more Given,When,Then steps
#Scenario Outline: List of steps for data-driven as an Examples and <placeholder>
#Examples: Container for s table
#Background: List of steps run before each of the scenarios
#""" (Doc Strings)
#| (Data Tables)
#@ (Tags/Labels):To group Scenarios
#<> (placeholder)
#""
## (Comments)
#Sample Feature Definition Template
@tag
Feature: LinkedCacheList
	Scenario: Adding and retrieving several items to the list
		Given a LinkedCacheList of cache size 64
		When 132 numbers are added
		Then we can retrieve them correctly
		And the size is 132
		
	Scenario: Out of bounds
		Given a LinkedCacheList of cache size 64
		When 1024 numbers are added
		And the position index 1024 is seeked
		Then an IndexOutOfBounds error is thrown
