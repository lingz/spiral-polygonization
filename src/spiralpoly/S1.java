package spiralpoly;
// S1 algorithm for Spiral Polygonization Paper by Godfried et al.
// Coded by Lingliang Zhang

public class S1 {
	public static LinkedList polygonize(LinkedList input) {
		LinkedList convex = new LinkedList();
		LinkedList concave = new LinkedList();
		boolean isConvex = true;
		
		// the linked list with all remaining Integers
		LinkedList remaining = BucketSort.sort(input);
				
		// special first case
		System.out.println("starting");
		LinkedList.printList(remaining);
		System.out.println("tail remaining");
		System.out.println(remaining.tail.previous.element[0]);
		LinkedList hull = Graham.graham(remaining);
		System.out.println("tail");
		System.out.println(hull.tail.previous.element[0]);
		System.out.println("starting2");
		hull.printList(hull);
		System.out.println("tail");
		System.out.println(hull.tail.previous.element[0]);
		hull.top();
		System.out.println("tail");
		System.out.println(hull.tail.previous.element[0]);
		hull.printList(hull);
		hull.pop();
		//hull.printList(hull);
		
		LinkedList to_remove = BucketSort.sort(hull);
		
		while (!to_remove.isEmpty()){
			// subtract most of the hull from the set of remaining points
			LinkedListItr remove_iterator = new LinkedListItr(to_remove.header);
			LinkedListItr remaining_iterator = new LinkedListItr(to_remove.header);
			LinkedListItr remove_trail = new LinkedListItr(to_remove.header);
			while (remove_iterator.hasNext()){
				remaining_iterator.advance();
				if (remaining_iterator.current.element == remove_iterator.current.element){
					remove_trail.current.next = remaining_iterator.current.next; // delete the node
					remove_iterator.advance();
				}
			}
			//prep for the next shell of the hull 
			isConvex = !isConvex;
			remaining.printList(remaining);
			hull = Graham.findHull(remaining, 1);
			
			if (isConvex) {
				convex.concatenate(hull);
			} else {
				concave.concatenate(hull);
			}
			
			hull.pop();
			hull.pop();
			
			// remove everything except the last two points
			to_remove = BucketSort.sort(hull);
		}
		return convex.concatenate((concave.reverse()));
	}
	public static void main(String[] args) {
    	LinkedList data = new LinkedList();
    	LinkedListItr p = data.zeroth();
    	
    	double[] x={0.2313,0.281};
    	data.insert(x,p);
    	p.advance();
    	double[] y={0.4214,0.291};
    	data.insert(y,p);
    	p.advance();
    	double[] a={0.4214,0.191};
    	data.insert(a,p);
    	p.advance();
    	double[] z={0.2315,0.261};
    	data.insert(z,p);
    	p.advance();
    	double[] w={0.8312,0.231};
    	data.insert(w,p);
    	p.advance();
    	double[] v={0.9913,0.786};
    	data.insert(v,p);
    	p.advance();
    	// dummy data
    	
    	LinkedList.printList(polygonize(data));
		
    	
	}

}
