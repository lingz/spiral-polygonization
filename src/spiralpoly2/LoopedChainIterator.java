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
		System.out.println("Calling Next");
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

	public Point current(){
		return current.element;
	}
	
	public ChainNode currentNode(){
		return current;
	}

	public boolean isLast(){
		System.out.println("Calling isLast ");
		return (current == secondLast);
	}

}