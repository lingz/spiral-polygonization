package spiralpoly;

import static org.junit.Assert.*;
import junit.framework.Assert;

import org.junit.Test;

public class S1Test {

	@Test
	public void circle() {
		LinkedList data = new LinkedList();
		LinkedListItr p = data.zeroth();

		double[] x={ 0 , 5.0 };
		data.insert(x,p);
		p.advance();
		double[] a={ 5.0 , 10 };
		data.insert(a,p);
		p.advance();
		double[] b={ 10 , 5 };
		data.insert(b,p);
		p.advance();
		double[] c={ 5 , 0 };
		data.insert(c,p);
		p.advance();
		double[] d={ 2.591231629 , 0.6184665998 };
		data.insert(d,p);
		p.advance();
		double[] e={ 7.679133975 , 0.7783603725 };
		data.insert(e,p);
		p.advance();
		double[] f={ 7.679133975 , 9.221639628 };
		data.insert(f,p);
		p.advance();
		double[] g={ 2.591231629 , 9.3815334 };
		data.insert(g,p);
		p.advance();

		//The return string method is a modified version of the printlist method that returns a string
		LinkedList convexResult = Graham.graham(data);
		String expectedString = "(2.591231629,9.3815334) (7.679133975,9.221639628) (7.679133975,0.7783603725) (5.0,0.0) (0.0,5.0) ";
		System.out.println(LinkedList.returnString(convexResult));
		Assert.assertEquals(expectedString, LinkedList.returnString(S1.polygonize(data)));
	}

	@Test
	public void wave(){
		LinkedList data = new LinkedList();
		LinkedListItr p = data.zeroth();
		
		double[] e={ 6 , 5 };
		data.insert(e,p);
		p.advance();
		double[] d={ 5 , 4 };
		data.insert(d,p);
		p.advance();
		double[] c={ 4 , 6 };
		data.insert(c,p);
		p.advance();
		double[] b={ 3, 8 };
		data.insert(b,p);
		p.advance();
		double[] a={ 2 , 5 };
		data.insert(a,p);
		p.advance();
		double[] x={ 1 , 1 };
		data.insert(x,p);
		p.advance();

		LinkedList convexResult = Graham.graham(data);
		String expectedString = "(1.0,1.0) (2.0,5.0) (3.0,8.0) (6.0,5.0) (5.0,4.0) ";
		System.out.println(LinkedList.returnString(convexResult));
		Assert.assertEquals(expectedString, LinkedList.returnString(S1.polygonize(data)));

	}

}
