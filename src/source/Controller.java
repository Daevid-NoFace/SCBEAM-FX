package source;

import auxiliar_source.EFSlab;
import auxiliar_source.GeneralVariables;
import javafx.concurrent.Task;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeMap;

public class Controller {

    private static Controller singletonController;

    //file lists
    private ArrayList<File> unprocessedFiles;
    private ArrayList<File> processedFiles;

    //beam lists
    private ArrayList<Structure> nonMeshedStructures;
    private ArrayList<Structure> meshStructures;

    private TreeMap<File, TreeMap<Double, ArrayList<Integer>>> timeTemperaturesTreeMap; //K: file processed

    /**
     * Class constructor
     */
    public Controller() {
        unprocessedFiles = new ArrayList<>();
        timeTemperaturesTreeMap = new TreeMap<>();
        processedFiles = new ArrayList<>();
        nonMeshedStructures = new ArrayList<>();
        meshStructures = new ArrayList<>();
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

    public TreeMap<File, TreeMap<Double, ArrayList<Integer>>> getTimeTemperaturesTreeMap() {
        return timeTemperaturesTreeMap;
    }

    public void setTimeTemperaturesTreeMap(TreeMap<File, TreeMap<Double, ArrayList<Integer>>> timeTemperaturesTreeMap) {
        this.timeTemperaturesTreeMap = timeTemperaturesTreeMap;
    }

    public ArrayList<File> getProcessedFiles() {
        return processedFiles;
    }

    public void setProcessedFiles(ArrayList<File> processedFiles) {
        this.processedFiles = processedFiles;
    }

    public ArrayList<String> getNamesOfProcessedFiles() {

        ArrayList<String> namesList = new ArrayList<>();

        for (File file: processedFiles)
            namesList.add(file.getName());

        return namesList;
    }

    public ArrayList<Structure> getNonMeshedStructures() {
        return nonMeshedStructures;
    }

    public void setNonMeshedStructures(ArrayList<Structure> nonMeshedStructures) {
        this.nonMeshedStructures = nonMeshedStructures;
    }

    public ArrayList<Structure> getMeshStructures() {
        return meshStructures;
    }

    public void setMeshStructures(ArrayList<Structure> meshStructures) {
        this.meshStructures = meshStructures;
    }

    public ArrayList<String> getNamesOfNonMeshedStructures() {

        ArrayList<String> namesList = new ArrayList<>();

        for (Structure structure: nonMeshedStructures)
            namesList.add(structure.getId());

        return namesList;
    }

    public ArrayList<String> getNamesOfMeshedStructures() {

        ArrayList<String> namesList = new ArrayList<>();

        for (Structure structure: meshStructures)
            namesList.add(structure.getId());

        return namesList;
    }

    //OTHER METHODS
    
}
