/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.fontys.ml.layer;

import java.io.Serializable;
import java.util.*;

import nl.fontys.ml.neuron.InputNode;
import nl.fontys.ml.neuron.Neuron;
import nl.fontys.ml.neuron.Node;

/**
 *
 * @author Dominik Kaisers <d.kaisers@student.fontys.nl>
 */
public class Layer implements Serializable {

    /**
     * List of nodes in this layer.
     */
    protected List<Node> nodes;

    /**
     * The previous layer. Null indicates, that this is the input layer.
     */
    protected Layer previousLayer;

    /**
     * The next layer. Null indicates, that this is the output layer.
     */
    protected Layer nextLayer;

    /**
     * The error gradient sums of the next layer for each node in this layer.
     */
    protected Map<Node, Double> errGradientSum;

    /**
     * Constructor of a Layer. Sets the next layer in the given previous layer (if != null) and
     * creates the bias node, if wanted.
     *
     * @param previousLayer
     * @param createBiasNode
     */
    public Layer(Layer previousLayer, boolean createBiasNode) {
        // Set previous layer
        this.previousLayer = previousLayer;
        if (this.previousLayer != null) {
            this.previousLayer.setNextLayer(this);
        }

        // Create error gradient sum map
        this.errGradientSum = new HashMap<>();

        // Create nodes list and add bias node if wanted
        this.nodes = new ArrayList<>();
        if (createBiasNode) {
            InputNode bias = new InputNode(1);
            this.nodes.add(bias);
            this.errGradientSum.put(bias, 0d);
        }
    }

    public Layer(Layer previousLayer, boolean createBiasNode, int numberOfRandomNodes) {
        this(previousLayer, createBiasNode);

        HashMap<Node, Double> inputLayer = new HashMap<>();
        Random r = new Random();
        for (Node n : previousLayer.nodes) {
            inputLayer.put(n, r.nextDouble());
        }

        for (int i = 0; i < numberOfRandomNodes; i++) {
            Neuron neuron = new Neuron();
            neuron.setInputLayer(inputLayer);
            nodes.add(neuron);
        }
    }

    public Layer getPreviousLayer() {
        return previousLayer;
    }

    public void setPreviousLayer(Layer previousLayer) {
        this.previousLayer = previousLayer;
    }

    public Layer getNextLayer() {
        return nextLayer;
    }

    public void setNextLayer(Layer nextLayer) {
        this.nextLayer = nextLayer;
    }

    /**
     * Called by the output-layer-back-propagation. Goes through the nodes and updates their
     * weights. Calls this method recursively on the previous layer. Executed till the input layer
     * is reached.
     * 
     * @param learningRate Learning rate.
     */
    protected void backPropagateError(double learningRate) {
        for (Node n : this.nodes) {
            if (n instanceof Neuron) {
                Neuron node = (Neuron) n;

                double output = node.getOutput();
                double errorGradient = this.errGradientSum.get(n) * output * (1 - output);

                for (Map.Entry<Node, Double> weight : node.getInputLayer().entrySet()) {
                    double newWeight = weight.getValue() + (learningRate * errorGradient * weight.getKey().getOutput());
                    node.getInputLayer().put(weight.getKey(), newWeight);

                    double gradientSum = previousLayer.errGradientSum.get(node) + (newWeight * errorGradient);
                    previousLayer.errGradientSum.put(node, gradientSum);
                }
            }
        }
        
        previousLayer.backPropagateError(learningRate);
    }

}
