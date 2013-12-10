package com.ornilabs.core;

import com.ornilabs.neurons.Entity;

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
	public void registerBrain(Entity entity);
	public Entity getBrain();
}
