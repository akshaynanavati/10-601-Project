package preprocess;

import java.awt.color.ColorSpace;
import java.util.ArrayList;

import weka.core.Instances;
import weka.core.Instance;
import weka.core.Attribute;

/**
 * This code was modified from:
 * http://stackoverflow.com/questions/4593469/java-how-to-convert-rgb-color-to-cie-lab
 */
public class CIELab extends ColorSpace {

    private Instance transformInstance(Instance d) {
        int numAttr = d.numAttributes();
        int n = numAttr - 1;
        if (n % 3 != 0) {
            System.out.println(
                "Number of attributes must be divisible by 3 " +
                "but only " + n + " attributes found"
            );
            System.exit(-1);
        }
        int m = n / 3;
        Instance newInst = new Instance(numAttr);
        for (int i = 0; i < m; i++) {
            float[] rgb = {
                (float) d.value(i),
                (float) d.value(i + m),
                (float) d.value(i + m * 2)
            };
            float[] lab = this.fromRGB(rgb);
            newInst.setValue(i, (double) lab[0]);
            newInst.setValue(i + m, (double) lab[1]);
            newInst.setValue(i + m * 2, (double) lab[2]);
        }
        newInst.setValue(numAttr - 1, d.classValue());
        if (newInst.numAttributes() != d.numAttributes()) {
            System.out.println(
                "Number of transformed attributes = " +
                newInst.numAttributes() + " but original data had " +
                d.numAttributes() + " attributes"
            );
            System.exit(-1);
        }
        return newInst;
    }

    public Instances transformInstances(Instances data) {
        int n = data.numInstances();
        System.out.println(
            "Trasnforming instances with " + n + " instances"
        );
        Instances newInsts =
            new Instances(data, n);
        for (int i = 0; i < n; i++) {
            Instance d = data.instance(i);
            d.setDataset(data);
            Instance trans = this.transformInstance(d);
            newInsts.add(trans);
            if (i % 1000 == 999) {
                System.out.println("Transformed " + (i + 1) + " instances");
            }
        }
        newInsts.setClassIndex(newInsts.numAttributes() - 1);
        System.out.println(
            "Transformed instances resulting in " + newInsts.numInstances()
        );
        return newInsts;
    }

    public static CIELab getInstance() {
        return Holder.INSTANCE;
    }

    public float[] fromCIEXYZ(float[] colorvalue) {
        double l = f(colorvalue[1]);
        double L = 116.0 * l - 16.0;
        double a = 500.0 * (f(colorvalue[0]) - l);
        double b = 200.0 * (l - f(colorvalue[2]));
        return new float[] {(float) L, (float) a, (float) b};
    }

    public float[] fromRGB(float[] rgbvalue) {
        float[] xyz = CIEXYZ.fromRGB(rgbvalue);
        return fromCIEXYZ(xyz);
    }

    public float getMaxValue(int component) {
        return 128f;
    }

    public float getMinValue(int component) {
        return (component == 0)? 0f: -128f;
    }

    public String getName(int idx) {
        return String.valueOf("Lab".charAt(idx));
    }

    public float[] toCIEXYZ(float[] colorvalue) {
        double i = (colorvalue[0] + 16.0) * (1.0 / 116.0);
        double X = fInv(i + colorvalue[1] * (1.0 / 500.0));
        double Y = fInv(i);
        double Z = fInv(i - colorvalue[2] * (1.0 / 200.0));
        return new float[] {(float) X, (float) Y, (float) Z};
    }

    public float[] toRGB(float[] colorvalue) {
        float[] xyz = toCIEXYZ(colorvalue);
        return CIEXYZ.toRGB(xyz);
    }

    CIELab() {
        super(ColorSpace.TYPE_Lab, 3);
    }

    private static double f(double x) {
        if (x > 216.0 / 24389.0) {
            return Math.cbrt(x);
        } else {
            return (841.0 / 108.0) * x + N;
        }
    }

    private static double fInv(double x) {
        if (x > 6.0 / 29.0) {
            return x*x*x;
        } else {
            return (108.0 / 841.0) * (x - N);
        }
    }

    private Object readResolve() {
        return getInstance();
    }

    private static class Holder {
        static final CIELab INSTANCE = new CIELab();
    }

    private static final long serialVersionUID = 5027741380892134289L;

    private static final ColorSpace CIEXYZ =
        ColorSpace.getInstance(ColorSpace.CS_CIEXYZ);

    private static final double N = 4.0 / 29.0;

}
