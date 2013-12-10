package com.ornilabs.core;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.HashMap;

import javax.swing.JPanel;

import com.ornilabs.main.Launch;

public class DrawF extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5082827415095204466L;
	private double ySize;
	private double xSize;
	private CircularRobot robot;

	public DrawF(double xSize, double ySize) {
		this.xSize =xSize;
		this.ySize = ySize;
		this.robot = new CircularRobot(xSize/2, ySize/2, 0, 0, 0, 100);
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		double a = 100;
		double prec = 1000;
		Graphics2D g2d = (Graphics2D) g;
		for(int x = (int) -xSize ; x < xSize ; x+=xSize/prec) {
			for(int y = (int) -ySize ; y < ySize ; y+=ySize/prec) {
				double[] result = {x,y};
				//Shoot zone
				if(x>0 && Math.abs(y)<a) {
					result[1] = Math.tan(y/a*Math.PI/2);
					g2d.setColor(Color.RED);
				}
				else if(x>0 && y>=a) {
					result[0] = -x;
					result[1] = Math.atan(y-a)*a/Math.PI*2;
					g2d.setColor(Color.BLUE);
				}
				else if(x>0 && y<=-a) {
					result[0] = -x;
					result[1] = Math.atan(y+a)*a/Math.PI*2;
					g2d.setColor(Color.YELLOW);
				}
				else if(x<=0 && y>0) {
					g2d.setColor(Color.GREEN);
					result[1] = y+a;
				}
				else if(x<=0 && y<=0) {
					g2d.setColor(Color.BLACK);
					result[1] = y-a;
				}
				
				Rectangle2D.Double rect= new Rectangle2D.Double(result[0]-xSize/prec/2+xSize/2, ySize/2+result[1]-ySize/prec/2, xSize/prec, ySize/prec);
				g2d.fill(rect);
			}
			
		}

		
	}
}
