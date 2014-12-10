import weka.core.Instances;
import weka.clusterers.SimpleKMeans;

import classifier.KNN;
import preprocess.CIELab;
import util.DataIO;
import util.ClassifyInstances;

public class RunKNN {
    private static final String help =
        "Usage: java MS1Main <data-filename> <train-filename> <results=filename>";

    public static void main(String[] args) throws Exception {
        if (args.length != 3) {
            System.out.println(RunKNN.help);
            return;
        }
        Instances data;
        Instances test;
        String dataFileName = args[0];
        String testFileName = args[1];
        String outputFname = args[1];
        CIELab cielab = CIELab.getInstance();

        data = DataIO.readArff(dataFileName);
        KNN model = new KNN(5);

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
