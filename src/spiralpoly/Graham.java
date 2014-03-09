package spiralpoly;

public class Graham {

    ListNode listHead = null;
    ListNode newListhead = null;
    int numToKeep = 0;

    // made non static so it can store what the supposed listHead is supposed to be
    public Graham(ListNode listHead) {
        this.listHead = listHead;
        this.numToKeep = numToKeep;
    }

    public Graham() {

    }


	public LinkedList graham(LinkedList sortedList) {
		// return straight away if there is only one element in the list
		if (sortedList.header.next.next == sortedList.tail) {
			return sortedList;
		}

		// Separate the points to for upper convex hull and lower convex hull
		LinkedList upper = new LinkedList();
		LinkedList lower = new LinkedList();




		//Get first and last node
		ListNode firstNode = sortedList.getHead();


        // we need to make sure that if the firstNode is the listHead, we rotate it away.
        if (firstNode == listHead) {
            sortedList.rotateToHead(firstNode.getNext());
            firstNode = sortedList.getHead();
        }

        ListNode currentNode = firstNode;

            while (currentNode.next.element != null){
			currentNode = currentNode.next;
		}
		ListNode lastNode = currentNode;
		currentNode = firstNode.next;

		upper.appendToHull(firstNode);
		lower.appendToHull(firstNode);

		while (currentNode.next.element != null){
			if (ccw(firstNode, lastNode, currentNode) <= 0){
				lower.appendToHull(currentNode);
			}
			else{
				upper.appendToHull(currentNode);
			}
			currentNode = currentNode.next;
		}
		upper.appendToHull(lastNode);
		lower.appendToHull(lastNode);
		//Declare convexHull for the output
		LinkedList convexHull;

		// Make each convex hull
		LinkedList lowerHull = findHull(lower,1);
		LinkedList upperHull = findHull(upper,-1);

		upperHull.pop();
		//Remove the same beginning and end of the lower hull
		lowerHull.reverse_2();
		lowerHull.pop();
		// Combine the two hulls
		convexHull = upperHull;
		convexHull.concatenate(lowerHull);
        // delete them from the origin list
        convexHull.removeOriginals();
        if (newListhead != null) convexHull.rotateToHead(newListhead);
		return convexHull;
	}

	public LinkedList findHull(LinkedList setOfPoints, Integer direction){
        ListNode latestAddition;
		LinkedList hull = new LinkedList();
		//Put first two points in the hull
		ListNode pointConsidered = setOfPoints.header.next;
		hull.appendToHull(pointConsidered);
		ListNode pointSecondLast = pointConsidered;
		pointConsidered = pointConsidered.next;
		hull.appendToHull(pointConsidered);
		ListNode pointLast = pointConsidered;
		pointConsidered = pointConsidered.next;
        LinkedList.printList(hull);

		//Graham scan
		while (pointConsidered.element != null){

			// if the last two points in the hull and the point to be considered form convex, 
			// accept the point and keep scanning
			if (direction*ccw(pointSecondLast, pointLast, pointConsidered) >= 0){
				latestAddition = hull.appendToHull(pointConsidered);
				pointSecondLast = pointLast;
				pointLast = pointConsidered;
				pointConsidered = pointConsidered.next;
                if (pointLast.original == listHead) newListhead = latestAddition;
			}
			// if not remove the last point added
			else{
                System.out.println("REMOVING LAST");
                if (pointSecondLast.previous != hull.header) {
                    System.out.println(hull);
                    System.out.println(pointLast);
                    System.out.println(pointSecondLast);
                    hull.pop();
                    pointLast = hull.tail.previous;
                    pointSecondLast = pointLast.previous;
                    System.out.println("***");
                    System.out.println(pointLast);
                    System.out.println(pointSecondLast);
                }
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
		LinkedList convexResult = new Graham().graham(data);
		System.out.println();
		System.out.println("Convex Hull: ");
		LinkedList.printList(convexResult);
        System.out.println("Remaining points");
        LinkedList.printList(data);
    }
}
