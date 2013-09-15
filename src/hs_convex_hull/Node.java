package hs_convex_hull;

/**
 * This class implements the nodes used in the interval tree, along with 
 */
class Node {
	Chain hull;
	ChainNode[] tan;
	Node left;
	Node right;

	Node(Point point){
		this.hull = new Chain(new ChainNode(point));
		this.tan = null;
		this.left = null;
		this.right = null;
	}

	Node(Node u, Node w) {
		ChainNode lSplice;
		ChainNode rSplice;
		this.left = u;
		this.right = w;
		if(u.hull.head.element == null) {
			this.hull = w.hull;
			this.tan = w.tan;
			this.left = w.left;
			this.right = w.right;
		} else if (w.hull.head.element == null){
			this.hull = u.hull;
			this.tan = u.tan;
			this.left = u.left;
			this.right = u.right;
		} else {
			this.tan = tan(u.hull, w.hull);
			lSplice = this.tan[0].next;
			rSplice = this.tan[1].previous;
			try {
				this.hull = new Chain(u.hull, w.hull, this.tan[0], this.tan[1]);
			} catch (CloneNotSupportedException e) {
				e.printStackTrace();
				this.hull = null;
			}
			
			//Remove the points from u and w which are stored in v
			if (lSplice == null) 
				u.hull = null;
			else
				u.hull.head = lSplice;

			if (rSplice == null) 
				w.hull = null;
			else
				w.hull.tail = rSplice;
		}
	}

	static ChainNode[] tan(Chain u, Chain w){
		return tan(u, w, new ChainNode[] {u.head,w.head});
	}

	static ChainNode[] tan(Chain u, Chain w, ChainNode[] pq)
	{
		ChainIterator[] iter = {new ChainIterator(u, pq[0]), new ChainIterator(w, pq[1])};
		double[][] midPoints = {iter[0].current().coord, iter[1].current().coord};
		Boolean[] side = {null, null, null, null};
		double[] angles = new double[2];
		int idx;
		double caliperAngle = (Math.PI)/2;

		while (iter[0].hasNext()  || iter[1].hasNext()){
			//Get the side of the 4 adjacent points (previous/next of left/right anchors)
			for (int i=0; i<2; i++){
				side[i*2] = (iter[i].hasPrevious() ? 
						side(iter[i].peekPrevious().coord, midPoints[0], midPoints[1])
						: null);
				side[i*2+1] = (iter[i].hasNext() ?
						side(iter[i].peekNext().coord, midPoints[0], midPoints[1])
						: null);
				angles[i] = (iter[i].hasNext() ?
						angle(caliperAngle, midPoints[i], iter[i].peekNext().coord)
						: Math.PI*2);
			}

			//Compare the different sides
			boolean same = false;
			Boolean below = null;
			for (int i = 0; i<4; i++){
				if (side[i]!= null){
					if(below == null) {
						below = side[i];
						same = true;
					} else
						same = (side[i] == below);
				}
			}

			//Found
			if ((same && below) || below == null) break;
			//Find smallest angle and re-adjust midpoint;
			else {
				idx = (angles[0]<=angles[1] ? 0 : 1);
				caliperAngle -= angles[idx];
				midPoints[idx] = iter[idx].next().coord;
			}
		}
		return new ChainNode[] {iter[0].currentNode(), iter[1].currentNode()};
	}

	private static double angle(double caliperAngle, double[] p1, double[] p2){
		return caliperAngle-Math.atan2(p2[1]-p1[1], p2[0]-p1[0]);
	}

	//True if p1 is below a horizontal line p2-p3
	private static boolean side(double[] p1, double[] p2, double[] p3){
		return ((p2[0]-p1[0])*(p3[1]-p1[1])-(p2[1]-p1[1])*(p3[0]-p1[0])) <= 0;
	}
}
