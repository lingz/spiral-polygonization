package com.algorithms.serpentine;

import java.util.Arrays;
import java.util.LinkedList;

public class Hull {
	private Node root;
	public Point[] points;

	public Hull(Point[] S) {
		this.points = S;
		buildIntervalTree();
	}

	public Chain inspect(){
		return root.hull;
	}

	public static void printChainCoord(Chain c, boolean inverse){
		if (c == null)
			System.out.println("Empty chain");
		else {
			ChainIterator iter = new ChainIterator(c, c.head);
			PointDistribution.printCoord(iter.current(), inverse);
			while (iter.hasNext()){
				PointDistribution.printCoord(iter.next(), inverse);
			}
		}
	}

	private void buildIntervalTree() {
		LinkedList<Node> queue = new LinkedList<>();
		int size = (int) Math.pow(2, Math.ceil(Math.sqrt(this.points.length)));
		for (int i=0; i<this.points.length; i++)
			queue.add(new Node(this.points[i]));
		for (int i = this.points.length; i<size; i++)
			queue.add(new Node(null));
		while (queue.size()!=1)
			queue.add(new Node(queue.poll(), queue.poll()));
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

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		while (true){
			PointDistribution ps = new PointDistribution(Math.round((float)Math.random()*100));
			Arrays.sort(ps.uniformPoints[0]);
			Arrays.sort(ps.uniformPoints[1]);
			Hull[] hull = {new Hull(ps.uniformPoints[0]), new Hull(ps.uniformPoints[1])};
			for (int i = 0; i<2; i++){
				if (i==0)
					System.out.print("Upper ");
				else
					System.out.print("Lower ");
				System.out.println("hull:");
				Hull.printChainCoord(hull[i].inspect(), (i==0? false : true));
				System.out.println();
				while (hull[i].points.length != 0){
					Point p = hull[i].points[Math.round((float)Math.random()*(hull[i].points.length-1))];
					hull[i].delete(p);

					//Only needed for test since all points are randomly removed
					int idx = Arrays.binarySearch(hull[i].points, p);
					Point[] arrayl = Arrays.copyOfRange(hull[i].points, 0, idx);
					Point[] arrayr = Arrays.copyOfRange(hull[i].points, idx+1, hull[i].points.length);
					for (int j = 0; j<hull[i].points.length-1;j++){
						if(j<arrayl.length) hull[i].points[j] = arrayl[j];
						else hull[i].points[j] = arrayr[j-arrayl.length];
					}
					hull[i].points = Arrays.copyOf(hull[i].points, hull[i].points.length-1);

					System.out.println("Deleted point:");
					PointDistribution.printCoord(p, (i==0? false : true));
					if (i==0)
						System.out.print("Upper ");
					else
						System.out.print("Lower ");
					System.out.println("hull:");
					Hull.printChainCoord(hull[i].inspect(), (i==0? false : true));
					System.out.println();
				}
			}
			System.out.println();
		}
	}
}