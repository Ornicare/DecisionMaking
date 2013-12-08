package com.ornilabs.neurons;

import java.util.HashMap;
import java.util.Map;
import java.util.Observable;

public class CalculationNeuron extends Neuron implements INeuron{

	public CalculationNeuron(Map<INeuron, Double> predecessors) {
		super(predecessors);
	}

	@Override
	public void update(Observable o, Object arg) {
		calculateValue();
		super.update(o, arg);
	}

	private void calculateValue() {
		out = 0;
		for(INeuron neuron : predecessors.keySet()) out+=neuron.getOut()*predecessors.get(neuron);
		out=sigmoid(out);
	}

	private double sigmoid(double out) {
		return 1.0/(1+Math.exp(-out));
	}

	public void learn(double erreur) {
		Map<INeuron, Double> predecessorsCorrected = new HashMap<INeuron, Double>();
		for(INeuron neuron : predecessors.keySet()) {
			double weight = predecessors.get(neuron);
			weight+=learningRate*(erreur*neuron.getOut());
			predecessorsCorrected.put(neuron, weight);
		}
		predecessors = predecessorsCorrected;
	}

}
