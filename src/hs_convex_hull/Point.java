package hs_convex_hull;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.BooleanUtils;

public class Point implements Comparable<Point>{
	public double[] coord;
	public boolean inverted;
	
	public Point(double[] coord){
		this.coord = coord;
		this.inverted = false;
	}
	
	public Point(double[] coord, boolean inverted){
		this.coord = coord;
		this.inverted = inverted;
	}
	@Override
	public int compareTo(Point p) {
		double x = (this.coord[0]-p.coord[0]);
		if (x != 0) return BooleanUtils.toInteger(x>0, 1, -1);
		else {
			double y = (this.coord[1]-p.coord[1]);
			if (y==0) return 0;
			else return BooleanUtils.toInteger(y>0, 1, -1);
		}
	}
}
