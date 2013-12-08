package com.ornilabs.neurons;

import java.util.Map;

public class InputNeuron extends Neuron{

	public InputNeuron(Map<INeuron, Double> predecessors) {
		super(predecessors);
	}
	
	public void setValue(double value) {
		out = value;
		super.update(this, null);
	}


}
