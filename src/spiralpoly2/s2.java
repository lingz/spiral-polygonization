package spiralpoly2;

import hs_convex_hull.Chain;
import hs_convex_hull.Hull;
import hs_convex_hull.Point;
import hs_convex_hull.PointDistribution;
import hs_convex_hull.ChainIterator;

import java.util.ArrayList;
import java.util.Arrays;

import spiralpoly.LinkedList;
import spiralpoly.ListNode;

public class S2 {
	
	public static void main(String[] args) {
		//int size = (int)Math.ceil(Math.random()*10000);
		//System.out.println();System.out.println("n: "+size);
		int size = 10;
		PointDistribution ps = new PointDistribution(size);
		LinkedList output = S2.polygonize(ps.uniformPoints[0]);
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
	
	public static LinkedList polygonize(Point[] points) {
		
		//hershberg is the class of the delete-only datastructure
		Hull poly1 = new Hull(points);
		Hull poly2 = new Hull(invert(points));

		Chain hull = poly2.inspect().concatenate(poly1.inspect());
		
		
		LinkedList convex = new LinkedList();
		LinkedList concave = new LinkedList();
		convex.append(new ListNode(hull.head.element.coord));
		
		//true =clockwise
		boolean direction = true;
		//keeps track of chains. First 2 iterations of chains are "unusual"
		int k = 0;
		//makes sure the chain is added starting from 1, not 0
		int f = 1;
		
		while (!hull.isEmpty()) {
			ChainIterator itr = new ChainIterator(hull, hull.head);
			itr.next();
			//&& (!itr.peekNextNode().equals((k == 0) ? hull.head : hull.tail))
			while (itr.hasNext() && (!itr.peekNextNode().equals((k > 0) ? hull.tail : hull.head))) {
				
				if (direction) {
					convex.append(new ListNode(itr.currentNode().element.coord));
				}
				else {
					concave.append(new ListNode(itr.currentNode().element.coord));
				}
				
				if (!itr.currentNode().equals(hull.tail)) {
					poly1.delete(itr.currentNode().element);
					poly2.delete(invert(itr.currentNode().element));
				}
				
				itr.next();
			}
			
			if (k > 1) {
				poly1.delete(hull.tail.element);
				poly2.delete(invert(hull.tail.element));
			}
			else {
				k++;
			}
			hull = poly2.inspect().concatenate(poly1.inspect());
			direction = !direction;
		}
		
		return concave.reverse().concatenate(convex);
	
	}

}
//		
//		if (false)	{
//			for(int i = f; i < hull.size() - k; i++) {
//				
//				if (direction){
//					convex.append(hull.get(i));
//				}
//				else {
//					concave.append(hull.get(i));
//				}
//				
//				if (i != hull.size() - (k+1)) {
//					poly.delete(hull.get(i));
//				}
//				
//			}
//			
//			//k makes sure first 2 runs are different from the rest in amount of points deleted
//			if (k > 1) {
//				poly.delete(hull.get(hull.size()));
//			}
//			else {
//				k++;
//				//assignment of f is here in order to minimize the number of times it's done unnecessary
//				//only the very first time 0 assigned to f is "useful"
//				f = 0;
//			}
//			direction = !direction;
//			hull = poly.inspect();
//			
//		}
