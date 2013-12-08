package com.ornilabs.core;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

public class BoardPanel extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5082827415095204466L;
	private IBoard board;
	private List<IRobot> fireringRobots = new ArrayList<IRobot>();

	public BoardPanel(IBoard board) {
		this.board = board;
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D g2d = (Graphics2D) g;
		for(IRobot robot : board.getRobots()) {
			g2d.setColor(Color.BLUE);
			Ellipse2D.Double circle = new Ellipse2D.Double(robot.getPosition()[0]-robot.getRobotRadius(), robot.getPosition()[1]-robot.getRobotRadius(), robot.getRobotRadius()*2, robot.getRobotRadius()*2);
			g2d.fill(circle);
			
			g2d.setColor(Color.RED);
			g2d.drawLine((int)robot.getPosition()[0], (int)robot.getPosition()[1], (int)(robot.getPosition()[0]+robot.getRobotRadius()*Math.cos(robot.getAngle())), (int)(robot.getPosition()[1]+robot.getRobotRadius()*Math.sin(robot.getAngle())));
		
			if(fireringRobots.contains(robot)) {
				g2d.setColor(Color.BLACK);
				//TODO range
				g2d.drawLine((int)(robot.getPosition()[0]+robot.getRobotRadius()*Math.cos(robot.getAngle())), (int)(robot.getPosition()[1]+robot.getRobotRadius()*Math.sin(robot.getAngle())), (int)(robot.getPosition()[0]+1000000*Math.cos(robot.getAngle())), (int)(robot.getPosition()[1]+1000000*Math.sin(robot.getAngle())));
			
			}
			
			//life bar
			int maxLife = robot.getMaxLife();
			double life = robot.getLife();
			

			int xS = (int)(robot.getPosition()[0]-robot.getRobotRadius());
			int yS = (int)(robot.getPosition()[1]+1.3*robot.getRobotRadius());
			
			g2d.setColor(Color.GREEN);
			g2d.drawLine(xS, yS, (int)(xS+(life/maxLife)*robot.getRobotRadius()*2), yS);

			g2d.setColor(Color.RED);
			if(maxLife!=life)g2d.drawLine((int)(xS+(life/maxLife)*robot.getRobotRadius()*2),yS, (int)(xS+robot.getRobotRadius()*2), yS);
		
		}
		
		fireringRobots.removeAll(fireringRobots);

	}

	public void setFirering(List<IRobot> fireringRobots) {
		this.fireringRobots  = fireringRobots;
	}
}
