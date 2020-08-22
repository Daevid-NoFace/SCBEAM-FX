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
    public boolean fitsBarInLitter(Bar newCrossBar) {
        boolean lit = false;

        //Sh es la diferencia minima entre barras por camada
        double Sh = (this.width - 2 * covering - 2 * newCrossBar.getDiameter()
                - ((crossBars.size() + 1) * newCrossBar.getDiameter())) / (crossBars.size());

        if (Sh >= 2) {
            lit = true;
        } else {
            lit = false;
            this.numberOfLitters++;
        }

        return lit;
    }

    public void setOriginalWidth() {
        setWidth(this.widthDifference + this.width);
        this.widthDifference = 0;
    }

    public void setNewWidth(double indexTime) {
        double numberOfQuadrantsAcross = (this.width / GeneralVariables.net);
        int counter = 0;

        ArrayList<ArrayList<Quadrant>> mesh = this.temperatureMeshes.get((double) indexTime);

        int midpointOfHeight = (int) (this.height / 2 / GeneralVariables.net);

        for (int i = 0; i < numberOfQuadrantsAcross; i++)
            if (mesh.get(midpointOfHeight).get(i).calculateTemperature() >= 500)
                counter++;

        setWidth((numberOfQuadrantsAcross - counter * 2) * GeneralVariables.net);
        setWidthDifference(counter);
    }

    public double getTotalCrossbarsArea() {
        double totalArea = 0;

        for (Bar crossBar: crossBars)
            totalArea += Math.pow(crossBar.getDiameter() / 2, 2) * Math.PI;

        return totalArea;
    }

    public double KeCalculation(double indexTime, char methodType) {
        double Ke = -1;
        double averageTemperature = -1;

        if (methodType == 'a') {
            System.out.println("Average method");
        } else {
            averageTemperature = temperatureMeshes.get((double) indexTime).get((int) (this.effectiveHeight / GeneralVariables.net)).get((int) (this.covering / GeneralVariables.net)).calculateTemperature();
        }

        if (averageTemperature >= 0 && averageTemperature <= 100)
            Ke = 1;
        else if (averageTemperature > 100 && averageTemperature <= 300)
            Ke = (1.25 - 0.0025 * averageTemperature);
        else if (averageTemperature > 300 && averageTemperature <= 400)
            Ke = (2.0 - 0.005 * averageTemperature);
        else if (averageTemperature > 400)
            Ke = 0;

        return Ke;
    }

    public double KfCalculation(double indexTime, char methodType) {
        double Kf = -1;
        double averageTemperature = -1;

        if (methodType == 'a') {
            System.out.println("Average method");
        } else {
            averageTemperature = temperatureMeshes.get((double) indexTime).get((int) (this.effectiveHeight / GeneralVariables.net)).get((int) (this.covering / GeneralVariables.net)).calculateTemperature();
        }

        if (averageTemperature >= 0 && averageTemperature <= 400)
            Kf = (1 - 0.0025 * averageTemperature);
        else
            Kf = 0;

        return Kf;
    }
}
