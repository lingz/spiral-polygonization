package visualize;

import java.awt.Window;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;


public class Polygonization extends JFrame {

    private Polygonization bs;
    public ArrayList points;

	public Polygonization() {

        setTitle("Spiral Polygonization");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        bs = new Polygonization();
       
    }
    
    public void add(double x, double y) {
    	points.add(new double[] {x,y});
    }

    public void show() {
    	
        add(new Surface(points));

        setSize(800, 800);
        setLocationRelativeTo(null);
    	
        SwingUtilities.invokeLater(new Runnable() {
        	
            @Override
            public void run() {

                bs.setVisible(true);
            }
        });	
    }
    
}