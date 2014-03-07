package spiralpoly;

import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by ling on 7/03/14.
 */
public class LinkedListTest {

    private LinkedList data;
    private LinkedList testData;
    ListNode first;
    ListNode realFirst;
    ListNode second;
    ListNode realSecond;
    ListNode third;
    ListNode realThird;
    ListNode last;
    ListNode realLast;

    @Before
    public void setupList() {
        data = new LinkedList();
        LinkedListItr p = data.zeroth();

        double[] x={0.2313,0.281};
        data.insert(x,p);
        p.advance();
        double[] y={0.4214,0.291};
        data.insert(y,p);
        p.advance();
        double[] a={0.4214,0.191};
        data.insert(a,p);
        p.advance();
        double[] z={0.2315,0.261};
        data.insert(z,p);
        p.advance();
        double[] w={0.8312,0.231};
        data.insert(w,p);
        p.advance();
        double[] v={0.9913,0.786};
        data.insert(v,p);
        p.advance();

        testData = data.clone();
        first = testData.header.next;
        realFirst = data.header.next;
        second = testData.header.next.next;
        realSecond = realFirst.next;
        third = testData.header.next.next.next;
        realThird = realSecond.next;
        last = testData.tail.previous;
        realLast = data.tail.previous;
    }

    @Test
    public void testRotateFirstToHead() throws Exception {
        testData.rotateToHead(first);
        Assert.assertEquals(testData.toString(), data.toString());
    }
    @Test
    public void testRotateSecondToHead() throws Exception {
        testData.rotateToHead(second);
        realFirst.removeSelf();
        data.header.setNext(second);
        realLast.setNext(realFirst);
        realFirst.setNext(data.tail);
        Assert.assertEquals(testData.toString(), data.toString());
    }
    @Test
    public void testRotateLastToHead() throws Exception {
        testData.rotateToHead(last);
        realLast.previous.setNext(data.tail);
        realLast.setNext(realFirst);
        data.header.setNext(realLast);
        Assert.assertEquals(testData.toString(), data.toString());



    }
}
