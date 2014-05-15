package nl.fontys.ml;

import java.util.List;
import java.util.Map;

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
    private float learningRate;
    private int numberOfHiddenLayers;
    private int numberOfNodesPerLayer;
    private int numberOfClasses;
    private int numberOfInputNodes;
    private Map<NeuralNetwork, Double> networkIterations;
    private List<Double[]> trainingData;
    
    public NeuralNetwork getBestIteration() {
        return null;
    }
    
    public void trainNetwork(int numberOfIterations) {
        
    }
    
    public void trainNetwork(List<Double[]> validationData, int stopAfter) {
        
    }
    
    public void trainNetwork() {
        
    }
    
    public Double[] getOutput(Double[] inputData) {
        return null;
    }
    
    public static NeuralNetwork deepCopy(NeuralNetwork network) {
        return null;
    }
    
}
