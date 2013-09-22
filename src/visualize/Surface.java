package visualize;

import java.awt.BasicStroke;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.geom.Arc2D.Double;
import java.awt.geom.GeneralPath;
import java.util.ArrayList;

import javax.swing.JPanel;

class Surface extends JPanel {
	
	ArrayList<double[]> points;
	
	public Surface (ArrayList x) {
		points = x;
	}

    private void doDrawing(Graphics g) {

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
        
        GeneralPath polygon = new GeneralPath();
        
        for (int k = 0; k < points.size(); k++) {
        	int cordx = (int) Math.round(points.get(k)[0] * Math.min(w,h));
        	int cordy = (int) Math.round((1 - points.get(k)[1]) * Math.min(w,h));
        	int radius = 10;
        	g2d.fillOval(cordx - radius/2, cordy - radius/2, radius, radius);
            if (k < 1) polygon.moveTo(cordx, cordy);
            else polygon.lineTo(cordx, cordy);
        }
        
        polygon.closePath();
        g2d.draw(polygon);
        
    }

    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        doDrawing(g);
    }
}
