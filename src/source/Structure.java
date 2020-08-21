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
    private Bar longitudinalBar;
    private ArrayList<Bar> crossBars;
    private TreeMap<Double, ArrayList<ArrayList<Quadrant>>> temperatureMeshes;  //K = time, V = matrix of quadrants

    public Structure() {
        super();
        this.crossBars = new ArrayList<>();
        longitudinalBar = new Bar();
        crossBars =  new ArrayList<>();
        temperatureMeshes = new TreeMap<>();
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
        this.longitudinalBar = new Bar();
        this.crossBars = new ArrayList<>();
        this.temperatureMeshes = new TreeMap<>();
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

    public Bar getLongitudinalBar() {
        return longitudinalBar;
    }

    public void setLongitudinalBar(Bar longitudinalBar) {
        this.longitudinalBar = longitudinalBar;
    }

    public ArrayList<Bar> getCrossBars() {
        return crossBars;
    }

    public void setCrossBars(ArrayList<Bar> crossBars) {
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
}
