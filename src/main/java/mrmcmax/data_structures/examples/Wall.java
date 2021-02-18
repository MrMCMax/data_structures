package mrmcmax.data_structures.examples;

import mrmcmax.data_structures.utils.Point2D;

public class Wall {
	public Point2D p1;
	public Point2D p2;
	
	public Wall(int x1, int y1, int x2, int y2) {
		p1 = new Point2D(x1, y1);
		p2 = new Point2D(x2, y2);
	}
}
