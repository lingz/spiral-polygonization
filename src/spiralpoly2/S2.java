package spiralpoly2;

import hs_convex_hull.Chain;
import hs_convex_hull.HalfHull;
import hs_convex_hull.Hull;
import hs_convex_hull.Point;
import hs_convex_hull.PointDistribution;
import hs_convex_hull.ChainIterator;

import java.util.ArrayList;
import java.util.Arrays;

import spiralpoly.LinkedList;
import spiralpoly.ListNode;
import visualize.Polygonization;

public class S2 {
	
	private static final boolean DEBUG = true;
	
	public static void main(String[] args) {
		//int size = (int)Math.ceil(Math.random()*10000);
		//System.out.println();System.out.println("n: "+size);
		int size = 30;
		PointDistribution ps = new PointDistribution(size);
		LinkedList output = S2.polygonize(ps.uniformPoints);
	}
	
	static Point[] invert(Point[] x) {
		
		for (int i = 1; i < x.length; i++) {
			x[i].coord[0] = 1 - x[i].coord[0];
			x[i].coord[1] = 1 - x[i].coord[1];
		}
		
		return x;
		
	}
	
	static Point invert(Point x) {
		x.coord[0] = 1 - x.coord[0];
		x.coord[1] = 1 - x.coord[1];
		return x;
	}
	
	static double[] invert(double[] x) {
		double[] x1 = x.clone();
		x1[0] = 1 - x1[0];
		x1[1] = 1 - x1[1];
		return x1;
	} 
	
	public static LinkedList polygonize(Point[][] points) {
		Polygonization img1, img2;
		
		img2 = new Polygonization(3);
		
		if (DEBUG) 
		{
			img1 = new Polygonization(3);
			for(int i = 0; i<points[0].length; i++)
			{
				img1.add(points[0][i].coord[0],points[0][i].coord[1], true);
				img2.add(points[0][i].coord[0],points[0][i].coord[1], true);
			}
		}
		
		//hershberg is the class of the delete-only datastructure
		Hull poly = new Hull(points);
		Chain[] hull = poly.inspect(); 
		
		LinkedList convex = new LinkedList();
		LinkedList concave = new LinkedList();
		
		
		if (DEBUG)
		{
			ChainIterator itr = new ChainIterator(hull[0], hull[0].head);
			img1.add(hull[0].head.element.coord);
			while (itr.hasNext()) {
				img1.add(itr.next().coord);
			}
			itr = new ChainIterator(hull[1], hull[1].head);
			img1.add(invert(hull[1].head.element.coord));
			while (itr.hasNext()) {
				img1.add(invert(itr.next().coord));
			}
		}
		
		while (!hull[0].isEmpty())
		{
			ChainIterator itr = new ChainIterator(hull[0], hull[0].head);
			img2.add(hull[0].head.element.coord);
			while (itr.hasNext()) {
//				poly.delete(itr.peekNext());
				img2.add(itr.next().coord);
			}
			itr = new ChainIterator(hull[1], hull[1].head);
			img2.add(invert(hull[1].head.element.coord));
			while (itr.hasNext()) {
//				poly.delete(itr.peekNext());
				img2.add(invert(itr.next().coord));
			}
			hull = poly.inspect();
			break;
		}
			
//		convex.append(new ListNode(hull[0].head.element.coord));
		
//		if (DEBUG)
//		{
//			img1.add(hull.head.element.coord, 0);
//		}
//		
//		//true =clockwise
//		boolean direction = true;
//		//keeps track of chains. First 2 iterations of chains are "unusual"
//		int k = 0;
//		//makes sure the chain is added starting from 1, not 0
//		int f = 1;
//		
//		
//		
//		while (!hull.isEmpty()) {
//			//&& (!itr.peekNextNode().equals((k == 0) ? hull.head : hull.tail))
//			ChainIterator itr = new ChainIterator(hull, hull.head);
//			itr.next();
//			while (itr.hasNext() && (!itr.peekNextNode().equals((k > 0) ? hull.tail : hull.head))) {
//				
//				if (direction) {
//					convex.append(new ListNode(itr.currentNode().element.coord));
//					if (DEBUG) {
//						img1.add(itr.currentNode().element.coord, 0);
//					}
//				}
//				else {
//					concave.append(new ListNode(itr.currentNode().element.coord));
//					if (DEBUG) {
//						img1.add(itr.currentNode().element.coord, 1);
//					}
//				}
//				
//				if (!itr.currentNode().equals(hull.tail)) {
//					poly1.delete(itr.currentNode().element);
//					poly2.delete(invert(itr.currentNode().element));
//				}
//				
//				itr.next();
//			}
//			
//			if (k > 1) {
//				poly1.delete(hull.tail.element);
//				poly2.delete(invert(hull.tail.element));
//			}
//			else {
//				k++;
//			}
//			hull = poly2.inspect().concatenate(poly1.inspect());
//			direction = !direction;
//	
//		}
//		
		return null;
	
	}

}