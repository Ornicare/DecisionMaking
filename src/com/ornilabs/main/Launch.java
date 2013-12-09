package com.ornilabs.main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;

import com.ornilabs.core.Board;
import com.ornilabs.core.CircularRobot;
import com.ornilabs.core.DrawF;
import com.ornilabs.core.DrawResults;
import com.ornilabs.core.GraphicBoard;
import com.ornilabs.core.IRobot;
import com.ornilabs.neurons.CalculationNeuron;
import com.ornilabs.neurons.INeuron;
import com.ornilabs.neurons.InputNeuron;

public class Launch {
	public static void main(String[] args) {
		
		
		//pas au point => rendre le problème linéairement séparable
		
		double xSize = 1000;
		double ySize = 1000;
		
		
//		//draw f
//		DrawF drawF = new DrawF(xSize, ySize);
//		JFrame frame1 = new JFrame();
//		frame1.setBounds(0,0,(int)xSize,(int)ySize);
//		frame1.add(drawF);
//		frame1.setVisible(true);
//		
		
		IRobot robot = new CircularRobot(100, 200, 0, 0.1, 10, Integer.MAX_VALUE);
		IRobot robotCible = new CircularRobot(100, 200, 0, 0.1, 10, 10);
		
		List<IRobot> robots = new ArrayList<IRobot>();
		robots.add(robot);
		robots.add(robotCible);
//		
//		for(int i = 0 ; i < 10000 ; ++i) {
//			robots.add(new CircularRobot(Math.random()*xSize, Math.random()*ySize, Math.random()*Math.PI*2, 0, 0, 10));
//		}
//		
		
		
		Map<Double[],Boolean[]> learningSet = new HashMap<Double[], Boolean[]>();
		//generate learning sets
		
		HashMap<Double[], Double> results = new HashMap<Double[], Double>();
		
		/*
		 * First test : fire only
		 */
		for(int xDiff = (int) -xSize ; xDiff < xSize ; xDiff+=xSize/100) {
			for(int yDiff = (int) -ySize ; yDiff < ySize ; yDiff+=ySize/100) {
				for(double angle = 0 ; angle < 360 ; angle+=360/100.0) {
//					double distance = Math.sqrt(xDiff*xDiff+yDiff*yDiff);
//					double xCoinc = distance*Math.cos(angle);
//					double yCoinc = distance*Math.sin(angle);
//					
//					double[] coinc = {xCoinc,yCoinc};
//					double[] positionCible = {xDiff,yDiff};
					
					Double[] set = {(double) xDiff,(double) yDiff,angle};
					Boolean[] result = {false,false,false,false,false};
					
					//plan transformé
					if(xDiff<0 && Math.abs(yDiff)<robot.getRobotRadius()) result[4] = true;
//					if(squareDistance(coinc, positionCible)<robot.getRobotRadius()*robot.getRobotRadius()) {
//						result[4] = true;
//					}

					learningSet.put(set, result);
					
				}
			}
		}
		
		System.out.println("Generation ended");
		
		//create neuronal network
		//first case : 3 inputs : x difference (player-computer), y diff, computer angle
		//5 outputs : keys
		
		InputNeuron inputXDiff = new InputNeuron((Map<INeuron, Double>)new HashMap<INeuron, Double>());
		InputNeuron inputYDiff = new InputNeuron((Map<INeuron, Double>)new HashMap<INeuron, Double>());
		InputNeuron computerAngle = new InputNeuron((Map<INeuron, Double>)new HashMap<INeuron, Double>());
		
		Map<INeuron,Double> inputLayer = new HashMap<INeuron,Double>();
		inputLayer.put(inputXDiff, Math.random()-Math.random());
		inputLayer.put(inputYDiff, Math.random()-Math.random());
		inputLayer.put(computerAngle, Math.random()-Math.random());

		List<INeuron> inputIa = new ArrayList<INeuron>();
		inputIa.add(inputXDiff);
		inputIa.add(inputYDiff);
		inputIa.add(computerAngle);
		
		//TODO only fire
//		CalculationNeuron up = new CalculationNeuron(inputLayer);
//		CalculationNeuron down = new CalculationNeuron(inputLayer);
//		CalculationNeuron left = new CalculationNeuron(inputLayer);
//		CalculationNeuron right = new CalculationNeuron(inputLayer);
		
		
//		CalculationNeuron space01 = new CalculationNeuron(inputLayer);
//		CalculationNeuron space02 = new CalculationNeuron(inputLayer);
//		
//		Map<INeuron,Double> layer0 = new HashMap<INeuron,Double>();
//		layer0.put(space01, Math.random()-Math.random());
//		layer0.put(space02, Math.random()-Math.random());
		
		//space  11
		CalculationNeuron space = new CalculationNeuron(inputLayer);
		
		List<INeuron> outputLayer = new ArrayList<INeuron>();
		outputLayer.add(space);
		
		
		//test
		int failures = 0;
		for(Double[] set : learningSet.keySet()) {
			double[] resultsCoord = f(set[0],set[1],robotCible.getRobotRadius());
			inputXDiff.setValue(resultsCoord[0]);
			inputYDiff.setValue(resultsCoord[1]);
			computerAngle.setValue(set[2]);
			
			//collect results
			//TODO only fire
			boolean result = space.getOut() > 0.5 ? true : false;
			
			//compare and learn
			if(result!=learningSet.get(set)[4]) {
				++failures;
			}
		}
		System.out.println("% failures with no learning : "+100*failures/((double)learningSet.size()));
		
		//learning
		for(Double[] set : learningSet.keySet()) {
			double[] resultsCoord = f(set[0],set[1],robotCible.getRobotRadius());
			inputXDiff.setValue(resultsCoord[0]);
			inputYDiff.setValue(resultsCoord[1]);
			computerAngle.setValue(set[2]);
			
//			System.out.println(set[0]+" "+set[1]+" "+set[2]+"-"+space.getOut());
			
			//collect results
			//TODO only fire
			boolean result = space.getOut() > 0.5 ? true : false;
			
			//compare and learn
			if(result!=learningSet.get(set)[4]) {
				//failure : learn
				double erreur = 1-space.getOut();
				if(result) erreur = -1-space.getOut();
				
				space.learn(erreur);
//				space01.learn(erreur);
//				space02.learn(erreur);
				
			}
		}
		
		//retest
		failures = 0;
		for(Double[] set : learningSet.keySet()) {
			double[] resultsCoord = f(set[0],set[1],robotCible.getRobotRadius());
			inputXDiff.setValue(resultsCoord[0]);
			inputYDiff.setValue(resultsCoord[1]);
			computerAngle.setValue(set[2]);
			
			//collect results
			//TODO only fire
			boolean result = space.getOut() > 0.5 ? true : false;

			if(set[2]==0d) results.put(set, space.getOut());
			
			//compare and learn
			if(result!=learningSet.get(set)[4]) {
				++failures;
			}
		}
		System.out.println("% failures with learning : "+100*failures/((double)learningSet.size()));
		
		
		//draw results
		DrawResults drawResults = new DrawResults(results, xSize/100.0, xSize, ySize);
		JFrame frame = new JFrame();
		frame.setBounds(0,0,(int)xSize,(int)ySize);
		frame.add(drawResults);
		frame.setVisible(true);
		
		
		//launch game

//		GraphicBoard graphicBoard = new GraphicBoard();
//		new Board(robots, xSize, ySize, graphicBoard, robot, inputIa, outputLayer);
	}
	
	public static double[] f(double x, double y, double a) {
		
		double[] result = {x,y};
		//Shoot zone
		if(x>0 && Math.abs(y)<a) {
			result[1] = Math.tan(y/a*Math.PI/2);
		}
		else if(x>0 && y>=a) {
			result[0] = -x;
			result[1] = Math.atan(y-a)*a/Math.PI*2;
		}
		else if(x>0 && y<=-a) {
			result[0] = -x;
			result[1] = Math.atan(y+a)*a/Math.PI*2;
		}
		else if(x<0 && y>0) {
			result[1] = y+a;
		}
		else if(x<0 && y<0) {
			result[1] = y-a;
		}
		return result;
	}
	
	public static double[] f_1(double x, double y, double a) {
		double[] result = {x,y};
		if(x>0) {
			result[1] =  Math.atan(x)*a/Math.PI*2;
		}
		else if(x<0) {
			if(Math.abs(y)<a) {
				result[0] = -x;
				if(y>0) {
					result[1] =  Math.atan(y)*a/Math.PI*2+a;
				}
				else if(y<0){
					result[1] =  Math.atan(y)*a/Math.PI*2-a;
				}
			}
			else {
				if(y>0) {
					result[1] = y-a;
				}
				else {
					result[1] = y+a;
				}
			}
		}
		return result;
	}

	private static double squareDistance(double[] p1, double[] p2) {
		return (p1[0]-p2[0])* (p1[0]-p2[0])+ (p1[1]-p2[1])* (p1[1]-p2[1]);
	}
}
