/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package nl.fontys.ml.neuron;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

/**
 * Output neurons get a their information to calculate the outcome from their
 * input nodes (input layer).
 *
 * @author Dominik Kaisers <d.kaisers@student.fontys.nl>
 */
public class Neuron extends Node {

    private Double cache = null;

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
        if (cache == null) {
            double inputSum = 0;

            for (Map.Entry<Node, Double> input : inputLayer.entrySet()) {
                inputSum += input.getValue() * input.getKey().getOutput();
            }

            cache = sigmoid(inputSum);
        }

        return cache;
    }

    @Override
    public void clearCache() {
        cache = null;
    }

    public void setInputLayer(HashMap<Node, Double> inputLayer) {
        this.inputLayer = inputLayer;
    }

    public HashMap<Node, Double> getInputLayer() {
        return inputLayer;
    }

}
