/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package nl.fontys.ml.layer;

import java.util.ArrayList;
import java.util.List;
import nl.fontys.ml.neuron.InputNode;
import nl.fontys.ml.neuron.Node;

/**
 *
 * @author Dominik Kaisers <d.kaisers@student.fontys.nl>
 */
public class Layer {
    
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
     * The error gradient sum, the next layer temporarily saves in its previous layer for error back propagation.
     */
    protected double nextLayerErrorGradientSum;
    
    /**
     * Constructor of a Layer. Sets the next layer in the given previous layer (if != null) and creates the bias node,
     * if wanted.
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
        
        // Create nodes list and add bias node if wanted
        this.nodes = new ArrayList<>();
        if (createBiasNode) {
            this.nodes.add(new InputNode(1));
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
     * Called by the output-layer-back-propagation. Goes through the nodes and updates their weights. Calls this
     * method recursively on the previous layer. Executed till the input layer is reached.
     * 
     * @param nextLayerErrorGradientSum Error gradient sum of calling layer.
     */
    protected void backPropagateError(double nextLayerErrorGradientSum) {
        this.nextLayerErrorGradientSum = nextLayerErrorGradientSum;
    }
    
}
