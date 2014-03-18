package spiralpoly;
// S1 algorithm for Spiral Polygonization Paper by Godfried et al.
// Coded by Lingliang Zhang

import hs_convex_hull.Point;
import hs_convex_hull.PointDistribution;
import visualize.Polygonization;

public class S1 {
    private static final boolean DEBUG = false, SHOW_COUNTER = true, DRAW_SEPARATE = false, DRAW = false, STEP = false, VERBOSE = false;
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

        while (!remaining.isEmpty()){
            // special first case
            if (DEBUG) {
                System.out.println("PROGRESS BEFORE START");
                System.out.println(remaining);
            }

            if (DRAW && DEBUG && STEP) {
                System.out.println("PREPARING TO GRAHAM");
                img = new Polygonization(0.01f);
                img.add(remaining, 0, 200, SCALE);
                System.out.println("Press Enter to Continue");
                System.in.read();
            }

            hull = new Graham(nextHead).graham(remaining);

            // throw two points goBack in, taking the last of those points as the nextHead
            if (DRAW && DEBUG && STEP) {
                System.out.println("THIS IS THE HULL");
                System.out.println(hull);
                img = new Polygonization(0.01f);
//                img.add(convex, 0, 150, SCALE);
                img.add(input, SCALE, true);
                img.add(hull, 0, 0, SCALE);
//                img.add(remaining, 0, 200, SCALE);
                System.out.println("Press Enter to Continue");
                System.in.read();
            }
            if (DEBUG) {
                System.out.println("PROGRESS PRE REMOVAL");
                System.out.println(hull);
                System.out.println(remaining);
            }

            // if remaining has two elements left, prepare for the next loop, otherwise end
            if (remaining.hasLeft(3)) {
                nextHead = hull.pop().original;
                hull.pop();
            } else {
                while (!remaining.isEmpty())
                    remaining.pop();

            }

            if (DEBUG) {
                System.out.println("PROGRESS POST REMOVAL");
                System.out.println(hull);
                System.out.println(remaining);
            }
            if (DRAW && STEP) {
                System.out.println("THIS IS AFTER POPPING");
                System.out.println(hull);
                img = new Polygonization(0.01f);
//                img.add(convex, 0, 150, SCALE);
//                img.add(concave, 0, 150, SCALE);
                img.add(input, SCALE, true);
                img.add(hull, 0, 0, SCALE);
//                img.add(remaining, 0, 200, SCALE);
                System.out.println("Press Enter to Continue");
                System.in.read();
            }

            // merge copies of lists

            if (isConvex)
			    convex.newConcatenate(hull);
            else
                concave.newConcatenate(hull);

            if (DRAW && STEP) {
                System.out.println("THIS IS SUMMARY");
                System.out.println(hull);
                img = new Polygonization(0.01f);
                img.add(convex, 0, 150, SCALE);
                img.add(concave, 0, 50, SCALE);
                img.add(input, SCALE, true);
                System.out.println("Press Enter to Continue");
                System.in.read();
            }


            // flip the direction from clockwise to counter-clockwise or vice versa
            isConvex = !isConvex;

            if (DEBUG) {
                System.out.println("ONE LOOP FINISHED");
                System.out.println("CONVEX:");
                System.out.println(convex);
            }



		}
        if (VERBOSE)
		    System.out.println("final convex");
        convex.concatenate(concave.reverse_2());


        if (DRAW) {
            System.out.println("DRAWING");
            if (DEBUG)
                System.out.println(convex);
            img = new Polygonization(0.01f, false, new String("Polygonization. Convex and Concave shaded"));
            img.add(convex, 0, SCALE);
        }
        return convex;



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


        PointDistribution ps = new PointDistribution(500000);
        Point[][] seed = ps.uniformPoints;

        long startGenerating = System.currentTimeMillis();
        for (int i = 0; i < seed[0].length; i++) {
            data.insert(new double[] {seed[0][i].coord[0], seed[0][i].coord[1]}, p);
            p.advance();
        }
        System.out.println("DONE GENERATING");
        System.out.println(System.currentTimeMillis() - startGenerating);

        if (DRAW) {
            Polygonization poly = new Polygonization(0.01f, false, new String("Polygonization. Convex and Concave shaded"));
            poly.add(data);
            System.in.read();
        }



        System.out.println("STARTING");
        final long startTime = System.currentTimeMillis();
        LinkedList result = polygonize(data, 1);
        if (VERBOSE)
            System.out.println(result);
        System.out.println(System.currentTimeMillis() - startTime);
        System.out.println("finished");
	}

}
