Feature: Dinics algorithm
  @tag1
  Scenario: Dinics DFS
  	Given a graph with 7 vertices
  	And the capacity edges "0 1 3, 0 5 2, 1 2 3, 1 4 2, 2 3 2, 4 3 2, 5 6 2, 6 3 2"
  	And the source is 0 and the sink is 3
  	When Dinics algorithm is run
  	Then the maximum flow is 5
  	And the flows from 0 are "3 2"
  	And the flows from 1 are "2 1"
  	And the flows from 2 are "2"
  	And the flows from 3 are ""
  	And the flows from 4 are "1"
  	And the flows from 5 are "2"
  	And the flows from 6 are "2"
#
#  @tag2
#  Scenario Outline: Title of your scenario outline
#    Given I want to write a step with <name>
#    When I check for the <value> in step
#    Then I verify the <status> in step
#
#    Examples: 
#      | name  | value | status  |
#      | name1 |     5 | success |
#      | name2 |     7 | Fail    |
