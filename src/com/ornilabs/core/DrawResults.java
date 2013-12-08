package com.ornilabs.core;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.HashMap;

import javax.swing.JPanel;

public class DrawResults extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5082827415095204466L;
	private HashMap<Double[], Double> results;
	private double pas;
	private double ySize;
	private double xSize;
	private CircularRobot robot;

	public DrawResults(HashMap<Double[],Double> results, double pas, double xSize, double ySize) {
		this.results = results;
		this.pas = pas;
		this.xSize =xSize;
		this.ySize = ySize;
		this.robot = new CircularRobot(xSize/2, ySize/2, 0, 0, 0, 100);
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.BLUE);
		Ellipse2D.Double circle = new Ellipse2D.Double(robot.getPosition()[0]-robot.getRobotRadius(), robot.getPosition()[1]-robot.getRobotRadius(), robot.getRobotRadius()*2, robot.getRobotRadius()*2);
		g2d.fill(circle);
		
		g2d.setColor(Color.RED);
		g2d.drawLine((int)robot.getPosition()[0], (int)robot.getPosition()[1], (int)(robot.getPosition()[0]+robot.getRobotRadius()*Math.cos(robot.getAngle())), (int)(robot.getPosition()[1]+robot.getRobotRadius()*Math.sin(robot.getAngle())));
		
		for(Double[] set : results.keySet()) {
			int x = set[0].intValue();
			int y = set[1].intValue();
			
			double result = results.get(set);
			
			g2d.setColor(new Color((float)(result), (float)(result), (float)(result)));
			Rectangle2D.Double rect= new Rectangle2D.Double(xSize/2+x-pas/2, ySize/2+y-pas/2, pas, pas);
			g2d.fill(rect);
		}
		
	}
}
