package nl.fontys.ml.layer;

import java.util.Map;
import nl.fontys.ml.NeuralNetwork;
import nl.fontys.ml.neuron.Neuron;
import nl.fontys.ml.neuron.Node;

/**
 *
 * @author Dominik Kaisers <d.kaisers@student.fontys.nl>
 */
public class OutputLayer extends Layer {

    public OutputLayer(Layer previousLayer, int numberOfClasses) {
        super(previousLayer, false, numberOfClasses);
    }

    private OutputLayer(Layer previousLayer, boolean createBiasNode) {
        super(previousLayer, createBiasNode);
    }

    protected OutputLayer() {
        super();
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
     * @param learningRate learningRate.
     */
    public void backPropagateError(Double[] expectedOutput, double learningRate) {
        for (int i = 0; i < this.nodes.size(); i++) {
            Neuron node = (Neuron) this.nodes.get(i);

            double output = node.getOutput();
            double error = expectedOutput[i] - output;
            double errorGradient = error * output * (1 - output);

            for (Map.Entry<Node, Double> weight : node.getInputLayer().entrySet()) {
                double newWeight = weight.getValue() + (learningRate * errorGradient * weight.getKey().getOutput());
                node.getInputLayer().put(weight.getKey(), newWeight);

                double gradientSum = previousLayer.errGradientSum.containsKey(weight.getKey()) ? previousLayer.errGradientSum.get(weight.getKey()) : 0;
                gradientSum += (newWeight * errorGradient);
                previousLayer.errGradientSum.put(weight.getKey(), gradientSum);
            }
        }
        
        previousLayer.backPropagateError(learningRate);
    }

}
