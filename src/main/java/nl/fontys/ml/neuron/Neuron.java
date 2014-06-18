/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package nl.fontys.ml.neuron;

import java.util.HashMap;
import java.util.Map;

/**
 * Output neurons get a their information to calculate the outcome from their
 * input nodes (input layer).
 *
 * @author Dominik Kaisers <d.kaisers@student.fontys.nl>
 */
public class Neuron extends Node {

    /**
     * HashMap with the weights for every input neuron.
     */
    protected HashMap<Node, Double> inputLayer;

    /**
     * Calculates the output based on the weights and input values.
     *
     * @return Output for this neuron.
     */
    @Override
    public double getOutput() {
        double inputSum = 0;

        for (Map.Entry<Node, Double> input : inputLayer.entrySet()) {
            inputSum += input.getValue() * input.getKey().getOutput();
        }

        return sigmoid(inputSum);
    }

    @Override
    public Node deepCopy() {
        Neuron copy = new Neuron();
        copy.inputLayer = new HashMap<>();

        for (Map.Entry<Node, Double> entry : inputLayer.entrySet()) {
            copy.inputLayer.put(entry.getKey(), entry.getValue());
        }
        return copy;
    }

    public void setInputLayer(HashMap<Node, Double> inputLayer) {
        this.inputLayer = inputLayer;
    }

    public HashMap<Node, Double> getInputLayer() {
        return inputLayer;
    }

}
