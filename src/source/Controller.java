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
    public boolean buildMeshes (int index, TreeMap<Double, ArrayList<Integer>> fileReading) {
        Structure currentStructure = nonMeshedStructures.get(index);
        boolean build = false;
        ArrayList<ArrayList<Quadrant>> rowsOfMesh;
        ArrayList<Quadrant> columnsOfMesh;
        Iterator<Double> iterator = fileReading.keySet().iterator();
        System.out.println("current thread in build mesh => " + Thread.currentThread().getName());
        while (iterator.hasNext()) {

            rowsOfMesh = new ArrayList<>();
            double key = iterator.next();
            int counter = 0;

            while (counter <= currentStructure.getHeight() / GeneralVariables.net - 1) {
                columnsOfMesh = new ArrayList<>();

                for (int i = 1; i <= currentStructure.getWidth() / GeneralVariables.net; i++)
                    columnsOfMesh.add(L_I(currentStructure, i, counter, key, fileReading.get(key)));

                counter++;
                rowsOfMesh.add(columnsOfMesh);
            }

            currentStructure.setTemperatureMeshesAt(key, rowsOfMesh);

            if (!iterator.hasNext())
                build = true;
        }

        return build;
    }

    private Quadrant L_I (Structure currentStructure, int i, int counter, double key, ArrayList<Integer> temperatures) {
        ArrayList<Node> nodos = new ArrayList<>();

        final int p1 = EFSlab.F11(i, counter + 1, (int) (currentStructure.getWidth() / GeneralVariables.net)) + counter;
        Node node1 = new Node(p1, temperatures.get((int) p1 - 1));
        final int p2 = EFSlab.F12(i, counter + 1, (int) (currentStructure.getWidth() / GeneralVariables.net)) + counter;
        Node node2 = new Node(p2, temperatures.get((int) p2 - 1));
        final int p3 = EFSlab.F13(i, counter + 1, (int) (currentStructure.getWidth() / GeneralVariables.net)) + counter;
        Node node3 = new Node(p3, temperatures.get((int) p3 - 1));
        final int p4 = EFSlab.F14(i, counter + 1, (int) (currentStructure.getWidth() / GeneralVariables.net)) + counter;
        Node node4 = new Node(p4, temperatures.get((int) p4 - 1));
        final double prom = (node1.getTemperature() + node2.getTemperature() + node3.getTemperature() + node4.getTemperature()) / 4;

        nodos.add(node1);
        nodos.add(node2);
        nodos.add(node3);
        nodos.add(node4);

        return new Quadrant(nodos, prom);
    }
}
