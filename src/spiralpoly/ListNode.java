// Basic node stored in a linked list
// Note that this class is not accessible outside
// of package DataStructures
package spiralpoly;

public class ListNode
{
		// Constructors
	public ListNode( double[] theElement )
	{
		this( theElement, null, null );
	}

	ListNode( double[] theElement, ListNode n, ListNode p )
	{
		element = theElement;
		next	= n;
		previous = p;
	}

    public ListNode getNext() {
        return next;
    }

    public void setNext(ListNode next) {
        this.next = next;
        next.previous = this;
    }

    public ListNode getPrevious() {
        return previous;
    }

    public void setPrevious(ListNode previous) {
        previous.next = this;
        this.previous = previous;
    }

    // eliminates itself from the LinkedList
    public void removeSelf() {
        System.out.println(element[0]);
        System.out.println(element[1]);
        if (previous.next == this && next.previous == this)
            previous.setNext(next);

    }

    public ListNode clone() {
        ListNode clone = new ListNode(this.element);
        if (this.original != null) {
            clone.original = this.original;
        }
        else                    {
            clone.original = this;
        }

        return clone;
    }

    public void deleteOriginal() {
        System.out.println("checking against original");
        System.out.println(this.element[0]);
        System.out.println(this.original.element[0]);
        original.removeSelf();
    }

		// Friendly data; accessible by other package routines
	double[]   element;
	ListNode next;
	ListNode previous;
    // a way to keep track of the original item in the list
    ListNode original = null;
}

