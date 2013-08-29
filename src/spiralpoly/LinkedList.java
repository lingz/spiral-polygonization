package spiralpoly;
// LinkedList class
//
// CONSTRUCTION: with no initializer
// Access is via LinkedListItr class
//
// ******************PUBLIC OPERATIONS*********************
// boolean isEmpty( )	 --> Return true if empty; else false
// void makeEmpty( )	  --> Remove all items
// LinkedListItr zeroth( )--> Return position to prior to first
// LinkedListItr first( ) --> Return first position
// void insert( x, p )	--> Insert x after current iterator position p
// void remove( x )	   --> Remove x
// LinkedListItr find( x )
//						--> Return position that views x
// LinkedListItr findPrevious( x )
//						--> Return position prior to x
// ******************ERRORS********************************
// No special errors

/**
 * Linked list implementation of the list
 *	using a header node.
 * Access to the list is via LinkedListItr.
 * @author Mark Allen Weiss
 * @see LinkedListItr
 */
public class LinkedList
{
	/**
	 * Construct the list
	 */
	public LinkedList( )
	{
		header = new ListNode(null);
		tail = new ListNode(null, null, header);
		header.next = tail;
	}

	/**
	 * Test if the list is logically empty.
	 * @return true if empty, false otherwise.
	 */
	public boolean isEmpty( )
	{
		return header.next == tail;
	}

	/**
	 * Make the list logically empty.
	 */
	public void makeEmpty( )
	{
		header.next = null;
	}


	/**
	 * Return an iterator representing the header node.
	 */
	public LinkedListItr zeroth( )
	{
		return new LinkedListItr( header );
	}

	/*
	 * Return an iterator representing the first node in the list.
	 * This operation is valid for empty lists.
	 */
	public LinkedListItr first( )
	{
		return new LinkedListItr( header.next );
	}
	
	public LinkedListItr last() {
		return new LinkedListItr( tail.previous);
	}

	/**
	 * Insert after p.
	 * @param x the item to insert.
	 * @param p the position prior to the newly inserted item.
	 */
	public void insert( double[] x, LinkedListItr p )
	{
		if( p != null && p.current != null ) {
			ListNode nextNode = p.current.next;
			p.current.next = new ListNode( (double[]) x, nextNode, p.current );
			nextNode.previous = p.current.next;
		}
	}

	/**
	 * Return iterator corresponding to the first node containing an item.
	 * @param x the item to search for.
	 * @return an iterator; iterator isPastEnd if item is not found.
	 */
	public LinkedListItr find( Object x )
	{
/* 1*/	  ListNode itr = header.next;

/* 2*/	  while( itr != null && !itr.element.equals( x ) )
/* 3*/		  itr = itr.next;

/* 4*/	  return new LinkedListItr( itr );
	}
	
	
	/**
	 * Return iterator prior to the first node containing an item.
	 * @param x the item to search for.
	 * @return appropriate iterator if the item is found. Otherwise, the
	 * iterator corresponding to the last element in the list is returned.
	 */
	public LinkedListItr findPrevious( double[] x )
	{
/* 1*/	  ListNode itr = header;

/* 2*/	  while( itr.next != null && !(itr.next.element[0]==x[0]) && !(itr.next.element[1]==x[1]) )
/* 3*/		  itr = itr.next;

/* 4*/	  return new LinkedListItr( itr );
	}

	/**
	 * Remove the first occurrence of an item.
	 * @param x the item to remove.
	 */
	public void remove( double[] x )
	{
		LinkedListItr p = findPrevious( x );

		if( p.current.next != null )
			p.current.next = p.current.next.next;  // Bypass deleted node
	}
	// Append method
	public void append(ListNode node) {
		ListNode last = tail.previous;
		last.next = node;
		node.next = tail;
		node.previous = last;
		tail.previous = node;
	}
	
	public void append2(ListNode node) {
		append(new ListNode(node.element));
/*		if (node.element != null){
			double[] newElement={0,0};
			newNode.element = newElement;
			newNode.element[0] = node.element[0];
			newNode.element[1] = node.element[1];}
		else{
			newNode.element = node.element;
		}

		newNode.next	= node.next;
		newNode.previous = node.previous;
		ListNode last = tail.previous;
		last.next = newNode;
		newNode.next = tail;
		newNode.previous = last;
		tail.previous = newNode;*/
	}
	
	// Pop method
	public ListNode pop() {
		ListNode oldLast = tail.previous;
		System.out.println("popping");
		System.out.println(String.valueOf(oldLast.element[0]));
		System.out.println(String.valueOf(oldLast.previous.element[0]));
		System.out.println(String.valueOf(oldLast.previous.previous.element[0]));
		oldLast.previous.next = tail;
		tail.previous = oldLast.previous;
		return oldLast;
	}
	
	public ListNode top() {
		ListNode oldFirst = header.next;
		header.next = oldFirst.next;
		oldFirst.next.previous = header;
		return oldFirst;
	}
	
	// Reverse method
	public LinkedList reverse() {
		LinkedListItr tailItr = new LinkedListItr(tail.previous);
		ListNode currentElement;
		ListNode previousElement;
		while (tailItr.hasPrevious()) {
			currentElement = tailItr.current;
			previousElement = currentElement.previous;
			currentElement.previous = currentElement.next;
			currentElement.next = previousElement;
			tailItr.advance();
		}
		ListNode tempPrevious;
		tempPrevious = tail.previous;
		tail.previous = tempPrevious;
		tail.previous = header.next;
		header.next = tempPrevious;
		return this;
	}

	public LinkedList concatenate(LinkedList secondList) {
		tail.previous.next = secondList.header.next;
		return this;
	}
	
	public LinkedList reverse_2() {

		ListNode backNode = tail.previous;
		ListNode frontNode = header.next;

		while (frontNode != backNode){

			double[] newElement;
			newElement = backNode.element;
			backNode.element = frontNode.element;
			frontNode.element = newElement;

			if (frontNode.next == backNode){
				break;
			}

			backNode = backNode.previous;
			frontNode = frontNode.next;	

		}

		return this;
	}
	
	// Simple print method
	public static void printList( LinkedList theList )
	{
		if( theList.isEmpty( ) )
			System.out.print( "Empty list" );
		else
		{
			LinkedListItr itr = theList.first( );
			for( ; !itr.isPastEnd( ); itr.advance( ) )
				System.out.print( "(" + itr.retrieve( )[0] + ","+ itr.retrieve( )[1]+")" + " " );
		}

		System.out.println( );
	}
	
	//Modified version of print method. Returns string
	public static String returnString( LinkedList theList )
	{	String string1 = "";

	if( theList.isEmpty( ) )
		string1 = string1.concat("Empty list" );
	else
	{
		LinkedListItr itr = theList.first( );
		for( ; !itr.isPastEnd( ); itr.advance( ) )
			string1 = string1.concat("(" + itr.retrieve( )[0] + ","+ itr.retrieve( )[1]+")" + " " );
	}
	return string1;
	}

	
	
	 ListNode header;
	 ListNode tail;

}
