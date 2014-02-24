package visualize;

import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;


public class Display extends JFrame {

    public Display(ArrayList<ArrayList<double[]>> points, ArrayList<double[]> debug, 
    		float stroke, boolean displayCoordinates, String title) {
        setTitle(title);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(new Surface(points, debug, stroke, displayCoordinates));
        setSize(800, 800);
        setLocationRelativeTo(null);
    }
}