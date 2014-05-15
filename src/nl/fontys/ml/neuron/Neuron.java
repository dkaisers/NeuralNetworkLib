/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package nl.fontys.ml.neuron;

import java.util.HashMap;

/**
 * Output neurons get a their information to calculate the outcome from their
 * input nodes (input layer).
 *
 * @author Dominik Kaisers <d.kaisers@student.fontys.nl>
 */
public class Neuron extends Node{
    
    /**
     * HashMap with the weights for every input neuron.
     */
    private HashMap<Node, Double> inputLayer;

    /**
     * Calculates the output based on the weights and input values.
     * @return 
     */
    @Override
    public double getOutput() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
