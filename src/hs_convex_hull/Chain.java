package com.algorithms.serpentine;

class Chain implements Cloneable{
	ChainNode head;
	ChainNode tail;

	Chain(ChainNode n){
		this.head = n;
		this.tail = n;
	}

	Chain(Chain l, Chain r, ChainNode lSplice, ChainNode rSplice) throws CloneNotSupportedException {
		Chain lClone = (l == null ? 
				null 
				: (Chain) l.clone());
		Chain rClone = (r == null ? 
				null 
				: (Chain) r.clone());
		
		if (lSplice != null) {
			if (rSplice != null)
				lSplice.next = rSplice;
			else
				lSplice.next = (rClone != null ? 
						rClone.head 
						: null);
		}

		
		if(rSplice != null) {
			if (lSplice != null)
				rSplice.previous = lSplice;
			else
				rSplice.previous = (lClone != null ? 
						lClone.tail 
						: null);
		}

		if (l!= null && lSplice != null)
			lClone.tail = lSplice;
		
		if (r!= null)
			if (r.head.next == null && r.tail.previous == null){
				rClone.tail = rSplice;
				rClone.head = rSplice;
			}

		if (lClone != null) 
			this.head = lClone.head;
		else {
			this.head = rClone.head;
			this.head.previous = null;
		}

		if (rClone != null) 
			this.tail = rClone.tail;
		else if (lClone != null) {
			this.tail = lClone.tail;
			this.tail.next = null;
		}
		
		if (this.head != null && this.head == this.tail){
			this.head.next = null;
			this.tail.previous = null;
		} 
	}

	protected Chain clone() {
		try {
			Chain chain;
			chain = (Chain) super.clone();
			chain.head = this.head;
			chain.tail = this.tail;
			return chain;
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
			return null;
		}
	}

}