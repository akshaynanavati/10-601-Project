import weka.core.Instances;
import weka.clusterers.SimpleKMeans;

import classifier.KNN;
import util.DataIO;
import util.ClassifyInstances;
import weka.core.converters.ArffSaver;
import java.io.File;

public class RunKNN {
    private static final String help =
        "Usage: java MS1Main <data-filename> <train-filename> <results-filename>";

    public static void main(String[] args) throws Exception {
        if (args.length != 3) {
            System.out.println(RunKNN.help);
            return;
        }
        Instances data;
        Instances test;
        Instances dataNoL;
        Instances testNoL;
        String dataFileName = args[0];
        String testFileName = args[1];
        String outputFname = args[2];

        data = DataIO.readArff(dataFileName);
        KNN model = new KNN(5);


        System.out.println("Training model...");
        int n = data.numAttributes() - 1;
        data.setClassIndex(n);
        model.buildClassifier(data);
        // Transform to LAB
        // data = cielab.transformInstances(data);

        // Create a dataset without L component
        // dataNoL = cielab.removeComponent(data, "L");

        System.out.println("Got data and dataNoL instances");

        // ArffSaver saver = new ArffSaver();
        // saver.setInstances(data);
        // saver.setFile(new File("./data.arff"));
        // saver.writeBatch();
        // System.out.println("Wrote data.arff");

        // ArffSaver saverNoL = new ArffSaver();
        // saverNoL.setInstances(dataNoL);
        // saverNoL.setFile(new File("./dataNoL.arff"));
        // saverNoL.writeBatch();
        // System.out.println("Wrote dataNoL.arff");




        model.buildClassifier(data);
        // modelNoL.buildClassifier(dataNoL);
        System.out.println("Trained model...");

        // test = DataIO.readArff(testFileName);
        test.setClassIndex(test.numAttributes() - 1);
        // test = cielab.transformInstances(test);
        // testNoL = cielab.removeComponent(test, "L");

        System.out.println("Got test and testNoL instances");

        // ArffSaver saverTest = new ArffSaver();
        // saverTest.setInstances(test);
        // saverTest.setFile(new File("./testData.arff"));
        // saverTest.writeBatch();
        // System.out.println("Wrote testData.arff");

        // ArffSaver saverTestNoL = new ArffSaver();
        // saverTestNoL.setInstances(dataNoL);
        // saverTestNoL.setFile(new File("./testDataNoL.arff"));
        // saverTestNoL.writeBatch();
        // System.out.println("Wrote testDataNoL.arff");


        String[] results;

        System.out.println("Testing model...");
        results = ClassifyInstances.classify(model, test);
        DataIO.writeCSV(outputFname, results);
        // resultsNoL = ClassifyInstances.classify(modelNoL, testNoL);

        System.out.println("Writing test results...");
        DataIO.writeCSV(outputFname + ".csv", results);
        // DataIO.writeCSV(outputFname + "NoL.csv", resultsNoL);
        System.out.println("Classification done!");
    }
}
