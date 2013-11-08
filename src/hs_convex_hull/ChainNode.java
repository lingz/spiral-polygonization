package hs_convex_hull;

public class ChainNode implements Cloneable{
	public Point element;
	ChainNode next;
	ChainNode previous;
	ChainNode(Point p){
		this.element = p;
		this.previous = null;
		this.next = null;
	}
	
	public boolean equals (ChainNode node) {
		return this.element == node.element;
	}
	
	protected ChainNode clone() {
		ChainNode node;
		try {
			node = (ChainNode) super.clone();
			node.element = this.element;
			node.next = this.next;
			node.previous = this.previous;
			return node;
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
			return null;
		}
	}
}
