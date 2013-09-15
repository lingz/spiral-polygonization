package hs_convex_hull;

import java.util.Arrays;

import org.uncommons.maths.number.NumberGenerator;
import org.uncommons.maths.random.ContinuousUniformGenerator;
import org.uncommons.maths.random.GaussianGenerator;
import org.uncommons.maths.statistics.DataSet;

/**
 * Class to generate uniform and normal distributions of points 
 * in a unit square and their reflections about y=0.5
 * @author Moiri Gamboni
 */
public class PointDistribution {
	public DataSet[] uniformDS = new DataSet[2];
	public DataSet[] normalDS = new DataSet[2];
	public double[][] uniformData = new double[2][];
	public double[][] normalData = new double[2][];
	public Point[][] uniformPoints = new Point[2][];
	public Point[][] normalPoints = new Point[2][];

	/**
	 * Generates 100 coordinate pairs for uniform and normal distributions in a unit square
	 */
	public PointDistribution(){
		this(100);
	}

	/**
	 * Generates n coordinate pairs for uniform and normal distributions in a unit square
	 * * {@code} test
	 * @param n The number of coordinate pairs
	 */
	public PointDistribution(int n){
		this.uniformData[0] = PointDistribution.uniformSquare(n);
		this.normalData[0] = PointDistribution.normalSquare(n);
		this.uniformData[1] = PointDistribution.invert(this.uniformData[0]);
		this.normalData[1] = PointDistribution.invert(this.normalData[0]);
		for (int i = 0; i<2; i++){
			this.uniformDS[i] = new DataSet(this.uniformData[i]);
			this.normalDS[i] = new DataSet(this.normalData[i]);
			this.uniformPoints[i] = PointDistribution.convertToPoints(this.uniformData[i]);
			this.normalPoints[i] = PointDistribution.convertToPoints(this.normalData[i]);
			Arrays.sort(this.uniformPoints[i]);
			Arrays.sort(this.normalPoints[i]);
		}
	}
	
	public static double[] invert (double[] values){
		double[] inverse = new double[values.length];
		for (int i = 0; i<values.length; i++)
			inverse[i] = 1 - values[i];
		return inverse;
	}

	/**
	 * Converts an array of values to coordinate pairs
	 * @param values The array of values to convert
	 * @return An array of coordinate pairs [(x1, y1), (x2,y2),...]
	 */
	public static Point[] convertToPoints(double[] values)
	{
		Point[] array = new Point[values.length/2];
		for (int i = 0; i<array.length;i++)
		{
			array[i]=new Point(new double[]{values[i*2], values[i*2+1]});
		}
		return array;
	}

	/**
	 * Prints an array of coordinate pairs
	 * @param coord The array of coordinate pairs [(x1, y1), (x2,y2),...]
	 */
	public static void printCoord(Point p, boolean inverse)
	{
		if (p == null) System.out.println("(null, null)");
		else System.out.format("(%.2f, %.2f)%n", (inverse ? 1-p.coord[0] : p.coord[0]), (inverse ? 1-p.coord[1] : p.coord[1]));
		return;
	}

	/**
	 * Generates values in a unit square according to a distribution
	 * @param n The number of coordinate pairs
	 * @param rng The distribution to use
	 * @return An array of coordinate pairs in a unit square [(x1, y1), (x2,y2),...]
	 */
	public static double[] generateValues(int n, NumberGenerator<Double> rng)
	{
		return PointDistribution.generateValues(n, 0, 1, rng);
	}

	/**
	 * Generates values according to a distribution
	 * @param n The number of coordinate pairs
	 * @param min The minimum value for any coordinate
	 * @param max The maximum value for any coordinate
	 * @param rng The distribution to use
	 * @return An array of coordinate pairs in a unit square [(x1, y1), (x2,y2),...]
	 */
	public static double[] generateValues(int n, double min, double max, NumberGenerator<Double> rng) {
		double[] array = new double[n];
		double val;
		for (int i =0; i<n; i++)
		{
			val = rng.nextValue();
			while (val>=max || val<=min)
			{
				val = rng.nextValue();
			}
			array[i]=val;
		}
		return array;
	}

	/**
	 * Generates values in a unit square according to a normal distribution
	 * @param n The number of coordinate pairs
	 * @return An array of coordinate pairs in a unit square with normal distribution [(x1, y1), (x2,y2),...]
	 */
	public static double[] normalSquare(int n){
		return PointDistribution.gaussian(n*2, 0, 1, 0.5, 1);
	}

	/**
	 * Generates values in a unit square according to a uniform distribution
	 * @param n The number of coordinate pairs
	 * @return An array of coordinate pairs in a unit square with uniform distribution [(x1, y1), (x2,y2),...]
	 */
	public static double[] uniformSquare(int n){
		return PointDistribution.uniform(n*2, 0, 1);
	}

	/**
	 * Generates values according to a uniform distribution
	 * @param n The number of coordinate pairs
	 * @param min The minimum value for any coordinate
	 * @param max The maximum value for any coordinate
	 * @return An array of coordinate pairs [(x1, y1), (x2,y2),...]
	 */
	public static double[] uniform(int n, double min, double max){
		ContinuousUniformGenerator rng = new ContinuousUniformGenerator(min, max, new java.util.Random());
		return PointDistribution.generateValues(n, min, max, rng);
	}

	/**
	 * Generates values according to a normal distribution
	 * @param n The number of coordinate pairs
	 * @param min The minimum value for any coordinate
	 * @param max The maximum value for any coordinate
	 * @param mean The mean of the gaussian distribution
	 * @param stddev The standard deviation of the gaussian distribution
	 * @return An array of coordinate pairs [(x1, y1), (x2,y2),...]
	 */
	public static double[] gaussian(int n, double min, double max, double mean, double stddev) {
		GaussianGenerator rng = new GaussianGenerator(mean, stddev, new java.util.Random());
		return PointDistribution.generateValues(n, min, max, rng);
	}
}


