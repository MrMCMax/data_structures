Feature: Timing
	@timing
	Scenario: Time the performance of 50 50 without walls: Scaling algorithm
	  Given the output from "input50_50_0.txt"
	  And Scaling algorithm
    When the script is run 1000 times
    Then the statistics for its runtime are printed
   
  @timing
	Scenario: Time the performance of 50 50 without walls: Dinics algorithm
	  Given the output from "input50_50_0.txt"
	  And Dinics algorithm
    When the script is run 1000 times
    Then the statistics for its runtime are printed
    
#	Scenario: Time the performance of 50 50 with walls
#	  Given the output from "input50walls.txt"
#    When the script is run 1000 times
#    Then the statistics for its runtime are printed