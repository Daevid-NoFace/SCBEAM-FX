package source;

import auxiliar_source.EFSlab;
import auxiliar_source.GeneralVariables;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeMap;

public class Structure {

    private String id;
    private double width;
    private double height;
    private double widthDifference;
    private double effectiveHeight;
    private short numberOfLitters;
    private double covering;
    private double compressiveStrengthOfConcrete;
    private double fenceSpacing;
    private LongitudinalBar longitudinalBar;
    private ArrayList<CrossBar> crossBars;
    private TreeMap<Double, ArrayList<ArrayList<Quadrant>>> temperatureMeshes;  //K = time, V = matrix of quadrants

    public Structure() {
        super();
        this.crossBars = new ArrayList<>();
    }

    public Structure(String id, double width, double height) {
        super();
        this.id = id;
        this.width = width;
        this.height = height;
        this.effectiveHeight = 0;
        this.widthDifference = 0;
        this.numberOfLitters = 0;
        this.fenceSpacing = 0;
        this.covering = 0;
        this.compressiveStrengthOfConcrete = 0;
        this.crossBars = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWidthDifference() {
        return widthDifference;
    }

    public void setWidthDifference(double widthDifference) {
        this.widthDifference = widthDifference;
    }

    public double getEffectiveHeight() {
        return effectiveHeight;
    }

    public void setEffectiveHeight(double effectiveHeight) {
        this.effectiveHeight = effectiveHeight;
    }

    public short getNumberOfLitters() {
        return numberOfLitters;
    }

    public void setNumberOfLitters(short numberOfLitters) {
        this.numberOfLitters = numberOfLitters;
    }

    public double getCovering() {
        return covering;
    }

    public void setCovering(double covering) {
        this.covering = covering;
    }

    public double getCompressiveStrengthOfConcrete() {
        return compressiveStrengthOfConcrete;
    }

    public void setCompressiveStrengthOfConcrete(double compressiveStrengthOfConcrete) {
        this.compressiveStrengthOfConcrete = compressiveStrengthOfConcrete;
    }

    public double getFenceSpacing() {
        return fenceSpacing;
    }

    public void setFenceSpacing(double fenceSpacing) {
        this.fenceSpacing = fenceSpacing;
    }

    public LongitudinalBar getLongitudinalBar() {
        return longitudinalBar;
    }

    public void setLongitudinalBar(LongitudinalBar longitudinalBar) {
        this.longitudinalBar = longitudinalBar;
    }

    public ArrayList<CrossBar> getCrossBars() {
        return crossBars;
    }

    public void setCrossBars(ArrayList<CrossBar> crossBars) {
        this.crossBars = crossBars;
    }

    public TreeMap<Double, ArrayList<ArrayList<Quadrant>>> getTemperatureMeshes() {
        return temperatureMeshes;
    }

    public void setTemperatureMeshes(TreeMap<Double, ArrayList<ArrayList<Quadrant>>> temperatureMeshes) {
        this.temperatureMeshes = temperatureMeshes;
    }

    public void setTemperatureMeshesAt(double index, ArrayList<ArrayList<Quadrant>> mesh) {
        this.temperatureMeshes.put(index, mesh);
    }

    //**************others methods**************\\

    public boolean buildMeshes (TreeMap<Double, ArrayList<Integer>> fileReading) {
        boolean build = false;
        ArrayList<ArrayList<Quadrant>> rowsOfMesh;
        ArrayList<Quadrant> columnsOfMesh;
        Iterator<Double> iterator = fileReading.keySet().iterator();

        while (iterator.hasNext()) {

            rowsOfMesh = new ArrayList<>();
            double key = iterator.next();
            int counter = 0;

            while (counter <= getHeight() / GeneralVariables.net - 1) {
                columnsOfMesh = new ArrayList<>();

                for (int i = 1; i <= getWidth() / GeneralVariables.net; i++)
                    columnsOfMesh.add(L_I(i, counter, key, fileReading.get(key)));

                counter++;
                rowsOfMesh.add(columnsOfMesh);
            }

            setTemperatureMeshesAt(key, rowsOfMesh);

            if (!iterator.hasNext())
                build = true;
        }

        return build;
    }

    private Quadrant L_I (int i, int counter, double key, ArrayList<Integer> temperatures) {
        ArrayList<Node> nodos = new ArrayList<>();

        final int p1 = EFSlab.F11(i, counter + 1, (int) (getWidth() / GeneralVariables.net)) + counter;
        Node node1 = new Node(p1, temperatures.get((int) p1 - 1));
        final int p2 = EFSlab.F12(i, counter + 1, (int) (getWidth() / GeneralVariables.net)) + counter;
        Node node2 = new Node(p2, temperatures.get((int) p2 - 1));
        final int p3 = EFSlab.F13(i, counter + 1, (int) (getWidth() / GeneralVariables.net)) + counter;
        Node node3 = new Node(p3, temperatures.get((int) p3 - 1));
        final int p4 = EFSlab.F14(i, counter + 1, (int) (getWidth() / GeneralVariables.net)) + counter;
        Node node4 = new Node(p4, temperatures.get((int) p4 - 1));
        final double prom = (node1.getTemperature() + node2.getTemperature() + node3.getTemperature() + node4.getTemperature()) / 4;

        nodos.add(node1);
        nodos.add(node2);
        nodos.add(node3);
        nodos.add(node4);

        return new Quadrant(nodos, prom);
    }
}
