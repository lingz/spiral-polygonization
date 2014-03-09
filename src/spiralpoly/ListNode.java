// Basic node stored in a linked list
// Note that this class is not accessible outside
// of package DataStructures
package spiralpoly;

public class ListNode implements Comparable<ListNode>
{

    public int compareTo(ListNode other) {
        if (this.element[0] > other.element[0])
            return 1;
        else if (this.element[0] == other.element[0])
            if (this.element[1] > other.element[1])
                return 1;
            else if (this.element[1] == other.element[1])
                return 0;
            else
                return -1;
        else
            return -1;
    }
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

    // clone only the element value
    public ListNode shallowClone() {
        return new ListNode(this.element);
    }

    public void deleteOriginal() {
        original.removeSelf();
    }

    public String toString() {
        String left;
        String right;
        if (previous == null)
            left = "";
        else
            left = (previous.element == null) ? "*Empty*" : "*" + previous.element[0] + "," + previous.element[1] + "*";
        if (next == null)
            right = "";
        else
            right = (next.element == null) ? "*Empty*" : "*" + next.element[0] + "," + next.element[1] + "*";
        String center = (element == null) ? "*Empty*" : "*" + element[0] + "," + element[1] + "*";
        return left + "<-" + center + "->" + right;
    }

		// Friendly data; accessible by other package routines
	double[]   element;
	ListNode next;
	ListNode previous;
    // a way to keep track of the original item in the list
    ListNode original = null;
}

