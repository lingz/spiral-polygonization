package visualize;

import java.util.ArrayList;

import javax.swing.SwingUtilities;


public class Polygonization {

    public ArrayList<double []> points, debug;
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
        points = new ArrayList<double[]>();
        debug = new ArrayList<double[]>();
        stroke = sizeOfTheBrush;
        disp = displayCoordinates;
    }

    public void add(double x, double y) {
        add(x,y,0);
    }
    
    public void add(double x, double y, boolean notInTheHull) {
        if (notInTheHull) debug.add(new double[] {x,y,0});
        else points.add(new double[] {x,y,0});
    }
    
    public void add(double x, double y, int GrayscaleColor) {
        points.add(new double[] {x,y, GrayscaleColor});
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
        img.add(0.001, 0.001, 200);
        img.add(0.5, 0.7, 100);
        img.add(0.999, 0.999, 200);
        img.add(0.729, 0.599, true);
        img.add(0.569, 0.499, true);
        img.show();
    }

}