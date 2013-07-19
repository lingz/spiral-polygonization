package spiralpoly;
public class Graham {

	public static LinkedList graham(LinkedList SortedList) {
		
		//Get first node
		ListNode firstNode = SortedList.header.next;
		//Get last node
		ListNode lastNode = firstNode;
		while (lastNode.next.element != null){
			lastNode = lastNode.next;
		}
		//Find angle of line from new origin to Xmax with the new x-axis
		//Make Xmax relative to Xmin - and find angle from the relative coordinate of Xmax
		double angle = Math.atan((lastNode.element[1]-firstNode.element[1]) / (lastNode.element[0]-firstNode.element[0]));
		
		double newXorigin = firstNode.element[0];
		double newYorigin = firstNode.element[1];
		
		//Transform coordinates
		ListNode currentShift = firstNode;
		double newAngle;
		double radius;

		while (currentShift.element != null){
			currentShift.element[0] += (-1.0)*newXorigin;
			currentShift.element[1] += (-1.0)*newYorigin;
			if (currentShift.element[0] == 0){
				newAngle = 0 - angle;
			}
			else{
			newAngle = Math.atan(currentShift.element[1]/currentShift.element[0]) - angle;
			}
			radius = Math.sqrt( Math.pow(currentShift.element[0],2) + Math.pow(currentShift.element[1],2) );
			currentShift.element[0] = radius*Math.cos(newAngle);
			currentShift.element[1] = radius*Math.sin(newAngle);
			currentShift = currentShift.next;
		}
		
		//Declare convexHull for the output
		LinkedList convexHull;
		
		// Separate the points to for upper convex hull and lower convex hull
		LinkedList upper = new LinkedList();
		LinkedList lower = new LinkedList();
		
		// get the y position of Xmin to be the separate line
		ListNode currentNode = SortedList.header.next;
		double separateLine = currentNode.element[1];
		
		while (currentNode.element != null){
			// Put the beginning point in both lists
			if (currentNode == SortedList.header.next){
				upper.append(currentNode);
				lower.append(currentNode);
			}
			// Put the last point in both lists
			else if (currentNode.next.element == null){
				upper.append(currentNode);
				lower.append(currentNode);
			}
			// Separate the points for upper and lower list
			else if (currentNode.element[1] >= separateLine){
				upper.append(currentNode);
			}
			else if (currentNode.element[1] < separateLine) {
				lower.append(currentNode);
			}
			currentNode = currentNode.next;
		}
		// Make each convex hull
		LinkedList upperHull = findHull(upper,1);
		LinkedList lowerHull = findHull(lower,-1);
		
		//Transform Hulls back
		ListNode currentShiftBack = upperHull.header.next;
		while (currentShiftBack.element != null){
			if (currentShift.element[0] == 0){
				newAngle = angle;
			}
			else{
			newAngle = Math.atan(currentShift.element[1]/currentShift.element[0]) + angle;
			}
			radius = Math.sqrt( Math.pow(currentShiftBack.element[0],2) + Math.pow(currentShiftBack.element[1],2) );
			currentShiftBack.element[0] = radius*Math.cos(newAngle);
			currentShiftBack.element[1] = radius*Math.sin(newAngle);
			currentShiftBack.element[0] += newXorigin;
			currentShiftBack.element[1] += newYorigin;
			currentShiftBack = currentShiftBack.next;
		}
		
		currentShiftBack = lowerHull.header.next;
		while (currentShiftBack.element != null){
			if (currentShift.element[0] == 0){
				newAngle = angle;
			}
			else{
			newAngle = Math.atan(currentShift.element[1]/currentShift.element[0]) + angle;
			}
			radius = Math.sqrt( Math.pow(currentShiftBack.element[0],2) + Math.pow(currentShiftBack.element[1],2) );
			currentShiftBack.element[0] = radius*Math.cos(newAngle);
			currentShiftBack.element[1] = radius*Math.sin(newAngle);
			currentShiftBack.element[0] += newXorigin;
			currentShiftBack.element[1] += newYorigin;
			currentShiftBack = currentShiftBack.next;
		}
		
		//Remove the same beginning and end of the lower hull
		lowerHull.pop();
		lowerHull.reverse();
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
		hull.append(pointConsidered);
		ListNode pointSecondLast = pointConsidered;
		
		pointConsidered = pointConsidered.next;
		hull.append(pointConsidered);
		ListNode pointLast = pointConsidered;
		
		//Graham scan
		while (pointConsidered.element != null){
			// if the last two points in the hull and the point to be considered form convex, 
			// accept the point and keep scanning
			if (direction*ccw(pointSecondLast, pointLast, pointConsidered) >= 0){
				hull.append(pointConsidered);
				pointSecondLast = pointLast;
				pointLast = pointConsidered;
				pointConsidered = pointConsidered.next;
			}
			// if not remove the last point added
			else{
				hull.pop();
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
	
}
