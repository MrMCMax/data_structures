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
@CountMinApplications
Feature: HeavyHitters and Range solution with Dyadic Intervals and CountMin
  
  @AppTest
  Scenario: Test of HeavyHitters
    Given The no. of functions is 4
    And the Universe n is two to the power of 32
    And The Range m is two to the power of 6
    When The Dyadic Intervals data structure is created
    And We have the stream 3, 3, 3, 4, 5, 6, 3, 3, 3
    And We query the heavy hitters
    Then We get 3 in the result
    
    
  @AppTest
  Scenario: Test of RangeQuery
    Given The no. of functions is 4
    And the Universe n is two to the power of 32
    And The Range m is two to the power of 6
    When The Dyadic Intervals data structure is created
    And We have the stream 3, 4, 5, 6, 7, 8, 9, 10, 11, 12
    And We query the range [5, 8]
    Then We get at least 4 in the range
