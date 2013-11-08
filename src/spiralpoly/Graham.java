package spiralpoly;
public class Graham {

	public static LinkedList graham(LinkedList SortedList) {
		// return straight away if there is only one element in the list
		if (SortedList.header.next.next == SortedList.tail) {
			return SortedList;
		}

		// Separate the points to for upper convex hull and lower convex hull
		LinkedList upper = new LinkedList();
		System.out.println(upper.length);
		LinkedList lower = new LinkedList();

		//Get first and last node
		ListNode firstNode = SortedList.header.next;
		ListNode currentNode = firstNode;

		while (currentNode.next.element != null){
			currentNode = currentNode.next;
		}
		ListNode lastNode = currentNode;
		currentNode = firstNode.next;

		upper.append2(firstNode);
		System.out.println(upper.length);
		lower.append2(firstNode);

		while (currentNode.next.element != null){
			if (ccw(firstNode, lastNode, currentNode) <= 0){
				lower.append2(currentNode);
			}
			else{
				upper.append2(currentNode);
				System.out.println(upper.length);
			}
			currentNode = currentNode.next;
		}
		upper.append2(lastNode);
		System.out.println(upper.length);
		lower.append2(lastNode);
		//Declare convexHull for the output
		LinkedList convexHull;

		// Make each convex hull
		LinkedList lowerHull = findHull(lower,1);
		LinkedList upperHull = findHull(upper,-1);
		System.out.println(upperHull.length);

		upperHull.pop();
		//Remove the same beginning and end of the lower hull
		lowerHull.reverse_2();
		lowerHull.pop();
		// Combine the two hulls
		convexHull = upperHull;
		convexHull.concatenate(lowerHull);
		return convexHull;
	}
	public static LinkedList findHull(LinkedList setOfPoints, Integer direction){
		LinkedList hull = new LinkedList();
		//Put first two points in the hull
		ListNode pointConsidered = setOfPoints.header.next;
		hull.append2(pointConsidered);
		ListNode pointSecondLast = pointConsidered;
		pointConsidered = pointConsidered.next;
		hull.append2(pointConsidered);
		ListNode pointLast = pointConsidered;
		pointConsidered = pointConsidered.next;

		//Graham scan
		while (pointConsidered.element != null){

			// if the last two points in the hull and the point to be considered form convex, 
			// accept the point and keep scanning

			if (direction*ccw(pointSecondLast, pointLast, pointConsidered) >= 0){
				hull.append2(pointConsidered);
				pointSecondLast = pointLast;
				pointLast = pointConsidered;
				pointConsidered = pointConsidered.next;

			}
			// if not remove the last point added
			else{
				hull.pop();
				pointLast = hull.tail.previous;
				pointSecondLast = pointLast.previous;
			}
		}
		return hull;
	}

	public static double ccw(ListNode P1, ListNode P2, ListNode P3){
		//Three points are a counter-clockwise turn if ccw > 0, clockwise if
		//ccw < 0, and collinear if ccw = 0
		double product1, product2;
		product1 = (P2.element[0] - P1.element[0])*(P3.element[1] - P1.element[1]);
		product2 = (P2.element[1] - P1.element[1])*(P3.element[0] - P1.element[0]);
		return (product1 - product2); 
	}

	public static void main(String[] args){
		//unsortedList is the input LinkedList
		LinkedList data = new LinkedList();
		LinkedListItr p = data.zeroth();
		/*
		double[] x={ 0 , 5.0 };
		data.insert(x,p);
		p.advance();
		double[] a={ 5.0 , 10 };
		data.insert(a,p);
		p.advance();
		double[] b={ 10 , 5 };
		data.insert(b,p);
		p.advance();
		double[] c={ 5 , 0 };
		data.insert(c,p);
		p.advance();
		double[] d={ 2.591231629 , 0.6184665998 };
		data.insert(d,p);
		p.advance();
		double[] e={ 7.679133975 , 0.7783603725 };
		data.insert(e,p);
		p.advance();
		double[] f={ 7.679133975 , 9.221639628 };
		data.insert(f,p);
		p.advance();
		double[] g={ 2.591231629 , 9.3815334 };
		data.insert(g,p);
		p.advance();
		 */

		double[] e={ 6 , 5 };
		data.insert(e,p);
		p.advance();
		double[] d={ 5 , 4 };
		data.insert(d,p);
		p.advance();
		double[] c={ 4 , 6 };
		data.insert(c,p);
		p.advance();
		double[] b={ 3, 8 };
		data.insert(b,p);
		p.advance();
		double[] a={ 2 , 5 };
		data.insert(a,p);
		p.advance();
		double[] x={ 1 , 1 };
		data.insert(x,p);
		p.advance();


		//Test
		System.out.println("Given sorted points (X-ascending, Y-ascending): ");
		LinkedList.printList(data);	
		LinkedList convexResult = graham(data);
		System.out.println();
		System.out.println("Convex Hull: ");
		LinkedList.printList(convexResult);
	}
}
