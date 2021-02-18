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
#@tag
Feature: Building of a directed multigraph
  Scenario: Build a small graph with a loop and a multiple edge
  Given a set of 4 vertices
  And the edges "0 1,0 2,2 2,1 2,2 1,1 3,1 3"
  When the graph is built
  Then there exists an edge between 0 and 1
  And there exists an edge between 0 and 2
  And there exists an edge between 2 and 2
  And there exists an edge between 1 and 2
  And there exists an edge between 2 and 1
  And there exist 2 edges between 1 and 3
  And there does not exist an edge between 0 and 0
  And there does not exist an edge between 1 and 1
  And there does not exist an edge between 3 and 3
  And there does not exist an edge between 1 and 0
  And there does not exist an edge between 2 and 0
  And there does not exist an edge between 3 and 1
  And there does not exist an edge between 2 and 3
  And there does not exist an edge between 3 and 2
  And there does not exist an edge between 0 and 3
  And there does not exist an edge between 3 and 0
  

#  @tag2
#  Scenario Outline: Title of your scenario outline
#    Given I want to write a step with <name>
#    When I check for the <value> in step
#    Then I verify the <status> in step

#    Examples: 
#      | name  | value | status  |
#      | name1 |     5 | success |
#      | name2 |     7 | Fail    |
