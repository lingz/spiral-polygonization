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
		LinkedList hull = Graham.graham(remaining);
		hull.top();
		hull.pop();
		
		LinkedList.printList(hull);
		LinkedList to_remove = BucketSort.sort(hull);
		LinkedList.printList(to_remove);
		
		while (!to_remove.isEmpty()){
			// subtract most of the hull from the set of remaining points
			LinkedListItr remove_iterator = to_remove.first();
			LinkedListItr remaining_iterator = remaining.first();
			LinkedList.printList(to_remove);
			LinkedList.printList(remaining);
			while (!remove_iterator.isPastEnd()){
				if (remaining_iterator.current.element[0] == remove_iterator.current.element[0]
						&& remaining_iterator.current.element[1] == remove_iterator.current.element[1]){
					remaining_iterator.current.previous.next = remaining_iterator.current.next; // delete the node
					System.out.println("Removing");
					remaining_iterator.current.next.previous = remaining_iterator.current.previous; // doubly link
					remove_iterator.advance();
				} else {
					System.out.println("not removing");
				}
				remaining_iterator.advance();
			}
			LinkedList.printList(to_remove);
			LinkedList.printList(remaining);
			
			//prep for the next shell of the hull 
			isConvex = !isConvex;
			hull = Graham.graham(remaining);
			System.out.println(hull.header.next.previous == hull.header);
			
			// merge copies of lists
			if (isConvex) {
				LinkedList.printList(hull);
				LinkedList.printList(convex);
				convex.newConcatenate(hull);
				LinkedList.printList(convex);
			} else {
				LinkedList.printList(hull);
				LinkedList.printList(concave);
				concave.newConcatenate(hull);
				LinkedList.printList(concave);
			}
			
			System.out.println(hull.header.next.previous == hull.header);
			hull.pop();
			System.out.println(hull.header.next.previous == hull.header);
			// don't pop again if the hull only had one element this time.
			if (hull.header.next != hull.tail) {
				hull.pop();
			}
			
			// remove everything except the last two points
			if (hull.header.next != hull.tail) {
				to_remove = BucketSort.sort(hull);
			} else {
				to_remove = hull;
			}
		}
		LinkedList.printList(concave);
		LinkedList.printList(convex);
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
    	System.out.println("finished");
	}

}
