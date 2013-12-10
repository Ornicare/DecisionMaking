package com.ornilabs.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.Timer;
import java.util.TimerTask;

import com.ornilabs.main.Launch;
import com.ornilabs.neurons.CalculationNeuron;
import com.ornilabs.neurons.Entity;
import com.ornilabs.neurons.INeuron;
import com.ornilabs.neurons.InputNeuron;

public class Board extends TimerTask implements IBoard, Observer{
	private List<IRobot> robots;
	private double xSize;
	private double ySize;
	private IGraphicBoard graphicBoard;
	private Timer timer;
	private List<IRobot> fireringRobots = new ArrayList<IRobot>();
//	private List<INeuron> iaIn;
//	private List<INeuron> iaOut;
	private IRobot userRobot;

	public Board(List<IRobot> robots, double xSize, double ySize, IGraphicBoard graphicBoard, IRobot userRobot){//, List<INeuron> iaIn, List<INeuron> iaOut) {
		this.robots = robots;
		this.xSize = xSize;
		this.ySize = ySize;
		this.graphicBoard = graphicBoard;
//		this.iaIn = iaIn;
//		this.iaOut = iaOut;
		this.userRobot = userRobot;
		graphicBoard.setParameters(this, userRobot);
		
		timer = new Timer();
		timer.scheduleAtFixedRate(this, 0, 1000 / 60);
		
		for(IRobot robot : robots) {
			((Observable)robot).addObserver(this);
		}
	}

	@Override
	public void compute() {
		//Call ia
//		iaAction();
		
		for(Entity ent : Launch.managedEntities) {
			if(ent.getManagedRobot().getLife()<=0) continue;
			IRobot target = null;
			for(IRobot robot : robots) {
				if(robot!=ent.getManagedRobot() &&((target==null) || (Launch.squareDistance(robot.getPosition(), ent.getManagedRobot().getPosition())<Launch.squareDistance(target.getPosition(), ent.getManagedRobot().getPosition())))) target = robot;
			}
			if(target!=null)ent.update(target);
		}
		
		
		List<IRobot> copyRobots = new ArrayList<IRobot>(robots);
		for(IRobot robot : copyRobots) {
			double[] position = robot.getPosition();
			double x = position[0];
			double y = position[1];
			x +=robot.getAccel()*Math.cos(robot.getAngle());
			y +=robot.getAccel()*Math.sin(robot.getAngle());
//			x = x > xSize ? x - xSize : (x < 0 ? x+xSize : x);
//			y = y > ySize ? y - ySize : (y < 0 ? y+ySize : y);

			x = x > xSize ? xSize : (x < 0 ? 0 : x);
			y = y > ySize ? ySize : (y < 0 ? 0 : y);
			
			boolean collide = false;
			//No collisions
//			IRobot robotCollide = null;
//			for(IRobot robotConcurrent : robots) {
//				//basic collide condition
//				double collideRadius = robot.getRobotRadius()+robotConcurrent.getRobotRadius()+robot.getAccel()+robotConcurrent.getAccel();
//				if(robot!=robotConcurrent && squareDistance(robotConcurrent.getPosition(),robot.getPosition())<(collideRadius*collideRadius)) {
//					collide = true;
//					robotCollide = robotConcurrent;
//				}
//			}
			if(!collide) {
				robot.setPosition(x, y);
			}
//			else {
//				//rebond
//				double xDiff = robotCollide.getPosition()[0]-robot.getPosition()[0];
//				double yDiff = robotCollide.getPosition()[1]-robot.getPosition()[1];
//				
//				System.out.println(Math.atan(yDiff/xDiff));
//				double angle = Math.PI+Math.atan(yDiff/xDiff)-robot.getAngle();
//				
//				robot.turn(angle);
//			}
		}
		
		List<IRobot> removeRobots = new ArrayList<IRobot>();
		
		//uptdate positions
		for(int i = 0 ; i < robots.size() ; ++i) {
			robots.get(i).setPosition(copyRobots.get(i).getPosition());
			if(robots.get(i).getLife()<=0) removeRobots.add(robots.get(i));
		}
		
		//remove
		for(IRobot robot : removeRobots) {
			robots.remove(robot);
		}
		
		graphicBoard.update(fireringRobots);
	}

//	private void iaAction() {
//		InputNeuron inputXDiff = (InputNeuron) iaIn.get(0);
//		InputNeuron inputYDiff = (InputNeuron) iaIn.get(1);
//		InputNeuron computerAngle = (InputNeuron) iaIn.get(2);
//		
//		CalculationNeuron space = (CalculationNeuron) iaOut.get(0);
//		
//		for(IRobot robot : robots) {
//			if(robot!=userRobot) {
//				inputXDiff.setValue(userRobot.getPosition()[0]-robot.getPosition()[0]);
//				inputYDiff.setValue(userRobot.getPosition()[1]-robot.getPosition()[1]);
//				computerAngle.setValue(robot.getAngle());
//				
//				//Action to make 
//				if(space.getOut()>0.5) {
//					robot.fire();
//					drawFire(robot);
//				}
//				
////				int xDiff = (int) (userRobot.getPosition()[0]-robot.getPosition()[0]);
////				int yDiff = (int) (userRobot.getPosition()[1]-robot.getPosition()[1]);
////				double distance = Math.sqrt(xDiff*xDiff+yDiff*yDiff);
////				double angle = robot.getAngle();
////				double xCoinc = distance*Math.cos(angle);
////				double yCoinc = distance*Math.sin(angle);
////				
////				double[] coinc = {xCoinc,yCoinc};
////				double[] positionCible = {xDiff,yDiff};
////				
////				if(squareDistance(coinc, positionCible)<robot.getRobotRadius()*robot.getRobotRadius()) {
////					System.out.println("Booom ! "+space.getOut());
////				}
////				else {
////					System.out.println(space.getOut());
////				}
//
//			}
//		}
//		
//		
//	}

	private double squareDistance(double[] p1, double[] p2) {
		return (p1[0]-p2[0])* (p1[0]-p2[0])+ (p1[1]-p2[1])* (p1[1]-p2[1]);
	}

	@Override
	public int getXSize() {
		return (int) xSize;
	}

	@Override
	public int getYSize() {
		return (int) ySize;
	}

	@Override
	public List<IRobot> getRobots() {
		return robots;
	}
	
	@Override
	public void run() {
		compute();
	}

	@Override
	public void drawFire(IRobot robot) {
		fireringRobots.add(robot);
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		IRobot robot = (IRobot) arg0;
		if(robot.isFirering()) {
			robot.setFirering(false);
			
			//Applying damages to targets
			//TODO instant damages
			for(IRobot robotCible : robots) {
				if(robot!=robotCible) {
					//angle between to robots
//					double theta = Math.atan((robotCible.getPosition()[0]-robot.getPosition()[0])/(robotCible.getPosition()[1]-robot.getPosition()[1]));
					
					double fireringAngle = robot.getAngle();
					double distance = Math.sqrt(squareDistance(robot.getPosition(), robotCible.getPosition()));
					double xCoinc = robot.getPosition()[0]+distance*Math.cos(fireringAngle);
					double yCoinc = robot.getPosition()[1]+distance*Math.sin(fireringAngle);
					
					double[] coinc = {xCoinc,yCoinc};
					
					if(squareDistance(coinc, robotCible.getPosition())<robotCible.getRobotRadius()*robotCible.getRobotRadius()) {
						int life = robotCible.getLife();
						//1 damage
						if(robot.getBrain()!=null) robot.getBrain().score++;
						robotCible.setLife(life-2);
						
						//leeech
						robot.setLife(robot.getLife()+1);
					}
				}
			}
			
		}
	}
}
