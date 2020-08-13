package source;

import java.util.ArrayList;
import java.util.TreeMap;

public class CrossBar {

    private double diameter;
    private double tensileStrength;
    private double tensileModulusOfElasticity;
    private TreeMap<Double, ArrayList<Quadrant>> time_quadrantList_TreeMap;

    public CrossBar() {
        this.time_quadrantList_TreeMap = new TreeMap<>();
    }

    public CrossBar(double diameter, double tensileStrength, double tensileModulusOfElasticity) {
        this.diameter = diameter;
        this.tensileStrength = tensileStrength;
        this.tensileModulusOfElasticity = tensileModulusOfElasticity;
        this.time_quadrantList_TreeMap = new TreeMap<>();
    }

    public double getDiameter() {
        return diameter;
    }

    public void setDiameter(double diameter) {
        this.diameter = diameter;
    }

    public double getTensileStrength() {
        return tensileStrength;
    }

    public void setTensileStrength(double tensileStrength) {
        this.tensileStrength = tensileStrength;
    }

    public double getTensileModulusOfElasticity() {
        return tensileModulusOfElasticity;
    }

    public void setTensileModulusOfElasticity(double tensileModulusOfElasticity) {
        this.tensileModulusOfElasticity = tensileModulusOfElasticity;
    }

    public TreeMap<Double, ArrayList<Quadrant>> getTime_quadrantList_TreeMap() {
        return time_quadrantList_TreeMap;
    }

    public ArrayList<Quadrant> getTime_quadrantList_TreeMapAt(int index) {
        return time_quadrantList_TreeMap.get((double) index);
    }

    public void setTime_quadrantList_TreeMap(TreeMap<Double, ArrayList<Quadrant>> time_quadrantList_TreeMap) {
        this.time_quadrantList_TreeMap = time_quadrantList_TreeMap;
    }
}
