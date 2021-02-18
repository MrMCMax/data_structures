Feature: Big tests
  Scenario: 100 without walls test
    Given the output from "input100.txt"
    When the script is run
    Then the output is 0 100
   
	Scenario: 20 20 walls test
	  Given the output from "inputComp.txt"
    When the script is run
    Then the output is 34 68
    
  Scenario: 50 50 walls test
	  Given the output from "input50walls.txt"
    When the script is run
    Then the output is 355 497
   
  Scenario: 50 50 without walls test
  	Given the output from "input50_50_0.txt"
    When the script is run
    Then the output is 0 213
    