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
public abstract class Layer {
    
    /**
     * List of nodes in this layer.
     */
    private List<Node> nodes;
    
    /**
     * The previous layer. Null indicates, that this is the input layer.
     */
    private Layer previousLayer;
    
    /**
     * The next layer. Null indicates, that this is the output layer.
     */
    private Layer nextLayer;
    
    /**
     * The error gradient sum, the next layer temporarily saves in its previous layer for error back propagation.
     */
    private float nextLayerErrorGradientSum;
    
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
     * Calculates the error of the next layer when backpropagating. If this is the output layer, the method will use
     * the error calculated by the actual and expected output, else it will use the saved error gradient sum.
     * 
     * @return The error of the next layer, used for updating the weights of this layer.
     */
    public float getError() {
        return 0;
    }
    
    /**
     * Called by the output-layer-back-propagation. Goes through the nodes and updates their weights. Calls this
     * method recursively on the previous layer. Executed till the input layer is reached.
     * 
     * @param nextLayerErrorGradientSum Error gradient sum of calling layer.
     */
    public void backPropagateError(float nextLayerErrorGradientSum) {
        this.nextLayerErrorGradientSum = nextLayerErrorGradientSum;
    }
    
    /**
     * Called by the network on the output layer to start the back propagation. After execution, the back propagation on
     * the previous layer is called.
     * 
     * @param expectedOutputs Expected outputs.
     * @param actualOutputs Actual Outputs.
     */
    public void backPropagateError(List<Float> expectedOutputs, List<Float> actualOutputs) {
        
    }
    
}
