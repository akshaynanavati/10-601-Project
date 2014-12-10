import weka.classifiers.functions.LibSVM;

public class RunSVM {
    private LibSVM model;
    private static final String help =
        "Usage: java RunSVM <data-filename> <train-filename> <results-filename>"

    public RunSVM(String[] options, Instances data) {
        this.model = new LibSVM();
        this.model.setOptions(options);
        data.setClassIndex(data.numAttributes() - 1);
        System.out.println("Building classifier");
        this.model.buildClassifier(data);
        System.out.println("Built classifier");
    }

    public static void main(String[] args) {
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
        String[] options = {};
        RunSVM svm = new RunSVM(options, data);

        test = DataIO.readArff(testFileName);
        test.setClassIndex(test.numAttributes() - 1);
        String[] results;

        System.out.println("Testing...");
        results = ClassifyInstance.classify(model, test);
        DataIO.writeCSV(outputFname, results);
        System.out.println("Written results");
    }
}
