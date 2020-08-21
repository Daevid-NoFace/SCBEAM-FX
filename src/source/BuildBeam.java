package source;

import auxiliar_source.EFSlab;
import auxiliar_source.GeneralVariables;
import javafx.concurrent.Task;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeMap;

public class BuildBeam extends Thread {


    private Structure structure;
    private TreeMap<Double, ArrayList<Integer>> fileReading;
    int count = 0;

    public TreeMap<Double, ArrayList<Integer>> getFileReading() {
        return fileReading;
    }

    public void setFileReading(TreeMap<Double, ArrayList<Integer>> fileReading) {
        this.fileReading = fileReading;
    }

    public BuildBeam(Structure structure, TreeMap<Double, ArrayList<Integer>> fileReading) {
        this.structure = structure;
        this.fileReading = fileReading;
    }

    public BuildBeam() {
    }

    public boolean buildMeshes () {

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

            while (counter <= structure.getHeight() / GeneralVariables.net - 1) {
                columnsOfMesh = new ArrayList<>();
                count++;
                for (int i = 1; i <= structure.getWidth() / GeneralVariables.net; i++) {
                    columnsOfMesh.add(L_I(i, counter, fileReading.get(key)));
                    count++;
                }
                counter++;
                rowsOfMesh.add(columnsOfMesh);
            }

            structure.setTemperatureMeshesAt(key, rowsOfMesh);

            if (!iterator.hasNext())
                build = true;
        }

        //Controller.getSingletonController().getMeshStructures().add(structure);
        System.out.println("Iteraciones => " + count);
        return build;
    }

    private Quadrant L_I (int i, int counter, ArrayList<Integer> temperatures) {

        final int p1 = EFSlab.F11(i, counter + 1, (int) (structure.getWidth() / GeneralVariables.net)) + counter;
        final int p2 = EFSlab.F12(i, counter + 1, (int) (structure.getWidth() / GeneralVariables.net)) + counter;
        final int p3 = EFSlab.F13(i, counter + 1, (int) (structure.getWidth() / GeneralVariables.net)) + counter;
        final int p4 = EFSlab.F14(i, counter + 1, (int) (structure.getWidth() / GeneralVariables.net)) + counter;

        ArrayList<Node> nodes = new ArrayList<>();
        nodes.add(new Node(p1, temperatures.get((int) p1 - 1)));
        nodes.add(new Node(p2, temperatures.get((int) p2 - 1)));
        nodes.add(new Node(p3, temperatures.get((int) p3 - 1)));
        nodes.add(new Node(p4, temperatures.get((int) p4 - 1)));

        return new Quadrant(nodes);
    }

    @Override
    public void run() {
        buildMeshes();
    }
}
