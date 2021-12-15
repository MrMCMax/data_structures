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
@CountMin
Feature: CountMin
  I want this to include the different functions of CountMin

  @UnitTest
  Scenario: Initialise functions and data structure
    Given The no. of functions is 4
    And the Universe n is two to the power of 32
    And The Range m is two to the power of 6
    When The CountMin is created
    Then The CountMin object has chosen 4 hash functions
    And The CountMin object has a table of 4 x 64 counters

  @UnitTest
  Scenario: A value is hashed and queried
    Given The no. of functions is 4
    And the Universe n is two to the power of 32
    And The Range m is two to the power of 6
  	And The next element in the stream is 3812465
    When The CountMin is created
  	Given The current state of the CountMin is known
  	When The CountMin hashes the next element
  	Then The counters increase by one in each row of the CountMin
  	When We query the element 3812465
  	Then The CountMin does not undercount and returns at least 1
