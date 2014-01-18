//package com.ornilabs.neurons;
//
//import com.ornilabs.core.BoardPanel;
//import com.ornilabs.core.GraphicBoard;
//import com.ornilabs.core.IRobot;
//import com.ornilabs.main.Launch;
//
//import exemples.MultiLayerPerceptron;
//
//public class Entity implements IEntity{
//
//	private MultiLayerPerceptron brain;
//	private IRobot managedRobot;
//	private double lastScore;
//	public double score = 0;
//	
//	public Entity(IRobot managedRobot) {
//		brain = new MultiLayerPerceptron(1, 3, 3, 1, 0.1);
//		this.managedRobot = managedRobot;
//		managedRobot.registerBrain(this);
//	}
//	
//	public IRobot getManagedRobot() {
//		return managedRobot;
//	}
//	
//	public double[] calculateAndTrain(Food target) {
//		double[] targetCoords = {target.getX(),target.getY()};
//		double[] coords = managedRobot.getPosition();
//		
////		double dotProductToTarget = (targetCoords[0]-coords[0])*((managedRobot.getAccel()*Math.cos(managedRobot.getAngle())))+(targetCoords[1]-coords[1])*(managedRobot.getAccel()*Math.sin(managedRobot.getAngle()));
//
//		
//		double angle = getAngle(coords, targetCoords, managedRobot.getAngle());
//		
//		double[] outAlt = {0,0,0};
//		if(((Double)angle).isNaN()) return outAlt;
//		
//		
////		System.out.println();
//		
//		double[] output= brain.classify(angle);
//		double accel = output[0]>0.5 ? 2*managedRobot.getMaxAccel() : -2*managedRobot.getMaxAccel();
//		
//		
////		double turn = output[1]>0.5 ? 1 : -1;
//		double turn = (output[1]-0.5)*2*GraphicBoard.stepAngle;
//		
//		boolean fire = output[2]>0.5;
//		
//		double[] outputExpected = {0,0,0};
////		System.out.println(accel+" "+dotProductToTarget);
////		if(accel*dotProductToTarget<0) {
////			//bad decision
////			outputExpected[0] = Math.signum(dotProductToTarget)*managedRobot.getMaxAccel();
////		}
////		else {
////			outputExpected[0] = Math.signum(dotProductToTarget)*managedRobot.getMaxAccel();
////		}
//		
//		//forcast
////		double xf =coords[0]+(managedRobot.getAccel()+accel)*Math.cos(managedRobot.getAngle()+Math.signum(turn)*GraphicBoard.stepAngle);
////		double yf =coords[1]+(managedRobot.getAccel()+accel)*Math.sin(managedRobot.getAngle()+Math.signum(turn)*GraphicBoard.stepAngle);
//		
//
////		double xf2 =coords[0]+(managedRobot.getAccel())*Math.cos(managedRobot.getAngle()+Math.signum(turn)*GraphicBoard.stepAngle);
////		double yf2 =coords[1]+(managedRobot.getAccel())*Math.sin(managedRobot.getAngle()+Math.signum(turn)*GraphicBoard.stepAngle);
//		
//		double x =coords[0]+managedRobot.getMaxAccel()*Math.cos(managedRobot.getAngle());
//		double y =coords[1]+managedRobot.getMaxAccel()*Math.sin(managedRobot.getAngle());
//		
//		double xp =coords[0]-managedRobot.getMaxAccel()*Math.cos(managedRobot.getAngle());
//		double yp =coords[1]-managedRobot.getMaxAccel()*Math.sin(managedRobot.getAngle());
//		
//		double afterSquareDistanceToForcastPlusAccel = (targetCoords[0]-x)*(targetCoords[0]-x)+(targetCoords[1]-y)*(targetCoords[1]-y);
//		double afterSquareDistanceToForcastMinusAccel = (targetCoords[0]-xp)*(targetCoords[0]-xp)+(targetCoords[1]-yp)*(targetCoords[1]-yp);
//		
//
////		double[] coords2 = {xf2,yf2};
//		double angleAfter = getAngle(coords , targetCoords, managedRobot.getAngle()+Math.signum(turn)*GraphicBoard.stepAngle);
//		
//		
//
//
//		outputExpected[1] = -angle;
//		if(getAngle(coords , targetCoords, managedRobot.getAngle()-Math.min(angle,GraphicBoard.stepAngle))>angle) 
//			outputExpected[1] = angle;
//		
//		outputExpected[1] = outputExpected[1]>GraphicBoard.stepAngle || outputExpected[1]<GraphicBoard.stepAngle ? (outputExpected[1] > 0 ? 1 : -1)*GraphicBoard.stepAngle : outputExpected[1];
//		
//		outputExpected[1] = outputExpected[1]/(2*GraphicBoard.stepAngle)+0.5;
////		if(angleAfter>angle) {
//////			outputExpected[1] = -turn;
////
//////			System.out.println("bad");
////			
////		}
////		else {
//////			outputExpected[1] = turn;
//////			System.out.println("good");
////		}
//		
//		
////		System.out.println(afterSquareDistanceToForcastMinusAccel+"   "+afterSquareDistanceToForcastPlusAccel);
////		System.out.println(x+" "+y+" -- "+xp+" "+yp+" - "+coords[0]+" "+coords[1]+" - "+angle+ " - "+targetCoords[0]+" "+targetCoords[1]);
//		
//		if(afterSquareDistanceToForcastPlusAccel>afterSquareDistanceToForcastMinusAccel) {
//			//bad decision to
//			outputExpected[0] = -1;
//		}
//		else {
//			outputExpected[0] = 1;
//		}
//		
//		//min radius => non linear
////		if(Math.min(afterSquareDistanceToForcastMinusAccel,afterSquareDistanceToForcastPlusAccel)<1000) {
////			outputExpected[0] = -outputExpected[0];
////		}
//		
//		
//		//fire ?
//			//angle between to robots
////				double theta = Math.atan((robotCible.getPosition()[0]-robot.getPosition()[0])/(robotCible.getPosition()[1]-robot.getPosition()[1]));
//			
//		double fireringAngle = managedRobot.getAngle();
//		double distance = Math.sqrt(Launch.squareDistance(managedRobot.getPosition(), targetCoords));
//		double xCoinc = managedRobot.getPosition()[0]+distance*Math.cos(fireringAngle);
//		double yCoinc = managedRobot.getPosition()[1]+distance*Math.sin(fireringAngle);
//		
//		double[] coinc = {xCoinc,yCoinc};
//		
////		System.out.println(angle+ " - "+getAngle(coords , targetCoords, managedRobot.getAngle()-Math.min(angle,GraphicBoard.stepAngle)));
//		
////		if(Launch.squareDistance(coinc, target.getPosition())<target.getRobotRadius()*target.getRobotRadius()) {
////			//booom !
////			outputExpected[2] = 1;
////		}
//		
//		
//		
//		outputExpected[0] = outputExpected[0] < 0 ? 0 : 1;
////		outputExpected[1] = outputExpected[1] < 0 ? 0 : 1;
//		
////		double[] input = {squareDistanceToTarget,Math.acos(dotProductToTarget/squareDistanceToTarget)};
//		double[] input = {angle};
//		brain.train(input , outputExpected);
//		
//		
//		lastScore = (Math.abs(outputExpected[0]-output[0])+Math.abs(outputExpected[1]-output[1])+Math.abs(outputExpected[2]-output[2]))/3;
//		System.out.println(lastScore+outputExpected[0]+" "+outputExpected[1]+" "+outputExpected[2]+" "+output[0]+" "+output[1]+" "+output[2]+" "+managedRobot.getLife()+" "+angle);
////		
////		System.out.println(angle);
////		System.out.println(output[0]+" "+output[1]+" "+output[2]+" -- "+outputExpected[0]+" "+outputExpected[1]+" "+outputExpected[2]);
////		output[1] = angle;
////		output[1] = outputExpected[1];
////		System.out.println("@"+outputExpected[1]);
//		return output;
//	}
//	
//	private double getAngle(double[] coords, double[] targetCoords, double angle2) {
//		double squareDistanceToTarget = (targetCoords[0]-coords[0])*(targetCoords[0]-coords[0])+(targetCoords[1]-coords[1])*(targetCoords[1]-coords[1]);
//		
//		double dotProductToTarget2 = (targetCoords[0]-coords[0])*((Math.cos(angle2)))+(targetCoords[1]-coords[1])*(Math.sin(angle2));
//		double angle = Math.acos(dotProductToTarget2/(Math.sqrt(squareDistanceToTarget)));
//		return angle;
//	}
//
//	public void update(Food target) {
//		double[] output = calculateAndTrain(target);
//		double accel = output[0]>0.5 ? 2*managedRobot.getMaxAccel() : -2*managedRobot.getMaxAccel();
////		double turn = output[1]>0.5 ? 1 : -1;
//
//		double turn = (output[1]-0.5)*2;
////		System.out.println("@"+turn*GraphicBoard.stepAngle);
////		System.out.println(getAngle(managedRobot.getPosition(), target.getPosition(), managedRobot.getAngle()));
////		System.out.println(turn);
////		boolean fire = output[2]>0.5;
//		System.out.println(output[0]+" "+output[1]+" "+output[2]);
//		
//		
//		//apply
//		managedRobot.accel(accel);
//		managedRobot.turn(turn*GraphicBoard.stepAngle);
////		if(fire) {
////			managedRobot.fire();
////			BoardPanel.fireringRobots.add(managedRobot);
////		}
//	}
//
//	public MultiLayerPerceptron getBrain() {
//		return brain;
//	}
//
//	public double getScore() {
//		return lastScore;
//	}
//}
