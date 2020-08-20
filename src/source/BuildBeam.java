package source;

import auxiliar_source.EFSlab;
import auxiliar_source.GeneralVariables;
import javafx.concurrent.Task;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeMap;

public class BuildBeam extends Task<Boolean> {

    private static BuildBeam singletonBuildBeam;

    private int index;
    private TreeMap<Double, ArrayList<Integer>> fileReading;
    int count = 0;

    public static BuildBeam getSingletonController() {
        if (singletonBuildBeam == null)
            singletonBuildBeam = new BuildBeam();

        return singletonBuildBeam;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public TreeMap<Double, ArrayList<Integer>> getFileReading() {
        return fileReading;
    }

    public void setFileReading(TreeMap<Double, ArrayList<Integer>> fileReading) {
        this.fileReading = fileReading;
    }

    public BuildBeam(int index, TreeMap<Double, ArrayList<Integer>> fileReading) {
        this.index = index;
        this.fileReading = fileReading;
    }

    public BuildBeam() {
    }

    @Override
    protected Boolean call() throws Exception {
        updateMessage("Procesando... Hilo => " + Thread.currentThread().getName());
        boolean mesh = buildMeshes();
        updateMessage("Terminado... Hilo => " + Thread.currentThread().getName());
        updateProgress(100, 100);
        return mesh;
    }

    public boolean buildMeshes () {
        Structure currentStructure = Controller.getSingletonController().getNonMeshedStructures().get(index);
        boolean build = false;
        ArrayList<ArrayList<Quadrant>> rowsOfMesh;
        ArrayList<Quadrant> columnsOfMesh;
        Iterator<Double> iterator = fileReading.keySet().iterator();
        System.out.println("current thread in build mesh => " + Thread.currentThread().getName());
        while (iterator.hasNext()) {
            count++;
            rowsOfMesh = new ArrayList<>();
            double key = iterator.next();
            int counter = 0;

            while (counter <= currentStructure.getHeight() / GeneralVariables.net - 1) {
                columnsOfMesh = new ArrayList<>();
                count++;
                for (int i = 1; i <= currentStructure.getWidth() / GeneralVariables.net; i++) {
                    columnsOfMesh.add(L_I(currentStructure, i, counter, fileReading.get(key)));
                    count++;
                }
                counter++;
                rowsOfMesh.add(columnsOfMesh);
            }

            currentStructure.setTemperatureMeshesAt(key, rowsOfMesh);

            if (!iterator.hasNext())
                build = true;
        }

        Controller.getSingletonController().getMeshStructures().add(index, currentStructure);
        System.out.println("Iteraciones => " + count);
        return build;
    }

    private Quadrant L_I (Structure currentStructure, int i, int counter, ArrayList<Integer> temperatures) {
        ArrayList<Node> nodes = new ArrayList<>();

        final int p1 = EFSlab.F11(i, counter + 1, (int) (currentStructure.getWidth() / GeneralVariables.net)) + counter;
        Node node1 = new Node(p1, temperatures.get((int) p1 - 1));
        final int p2 = EFSlab.F12(i, counter + 1, (int) (currentStructure.getWidth() / GeneralVariables.net)) + counter;
        Node node2 = new Node(p2, temperatures.get((int) p2 - 1));
        final int p3 = EFSlab.F13(i, counter + 1, (int) (currentStructure.getWidth() / GeneralVariables.net)) + counter;
        Node node3 = new Node(p3, temperatures.get((int) p3 - 1));
        final int p4 = EFSlab.F14(i, counter + 1, (int) (currentStructure.getWidth() / GeneralVariables.net)) + counter;
        Node node4 = new Node(p4, temperatures.get((int) p4 - 1));
        final double prom = (node1.getTemperature() + node2.getTemperature() + node3.getTemperature() + node4.getTemperature()) / 4;

        nodes.add(node1);
        nodes.add(node2);
        nodes.add(node3);
        nodes.add(node4);

        return new Quadrant(nodes, prom);
    }
}
