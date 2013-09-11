package com.algorithms.serpentine;

import java.util.NoSuchElementException;

class ChainNode implements Cloneable{
	Point element;
	ChainNode next;
	ChainNode previous;
	ChainNode(Point p){
		this.element = p;
		this.previous = null;
		this.next = null;
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

class ChainIterator{
	private ChainNode head;
	private ChainNode current;
	private ChainNode tail;

	ChainIterator(Chain chain, ChainNode start){
		this.head = chain.head;
		this.tail = chain.tail;
		this.current = start;
	}

	Point peekNext() {
		if (!hasNext()) throw new NoSuchElementException("No next element");
		else {
			return current.next.element;
		}
	}

	Point peekPrevious() {
		if (!hasPrevious()) throw new NoSuchElementException("No previous element");
		else {
			return current.previous.element;
		}
	}

	Point next() {
		Point p = peekNext();
		current = current.next;
		return p;
	}

	Point previous() {
		Point p = peekPrevious();
		current = current.previous;
		return p;
	}

	Point current(){
		return current.element;
	}
	
	ChainNode currentNode(){
		return current;
	}

	boolean hasNext() {
		return (current != tail && current.next != null);
	}

	boolean hasPrevious() {
		return (current != head && current.previous != null);
	}
}

