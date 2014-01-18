package com.ornilabs.core;

import java.awt.Color;
import java.util.List;

import com.ornilabs.neurons.EntityFood;
import com.ornilabs.neurons.IEntity;
import com.ornilabs.neurons.Trace;

public interface IRobot {
	public boolean turn(double angle);
	public double accel(double dir);
	public boolean fire();
	public double[] getPosition();
	public void setPosition(double x, double y);
	public double getAccel();
	public double getAngle();
	public int getLife();
	public void setLife(int life);
	public double getRobotRadius();
	public void setPosition(double[] position);
	public int getMaxLife();
	boolean isFirering();
	void setFirering(boolean firering);
	public double getMaxAccel();
	public void registerBrain(IEntity entity);
	public IEntity getBrain();
	List<Trace> getTraceList();
	void addTrace(Trace t);
	Color getColor();
	public void addScore();
	public String getScore();
}
