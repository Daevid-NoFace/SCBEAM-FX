package file_management;

import auxiliar_source.GeneralVariables;
import com.opencsv.CSVReader;
import source.Quadrant;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.TreeMap;

public class FileReading {

    public static TreeMap<Double, ArrayList<Integer>> readFile (File sourceFile) { //only reading from .csv, I need to implements for .xls and .xlsx files
        TreeMap<Double, ArrayList<Integer>> readInput = new TreeMap<>();

        try {
            CSVReader reader = new CSVReader(new InputStreamReader(new FileInputStream(sourceFile.toString()), StandardCharsets.ISO_8859_1));
            String[] nextLine;
            reader.readNext();  //I don't care the first line

            while ((nextLine = reader.readNext()) != null) {
                double time = Double.parseDouble(nextLine[0]);
                ArrayList<Integer> temperaturesList = new ArrayList<>();

                for (int i = 1; i < nextLine.length; i++) {
                    temperaturesList.add(Integer.parseInt(nextLine[i]));
                    readInput.put(time, temperaturesList);
                }
            }
        } catch (Exception e) {
            System.out.println("Reading File Error");
            e.getMessage();
            e.printStackTrace();
        }

        return readInput;
    }

    public static boolean checkTime (double time) { //check time every 5 minutes up to 1 hour
        boolean check = false;

        if (time == GeneralVariables.t0 || time == GeneralVariables.t5 || time == GeneralVariables.t10 ||
                time == GeneralVariables.t15 || time == GeneralVariables.t20 || time == GeneralVariables.t25||
                time == GeneralVariables.t30 || time == GeneralVariables.t35 || time == GeneralVariables.t40 ||
                time == GeneralVariables.t45 || time == GeneralVariables.t60) {
            check = true;
        } else {
            check = false;
        }

        return check;
    }
}
