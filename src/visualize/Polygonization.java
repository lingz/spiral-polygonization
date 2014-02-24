package visualize;

import java.util.ArrayList;

import javax.swing.SwingUtilities;

import spiralpoly.LinkedList;
import spiralpoly.LinkedListItr;


public class Polygonization {

    public ArrayList<double []> debug;
    public ArrayList<ArrayList<double []>> points;
    float stroke;
	static float DEFAULT_WIDTH = 4;
    boolean disp;
    String title = new String("Spiral Polyganization");

    public Polygonization() {
        this(DEFAULT_WIDTH, true, null);
    }
    
    public Polygonization(float sizeOfTheBrush) {
        this(sizeOfTheBrush, true, null);
    }
    
    public Polygonization(boolean displayCoordinates) {
        this(DEFAULT_WIDTH, displayCoordinates, null);
    }
    
    public Polygonization(float d, boolean displayCoordinates, String label) {
        points = new ArrayList<>();
        debug = new ArrayList<>();
        stroke = d;
        disp = displayCoordinates;
        title = label != null ? label : title;
        this.show();
    }

    public Polygonization(int sizeOfTheBrush, String label) {
        this(sizeOfTheBrush, true, label);
    }
    
    public void add(double x, double y) {
        add(x, y, 0, 0);
    }
    
    public void add(double x, double y, int chain) {
        add(x, y, chain, 0);
    }
    
    public void add(double x, double y, boolean notInTheHull) {
        if (notInTheHull) debug.add(new double[] {x,y,0});
        else points.get(0).add(new double[] {x,y,0});
    }
    
    public void add(double x, double y, int chain, int GrayscaleColor) {
        if (chain < points.size())
        	points.get(chain).add(new double[] {x,y, GrayscaleColor});
        else {
        	ArrayList<double []> tmp = new ArrayList<> ();
        	tmp.add(new double[] {x,y, GrayscaleColor});
        	points.add(tmp);
        }
    }
    
    public void add(double[] coords) {
    	add(coords, 0, 0);
    }
    
    public void add(double[] coords, int chain) {
    	add(coords, chain, 0);
    }
    
    public void add(double[] coords, int chain, int GrayscaleColor) {
    	add(coords[0], coords[1], chain, GrayscaleColor);
    }
    
    public void add(double[][] list) {
    	add(list, 0, 0);
    }
    
    public void add(double[][] list, int chain) {
    	add(list, chain, 0);
    }
    
    public void add(double[][] list, int chain, int GrayscaleColor) {
    	for (int i = 0; i < list.length; i++) {
            if (chain < points.size())
            	points.get(chain).add(new double[] {list[i][0], list[i][1], GrayscaleColor});
            else {
            	ArrayList<double []> tmp = new ArrayList<> ();
            	tmp.add(new double[] {list[i][0], list[i][1], GrayscaleColor});
            	points.add(tmp);
            }
    	}
    }
    
    public void add(LinkedList list) {
        add(list, 0, 0);
    }
    
    public void add(LinkedList list, int chain) {
        add(list, chain, 0);
    }
    
    public void add(LinkedList list, int chain, int GrayscaleColor) {
    	if( ! list.isEmpty( ) ) {
    		LinkedListItr itr = list.first( );
    		for( ; !itr.isPastEnd( ); itr.advance( ) ) {
    			double x = itr.retrieve( )[0]; 
    			double y = itr.retrieve( )[1];
	            if (chain < points.size())
	            	points.get(chain).add(new double[] {x,y, GrayscaleColor});
	            else {
	            	ArrayList<double []> tmp = new ArrayList<> ();
	            	tmp.add(new double[] {x,y, GrayscaleColor});
	            	points.add(tmp);
	            }
    		}
    	}
    }

    public void show() {

        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {

                Display bs = new Display(points, debug, stroke, disp, title);
                bs.setVisible(true);
            }
        });
    }
    


    public static void main(String[] args) {
        Polygonization img = new Polygonization(3); //line width - 3, coordinates displayed by default
        img.add(0.01, 0.01); //adds a point to chain 0
        img.add(0.5, 0.7, 0); //adds a point to chain 0
        img.add(0.999, 0.899, 0, 150); //adds a point to chain 0, segment that connects previous and this point is grey
        img.add(0.729, 0.599, true); //adds a point that is not connected to other points (colored in grey)
        img.add(0.569, 0.499, true); //adds a point that is not connected to other points (colored in grey)
        img.add(0.2, 0.3, 1); //adds a point to chain 1
        img.add(0.53, 0.87, 1, 200); //adds a point to chain 1, colors a link to very light grey
        img.add(0.67, 0.2, 2); //adds a point to chain 2
        img.add(0.1, 0.34, 1); //adds a point to chain 1
        
        LinkedList data = new LinkedList();
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
    	
    	img.add(data, 3); //adds points from linked list to chain 3
    	
    	int scale = 10;
    	img.add (new double[][] {{1.0/scale, 1.0/scale}, {2.0/scale,5.0/scale}, {3.0/scale,8.0/scale}, {6.0/scale,5.0/scale}, {5.0/scale,4.0/scale}}, 4, 160); // adds array of doubles to chain 4
		
        
    }

}