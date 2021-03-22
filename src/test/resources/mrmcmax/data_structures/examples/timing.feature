Feature: Timing
	@timing
	Scenario: Time the performance of 50 50 without walls
	  Given the output from "input50_50_0.txt"
    When the script is run 1000 times
    Then the statistics for its runtime are printed
    
#	Scenario: Time the performance of 50 50 with walls
#	  Given the output from "input50walls.txt"
#    When the script is run 1000 times
#    Then the statistics for its runtime are printed