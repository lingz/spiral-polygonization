package spiralpoly;

import java.util.PriorityQueue;

/**
 * Created by ling on 8/03/14.
 */
public class HeapSort {
    public static LinkedList sort(LinkedList input) {
        PriorityQueue<ListNode> heap = new PriorityQueue<>();
        while (!input.isEmpty()) heap.add(input.pop());
        while (!heap.isEmpty()) input.append(heap.poll());
        return input;
    }
}
