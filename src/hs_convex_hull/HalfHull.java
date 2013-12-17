package hs_convex_hull;

import java.util.Arrays;
import java.util.LinkedList;

import visualize.Polygonization;

public class HalfHull {
	private Node root;
	public Point[] points;

	public HalfHull(Point[] S) {
		this.points = S;
		buildIntervalTree();
	}

	public Chain inspect(){
		return root.hull;
	}



	public static void printChainCoord(Chain c, boolean inverse){
		double x = 0;
		if (c==null || c.head == null || c.head.element == null){
//			System.out.println("Empty chain");
		} else {
			ChainIterator iter = new ChainIterator(c, c.head);
			PointDistribution.printCoord(iter.current(), inverse);
			x = iter.current().coord[0];
			while (iter.hasNext()){
				PointDistribution.printCoord(iter.next(), inverse);
				if (x>iter.current().coord[0]){
//					System.out.println("ARRRRG");
				} else {
					x = iter.current().coord[0];
				}
			}
		}
	}

	public static void showHull(Polygonization img, Chain c, Point[] rest, boolean inverse){
		if (c==null || c.head == null || c.head.element == null){
//			System.out.println("Empty chain");
		} else {
			ChainIterator iter = new ChainIterator(c, c.head);
			double coord[] = new double[2];
			coord = iter.current().coord;
			if (inverse)
				img.add(1-coord[0], 1-coord[1]);
			else
				img.add(coord[0],coord[1]);
			while (iter.hasNext()){
				coord = iter.next().coord;
				if (inverse)
					img.add(1-coord[0], 1-coord[1]);
				else
					img.add(coord[0],coord[1]);
			}
			for (int i = 0; i<rest.length; i++){
				coord = rest[i].coord;
				if (inverse)
					img.add(1-coord[0], 1-coord[1], true);
				else
					img.add(coord[0],coord[1], true);
			}
		}
		return;
	}

	private void buildIntervalTree() {
		int chainNumber = -1;
		LinkedList<Node> queue = new LinkedList<>();
		double levels = Math.ceil(Math.log(this.points.length)/Math.log(2));
		int size = (int) Math.pow(2, levels);
		int nextIters = size;
		int currentIter = -1;
		int level = -1;
		int brightness = (int) (255/(levels-1));
		//System.out.println(nextIters);
		//System.out.println(brightness);
		for (int i=0; i<this.points.length; i++){
			currentIter++;
			queue.add(new Node(this.points[i]));
		}
		for (int i = this.points.length; i<size; i++){
			currentIter++;
			queue.add(new Node(null));
		}
		while (queue.size()!=1){
			Node nodel = queue.poll();
			Node noder = queue.poll();
			if (nodel.tan != null){
				//System.out.println("tan[0] nodel: ");
				//PointDistribution.printCoord(nodel.tan[0].element, false);
				//System.out.println("tan[1] nodel: ");
				//PointDistribution.printCoord(nodel.tan[1].element, false);
			}
			if (noder.tan != null){
				//System.out.println("tan[0] noder: ");
				//PointDistribution.printCoord(noder.tan[0].element, false);
				//System.out.println("tan[1] noder: ");
				//PointDistribution.printCoord(noder.tan[1].element, false);
				//System.out.println("chainNumber: " + chainNumber);
			}
			Node node = new Node(nodel, noder);
			queue.add(node);
			currentIter++;
			if(currentIter == nextIters){
				nextIters /= 2;
				currentIter = 0;
				level++;
			}
			if (node.tan!=null) {
				chainNumber++;
				//PointDistribution.printCoord(node.tan[0].element, false);
				//PointDistribution.printCoord(node.tan[1].element, false);
				//System.out.println();
				//img.add(node.tan[0].element.coord[0], node.tan[0].element.coord[1], chainNumber, brightness*level);
				//img.add(node.tan[1].element.coord[0], node.tan[1].element.coord[1], chainNumber, brightness*level);
			}
		}
		this.root = queue.poll();
	}

	public void delete(Point p) {
		delete(p, this.root);
	}

	private static void delete(Point p, Node v) {
		Node u = v.left;
		Node w = v.right;
		if (u!= null){
			try {
				u.hull = new Chain(v.hull, u.hull, v.tan[0], null);
			} catch (CloneNotSupportedException e) {
				e.printStackTrace();
				u.hull = null;
			}
			v.hull.head = v.tan[1];
		} 
		if (w!= null){
			try {
				w.hull = new Chain(w.hull, v.hull, null, v.tan[1]);
			} catch (CloneNotSupportedException e) {
				e.printStackTrace();
				w.hull = null;
			}
			v.hull.tail = v.tan[0];
		}
		if (v.tan != null){
			v.hull = null;

			Node recurse = (p.coord[0]<=u.hull.tail.element.coord[0] ? u : w);
			if (recurse.hull.head == recurse.hull.tail && recurse.hull.head.element == p){
				if (recurse == w){
					v.hull = v.left.hull;
					v.right = v.left.right;
					v.tan = v.left.tan;
					v.left = v.left.left;
				} else{
					v.hull = v.right.hull;
					v.left = v.right.left;
					v.tan = v.right.tan;
					v.right = v.right.right;
				}
			} else {

				//FIX THIS FOR O() EFFICIENCY?
				ChainNode[] pq;
				if (p == v.tan[0].element){ 

				} else {

				}
				if (p == v.tan[1].element){

				}else {

				}

				delete(p, recurse);
				ChainNode[] tan = Node.tan(u.hull, w.hull);


				ChainNode lSplice = tan[0].next;
				ChainNode rSplice = tan[1].previous; 
				try {
					v.hull = new Chain(u.hull, w.hull, tan[0], tan[1]);
				} catch (CloneNotSupportedException e) {
					e.printStackTrace();
					v.hull = null;
				}
				v.tan = tan;

				if (lSplice == null)
					u.hull = null;
				else 
					u.hull.head = lSplice;

				if (rSplice == null)
					w.hull = null;
				else 
					w.hull.tail = rSplice;
			}
		} else {
			v.hull = null;
			v.tan = null;
			v.left = null;
			v.right = null;
		}
	}
}