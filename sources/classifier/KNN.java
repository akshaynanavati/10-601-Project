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
        /* Train on a random 1/4 of the data */
        data.randomize(
            data.getRandomNumberGenerator(System.currentTimeMillis()));
        Instances train =
            new Instances(data, 0, data.numInstances() / 4);
        this.model = new LinearNNSearch(train);
        System.out.println(
            "Using " + train.numInstances() + " instances");
    }

    public double classifyInstance(Instance test) throws Exception {
        Instances knn = model.kNearestNeighbours(test, this.k);
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
