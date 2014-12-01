import preprocess.CIELab;

import weka.core.converters.ConverterUtils.DataSource;
import weka.core.Instances;
import weka.core.Instance;
import java.util.Enumeration;
import weka.classifiers.trees.RandomForest;
import java.io.PrintWriter;

public class MS1RF {
    private static final String help =
        "Usage: java MS1Main <data-filename> <train-filename>";
    private static final String outputFname = "RFResults.csv";

    public static void main(String[] args) {
        Instances data;
        Instances test;
        String dataFileName = args[0];
        String testFileName = args[1];
        try {
            System.out.println("Loading data file... " + dataFileName);
            data = DataSource.read(dataFileName);
            System.out.println("Loaded data file... " + dataFileName);
        } catch (Exception e) {
            e.printStackTrace(System.out);
            System.out.println("Could not load data file");
            return;
        }
        RandomForest model = new RandomForest();
        System.out.println("Training model...");
        data.setClassIndex(data.numAttributes() - 1);
        try {
            model.buildClassifier(data);
        } catch (Exception e) {
            e.printStackTrace(System.out);
            System.out.println("Could not train model");
            return;
        }
        System.out.println("Trained model...");
        try {
            System.out.println("Loading testing file... " + testFileName);
            test = DataSource.read(testFileName);
            System.out.println("Loaded testing file... " + testFileName);
        } catch (Exception e) {
            e.printStackTrace(System.out);
            System.out.println("Could not load data file");
            return;
        }
        int numTestInstances = test.numInstances();
        test.setClassIndex(test.numAttributes() - 1);
        String[] results = new String[numTestInstances];
        System.out.println("Testing model...");
        for (int i = 0; i < numTestInstances; i++) {
            Double cl;
            try {
                cl = model.classifyInstance(test.instance(i));
            } catch (Exception e) {
                e.printStackTrace(System.out);
                System.out.println("Could not classify instance " + i);
                return;
            }
            Integer clint = cl.intValue();
            results[i] = clint.toString();
            if (i % 500 == 0) {
                System.out.println("Tested " + i + " instances");
            }
        }
        System.out.println("Writing test results...");
        try (
            PrintWriter writer = new PrintWriter(outputFname, "UTF-8");
        ) {
            writer.println("Id,Category");
            for (Integer i = 1; i <= numTestInstances; i++) {
                writer.println(i.toString() + "," + results[i - 1]);
                if (i % 500 == 0) {
                    System.out.println("Written " + i + " instances");
                }
            }
            writer.flush();
        } catch (Exception e) {
            e.printStackTrace(System.out);
            System.out.println("Could not open output file");
            return;
        }
        System.out.println("Classification done!");
    }
}
