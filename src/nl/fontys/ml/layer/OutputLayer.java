package nl.fontys.ml.layer;

import java.util.List;

/**
 *
 * @author Dominik Kaisers <d.kaisers@student.fontys.nl>
 */
public class OutputLayer extends Layer {

    public OutputLayer(Layer previousLayer) {
        this(previousLayer, false);
    }

    private OutputLayer(Layer previousLayer, boolean createBiasNode) {
        super(previousLayer, createBiasNode);
    }

    public Double[] getOutput() {
        Double[] output = new Double[this.nodes.size()];

        for (int i = 0; i < this.nodes.size(); i++) {
            output[i] = this.nodes.get(i).getOutput();
        }

        return output;
    }

    /**
     * Called by the network on the output layer to start the back propagation. After execution, the back propagation on
     * the previous layer is called.
     *
     * @param expectedOutput Expected output.
     */
    public void backPropagateError(Double[] expectedOutput) {
        double errorGradientSum = 0;
        
        // TODO: Back propagation
        
        previousLayer.backPropagateError(errorGradientSum);
    }

}
