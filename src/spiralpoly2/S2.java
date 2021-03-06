package spiralpoly2;

import hs_convex_hull.*;
import org.apache.commons.lang3.ArrayUtils;
import visualize.Polygonization;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ListIterator;

public class S2 {

	private static final boolean DEBUG = false, SHOW_COUNTER = true, DRAW_SEPARATE = false, DRAW = false;
	private static final int SIZE = 1000000; //num of points
	private static int COUNTER = 0;

	public static void main(String[] args) {
		//int size = (int)Math.ceil(Math.random()*10000);
		//System.out.println();System.out.println("n: "+size);
		PointDistribution ps = new PointDistribution(SIZE);
        long start = System.currentTimeMillis();
		LinkedList<double[]> output = S2.polygonize(ps.uniformPoints);
        System.out.println(System.currentTimeMillis() - start);

		if (DRAW) {
			Polygonization img = new Polygonization(1f, SIZE < 30 && DEBUG, new String("Polygonization. Final"));
			for(int i = 0; i<ps.uniformPoints[0].length; i++)
			{
				img.add(ps.uniformPoints[0][i].coord[0],ps.uniformPoints[0][i].coord[1], true);
			}
			ListIterator<double[]> list_itr = output.listIterator();
			while (list_itr.hasNext())
			{
				img.add(list_itr.next());
			}
		}

		if (DEBUG) {
			System.out.println("OUTPUT");
			ListIterator<double[]> list_itr = output.listIterator();
			while (list_itr.hasNext())
			{
				System.out.print(String.format("%s, ", ArrayUtils.toString(list_itr.next())));
			}
		}
	}

	//	static LinkedList<double[]> arrayToLinkedList (Point[][] points) {
	//		LinkedList<double[]> out = new LinkedList<>();
	//		for (int i=0; i<points.length; i++) {
	//			out.add(points)
	//		}
	//		return out;
	//	}

	static double[] invert(double[] x) {
		double[] x1 = new double[2];
		x1[0] = 1 - x[0];
		x1[1] = 1 - x[1];
		return x1;
	} 

	public static Chain[] myInspect(Hull poly, ArrayList<Point> toDelete) {	
		ListIterator<Point> itr = toDelete.listIterator();
		while (itr.hasNext()) {
			COUNTER++;
			Point del = itr.next();
			if (SHOW_COUNTER && COUNTER%10000==0) {
				System.out.println(SIZE - COUNTER);
			}
			if (DEBUG) {
				System.out.print(" deleting point:");
				System.out.println(ArrayUtils.toString(del.coord));
			}		
			poly.delete(del);
			if (DEBUG) {
				System.out.println("Deleted.");	
			}
		}
		return poly.inspect();
	}

	public static LinkedList<double[]> polygonize(Point[][] points) {
		boolean isConvex = true, first = true; //determine which chain to add to
		//first and second loops are speical cases
		LoopedChainIterator itr;
		ArrayList<Point> toDelete = new ArrayList<>(); 
		ChainNode last, second_last = new ChainNode(null);
		LinkedList<double[]> convex = new LinkedList<>(), concave = new LinkedList<>();
		double[] toAdd; //tmp point variable for simplicity

		//delete only data structure Hull
		Hull poly = new Hull(points);
		Chain[] hull = poly.inspect();

		convex.add(hull[0].head.element.coord);
		int counter = 1; //checks for any point left out inside spiral poly



		//special cases
		int pointsLength = points[0].length;
		if (pointsLength == 1) {
			return convex;
		}
		if (pointsLength == 2) {
			convex.add(hull[0].tail.element.coord);
			return convex;
		}

		last = hull[0].head; //for first loop set up first point as last
		second_last = hull[1].tail.previous(); //second last point
		itr = new LoopedChainIterator(hull[0], hull[1], last, last, second_last);
		concave.add(itr.current().coord); //add first point to both concave and convex (convex is added earlier for base cases computations)

		//loop until the first point is last
		while (itr.current() != second_last.element) {
			if (!first) {
				toDelete.add(itr.current()); //if not the first loop, add first point to delete
			}
			while (!itr.isLast()) {
				if (DEBUG) {
					System.out.println("Second last/last/current/isLast");
					System.out.println(ArrayUtils.toString(itr.secondLast.element.coord));
					System.out.println(ArrayUtils.toString(itr.last.element.coord));
					System.out.println(ArrayUtils.toString(itr.current().coord));
					System.out.println(itr.isLast());
				}
				toAdd = itr.next().inverted ? invert(itr.current().coord) : itr.current().coord;
				if (!itr.isLast()) {
					counter++;
					if (isConvex ) {
						convex.add(toAdd);
					}
					else {
						concave.add(toAdd);
					}
					toDelete.add(itr.current());
					second_last = itr.currentNode();
				}
			}
			if (first) {
				//add last point if first iteration
				if (isConvex) {
					convex.add(itr.current().inverted ? invert(itr.current().coord) : itr.current().coord);
				}
				else {
					concave.add(itr.current().inverted ? invert(itr.current().coord) : itr.current().coord);
				}
			}
			else {
				//do not delete last point on first iteration
				toDelete.remove(toDelete.size()-1);
			}
			isConvex = !isConvex;
			hull = myInspect(poly, toDelete);
			if (first && hull[1].head.next() == hull[1].tail && ++counter == pointsLength) //return if nothing is in the lower hull
			{
				return concatenate(convex, concave);
			}
			toDelete.clear();
			if (DEBUG) {
				System.out.println("Second last/second_last");
				System.out.println(ArrayUtils.toString(itr.secondLast.element.coord));
				System.out.println(ArrayUtils.toString(second_last.element.coord));	
			}

			if (first) {
				itr = new LoopedChainIterator(hull[0], hull[1], itr.last, itr.last, itr.secondLast);
				first = false;
			} 
			else {
				itr = new LoopedChainIterator(hull[0], hull[1], itr.secondLast, itr.secondLast, second_last);
			}

			if (DEBUG) {
				System.out.println("SecondLast == Current. End Condition");
				System.out.println(second_last.element == itr.current());
			}
		}

		if (DRAW_SEPARATE) {
			Polygonization img = new Polygonization(0.01f, SIZE < 20 && DEBUG, new String("Polygonization. Convex and Concave shaded"));
			for(int i = 0; i<points[0].length; i++) {
				img.add(points[0][i].coord[0],points[0][i].coord[1], true);
			}

			//draw convex and concave
			ListIterator<double[]> list_itr = convex.listIterator();
			while (list_itr.hasNext())
			{
				img.add(list_itr.next());
			}
			img.add(concave.getLast());
			list_itr = concave.listIterator();
			while (list_itr.hasNext())
			{
				img.add(list_itr.next(), 1, 150);
			}
		}
		return concatenate(convex, concave);

	}

	public static LinkedList<double[]> concatenate (LinkedList<double[]> convex, LinkedList<double[]> concave) {
		ListIterator<double[]> list_itr = concave.listIterator(concave.size());
		while (list_itr.hasPrevious()) {
			convex.add(list_itr.previous());
		}
		return convex;
	}

}