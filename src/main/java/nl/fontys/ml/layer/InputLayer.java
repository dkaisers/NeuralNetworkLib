
package nl.fontys.ml.layer;

import nl.fontys.ml.neuron.InputNode;

/**
 * Subclass of layer, representing the input layer.
 *
 * @author Dominik Kaisers <d.kaisers@student.fontys.nl>
 */
public class InputLayer extends Layer{

    protected InputLayer(){
        super(null,false);
    }

    public InputLayer(int numberOfInputNodes) {
        super(null, true);
        
        for (int i = 0; i < numberOfInputNodes; i++) {
            this.nodes.add(new InputNode());
        }
    }
    
    /**
     * Sets the input data that is used to calculate an ouput from the network.
     * @param inputData New input data.
     */
    public void setInputData(Double[] inputData) {
        // Check for valid input data
        if (this.nodes.size() != inputData.length + 1)
            throw new IllegalArgumentException("Uncompatible array size. Was + " + inputData.length + ", should have been " + (this.nodes.size() - 1) + ".");
        
        // Set data for every node beginning with index = 1, because 0 is the bias node
        for (int i = 1; i < this.nodes.size(); i++) {
            InputNode node = (InputNode) this.nodes.get(i);
            node.setInputValue(inputData[i-1]);
        }
    }

    @Override
    protected void backPropagateError(double nextLayerErrorGradientSum) {
        // As this is the first layer and can't backpropagate anymore, stops backpropagation by doing nothing.
    }
    
}
