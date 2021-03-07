#Author: MrMCMax
@tag
Feature: Retrieve the minimum of a set
  @tag1
  Scenario: Retrieve the minimum of a set
		Given a set of integers
		When the elements 5, 6, 8, 9, 1, 10 are added
		And the minimum is retrieved
		Then the minimum is 1
