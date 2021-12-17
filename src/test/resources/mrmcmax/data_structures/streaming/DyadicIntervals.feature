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
  Scenario: Initialise data structure with the correct levels
    Given Dyadic Intervals for the problem u=two to the 32, d=8, range=two to the 6
    When The Dyadic Intervals data structure is created
    Then There are 32 CountMins in the data structure
    
  @UnitTest
  Scenario: Dyadic Intervals
  	Given an interval size m=8 at level 6
  	And an universe n=two to the power of 10
    When we want to know the identifier of all the elements
    Then the identifier only changes every m=8 elements
  	
  @UnitTest
  Scenario Outline: Compute second child
  	Given Dyadic Intervals for the problem u=two to the 3, d=2, range=two to the 2
  	When The Dyadic Intervals data structure is created
    Given that we are in the level <Level>
    When I check for the second child of <ID>
    Then We get the result <result>

    Examples: 
      | Level | ID | result |
      | 0     | 0  | 2      |
      | 0     | 4  | 6      |
      | 1     | 0  | 1      |
      | 1     | 2  | 3      |
      | 1     | 4  | 5      |
      | 1     | 6  | 7      |

  @UnitTest
  Scenario: Two consecutive values are hashed and retrieved
    Given Dyadic Intervals for the problem u=two to the 32, d=8, range=two to the 6
    When The Dyadic Intervals data structure is created
  	Given The next elements in the stream are 3812465 and 3812466
  	And The current state of the Heavy Hitters data structure
  	When The next two elements are analysed
		Then All the countmins have two more elements
  	When We query with k equal to 2 
  	Then We retrieve 3812465 and 3812466
  	And There are no more than 4 queries in each of the CountMins

