Feature: Binary Plunge Heap
  @plungeHeap
  Scenario: Add and retrieve items in heap
		Given a binary plunge heap of size 10
		When we add the values "3 2,10 1,4 8,5 7,9 5,6 4,1 10,8 9,2 6,7 3"
		Then the values retrieved are in the order "10,6,2,8,7,4,3,9,5,1"
