package util;

import weka.core.converters.ConverterUtils.DataSource;
import weka.core.Instances;
import weka.core.converters.ArffSaver;

import java.io.*;

public class DataIO {
    public static void
    writeCSV(String fname, String[] results) throws IOException {
        try (
            PrintWriter writer = new PrintWriter(fname, "UTF-8");
        ) {
            writer.println("Id,Category");
            for (Integer i = 1; i <= results.length; i++) {
                writer.println(i.toString() + "," + (results[i - 1]));
                if (i % 1000 == 0) {
                    System.out.println("Written " + i + " instances");
                }
            }
            writer.flush();
        }
    }

    public static Instances readArff(String fname) throws Exception {
        System.out.println("Loading data file... " + fname);
        Instances data = DataSource.read(fname);
        System.out.println("Loaded data file... " + fname);
        return data;
    }

    public static void writeArff(String fname, Instances data)
    throws Exception {
        System.out.println("Writing data to " + fname);
        ArffSaver saver = new ArffSaver();
        saver.setInstances(data);
        saver.setFile(new File(fname));
        saver.writeBatch();
        System.out.println("Finished writing to " + fname);
    }
}
