package nl.fontys.ml;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import nl.fontys.ml.layer.InputLayer;
import nl.fontys.ml.layer.Layer;
import nl.fontys.ml.layer.OutputLayer;

/**
 * Façade class for a neural network. A number of attributes can be
 * configurated in order for the network to learn and work properly. These
 * attribues include:
 * 
 * - n: the learning rate
 * - layers: the number of hidden layers in the network
 * - nodes: the number of nodes per hidden layer (excluding the bias)
 * - classes: number of possible classes (e.g. output neurons)
 * - input nodes: number of input nodes, e.g. array length or input data
 * - networks: previous networks with error accuracy
 *
 * @author Dominik Kaisers <d.kaisers@student.fontys.nl>
 */
public class NeuralNetwork {
    
    private int iteration; 
    private NeuralNetwork previousIteration;
    private NeuralNetwork nextIteration;
    private double accuracy;
    
    private double learningRate;
    private final int numberOfClasses;
    private final int numberOfInputNodes;
    private final int numberOfHiddenLayers;
    private final int numberOfNodesPerLayer;
    
    private InputLayer inputLayer;
    private OutputLayer outputLayer;

    public NeuralNetwork(int numberOfClasses, int numberOfInputNodes, int numberOfHiddenLayers, int numberOfNodesPerLayer) {
        this.numberOfClasses = numberOfClasses;
        this.numberOfInputNodes = numberOfInputNodes;
        this.numberOfHiddenLayers = numberOfHiddenLayers;
        this.numberOfNodesPerLayer = numberOfNodesPerLayer;

        this.inputLayer = new InputLayer(numberOfInputNodes);

        Layer lastLayer = inputLayer;
        for (int i = 0; i < numberOfHiddenLayers; i++) {
            Layer hiddenLayer = new Layer(lastLayer, true, numberOfNodesPerLayer);
            lastLayer = hiddenLayer;
        }

        this.outputLayer = new OutputLayer(lastLayer, numberOfClasses);
    }
    
    public NeuralNetwork getBestIteration() {
        return null;
    }
    
    /**
     * Trains the network a given number of times and returns the last iteration.
     * 
     * @param trainingData The data to train on.
     * @param numberOfIterations Number of times to train.
     * @return Newly trained neural network.
     */
    public NeuralNetwork trainNetwork(List<Instance> trainingData, int numberOfIterations) {
        // TODO Redo with recursion.
        
        NeuralNetwork newNetwork = NeuralNetwork.deepCopy(this);
        newNetwork.previousIteration = this;
        newNetwork.iteration = iteration++;
        this.nextIteration = newNetwork;
        
        for (int i = 0; i < numberOfIterations; i++) {
            for (Instance instance : trainingData) {
                newNetwork.inputLayer.setInputData(instance.getInputData());
                Double[] output = newNetwork.outputLayer.getOutput();
                
                if (!Arrays.equals(output, instance.getOutputData()))
                    newNetwork.outputLayer.backPropagateError(instance.getOutputData(), learningRate);
            }
        }
        
        return newNetwork;
    }
    
    /**
     * Trains the network against a validation dataset until a given number of times the resulting network is worse than
     * the one it was created from. After that, the iteration with the best accuracy is returned.
     * 
     * @param trainingData The data to train on.
     * @param validationData The data to validate on.
     * @param stopAfter Number of times the network gets worse, after which the training is stopped.
     * @return Newly trained NeuralNetwork.
     */
    public NeuralNetwork trainNetwork(List<Instance> trainingData, List<Instance> validationData, int stopAfter) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }
    
    /**
     * Get the calculated output for the given input data.
     * 
     * @param inputData Input data of the same dimension as number of input nodes.
     * @return Output data of the same dimension as the number of classes.
     */
    public Double[] getOutput(Double[] inputData) {
        this.inputLayer.setInputData(inputData);
        return this.outputLayer.getOutput();
    }
    
    public static NeuralNetwork deepCopy(NeuralNetwork network) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }
    
}
