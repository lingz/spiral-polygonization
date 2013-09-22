package visualize;

import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;


public class Polygonization {

    public ArrayList<double []> points;
    public ArrayList<double []> debug;

    public Polygonization() {
        points = new ArrayList<double[]>();
        debug = new ArrayList<double[]>();
    }

    public void add(double x, double y) {
        points.add(new double[] {x,y});
    }
    
    public void add(double x, double y, boolean z) {
        if (z) debug.add(new double[] {x,y});
        else points.add(new double[] {x,y});
    }

    public void show() {

        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {

                Display bs = new Display(points, debug);
                bs.setVisible(true);
            }
        });
    }
    


    public static void main(String[] args) {
        Polygonization img = new Polygonization();
        img.add(0.001, 0.001);
        img.add(0.5, 0.7);
        img.add(0.999, 0.999);
        img.add(0.729, 0.599, true);
        img.add(0.569, 0.499, true);
        img.show();
    }

}