package source;

import java.io.File;
import java.util.ArrayList;
import java.util.TreeMap;

public class Controller {

    private static Controller singletonController;

    private ArrayList<File> unprocessedFiles;
    private ArrayList<File> processedFiles;
    private TreeMap<Double, ArrayList<Integer>> timeTemperaturesTreeMap;

    /**
     * Class constructor
     */
    public Controller() {
        unprocessedFiles = new ArrayList<>();
        timeTemperaturesTreeMap = new TreeMap<>();
        processedFiles = new ArrayList<>();
    }

    /**
     * Singleton pattern
     *
     * @return Controller
     */
    public static Controller getSingletonController() {
        if (singletonController == null)
            singletonController = new Controller();

        return singletonController;
    }

    public ArrayList<File> getUnprocessedFiles() {
        return unprocessedFiles;
    }

    public void setUnprocessedFiles(ArrayList<File> unprocessedFiles) {
        this.unprocessedFiles = unprocessedFiles;
    }

    public TreeMap<Double, ArrayList<Integer>> getTimeTemperaturesTreeMap() {
        return timeTemperaturesTreeMap;
    }

    public void setTimeTemperaturesTreeMap(TreeMap<Double, ArrayList<Integer>> timeTemperaturesTreeMap) {
        this.timeTemperaturesTreeMap = timeTemperaturesTreeMap;
    }

    public ArrayList<File> getProcessedFiles() {
        return processedFiles;
    }

    public void setProcessedFiles(ArrayList<File> processedFiles) {
        this.processedFiles = processedFiles;
    }


}
