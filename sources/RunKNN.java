import weka.core.Instances;
import weka.clusterers.SimpleKMeans;

import classifier.KNN;
import util.DataIO;
import util.ClassifyInstances;

public class RunKNN {
    private static final String help =
        "Usage: java MS1Main <data-filename> <train-filename> <results-filename> <k>";

    public static void main(String[] args) throws Exception {
        if (args.length != 4) {
            System.out.println(RunKNN.help);
            return;
        }
        Instances data;
        Instances test;
        String dataFileName = args[0];
        String testFileName = args[1];
        String outputFname = args[2];
        int k = Integer.parseInt(args[3]);

        data = DataIO.readArff(dataFileName);
        KNN model = new KNN(k);

        System.out.println("Training model...");
        int n = data.numAttributes() - 1;
        data.setClassIndex(n);
        model.buildClassifier(data);
        System.out.println("Trained model...");

        test = DataIO.readArff(testFileName);
        test.setClassIndex(test.numAttributes() - 1);
        String[] results;

        System.out.println("Testing model...");
        results = ClassifyInstances.classify(model, test);
        DataIO.writeCSV(outputFname, results);
        System.out.println("Classification done!");
    }
}
