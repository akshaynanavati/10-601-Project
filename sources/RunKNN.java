import weka.core.Instances;

import classifier.KNN;
import preprocess.CIELab;
import util.DataIO;
import util.ClassifyInstances;

public class RunKNN {
    private static final String help =
        "Usage: java MS1Main <data-filename> <train-filename>";
    private static final String outputFname = "results/KNNResults";

    public static void main(String[] args) throws Exception {
        Instances data;
        Instances dataNoL;
        Instances test;
        Instances testNoL;
        String dataFileName = args[0];
        String testFileName = args[1];
        CIELab cielab = CIELab.getInstance();

        data = DataIO.readArff(dataFileName);
        KNN model = new KNN(5);
        KNN modelNoL = new KNN(5);

        System.out.println("Training model...");
        int n = data.numAttributes() - 1;
        data.setClassIndex(n);
        // Transform to LAB
        data = cielab.transformInstances(data);

        // Create a dataset without L component
        dataNoL = cielab.removeComponent(data, "L");

        model.buildClassifier(data);
        modelNoL.buildClassifier(dataNoL);
        System.out.println("Trained model...");

        test = DataIO.readArff(testFileName);
        test.setClassIndex(test.numAttributes() - 1);
        test = cielab.transformInstances(test);
        testNoL = cielab.removeComponent(test, "L");
        String[] results;
        String[] resultsNoL;

        System.out.println("Testing model...");
        results = ClassifyInstances.classify(model, test);
        resultsNoL = ClassifyInstances.classify(modelNoL, test);

        System.out.println("Writing test results...");
        DataIO.writeCSV(outputFname + ".csv", results);
        DataIO.writeCSV(outputFname + "NoL.csv", resultsNoL);
        System.out.println("Classification done!");
    }
}
