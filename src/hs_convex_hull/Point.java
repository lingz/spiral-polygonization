package com.algorithms.serpentine;

public class Point implements Comparable<Point>{
	double[] coord;
	
	public Point(double[] coord){
		this.coord = coord;
	}
	@Override
	public int compareTo(Point p) {
		if (this.coord[0]<p.coord[0]) return -1;
		else if (this.coord[0]== p.coord[0]) return 0;
		else return 1;
	}
}
