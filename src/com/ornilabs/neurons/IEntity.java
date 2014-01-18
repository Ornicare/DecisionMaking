package com.ornilabs.neurons;

import java.util.List;

import com.ornilabs.core.IRobot;

import exemples.MultiLayerPerceptron;

public interface IEntity {
	public abstract IRobot getManagedRobot();

	public abstract double[] calculateAndTrain(List<Food> target);

	public abstract void update(List<Food> targets);

	public abstract MultiLayerPerceptron getBrain();

	public abstract double getScore();

	Food getTarget();

}