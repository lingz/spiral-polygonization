package spiralpoly;

import junit.framework.Assert;
import org.junit.Test;
import visualize.Polygonization;

public class S1Test {

	@Test
	public void circle() {
		LinkedList data = new LinkedList();
		LinkedListItr p = data.zeroth();
		Polygonization img = new Polygonization(3);
		int scale = 10;

		double[] x={ 0 , 5.0 };
		img.add(0, 5.0/scale, true);
		data.insert(x,p);
		p.advance();
		double[] a={ 5.0 , 10.0 };
		img.add(5.0/scale, 10.0/scale, true);
		data.insert(a,p);
		p.advance();
		double[] b={ 10.0 , 5.0 };
		img.add(10.0/scale, 5.0/scale, true);
		data.insert(b,p);
		p.advance();
		double[] c={ 5.0 , 0.0 };
		img.add(5.0/scale, 0.0/scale, true);
		data.insert(c,p);
		p.advance();
		double[] d={ 2.591231629 , 0.6184665998 };
		img.add(2.591231629/scale, 0.6184665998/scale, true);
		data.insert(d,p);
		p.advance();
		double[] e={ 7.679133975 , 0.7783603725 };
		img.add(7.679133975/scale, 0.7783603725/scale, true);
		data.insert(e,p);
		p.advance();
		double[] f={ 7.679133975 , 9.221639628 };
		img.add(7.679133975/scale, 9.221639628/scale, true);
		data.insert(f,p);
		p.advance();
		double[] g={ 2.591231629 , 9.3815334 };
		img.add(2.591231629/scale, 9.3815334/scale, true);
		data.insert(g,p);
		p.advance();

		//The return string method is a modified version of the printlist method that returns a string
		LinkedList convexResult = new Graham().graham(data);
		String expectedString = "(2.591231629,9.3815334) (7.679133975,9.221639628) (7.679133975,0.7783603725) (5.0,0.0) (0.0,5.0) ";
		img.add (new double[][] {{2.591231629/scale, 9.3815334/scale}, {7.679133975/scale,9.221639628/scale}, {7.679133975/scale,0.7783603725/scale}, {5.0/scale,0.0}, {0.0,5.0/scale}}, 0, 160);
		System.out.println(LinkedList.returnString(convexResult));
		Assert.assertEquals(expectedString, LinkedList.returnString(S1.polygonize(data)));
		img.add(S1.polygonize(data),1);
	}

	@Test
	public void wave(){
		LinkedList data = new LinkedList();
		LinkedListItr p = data.zeroth();
		int scale = 10;
		Polygonization img = new Polygonization(3);
		
		double[] e={ 6 , 5 };
		img.add(6.0/scale, 5.0/scale, true);
		data.insert(e,p);
		p.advance();
		double[] d={ 5 , 4 };
		img.add(5.0/scale, 4.0/scale, true);
		data.insert(d,p);
		p.advance();
		double[] c={ 4 , 6 };
		img.add(4.0/scale, 6.0/scale, true);
		data.insert(c,p);
		p.advance();
		double[] b={ 3, 8 };
		img.add(3.0/scale, 8.0/scale, true);
		data.insert(b,p);
		p.advance();
		double[] a={ 2 , 5 };
		img.add(2.0/scale, 5.0/scale, true);
		data.insert(a,p);
		p.advance();
		double[] x={ 1 , 1 };
		img.add(1.0/scale, 1.0/scale, true);
		data.insert(x,p);
		p.advance();

		LinkedList convexResult = new Graham().graham(data);
		String expectedString = "(1.0,1.0) (2.0,5.0) (3.0,8.0) (6.0,5.0) (5.0,4.0) ";
		img.add (new double[][] {{1.0/scale, 1.0/scale}, {2.0/scale,5.0/scale}, {3.0/scale,8.0/scale}, {6.0/scale,5.0/scale}, {5.0/scale,4.0/scale}}, 0, 160);
		System.out.println(LinkedList.returnString(convexResult));
		Assert.assertEquals(expectedString, LinkedList.returnString(S1.polygonize(data)));
		img.add(S1.polygonize(data),1);

	}

}
