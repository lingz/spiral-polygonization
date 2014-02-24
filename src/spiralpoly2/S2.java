package spiralpoly2;

import hs_convex_hull.Chain;
import hs_convex_hull.Hull;
import hs_convex_hull.Point;
import hs_convex_hull.PointDistribution;
import hs_convex_hull.ChainIterator;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ListIterator;

import org.apache.commons.lang3.ArrayUtils;

import visualize.Polygonization;

public class S2 {
	
	private static final boolean DEBUG = true;
	
	public static void main(String[] args) {
		//int size = (int)Math.ceil(Math.random()*10000);
		//System.out.println();System.out.println("n: "+size);
		int size = 30;
		PointDistribution ps = new PointDistribution(size);
		LinkedList<double[]> output = S2.polygonize(ps.uniformPoints);
		System.out.print(ArrayUtils.toString(output));
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
		double[] x1 = new double[2];
		x1[0] = 1 - x[0];
		x1[1] = 1 - x[1];
		return x1;
	} 
	
	public static Chain[] myinspect(Hull poly, ArrayList<Point> toDelete)
	{	
		ListIterator<Point> itr = toDelete.listIterator();
		while (itr.hasNext())
		{
			Point del = itr.next();
			System.out.print("Deleting point:");
			System.out.println(ArrayUtils.toString(del.coord));
			poly.delete(del);
			System.out.println("Deleted.");
		}
		System.out.println("\n");
		return poly.inspect();
	}
	
	public static LinkedList<double[]> polygonize(Point[][] points) {
		Polygonization imgConvex, imgConcave, img;
		ChainIterator itr;
		ArrayList<Point> toDelete;
		Point last = new Point(new double[]{0,0});
		Point second_last = new Point(new double[]{0,0});
		toDelete = new ArrayList<>(); 
		imgConvex = new Polygonization(3, new String("Convex"));
		imgConcave = new Polygonization(3, new String("Concave"));
		img = new Polygonization(3, new String("Spiral"));
		
		if (DEBUG) 
		{
			for(int i = 0; i<points[0].length; i++)
			{
				imgConvex.add(points[0][i].coord[0],points[0][i].coord[1], true);
				imgConcave.add(points[0][i].coord[0],points[0][i].coord[1], true);
				img.add(points[0][i].coord[0],points[0][i].coord[1], true);
			}
		}
		
		//hershberg is the class of the delete-only datastructure
		Hull poly = new Hull(points);
		Chain[] hull = poly.inspect(); 
		
		LinkedList<double[]> convex = new LinkedList<>();
		LinkedList<double[]> concave = new LinkedList<>();
		
		
//		if (DEBUG)
//		{
//			ChainIterator itr = new ChainIterator(hull[0], hull[0].head);
//			img1.add(hull[0].head.element.coord);
//			while (itr.hasNext()) {
//				img1.add(itr.next().coord);
//			}
//			itr = new ChainIterator(hull[1], hull[1].head);
//			img1.add(invert(hull[1].head.element.coord));
//			while (itr.hasNext()) {
//				img1.add(invert(itr.next().coord));
//			}
//		}
		
		
		System.out.println("First special case");
		//first iteration (first special case)
		itr = new ChainIterator(hull[0], hull[0].head);
		img.add(hull[0].head.element.coord);
		imgConvex.add(hull[0].head.element.coord);
		convex.add(hull[0].head.element.coord);
		while (itr.hasNext())
		{
			img.add(itr.peekNext().coord);
			toDelete.add(itr.peekNext());
			imgConvex.add(itr.peekNext().coord);
			convex.add(itr.next().coord);
		}
		itr = new ChainIterator(hull[1], hull[1].head);
		//to delete the right-most point
		while (itr.hasNext())
		{
			itr.next();
			//do not add last(very first X min) point
			if (itr.hasNext())
			{
				img.add(invert(itr.current().coord));
				imgConvex.add(invert(itr.current().coord));
				convex.add(invert(itr.current().coord));
				itr.next();
				//do not add second last point to delete
				if (itr.hasNext())
				{
					toDelete.add(itr.previous());
					second_last = itr.current(); //non-inverted values
				}
				else
				{
					last = itr.previous(); //non-inverted values
				}
			}
		}
		 
		System.out.println(ArrayUtils.toString(second_last.coord));
		System.out.println(ArrayUtils.toString(last.coord));
		
		System.out.println("Deleting points");
		//delete points and inspect
		hull = myinspect(poly, toDelete);
		toDelete.clear();
		
		
		System.out.println("Second special case");
		//second iteration (second special case)
		itr = new ChainIterator(hull[0], hull[0].head);
		concave.add(hull[0].head.element.coord);
		//delete the X min point
		toDelete.add(hull[0].head.element);
		//start new round of drawing
		img.add(hull[0].head.element.coord, 1);
		while (itr.hasNext())
		{
			img.add(itr.peekNext().coord, 1);
			imgConcave.add(itr.peekNext().coord, 1);
			concave.add(itr.next().coord);
		}
		itr = new ChainIterator(hull[1], hull[1].head);
		toDelete.add(invert(hull[1].head.element));
		while (itr.hasNext())
		{
			itr.next();
			if (itr.hasNext())
			{
				img.add(invert(itr.current().coord));
				imgConcave.add(invert(itr.current().coord));
				concave.add(invert(itr.current().coord));
				itr.next();
				//do not add second last point to delete
				if (itr.hasNext())
				{
					toDelete.add(itr.previous());
				}
				else
				{
					second_last = itr.previous();
				}
			}
		}
		
		hull = myinspect(poly, toDelete);
		toDelete.clear();
		
		
		
		
		//draw convex and concave
		ListIterator<double[]> list_itr = convex.listIterator();
		while (list_itr.hasNext())
		{
			//img.add(list_itr.next());
		}
		
		list_itr = concave.listIterator();
		while (list_itr.hasNext())
		{
			//img.add(list_itr.next());
		}
		
		
		
//		while (hull[0] != null && !hull[0].isEmpty())
//		{
//			ArrayList<Point> toDelete = new ArrayList<>(); 
//			ChainIterator itr = new ChainIterator(hull[0], hull[0].head);
//			img2.add(hull[0].head.element.coord);
//			toDelete.add(hull[0].head.element);
//			System.out.print(ArrayUtils.toString(hull[0].head.element.coord));
//			while (itr.hasNext()) {
//				System.out.print(ArrayUtils.toString(itr.peekNext().coord));
//				toDelete.add(itr.peekNext());
//				img2.add(itr.next().coord);
//			}
//			itr = new ChainIterator(hull[1], hull[1].head);
//			img2.add(invert(hull[1].head.element.coord));
//			System.out.print(ArrayUtils.toString(invert(hull[1].head.element.coord)));
//			while (itr.hasNext()) {
//				System.out.print(ArrayUtils.toString(invert(itr.peekNext().coord)));
//				img2.add(invert(itr.next().coord));
//			}
//			for (Point p : toDelete)
//			{
//				poly.delete(p);
//			}
//			hull = poly.inspect();
//		}
			
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