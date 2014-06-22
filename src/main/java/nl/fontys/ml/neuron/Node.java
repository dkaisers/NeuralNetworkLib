package nl.fontys.ml.neuron;

import nl.fontys.ml.layer.Layer;

import java.io.Serializable;
import java.util.HashMap;

/**
 * @author Dominik Kaisers <d.kaisers@student.fontys.nl>
 */
public abstract class Node implements Serializable {

    /**
     * The layer, the node belongs to for the purpose of backpropagating the error.
     */
    protected Layer layer;

    public Layer getLayer() {
        return layer;
    }

    public void setLayer(Layer layer) {
        this.layer = layer;
    }

    public abstract double getOutput();

    public abstract void clearCache();

    /**
     * Sigmoid activation function.
     *
     * @param x X value of function.
     * @return f(x) = e^x / (1 + e^x).
     */
    public final double sigmoid(double x) {
        return 1.0d / (1.0d + Math.exp(-x));
    }
}
