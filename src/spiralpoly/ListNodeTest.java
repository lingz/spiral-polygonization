package spiralpoly;

import junit.framework.Assert;
import org.junit.Test;

/**
 * Created by ling on 8/03/14.
 */
public class ListNodeTest {
    @Test
    public void testCompareTo() throws Exception {
        double[] a = {1.0, 2.0};
        double[] b = {2.0, 1.0};
        double[] c = {2.0, 3.0};
        double[] d = {1.0, 2.0};
        Assert.assertEquals(new ListNode(a).compareTo(new ListNode(b)), -1);
        Assert.assertEquals(new ListNode(b).compareTo(new ListNode(a)), 1);
        Assert.assertEquals(new ListNode(a).compareTo(new ListNode(d)), 0);
        Assert.assertEquals(new ListNode(b).compareTo(new ListNode(c)), -1);
        Assert.assertEquals(new ListNode(c).compareTo(new ListNode(b)), 1);

    }
}
