package spiralpoly;
// S1 algorithm for Spiral Polygonization Paper by Godfried et al.
// Coded by Lingliang Zhang

import visualize.Polygonization;

public class S1 {
    private static final boolean DEBUG = true, SHOW_COUNTER = true, DRAW_SEPARATE = false, DRAW = true;
	public static LinkedList polygonize(LinkedList input) throws Exception{

		LinkedList convex = new LinkedList();
		LinkedList concave = new LinkedList();
        ListNode nextHead = null;
        boolean isConvex = true;
		
		// the linked list with all remaining Integers
		LinkedList remaining = BucketSort.sort(input);
		
		if (DEBUG) {
            System.out.println("Original:");
            LinkedList.printList(remaining);
            System.out.println(remaining.length);
        }

        LinkedList hull;
		
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
				System.out.println("new convex");
				convex.newConcatenate(hull);
				LinkedList.printList(convex);
				System.out.println(convex.length);
			} else {
				System.out.println("new concave");
				concave.newConcatenate(hull);
				LinkedList.printList(concave);
				System.out.println(concave.length);
			}
            isConvex = !isConvex;

            System.out.println("Press Enter to Continue");
            System.in.read();
		}
		System.out.println("final convex");
		LinkedList.printList(convex);
		System.out.println(convex.length);
		System.out.println("final concave");
		LinkedList.printList(concave);
		System.out.println(concave.length);
		System.out.println("final concave reversed");
		concave.reverse_2();
		concave.top();
		LinkedList.printList(concave);

		return convex.concatenate((concave));
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
    	// dummy data

        Polygonization img = new Polygonization(3);
        int scale = 10;

        double[] x={ 0 , 5.0 };
        img.add(0, 5.0/scale, true);
        data.insert(x,p);
        p.advance();
        double[] a={ 5.0 , 10.0 };
        img.add(5.0/scale, 10.0/scale, true);
        data.insert(a,p);
        p.advance();
        double[] b={ 10.0 , 5.0 };
        img.add(10.0/scale, 5.0/scale, true);
        data.insert(b,p);
        p.advance();
        double[] c={ 5.0 , 0.0 };
        img.add(5.0/scale, 0.0/scale, true);
        data.insert(c,p);
        p.advance();
        double[] d={ 2.591231629 , 0.6184665998 };
        img.add(2.591231629/scale, 0.6184665998/scale, true);
        data.insert(d,p);
        p.advance();
        double[] e={ 7.679133975 , 0.7783603725 };
        img.add(7.679133975/scale, 0.7783603725/scale, true);
        data.insert(e,p);
        p.advance();
        double[] f={ 7.679133975 , 9.221639628 };
        img.add(7.679133975/scale, 9.221639628/scale, true);
        data.insert(f,p);
        p.advance();
        double[] g={ 2.591231629 , 9.3815334 };
        img.add(2.591231629/scale, 9.3815334/scale, true);
        data.insert(g,p);
        p.advance();

        System.out.println("STARTING");
        System.out.println(data);
        System.out.println(data.tail);
        System.out.println(data.tail.previous);

        LinkedList.printList(polygonize(data));
    	System.out.println("finished");
	}

}
