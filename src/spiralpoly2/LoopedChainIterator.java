package spiralpoly2;

import java.util.NoSuchElementException;

import org.apache.commons.lang3.ArrayUtils;

import hs_convex_hull.Chain;
import hs_convex_hull.ChainNode;
import hs_convex_hull.Point;

public class LoopedChainIterator {
	private Chain chain1;
	ChainNode current;
	ChainNode last;
	ChainNode secondLast;
	private Chain chain2;
	
	public LoopedChainIterator(Chain chain1, Chain chain2, ChainNode start, ChainNode last, ChainNode second_last){
		this.last = last;
		this.secondLast = second_last;
		this.current = start;
		this.chain1 = chain1;
		this.chain2 = chain2;
	}
	
//	public Point peekNext() {
//		return current.next().element;
//	}
//	
//	public ChainNode peekNextNode() {
//		return current.next();
//	}

//	public Point peekPrevious() {
//		return current.previous().element;
//	}
//	
//	public ChainNode peekPreviousNode() {
//		return current.previous();
//	}

	public Point next() {
		if (current == chain1.tail)
		{
			if (chain2.isEmpty()) {
				current = chain1.head;
			}
			else {
				current = chain2.head.next();	
			}
		}
		else if (current == chain2.tail)
		{
			if (chain1.isEmpty()) {
				current = chain2.head;
			}
			else {
				current = chain1.head.next();	
			}
		}
		else
		{
			current = current.next();
		}
		return current.element;
	}

//	public Point previous() {
//		Point p = peekPrevious();
//		current = current.previous();
//		return p;
//	}
	
	static double[] invert(double[] x) {
		double[] x1 = new double[2];
		x1[0] = 1 - x[0];
		x1[1] = 1 - x[1];
		return x1;
	}

	public Point current(){
		return current.element;
	}
	
	public ChainNode currentNode(){
		return current;
	}

	public boolean isLast(){
		if (current == secondLast) {
			return true;
		}
		else if (current.element.inverted != secondLast.element.inverted) {
			return 0 == current.element.compareTo(new Point(new double[] {1-secondLast.element.coord[0], 1-secondLast.element.coord[1]}));
		}
		return false;
	}

}