package com.ornilabs.core;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JPanel;

public class BoardPanel extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5082827415095204466L;
	private IBoard board;
	public static List<IRobot> fireringRobots = new ArrayList<IRobot>();

	public BoardPanel(IBoard board) {
		this.board = board;
		this.setBackground(Color.white);
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		double min = 1;
		double max = 0;
		double moy = 0;
		
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
		
			double lastScore = robot.getBrain().getScore();
			min = min > lastScore ? lastScore : min;
			max = max < lastScore ? lastScore : max;
			moy+=lastScore;
			try{
				g2d.setColor(Color.DARK_GRAY);
				g2d.drawString((""+((int)(lastScore*100)/100.0)), (int)robot.getPosition()[0], (int)(robot.getPosition()[1]-1.3*robot.getRobotRadius()));
				g2d.drawString(""+robot.getLife()+"/"+robot.getMaxLife(), (int)robot.getPosition()[0], (int)(robot.getPosition()[1]-1.3*robot.getRobotRadius()-10));
				g2d.drawString(""+robot.getBrain().score, (int)robot.getPosition()[0], (int)(robot.getPosition()[1]-1.3*robot.getRobotRadius()-20));
			}catch(Exception e) {System.out.println("rol");}
			
		}
		moy = moy/board.getRobots().size();

		g2d.setColor(Color.red);
		g2d.drawString("min="+min,0,1000);
		g2d.setColor(Color.green);
		g2d.drawString("max="+max,0,1020);
		g2d.setColor(Color.BLUE);
		g2d.drawString("moy="+moy,0,1040);
		
		fireringRobots.removeAll(fireringRobots);

	}

	public void setFirering(List<IRobot> fireringRobots) {
		BoardPanel.fireringRobots  = fireringRobots;
	}
}
