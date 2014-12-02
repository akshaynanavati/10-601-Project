package util;

import weka.classifiers.Classifier;
import weka.core.Instances;
import weka.core.Instance;
import java.util.Iterator;
import java.util.HashMap;
import java.util.Map;

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

    public static double getMajorityClass(Instances data) throws Exception {
        HashMap<Integer, Integer> counter =
            new HashMap<Integer, Integer>();
        for (int i = 0; i < data.numInstances(); i++) {
            Instance d = data.instance(i);
            Double cvd = d.classValue();
            int cv = cvd.intValue();
            Integer v = counter.get(cv);
            if (v == null) {
                counter.put(cv, 1);
            } else {
                counter.put(cv, v + 1);
            }
        }
        Iterator<Map.Entry<Integer, Integer>> it =
            counter.entrySet().iterator();
        int maxClass = -1;
        int maxValue = -1;
        while (it.hasNext()) {
            Map.Entry<Integer, Integer> kv = it.next();
            int v = kv.getValue();
            if (v > maxValue) {
                maxValue = v;
                maxClass = kv.getKey();
            }
        }
        return (double) maxClass;
    }
}
