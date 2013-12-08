package com.ornilabs.core;

import java.util.List;

public interface IBoard {
	public void compute();

	public int getXSize();
	public int getYSize();

	public List<IRobot> getRobots();

	public void drawFire(IRobot userRobot);
}
