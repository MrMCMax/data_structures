Feature: Push-relabel algorithm

	@push_relabel
	Scenario: initialization of push-relabel
		Given a graph with 8 vertices
		And the capacity edges "0 1 2, 0 2 11, 0 3 9, 1 4 4, 1 5 3, 2 4 4, 2 5 4, 2 6 6, 3 5 5, 4 7 3, 5 7 14, 6 3 3, 6 7 2"
  	And the source is 0 and the sink is 7
  	When Push-relabel algorithm is initialized
  	Then the height of s is 8 and all others are 0
  	And the flows from 0 are "2 11 9"
  	And the flows from 1 are "0 0"
  	And the flows from 2 are "0 0 0"
  	And the flows from 3 are "0"
	
	@push_relabel
	Scenario: There are vertices with excess
		Given a graph with 8 vertices
		And the capacity edges "0 1 2, 0 2 11, 0 3 9, 1 4 4, 1 5 3, 2 4 4, 2 5 4, 2 6 6, 3 5 5, 4 7 3, 5 7 14, 6 3 3, 6 7 2"
  	And the source is 0 and the sink is 7
		When push-relabel is initialized and does one iteration
		Then the vertex 1 has excess 2
		And the vertex 2 has excess 11
		And the vertex 3 has excess 9
		Then the vertex 1 increases its height to 1
	
	@push_relabel
  Scenario: 50 50 walls test
	  Given the output from "input50walls.txt"
	  And Push-relabel algorithm
    When the script is run
    Then the output is 355 497