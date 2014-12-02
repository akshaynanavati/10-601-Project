package classifier;

import util.ClassifyInstances;
import weka.core.neighboursearch.LinearNNSearch;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.Capabilities;
import weka.classifiers.Classifier;

public class KNN extends Classifier {
    private static final long serialVersionUID = 1;

    private int k;
    private LinearNNSearch model;

    public KNN(int k) {
        this.k = k;
    }

    public void buildClassifier(Instances data) throws Exception {
        /* Train on a random 1/3 of the data */
        data.randomize(
            data.getRandomNumberGenerator(System.currentTimeMillis()));
        Instances subset = data.trainCV(4, 0);
        this.model = new LinearNNSearch(subset);
        System.out.println(
            "Using " + subset.numInstances() + " instances");
    }

    public double classifyInstance(Instance d) throws Exception {
        Instances knn = model.kNearestNeighbours(d, this.k);
        return ClassifyInstances.getMajorityClass(knn);
    }

    public double[]
    distributionForInstance(Instance d) throws Exception {
        return null;
    }

    public Capabilities getCapabilities() {
        return null;
    }

}
