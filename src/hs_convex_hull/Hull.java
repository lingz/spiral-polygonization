package hs_convex_hull;

import java.util.Arrays;

import org.apache.commons.lang3.ArrayUtils;

import visualize.Polygonization;

public class Hull {
	HalfHull[] hull;
//	Point[] endpoints;

	public Hull(Point[][] pointsAndInverse){
		hull = new HalfHull[2];
		hull[0] = new HalfHull(pointsAndInverse[0]);
		hull[1] = new HalfHull(pointsAndInverse[1]);
//		endpoints = new Point[2];
//		endpoints[0]=pointsAndInverse[0][0];
//		endpoints[1]=pointsAndInverse[0][pointsAndInverse.length-1];
	}

	public void showHull(Polygonization img){
		HalfHull.showHull(img, hull[0].inspect(), hull[0].points, false);
		HalfHull.showHull(img, hull[1].inspect(), hull[1].points, true);
	}

	public Chain[] inspect(){
		Chain[] hullChain = new Chain[2];
		hullChain[0] = hull[0].inspect();
		hullChain[1] = hull[1].inspect();
		return hullChain;
	}

	public void delete(Point p){
		int idx = Arrays.binarySearch(hull[0].points, p);
		if (idx < 0){
			idx = Arrays.binarySearch(hull[0].points, new Point(new double[] {1 - p.coord[0], 1- p.coord[1]}));
			p = hull[0].points[idx];
		}
//		if (p.compareTo(endpoints[0]) == 0)
//			endpoints[0]=hull[0].inspect().head.next.element;
//		if (p.compareTo(endpoints[1]) == 0)
//			endpoints[1]=hull[0].inspect().tail.previous.element;
		//System.out.println(idx);
		hull[0].delete(p);
		//System.out.println(ArrayUtils.toString(hull[1].points[hull[0].points.length-idx-1].coord));
		hull[1].delete(hull[1].points[hull[0].points.length-idx-1]);
	}
	

	public static void main(String[] args) {
		int size = 17;
		PointDistribution ps = new PointDistribution(size);
		Hull hull = new Hull(ps.normalPoints);
		Polygonization img = new Polygonization(false);
		hull.showHull(img);
		hull.delete(hull.hull[0].points[hull.hull[0].points.length-3]);
		img = new Polygonization(false);
		hull.showHull(img);
	}
}
