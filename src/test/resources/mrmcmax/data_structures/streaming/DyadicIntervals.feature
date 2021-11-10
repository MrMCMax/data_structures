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
@DyadicIntervals
Feature: CountMin with Dyadic Intervals
  I want to use this template for my feature file

  @UnitTest
  Scenario: Initialise data structure
    Given The no. of functions is 4
    And the Universe n is two to the power of 32
    And The Range m is two to the power of 6
    When The Dyadic Intervals data structure is created
    Then There are 32 CountMins in the data structure
    And Each CountMin object has 4 hash functions
    And Each CountMin object has a table of 4 x 64 counters

  @UnitTest
  Scenario: Two consecutive values are hashed and retrieved
  	Given The next elements in the stream are 3812465 and 3812466
  	And The current state of the Heavy Hitters data structure
  	When The next element is analysed
  	Then The counters increase by two in each row of all CountMins but the last
  	And In the last CountMin, two different values increase by 1
  	When We query with k equal to 2
  	Then We retrieve 3812465 and 3812466
