
package nl.fontys.ml;

/**
 * Representing a single instance of data that is trained and validated on.
 *
 * @author Dominik Kaisers <d.kaisers@student.fontys.nl>
 */
public class Instance {
    
    private final Double[] inputData;
    private final Double[] outputData;

    public Instance(Double[] inputData, Double[] outputData) {
        this.inputData = inputData;
        this.outputData = outputData;
    }

    public Double[] getInputData() {
        return inputData;
    }

    public Double[] getOutputData() {
        return outputData;
    }
    
}
