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
@RangeQueries
Feature: Range Queries solution with Dyadic Intervals and CountMin
  	
  @UnitTest
  Scenario: Find splitting node in tree
    Given Dyadic Intervals for the problem u=two to the 4, d=4, range=two to the 2
    When The Dyadic Intervals data structure is created 
    When We query the range [9, 13]
    Then The splitting node that is returned has ID 8 and level 0

	@UnitTest
	Scenario: Add values on the predecessor and successor branch    
    Given Dyadic Intervals for the problem u=two to the 4, d=4, range=two to the 2
    When The Dyadic Intervals data structure is created 
    And We have the stream "1, 2, 3, 4, 5"
    When We query the range [1, 5]
    Then The splitting node that is returned has ID 0 and level 0
    Then The predecessor branch returns at least 3
    And The successor branch returns at least 2
    And The final result is at least 5
