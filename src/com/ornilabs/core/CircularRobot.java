package com.ornilabs.core;

import java.util.Observable;

import com.ornilabs.neurons.Entity;

public class CircularRobot extends Observable implements IRobot{

	private double x;
	private double y;
	
	/**
	 * angle € [-pi,pi]
	 */
	private double angle;
	private double robotRadius;
	private double robotAccel;
	private double robotMaxSpeed;
	private int life;
	private double robotAccelStep;
	private int maxLife;
	private boolean firering;
	private Entity brain;

	public CircularRobot(double x, double y, double angle, double robotAccelStep, double robotMaxAccel, int life, double radius) {
		this.x = x;
		this.y = y;
		this.angle = angle;
		this.robotRadius = radius;
		this.robotAccel = 0;
		this.robotMaxSpeed = robotMaxAccel;
		this.life = life;
		this.robotAccelStep = robotAccelStep;
		this.maxLife = life;
	}
	
	@Override
	public int getLife() {
		return life;
	}
	
	@Override
	public void setLife(int life) {
		this.life = life;
	}
	
	@Override
	public double getRobotRadius() {
		return robotRadius;
	}

	@Override
	public boolean turn(double angle) {
		this.angle = (this.angle+angle) % (Math.PI*2);
		//no collision possibles
		return true;
	}

	/**
	 * Basic fire : instant
	 */
	@Override
	public boolean fire() {
		this.firering = true;
		setChanged();
		notifyObservers();
		return true;
	}

	@Override
	public boolean isFirering() {
		return firering;
	}

	@Override
	public void setFirering(boolean firering) {
		this.firering = firering;
	}

	@Override
	public double accel(double dir) {
		dir = dir > 0 ? 1 : (dir < 0 ? -1 : 0);
		robotAccel+=dir*robotAccelStep;
		robotAccel = robotAccel>robotMaxSpeed ? robotMaxSpeed : (-robotAccel>robotMaxSpeed ? -robotMaxSpeed : robotAccel);
		return robotAccel;
	}

	@Override
	public double[] getPosition() {
		double[] position = {x,y};
		return position;
	}

	@Override
	public void setPosition(double x, double y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public double getAccel() {
		return robotAccel;
	}

	@Override
	public double getAngle() {
		return angle;
	}

	@Override
	public void setPosition(double[] position) {
		setPosition(position[0], position[1]);
	}

	@Override
	public int getMaxLife() {
		return maxLife;
	}

	@Override
	public double getMaxAccel() {
		return robotMaxSpeed;
	}

	@Override
	public void registerBrain(Entity brain) {
		this.brain = brain;
	}

	@Override
	public Entity getBrain() {
		return brain;
	}

}
