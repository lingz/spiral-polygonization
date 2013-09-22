package visualize;

import java.util.ArrayList;

import javax.swing.SwingUtilities;


public class Polygonization {

    public ArrayList<double []> debug;
    public ArrayList<ArrayList<double []>> points;
    int stroke;
	static int DEFAULT_WIDTH = 4;
    boolean disp;

    public Polygonization() {
        this(DEFAULT_WIDTH, true);
    }
    
    public Polygonization(int sizeOfTheBrush) {
        this(sizeOfTheBrush, true);
    }
    
    public Polygonization(boolean displayCoordinates) {
        this(DEFAULT_WIDTH, displayCoordinates);
    }
    
    public Polygonization(int sizeOfTheBrush, boolean displayCoordinates) {
        points = new ArrayList<>();
        debug = new ArrayList<>();
        stroke = sizeOfTheBrush;
        disp = displayCoordinates;
        this.show();
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

    public void show() {

        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {

                Display bs = new Display(points, debug, stroke, disp);
                bs.setVisible(true);
            }
        });
    }
    


    public static void main(String[] args) {
        Polygonization img = new Polygonization(3);
        img.add(0.001, 0.001);
        img.add(0.5, 0.7);
        img.add(0.999, 0.999);
        img.add(0.729, 0.599, true);
        img.add(0.569, 0.499, true);
        img.add(0.2, 0.3, 1);
        img.add(0.53, 0.87, 1);
        img.add(0.67, 0.2, 2);
        img.add(0.1, 0.34, 1);
    }

}