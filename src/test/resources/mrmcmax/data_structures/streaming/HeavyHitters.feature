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
Feature: Heavy Hitters with Dyadic Intervals

  @UnitTest
  Scenario: Two Heavy Hitters
    Given Dyadic Intervals for the problem u=two to the 4, d=4, range=two to the 2
    When The Dyadic Intervals data structure is created
  	And We have the stream "8, 10, 9, 9, 9, 9, 9, 9, 9, 9, 3, 2, 2, 2, 2, 2, 2, 2, 2"
  	When We query with k equal to 3
  	Then We retrieve 2 and 9
  	And There are no more than 6 queries in each of the CountMins
