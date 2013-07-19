// Basic node stored in a linked list
// Note that this class is not accessible outside
// of package DataStructures
package spiralpoly;

class ListNode
{
		// Constructors
	ListNode( double[] theElement )
	{
		this( theElement, null, null );
	}

	ListNode( double[] theElement, ListNode n, ListNode p )
	{
		element = theElement;
		next	= n;
		previous = p;
	}

		// Friendly data; accessible by other package routines
	double[]   element;
	ListNode next;
	ListNode previous;
}

