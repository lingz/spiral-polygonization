package visualize;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.geom.GeneralPath;
import java.util.ArrayList;

import javax.swing.JPanel;

class Surface extends JPanel {
	
	ArrayList<double[]> points;
	ArrayList<double[]> debug;
	
	public Surface (ArrayList<double[]> x, ArrayList<double[]> y) {
		points = x;
		debug = y;
	}

    private void doDrawing(Graphics g) {
    	
    	int radius = 10;
    	int num = points.size() - 1;
    	int cordx, cordy;

        Graphics2D g2d = (Graphics2D) g;
        
        Dimension size = getSize();
        Insets insets = getInsets();

        int w = size.width - insets.left - insets.right;
        int h = size.height - insets.top - insets.bottom;

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setRenderingHint(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);
        
        BasicStroke bs = new BasicStroke(2, BasicStroke.CAP_ROUND,
                BasicStroke.JOIN_ROUND);
        
        g2d.setStroke(bs);
        
        for (int k = 0; k < debug.size(); k++) {
        	cordx = (int) Math.round(debug.get(k)[0] * Math.min(w,h));
        	cordy = (int) Math.round((1 - debug.get(k)[1]) * Math.min(w,h));
        	g2d.setColor(new Color(0, 200, 0));
        	g2d.fillOval(cordx - radius/2, cordy - radius/2, radius, radius);  
        }
        
        GeneralPath polygon = new GeneralPath();
        
        g2d.setColor(new Color(0, 0, 0));
        
        for (int k = 0; k <= num; k++) {
        	cordx = (int) Math.round(points.get(k)[0] * Math.min(w,h));
        	cordy = (int) Math.round((1 - points.get(k)[1]) * Math.min(w,h));
        	if (k < 1) polygon.moveTo(cordx, cordy);
            else polygon.lineTo(cordx, cordy);
        	g2d.fillOval(cordx - radius/2, cordy - radius/2, radius, radius);
            
        }
        
        polygon.closePath();
        g2d.draw(polygon);
        
        //Color first and last points
        cordx = (int) Math.round(points.get(0)[0] * Math.min(w,h));
    	cordy = (int) Math.round((1 - points.get(0)[1]) * Math.min(w,h));
    	g2d.setColor(new Color(0, 0, 200));
    	g2d.fillOval(cordx - radius/2, cordy - radius/2, radius, radius);
    	
        cordx = (int) Math.round(points.get(num)[0] * Math.min(w,h));
    	cordy = (int) Math.round((1 - points.get(num)[1]) * Math.min(w,h));
    	g2d.setColor(new Color(200, 0, 0));
    	g2d.fillOval(cordx - radius/2, cordy - radius/2, radius, radius);
    	
        
    }

    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        doDrawing(g);
    }
}
