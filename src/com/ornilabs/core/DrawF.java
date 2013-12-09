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
		
		double a = robot.getRobotRadius();

		Graphics2D g2d = (Graphics2D) g;
		for(int x = (int) -xSize ; x < xSize ; x+=xSize/100) {
			for(int y = (int) -ySize ; y < ySize ; y+=ySize/100) {

				double[] results = Launch.f(x, y, a);
				if(x>0 && Math.abs(y)<a) {
					g2d.setColor(Color.RED);
				}
				else if(x>0 && y>=a) {
					g2d.setColor(Color.BLUE);
				}
				else if(x>0 && y<=-a) {
					g2d.setColor(Color.YELLOW);
				}
				else if(x<0 && y>0) {
					g2d.setColor(Color.GREEN);
				}
				else if(x<0 && y<0) {
					g2d.setColor(Color.ORANGE);
				}
				Rectangle2D.Double rect= new Rectangle2D.Double(results[0]-xSize/100/2+xSize/2, ySize/2+results[1]-ySize/100/2, xSize/100, ySize/100);
				g2d.fill(rect);
			}
			
		}

		
	}
}
