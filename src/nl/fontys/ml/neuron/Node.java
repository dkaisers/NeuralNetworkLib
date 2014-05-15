package nl.fontys.ml.neuron;

import nl.fontys.ml.layer.Layer;

/**
 *
 * @author Dominik Kaisers <d.kaisers@student.fontys.nl>
 */
public abstract class Node {
    
    /**
     * The layer, the node belongs to for the purpose of backpropagating the error.
     */
    private Layer layer;

    public Layer getLayer() {
        return layer;
    }

    public void setLayer(Layer layer) {
        this.layer = layer;
    }
    
    public abstract double getOutput();
    
    // public abstract void updateWeights();
    
    /**
     * Sigmoid activation function.
     * 
     * @param x X value of function.
     * @return f(x) = e^x / (1 + e^x).
     */
    public final double sigmoid(double x) {
        return Math.exp(x) / (1 + Math.exp(x));
    }
    
}
