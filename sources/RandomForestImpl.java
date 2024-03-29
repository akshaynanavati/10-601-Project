package code;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Date;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.functions.MultilayerPerceptron;
import weka.classifiers.trees.RandomForest;
import weka.core.Instances;
import weka.core.Utils;

public class RandomForestImpl {

    public static BufferedReader readDataFile(String filename) {
        BufferedReader inputReader = null;

        try {
            inputReader = new BufferedReader(new FileReader(filename));
        } catch (FileNotFoundException ex) {
            System.err.println("File not found: " + filename);
        }

        return inputReader;
    }

    public static void p(Object s, long init) {
        System.out.print(s);
        System.out.println();
        System.out.print((new Date()).getTime() -init);
        System.out.println();
    }

    public static void main(String[] args) throws Exception {

        long init = (new Date()).getTime();



        BufferedReader trainfile = readDataFile("train.arff");
        BufferedReader testfile = readDataFile("test.arff");
        p("read all the data", init);

        Instances traindata = new Instances(trainfile);
        traindata.setClassIndex(traindata.numAttributes() - 1);
        Instances testdata = new Instances(testfile);
        testdata.setClassIndex(testdata.numAttributes() - 1);
        p("created all instances objects", init);

        //build the classifier
        Classifier model = new MultilayerPerceptron();
//        String[] options = Utils.splitOptions("-I 1 -K 0 -S 1");
//        model.setOptions(options);
        Evaluation validation = new Evaluation(traindata);
        model.buildClassifier(traindata);
        double[] predictions = validation.evaluateModel(model, testdata);
        FileWriter writer = new FileWriter("submission1.csv");
        writer.append("Id");
        writer.append(',');
        writer.append("Category");
        writer.append('\n');
        for (int i=0; i < predictions.length; i++) {
            Integer id = i+1;
            Integer category = ((int)predictions[i]) +1;
            writer.append(id.toString());
            writer.append(',');
            writer.append(category.toString());
            writer.append('\n');
        }
        writer.flush();
        writer.close();
        p("All done!", init);
    }



}
