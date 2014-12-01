package util;

import weka.classifiers.Classifier;
import weka.core.Instances;

public class ClassifyInstances {
    public static String[]
    classify(Classifier model, Instances data) throws Exception {
        int numInstances = data.numInstances();
        String[] results = new String[numInstances];
        for (int i = 0; i < numInstances; i++) {
            Double cl;
            cl = model.classifyInstance(data.instance(i));
            Integer clint = cl.intValue();
            results[i] = clint.toString();
            if (i % 1000 == 0) {
                System.out.println("Classified " + i + " instances");
            }
        }
        return results;
    }
}
