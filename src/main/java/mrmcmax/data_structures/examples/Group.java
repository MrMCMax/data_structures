package mrmcmax.data_structures.examples;

import mrmcmax.data_structures.utils.Point2D;

public class Group {
	public Point2D pos;
	public int capacity;
	
	public Group(int x, int y, int capacity) {
		pos = new Point2D(x, y);
		this.capacity = capacity;
	}
}
