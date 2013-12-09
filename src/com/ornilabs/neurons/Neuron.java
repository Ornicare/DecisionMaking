package com.ornilabs.neurons;

import java.util.Map;
import java.util.Observable;
import java.util.Observer;

public abstract class Neuron extends Observable implements Observer, INeuron{

	protected Map<INeuron,Double> predecessors;
	protected double out;
	protected double learningRate = 0.01;
	
	public Neuron(Map<INeuron,Double> predecessors) {
		this.predecessors = predecessors;
		for(INeuron neuron : predecessors.keySet()) ((Observable)neuron).addObserver(this);
	}
	
	@Override
	public double getOut() {
		return out;
	}

	@Override
	public void update(Observable o, Object arg) {
		setChanged();
		notifyObservers();
	}
	
	
}
