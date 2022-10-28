package mrmcmax.data_structures.examples;

import mrmcmax.utils.Point2D;

public class Wifi {
	public Point2D pos;
	public int capacity;
	public int radius;
	
	public Wifi(int x, int y, int radius, int capacity) {
		this.pos = new Point2D(x, y);
		this.radius = radius;
		this.capacity = capacity;
	}
}
