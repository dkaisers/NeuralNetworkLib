package nl.fontys.ml.neuron;

import java.util.HashMap;

/**
 * Input neurons are placed only in the input layer and provide the starting
 * values to calculate the output. Starting values are gained from the
 * attributes of a test case and can be set before calculating the outcome
 * from a OutputNode.
 *
 * @author Dominik Kaisers <d.kaisers@student.fontys.nl>
 */
public class InputNode extends Node {

    private double inputValue;

    public InputNode() {
        this(0f);
    }

    public InputNode(double inputValue) {
        this.inputValue = inputValue;
    }

    public double getInputValue() {
        return inputValue;
    }

    public void setInputValue(double inputValue) {
        this.inputValue = inputValue;
    }

    /**
     * Simply returns the InputNeurons input value, as these neurons do not
     * calculate anything.
     *
     * @return Input value.
     */
    @Override
    public double getOutput() {
        return this.inputValue;
    }

    @Override
    public void clearCache() {

    }

}
