package file_management;

import auxiliar_source.GeneralVariables;
import source.Quadrant;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.TreeMap;

public class FileReading {

    private static StringTokenizer stringTokenizer;

    public static TreeMap<Double, ArrayList<Integer>> readFile (String sourceFolder) throws Exception { //only reading from .csv, I need to implements for .xls and .xlsx files
        TreeMap<Double, ArrayList<Integer>> readInput = new TreeMap<>();
        BufferedReader cin = new BufferedReader(new FileReader(sourceFolder));
        cin.readLine(); //jump de first line

        while(true) {
            stringTokenizer = new StringTokenizer(cin.readLine());
            String line = stringTokenizer.nextToken();
            String[] array_element = line.split(",");
            double currentTime = Double.parseDouble(array_element[0]);
            ArrayList<Integer> temperaturesList = new ArrayList<>();

            //if (checkTime(time)) {
            for (int i = 1; i < array_element.length; i++)
                temperaturesList.add(Integer.parseInt(array_element[i]));

            readInput.put(currentTime, temperaturesList);
            //}
            if (!cin.ready())
                break;
        }

        cin.close();    //close the file

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
