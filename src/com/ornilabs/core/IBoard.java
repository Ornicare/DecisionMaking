package com.ornilabs.core;

import java.util.List;

import com.ornilabs.neurons.Food;

public interface IBoard {
	public void compute();

	public int getXSize();
	public int getYSize();

	public List<IRobot> getRobots();

	public void drawFire(IRobot userRobot);

	public List<Food> getFoodList();

	public void setFoodList(List<Food> currentFood);
}
