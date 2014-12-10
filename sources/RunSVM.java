import weka.classifiers.functions.LibSVM;
import weka.core.Instances;

import util.DataIO;
import util.ClassifyInstances;

public class RunSVM {
    private LibSVM model;
    private static final String help =
        "Usage: java RunSVM <data-filename> <train-filename> <results-filename>";

    public RunSVM(String[] options, Instances data)
    throws Exception {
        this.model = new LibSVM();
        this.model.setOptions(options);
        data.setClassIndex(data.numAttributes() - 1);
        System.out.println("Building classifier");
        this.model.buildClassifier(data);
        System.out.println("Built classifier");
    }

    public static void main(String[] args)
    throws Exception {
        if (args.length != 3) {
            System.out.println(RunSVM.help);
            return;
        }
        Instances data;
        Instances test;
        String dataFileName = args[0];
        String testFileName = args[1];
        String outputFname = args[2];

        data = DataIO.readArff(dataFileName);
        String[] options = {
            "-K", "1",
            "-D", "16",
            "-R", "1.0",
            "-Z"
        };
        RunSVM svm = new RunSVM(options, data);

        test = DataIO.readArff(testFileName);
        test.setClassIndex(test.numAttributes() - 1);
        String[] results;

        System.out.println("Testing...");
        results = ClassifyInstances.classify(svm.model, test);
        DataIO.writeCSV(outputFname, results);
        System.out.println("Written results");
    }
}
