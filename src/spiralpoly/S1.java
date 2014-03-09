package spiralpoly;
// S1 algorithm for Spiral Polygonization Paper by Godfried et al.
// Coded by Lingliang Zhang

import hs_convex_hull.PointDistribution;
import visualize.Polygonization;

public class S1 {
    private static final boolean DEBUG = false, SHOW_COUNTER = true, DRAW_SEPARATE = false, DRAW = true, STEP = false;
    private static int SCALE = 10; // default, easily overwritten


    public static LinkedList polygonize(LinkedList input) throws Exception{

		LinkedList convex = new LinkedList();
		LinkedList concave = new LinkedList();
        ListNode nextHead = null;
        boolean isConvex = true;
        Polygonization img;
        LinkedListItr drawItr;

        // the linked list with all remaining Integers
		LinkedList remaining = HeapSort.sort(input);

        LinkedList hull;
        System.out.println("STARTING LOOP");

        while (!remaining.isEmpty()){
            // special first case
            hull = new Graham(nextHead).graham(remaining);
            // throw two points back in, taking the last of those points as the nextHead
            if (DEBUG) {
                System.out.println("PROGRESS PRE REMOVAL");
                System.out.println(hull);
                System.out.println(remaining);
            }

            if (!remaining.isEmpty()) {
                nextHead = hull.pop().shallowClone();
                remaining.append(nextHead);
                remaining.append(hull.pop().shallowClone());
            }

            if (DEBUG) {
                System.out.println("PROGRESS POST REMOVAL");
                System.out.println(hull);
                System.out.println(remaining);
            }

            // merge copies of lists
			if (isConvex) {
				System.out.println("now on convex");
				convex.newConcatenate(hull);
			} else {
				System.out.println("now on concave");
				concave.newConcatenate(hull);
			}
            isConvex = !isConvex;

            if (DRAW && DEBUG) {
                img = new Polygonization(3);
                img.add(concave, 0, SCALE);
                img.add(convex, 1, SCALE);
            }
            if (DEBUG) {
                System.out.println("ONE LOOP FINISHED");
                System.out.println("CONVEX:");
                System.out.println(convex);
                System.out.println("CONCAVE");
                System.out.println(concave);
            }

            if (STEP) {
                System.out.println("Press Enter to Continue");
                System.in.read();
            }


		}
		System.out.println("final convex");
		LinkedList.printList(convex);
		System.out.println(convex.length);
		System.out.println("final concave");
		LinkedList.printList(concave);
		System.out.println(concave.length);
		System.out.println("final concave reversed");
//        if (!concave.isEmpty()) concave.pop();
		LinkedList.printList(concave);

		LinkedList result = concave.concatenate((convex));

        if (DRAW) {
            System.out.println("DRAWING");
            img = new Polygonization(3);
            img.add(result, 0, SCALE);
        }
        return result;



    }

    public static LinkedList polygonize(LinkedList input, int scale) throws Exception {
        SCALE = scale;
        return polygonize(input);
    }

	public static void main(String[] args) throws Exception{
    	LinkedList data = new LinkedList();
    	LinkedListItr p = data.zeroth();
    	
//    	double[] x={0.2313,0.281};
//    	data.insert(x,p);
//    	p.advance();
//    	double[] y={0.4214,0.291};
//    	data.insert(y,p);
//    	p.advance();
//    	double[] a={0.4214,0.191};
//    	data.insert(a,p);
//    	p.advance();
//    	double[] z={0.2315,0.261};
//    	data.insert(z,p);
//    	p.advance();
//    	double[] w={0.8312,0.231};
//    	data.insert(w,p);
//    	p.advance();
//    	double[] v={0.9913,0.786};
//    	data.insert(v,p);
//    	p.advance();


//        double[] x={ 0 , 5.0 };
//        data.insert(x,p);
//        p.advance();
//        double[] a={ 5.0 , 10.0 };
//        data.insert(a,p);
//        p.advance();
//        double[] b={ 10.0 , 5.0 };
//        data.insert(b,p);
//        p.advance();
//        double[] c={ 5.0 , 0.0 };
//        data.insert(c,p);
//        p.advance();
//        double[] d={ 2.591231629 , 0.6184665998 };
//        data.insert(d,p);
//        p.advance();
//        double[] e={ 7.679133975 , 0.7783603725 };
//        data.insert(e,p);
//        p.advance();
//        double[] f={ 7.679133975 , 9.221639628 };
//        data.insert(f,p);
//        p.advance();
//        double[] g={ 2.591231629 , 9.3815334 };
//        data.insert(g,p);
//        p.advance();

//        SCALE = 10;
//        double[] e={ 6 , 5 };
//        data.insert(e,p);
//        p.advance();
//        double[] d={ 5 , 4 };
//        data.insert(d,p);
//        p.advance();
//        double[] c={ 4 , 6 };
//        data.insert(c,p);
//        p.advance();
//        double[] b={ 3, 8 };
//        data.insert(b,p);
//        p.advance();
//        double[] a={ 2 , 5 };
//        data.insert(a,p);
//        p.advance();
//        double[] x={ 1 , 1 };
//        data.insert(x,p);
//        p.advance();


        PointDistribution ps = new PointDistribution(10);
        double[][] seed = ps.normalData;

        for (int i = 0; i < seed[0].length; i++) {
            data.insert(new double[] {seed[0][i], seed[1][i]}, p);
            p.advance();
        }

        System.out.println(data);
        Polygonization poly = new Polygonization(3);
        poly.add(data);
        System.in.read();

        System.out.println("STARTING");
        LinkedList.printList(polygonize(data, 1));
    	System.out.println("finished");
	}

}
