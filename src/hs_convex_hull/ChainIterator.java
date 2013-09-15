package hs_convex_hull;

import java.util.NoSuchElementException;

public class ChainIterator{
	private ChainNode head;
	ChainNode current;
	private ChainNode tail;

	public ChainIterator(Chain chain, ChainNode start){
		this.head = chain.head;
		this.tail = chain.tail;
		this.current = start;
	}

	public Point peekNext() {
		if (!hasNext()) throw new NoSuchElementException("No next element");
		else {
			return current.next.element;
		}
	}
	
	public ChainNode peekNextNode() {
		if (!hasNext()) throw new NoSuchElementException("No next element");
		else {
			return current.next;
		}
	}

	public Point peekPrevious() {
		if (!hasPrevious()) throw new NoSuchElementException("No previous element");
		else {
			return current.previous.element;
		}
	}

	public Point next() {
		Point p = peekNext();
		current = current.next;
		return p;
	}

	public Point previous() {
		Point p = peekPrevious();
		current = current.previous;
		return p;
	}

	public Point current(){
		return current.element;
	}
	
	public ChainNode currentNode(){
		return current;
	}

	public boolean hasNext() {
		return (current != tail && current.next != null);
	}

	public boolean hasPrevious() {
		return (current != head && current.previous != null);
	}
}

