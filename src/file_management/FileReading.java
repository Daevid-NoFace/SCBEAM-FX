package file_management;

import auxiliar_source.GeneralVariables;
import com.opencsv.CSVReader;
import javafx.concurrent.Task;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.TreeMap;

public class FileReading extends Task<TreeMap<Double, ArrayList<Integer>>> {

    private File sourceFile;
    public static Thread thread = new Thread();
    int count = 0;

    public FileReading(File sourceFile) {
        this.sourceFile = sourceFile;
    }

    public TreeMap<Double, ArrayList<Integer>> readFile () { //only reading from .csv, I need to implements for .xls and .xlsx files
        TreeMap<Double, ArrayList<Integer>> readInput = new TreeMap<>();
        System.out.println("current thread into file reading method => " + Thread.currentThread().getName());
        thread.setName(Thread.currentThread().getName());
        System.out.println("thread name => " + thread.getName() + " status => " + thread.isAlive());
        try {
            CSVReader reader = new CSVReader(new InputStreamReader(new FileInputStream(sourceFile.toString()), StandardCharsets.ISO_8859_1));
            String[] nextLine;
            reader.readNext();  //I don't care the first line

            while ((nextLine = reader.readNext()) != null) {
                double time = Double.parseDouble(nextLine[0]);
                ArrayList<Integer> temperaturesList = new ArrayList<>();
                count++;

                for (int i = 1; i < nextLine.length; i++) {
                    temperaturesList.add(Integer.parseInt(nextLine[i]));
                    readInput.put(time, temperaturesList);
                    count++;
                }
            }
            reader.close();
        } catch (Exception e) {
            System.out.println("Reading File Error");
            e.getMessage();
            e.printStackTrace();
        }
        System.out.println("Iteraciones => " + count);
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

    public static ArrayList<String> readTxt(String link) throws IOException {
        ArrayList<String> list = new ArrayList<>();
        System.out.println(link);

        BufferedReader input = new BufferedReader(new FileReader(link));

        try {
            String line = null;
            while (( line = input.readLine()) != null)
                list.add(line);
        } catch (IOException e) {
            System.err.println("Error, file " + link + " didn't exist.");
            e.printStackTrace();
        } finally {
            input.close();
        }

        return list;
    }

    @Override
    protected TreeMap<Double, ArrayList<Integer>> call() throws Exception {

        updateMessage("Procesando... Hilo => " + Thread.currentThread().getName());
        TreeMap<Double, ArrayList<Integer>> treeMap = readFile();
        updateMessage("Terminado... Hilo => " + Thread.currentThread().getName());
        updateProgress(100, 100);
        return treeMap;
    }
}
