package spiralpoly2;

import hs_convex_hull.Chain;
import hs_convex_hull.ChainNode;
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
		Polygonization img;
		double[] currentPoint;
		ChainIterator itr;
		ArrayList<Point> toDelete;
		ChainNode last;
		ChainNode second_last = new ChainNode(null);
		toDelete = new ArrayList<>(); 
		img = new Polygonization(3, new String("Polygonization"));
		
		if (DEBUG) 
		{
			for(int i = 0; i<points[0].length; i++)
			{
				img.add(points[0][i].coord[0],points[0][i].coord[1], true);
			}
		}
		
		//hershberg is the class of the delete-only datastructure
		Hull poly = new Hull(points);
		Chain[] hull = poly.inspect(); 
		
		LinkedList<double[]> convex = new LinkedList<>();
		LinkedList<double[]> concave = new LinkedList<>();
		
		System.out.println("First special case");
		//first iteration (first special case)
		itr = new ChainIterator(hull[0], hull[0].head);
		convex.add(hull[0].head.element.coord);
		last = hull[0].head;
		while (itr.hasNext())
		{
			toDelete.add(itr.peekNext());
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
				convex.add(invert(itr.current().coord));
				itr.next();
				//do not add second last point to delete
				if (itr.hasNext())
				{
					toDelete.add(itr.previous());
				}
				else
				{
					second_last = itr.peekPreviousNode(); //non-inverted values
				}
			}
		}
		 
		System.out.println(ArrayUtils.toString(second_last.element.coord));
		System.out.println(ArrayUtils.toString(last.element.coord));
		
		System.out.println("Deleting points");
		//delete points and inspect
		hull = myinspect(poly, toDelete);
		toDelete.clear();
		
		
		System.out.println("Second case");
		//second iteration (second special case)
		
		itr = new ChainIterator(hull[0], last);
		currentPoint = itr.current().coord;
		concave.add(currentPoint);
		//delete the X min point
		toDelete.add(itr.current());
		//start new round of drawing
		while (itr.hasNext())
		{
			currentPoint = itr.next().coord;
			if (itr.hasNext())
			toDelete.add(itr.current());
			concave.add(currentPoint);
		}
		itr = new ChainIterator(hull[1], hull[1].head);
		while (itr.hasNext())
		{
			itr.next();
			if (itr.hasNext())
			{
				currentPoint = invert(itr.current().coord);
				itr.next();
				//do not add second last point to delete
				if (itr.hasNext())
				{
					concave.add(currentPoint);
					itr.previous();
					if (itr.hasPrevious())
					{
						toDelete.add(itr.peekPrevious());
						second_last = itr.currentNode();
					}
				}
				else
				{
					last = itr.peekPreviousNode();
				}
			}
		}
		
		hull = myinspect(poly, toDelete);
		toDelete.clear();
		
		System.out.println(ArrayUtils.toString(second_last.element.coord));
		System.out.println(ArrayUtils.toString(last.element.coord));
		
		//draw convex and concave
		ListIterator<double[]> list_itr = convex.listIterator();
		while (list_itr.hasNext())
		{
			img.add(list_itr.next());
		}
		
		list_itr = concave.listIterator();
		while (list_itr.hasNext())
		{
			img.add(list_itr.next(), 1, 150);
		}
		
		return null;
	
	}

}