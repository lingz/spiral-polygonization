package visualize;

import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;


public class Display extends JFrame {

    public Display(ArrayList<double[]> points, ArrayList<double[]> debug) {
        setTitle("Spiral Polygonization");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(new Surface(points, debug));
        setSize(800, 800);
        setLocationRelativeTo(null);
    }
}