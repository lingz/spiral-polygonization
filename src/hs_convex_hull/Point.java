package hs_convex_hull;

public class Point implements Comparable<Point>{
	public double[] coord;
	
	public Point(double[] coord){
		this.coord = coord;
	}
	@Override
	public int compareTo(Point p) {
		if (this.coord[0]<p.coord[0]) return -1;
		else if (this.coord[0]== p.coord[0]) return (this.coord[1] < p.coord[1] ? -1 : 1);
		else return 1;
	}
}
