import weka.core.Instances;

import classifier.KNN;
import preprocess.CIELab;
import util.DataIO;
import util.ClassifyInstances;

public class RunKNN {
    private static final String help =
        "Usage: java MS1Main <data-filename> <train-filename>";
    private static final String outputFname = "results/KNNResults.csv";

    public static void main(String[] args) {
        Instances data;
        Instances test;
        String dataFileName = args[0];
        String testFileName = args[1];
        CIELab cielab = CIELab.getInstance();
        try {
            data = DataIO.readArff(dataFileName);
        } catch (Exception e) {
            e.printStackTrace(System.out);
            System.out.println("Could not load data file");
            return;
        }
        KNN model = new KNN(5);
        System.out.println("Training model...");
        data.setClassIndex(data.numAttributes() - 1);
        data = cielab.transformInstances(data);
        try {
            model.buildClassifier(data);
        } catch (Exception e) {
            e.printStackTrace(System.out);
            System.out.println("Could not train model");
            return;
        }
        System.out.println("Trained model...");
        try {
            test = DataIO.readArff(testFileName);
        } catch (Exception e) {
            e.printStackTrace(System.out);
            System.out.println("Could not load data file");
            return;
        }
        test.setClassIndex(test.numAttributes() - 1);
        test = cielab.transformInstances(test);
        String[] results;
        System.out.println("Testing model...");
        try {
            results = ClassifyInstances.classify(model, test);
        } catch (Exception e) {
            e.printStackTrace(System.out);
            System.out.println("Error testing model");
            return;
        }
        System.out.println("Writing test results...");
        try {
            DataIO.writeCSV(outputFname, results);
        } catch (Exception e) {
            e.printStackTrace(System.out);
            System.out.println("Error writing data");
            return;
        }
        System.out.println("Classification done!");
    }
}
