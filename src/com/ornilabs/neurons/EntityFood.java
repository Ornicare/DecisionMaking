package com.ornilabs.neurons;

import java.util.List;

import com.ornilabs.core.Board;
import com.ornilabs.core.BoardPanel;
import com.ornilabs.core.GraphicBoard;
import com.ornilabs.core.IRobot;
import com.ornilabs.main.Launch;

import exemples.MultiLayerPerceptron;

public class EntityFood implements IEntity {

	private MultiLayerPerceptron brain;
	private IRobot managedRobot;
	private double lastScore;
	public double score = 0;
	private double lastAngle = 0;
	private double lastDistance = Double.MAX_VALUE;
	private double lastAccInput = 0;
	public boolean lastFireringStrike;
	private double oldTurn;
	private Food target;
	
	public EntityFood(IRobot managedRobot) {
		brain = new MultiLayerPerceptron(1, 3, 3, 1, 1);
		this.managedRobot = managedRobot;
		managedRobot.registerBrain(this);
	}
	
	@Override
	public Food getTarget() {
		return target;
	}
	
	/* (non-Javadoc)
	 * @see com.ornilabs.neurons.IEntity#getManagedRobot()
	 */
	@Override
	public IRobot getManagedRobot() {
		return managedRobot;
	}
	
	/* (non-Javadoc)
	 * @see com.ornilabs.neurons.IEntity#calculateAndTrain(com.ornilabs.neurons.Food)
	 */
	@Override
	public double[] calculateAndTrain(List<Food> targets) {
		
		//get the nearest target
		if(target==null || target.isConsumed()) {
//			System.out.println("switch");
			Food tempGet = null;
			for(Food food : targets) {
				if(food==null) continue;
				double[] foodPosition = {food.getX(),food.getY()};
				double[] targetPosition = {0,0};
				if(tempGet!=null) {targetPosition[0]=tempGet.getX();targetPosition[1]=tempGet.getY();}
				if(((tempGet==null) || (tempGet.isConsumed()) || (Launch.squareDistance(foodPosition, managedRobot.getPosition())<Launch.squareDistance(targetPosition, managedRobot.getPosition())))) tempGet = food;
			}
			target = tempGet;
		}
		
		double[] outAlt = {0,0,0};
		if(target==null) return outAlt;
		
		double[] targetCoords = {target.getX(),target.getY()};
		double[] coords = managedRobot.getPosition();		
		double angle = getDist(coords, targetCoords, managedRobot.getAngle());
		
	
		if(((Double)angle).isNaN()) return outAlt;
		
		double[] output= brain.classify(getAngle(coords, targetCoords, managedRobot.getAngle()));
		double accel = output[0]>0.5 ? 2*managedRobot.getMaxAccel() : -2*managedRobot.getMaxAccel();
		
		
		double[] outputExpected = {0,0,0};
		
		
		double currentDistance = (targetCoords[0]-coords[0])*(targetCoords[0]-coords[0])+(targetCoords[1]-coords[1])*(targetCoords[1]-coords[1]);
	
		
//		if(lastFireringStrike) {
//			outputExpected[2] = 1;
//		}
//		else {
//			//randomly try to strike TODO pas beau
////			if(Math.random()<0.5) {
////				managedRobot.fire();
////				BoardPanel.fireringRobots.add(managedRobot);
////			}
//		}
		lastFireringStrike = false;
		if(lastDistance>currentDistance) {
			outputExpected[0] = Math.signum(lastAccInput);
		}
		else {
			outputExpected[0] = -Math.signum(lastAccInput);
		}
		lastAccInput = accel;
//		System.out.println(lastAngle+" "+angle);
		if(lastAngle>angle) {
			outputExpected[1] = oldTurn;
		}
		else {
			outputExpected[1] = -oldTurn;
		}
		oldTurn = (output[1]-0.5)*2;
		lastAngle  = angle;
		
//		outputExpected[1] = Math.signum(outputExpected[1])*GraphicBoard.stepAngle;
		
		outputExpected[1] = outputExpected[1]>GraphicBoard.stepAngle || outputExpected[1]<GraphicBoard.stepAngle ? (outputExpected[1] > 0 ? 1 : -1)*GraphicBoard.stepAngle : outputExpected[1];
		outputExpected[1] = outputExpected[1]/(2*GraphicBoard.stepAngle)+0.5;
		

		outputExpected[0] = outputExpected[0] < 0 ? 0 : 1;
		
		double[] input = {lastAngle};
		brain.train(input , outputExpected);
		
		return output;
	}
	
	private double getAngle(double[] coords, double[] targetCoords, double angle2) {
		double squareDistanceToTarget = (targetCoords[0]-coords[0])*(targetCoords[0]-coords[0])+(targetCoords[1]-coords[1])*(targetCoords[1]-coords[1]);

		
		double dotProductToTarget2 = (targetCoords[0]-coords[0])*((Math.cos(angle2)))+(targetCoords[1]-coords[1])*(Math.sin(angle2));
//		System.out.println(dotProductToTarget2/(Math.sqrt(squareDistanceToTarget)));
		double angle = Math.acos(dotProductToTarget2/(Math.sqrt(squareDistanceToTarget)));
		
		double angle1 = Math.atan((targetCoords[1]-coords[1])/(targetCoords[0]-coords[0]));
//		System.out.println("@"+(((angle2-angle1))%Math.PI));
		

		return angle;
//		return Math.PI - (angle2-angle1);
	}
	
	private double getDist(double[] coords, double[] targetCoords, double angle2) {
		//distance between the two points ?
		double squareDistanceToTarget = Math.sqrt((targetCoords[0]-coords[0])*(targetCoords[0]-coords[0])+(targetCoords[1]-coords[1])*(targetCoords[1]-coords[1]));
		double squareDistanceToTarget2 = ((targetCoords[0]-coords[0])/squareDistanceToTarget-Math.cos(angle2))*((targetCoords[0]-coords[0])/squareDistanceToTarget-Math.cos(angle2))+((targetCoords[1]-coords[1])/squareDistanceToTarget-Math.sin(angle2))*((targetCoords[1]-coords[1])/squareDistanceToTarget-Math.sin(angle2));
		return squareDistanceToTarget2;
	}

	/* (non-Javadoc)
	 * @see com.ornilabs.neurons.IEntity#update(com.ornilabs.neurons.Food)
	 */
	@Override
	public void update(List<Food> target) {
		double[] output = calculateAndTrain(target);
		
//		System.out.println(output[0]+" "+output[1]+" "+output[2]);
		
		//linearize
		double accel = output[0]>0.5 ? 2*managedRobot.getMaxAccel() : -2*managedRobot.getMaxAccel();
		double turn = (output[1]-0.5)*2;
		boolean fire = output[2]>0.5;

		//apply
//		managedRobot.accel(accel);
		managedRobot.turn(turn*GraphicBoard.stepAngle);
//		if(fire) {
//			managedRobot.fire();
//			BoardPanel.fireringRobots.add(managedRobot);
//		}
	}

	/* (non-Javadoc)
	 * @see com.ornilabs.neurons.IEntity#getBrain()
	 */
	@Override
	public MultiLayerPerceptron getBrain() {
		return brain;
	}

	/* (non-Javadoc)
	 * @see com.ornilabs.neurons.IEntity#getScore()
	 */
	@Override
	public double getScore() {
		return lastScore;
	}
}
