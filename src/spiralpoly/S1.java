package spiralpoly;
// S1 algorithm for Spiral Polygonization Paper by Godfried et al.
// Coded by Lingliang Zhang

public class S1 {
	public static LinkedList polygonize(LinkedList input) {
		LinkedList convex = new LinkedList();
		LinkedList concave = new LinkedList();
		ListNode last = null;
		ListNode secondLast;
		boolean isConvex = true;
		
		// the linked list with all remaining Integers
		LinkedList remaining = BucketSort.sort(input);
		
		System.out.println("Original:");
		LinkedList.printList(remaining);
		System.out.println(remaining.length);
				
		// special first case
		LinkedList hull = Graham.graham(remaining);
		System.out.println("First Shell:");
		LinkedList.printList(hull);
		System.out.println(hull.length);
		System.out.println("convex");
		convex.newConcatenate(hull);
		LinkedList.printList(convex);
		System.out.println(convex.length);
		
		hull.top();
		hull.pop();
		
		LinkedList to_remove = BucketSort.sort(hull);
		
		while (!to_remove.isEmpty()){
			// subtract most of the hull from the set of remaining points
			System.out.println("to remove");
			LinkedList.printList(to_remove);
			LinkedListItr remove_iterator = to_remove.first();
			LinkedListItr remaining_iterator = remaining.first();
			while (!remove_iterator.isPastEnd()){
				if (remaining_iterator.current.element[0] == remove_iterator.current.element[0]
						&& remaining_iterator.current.element[1] == remove_iterator.current.element[1]){
					remaining_iterator.current.previous.next = remaining_iterator.current.next; // delete the node
					remaining_iterator.current.next.previous = remaining_iterator.current.previous; // doubly link
					remaining.length -= 1;
					remove_iterator.advance();
				} else {
				}
				remaining_iterator.advance();
			}
			
			//prep for the next shell of the hull 
			isConvex = !isConvex;
			
			System.out.println("Remaining:");
			LinkedList.printList(remaining);
			System.out.println(remaining.length);
			hull = Graham.graham(remaining);
			
			System.out.println("Next Shell:");
			LinkedList.printList(hull);
			System.out.println(hull.length);
			
			if (hull.length == remaining.length) {
				if (isConvex) {
					convex.newConcatenate(hull);
				} else {
					concave.newConcatenate(hull);
				}
				System.out.println("wrapping up");
				break;
			}
			
			
			// remove everything except the last two points
			secondLast = hull.pop();
			// don't pop again if the hull only had one element this time.
			if (hull.header.next != hull.tail) {
				last = hull.pop();
			}
			System.out.println(hull.isEmpty());
			
			if (hull.isEmpty()) {
				if (isConvex) {
					convex.append2(secondLast);
					if (last != null) {
						convex.append2(last);
					}
				} else {
					concave.append2(secondLast);
					if (last != null) {
						concave.append2(last);
					}
				}
				System.out.println("wrapping up");
				break;
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
			
			if (hull.header.next != hull.tail) {
				to_remove = BucketSort.sort(hull);
			} else {
				to_remove = hull;
			}
			
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
