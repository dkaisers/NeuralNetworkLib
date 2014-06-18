package nl.fontys.ml;

import java.io.*;
import java.util.ArrayList;
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
 * <p/>
 * - n: the learning rate
 * - layers: the number of hidden layers in the network
 * - nodes: the number of nodes per hidden layer (excluding the bias)
 * - classes: number of possible classes (e.g. output neurons)
 * - input nodes: number of input nodes, e.g. array length or input data
 * - networks: previous networks with error accuracy
 *
 * @author Dominik Kaisers <d.kaisers@student.fontys.nl>
 */
public class NeuralNetwork implements Serializable {

    private int iteration;

    private double learningRate;
    private final int numberOfClasses;
    private final int numberOfInputNodes;
    private final int numberOfHiddenLayers;
    private final int numberOfNodesPerLayer;

    private InputLayer inputLayer;
    private OutputLayer outputLayer;

    public NeuralNetwork(double learningRate, int numberOfClasses, int numberOfInputNodes, int numberOfHiddenLayers, int numberOfNodesPerLayer) {
        // Set data
        this.learningRate = learningRate;
        this.numberOfClasses = numberOfClasses;
        this.numberOfInputNodes = numberOfInputNodes;
        this.numberOfHiddenLayers = numberOfHiddenLayers;
        this.numberOfNodesPerLayer = numberOfNodesPerLayer;

        // Create input layer
        this.inputLayer = new InputLayer(numberOfInputNodes);

        // Create hidden layers and set references
        Layer lastLayer = inputLayer;
        for (int i = 0; i < numberOfHiddenLayers; i++) {
            Layer hiddenLayer = new Layer(lastLayer, true, numberOfNodesPerLayer);
            lastLayer = hiddenLayer;
        }

        // Create output layer
        this.outputLayer = new OutputLayer(lastLayer, numberOfClasses);
    }

    public NeuralNetwork getBestIteration() {
        return null;
    }

    /**
     * Trains the network a given number of times and returns the last iteration.
     *
     * @param trainingData       The data to train on.
     * @param numberOfIterations Number of times to train.
     * @return Newly trained neural network.
     */
    public NeuralNetwork trainNetwork(List<Instance> trainingData, int numberOfIterations) {
        // Copy network

        NeuralNetwork newNetwork = deepCopy();
        newNetwork.iteration = iteration++;

        // Train copied network
        for (int i = 0; i < numberOfIterations; i++) {
            // Go through every instance
            for (Instance instance : trainingData) {

                // Set input data for instance
                newNetwork.inputLayer.setInputData(instance.getInputData());
                Double[] output = newNetwork.outputLayer.getOutput();

                // Back propagate error if classification was wrong
                if (!Arrays.equals(output, instance.getOutputData())) {
                    newNetwork.outputLayer.backPropagateError(instance.getOutputData(), learningRate);
                }
            }
        }

        // Return new network
        return newNetwork;
    }

    /**
     * Trains the network against a validation dataset until a given number of times the resulting network is worse than
     * the one it was created from. After that, the iteration with the best accuracy is returned.
     *
     * @param trainingData       The data to train on.
     * @param classification     The data to validate on.
     * @param numberOfIterations Number of times to train.
     * @return Newly trained NeuralNetwork.
     */
    public NeuralNetwork trainNetwork(Double[][] trainingData, Double[][] classification, int numberOfIterations) {
        List<Instance> trainingList = new ArrayList<>(trainingData.length);
        for (int i = 0; i < trainingData.length; i++) {
            Double[] d = trainingData[i];
            trainingList.add(new Instance(d, classification[i]));
        }
        return trainNetwork(trainingList, numberOfIterations);
    }


    /**
     * Trains the network against a validation dataset until a given number of times the resulting network is worse than
     * the one it was created from. After that, the iteration with the best accuracy is returned.
     *
     * @param trainingData   The data to train on.
     * @param validationData The data to validate on.
     * @param stopAfter      Number of times the network gets worse, after which the training is stopped.
     * @return Newly trained NeuralNetwork.
     */
    public NeuralNetwork trainNetwork(List<Instance> trainingData, List<Instance> validationData, int stopAfter) {
        int worseAttempts = 0;
        NeuralNetwork best = this;
        double bestAccuracy = 1d;
        NeuralNetwork current = this;

        // Stop when the network did not get more accurate after stopAfter continuous attempts
        while (worseAttempts < stopAfter) {
            // Train next iterations
            current = current.trainNetwork(trainingData, 1);

            // Validate
            double currentAccuracy = current.validate(validationData);

            // Check if network got better or worse
            if (currentAccuracy >= bestAccuracy)
                worseAttempts++;
            else {
                best = current;
                bestAccuracy = currentAccuracy;
                worseAttempts = 0;
            }
        }

        // Return best network
        return best;
    }

    /**
     * Test the neural network against validation data.
     *
     * @param validationData The data to validate on.
     * @return Accuracy between 0 and 1.
     */
    public double validate(List<Instance> validationData) {
        double errorCount = 0;

        // Iterate through validation instances
        for (Instance instance : validationData) {
            Double[] output = outputLayer.getOutput();

            // Increment error count if the instance was wrongly classified
            if (!Arrays.equals(output, instance.getOutputData())) {
                errorCount++;
            }
        }

        // Return accuracy
        return errorCount / validationData.size();
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

    /**
     * Deep copy a given neural network.
     *
     * @return Copied neural network.
     */
    public NeuralNetwork deepCopy() {
        NeuralNetwork newNetwork = new NeuralNetwork(this.learningRate, this.numberOfClasses, this.numberOfInputNodes, this.numberOfHiddenLayers, this.numberOfNodesPerLayer);
        newNetwork.iteration = this.iteration;
        newNetwork.inputLayer = (InputLayer) inputLayer.deepCopy();
        newNetwork.outputLayer = (OutputLayer) outputLayer.deepCopy();
        return newNetwork;
    }

    public int getIteration() {
        return iteration;
    }

    public double getLearningRate() {
        return learningRate;
    }

    public int getNumberOfClasses() {
        return numberOfClasses;
    }

    public int getNumberOfInputNodes() {
        return numberOfInputNodes;
    }

    public int getNumberOfHiddenLayers() {
        return numberOfHiddenLayers;
    }

    public int getNumberOfNodesPerLayer() {
        return numberOfNodesPerLayer;
    }

    /**
     * Serialize this network and save it to a file at the given path.
     *
     * @param filepath Filepath.
     */
    public void saveToFile(String filepath) {
        try {
            FileOutputStream fos = new FileOutputStream(filepath);
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            oos.writeObject(this);

            oos.close();
            fos.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Load a serialized neural network from file.
     *
     * @param filepath Filepath to serialized network.
     * @return NeuralNetwork or null if error.
     */
    public static NeuralNetwork loadFromFile(String filepath) {
        NeuralNetwork nn = null;

        try {
            FileInputStream fis = new FileInputStream(filepath);
            ObjectInputStream ois = new ObjectInputStream(fis);

            nn = (NeuralNetwork) ois.readObject();

            ois.close();
            fis.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }

        return nn;
    }
}
