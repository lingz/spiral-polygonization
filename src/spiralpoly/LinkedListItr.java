package spiralpoly;
// LinkedListItr class; maintains "current position"
    //
    // CONSTRUCTION: Package friendly only, with a ListNode
    //
    // ******************PUBLIC OPERATIONS*********************
    // void advance( )        --> Advance
    // boolean isPastEnd( )   --> True if at "null" position in list
    // Object retrieve        --> Return item in current position

    /**
     * Linked list implementation of the list iterator
     *    using a header node.
     * @author Mark Allen Weiss
     * @see LinkedList
     */
    public class LinkedListItr
    {
        /**
         * Construct the list iterator
         * @param theNode any node in the linked list.
         */
        LinkedListItr( ListNode theNode )
        {
            current = theNode;
        }

        /**
         * Test if the current position is past the end of the list.
         * @return true if the current position is null.
         */
        public boolean isPastEnd( )
        {
            return current.element == null;
        }
        
        public boolean hasNext() {
        	return current.next.next != null;
        }
        
        public boolean hasPrevious() {
        	return current.previous.previous == null;
        }

        /**
         * Return the item stored in the current position.
         * @return the stored item or null if the current position
         * is not in the list.
         */
        public double[] retrieve( )
        {
            return isPastEnd( ) ? null : current.element;
        }

        /**
         * Advance the current position to the next node in the list.
         * If the current position is null, then do nothing.
         */
        public void advance( )
        {
            if( !isPastEnd( ) )
                current = current.next;
        }
        
        public void back() {
        	if (!isPastEnd()) {
        		current = current.previous;
        	}
        }

        ListNode current;    // Current position
    }

