package source;

import java.util.ArrayList;
import java.util.TreeMap;

public class Bar {

    private double diameter;
    private double tensileStrength;
    private double tensileModulusOfElasticity;
    private char exposureType;
    private char fiberType;
    private TreeMap<Double, ArrayList<Quadrant>> time_quadrantList_TreeMap;

    public Bar() {
        time_quadrantList_TreeMap = new TreeMap<>();
    }

    public Bar(double diameter, double tensileStrength, double tensileModulusOfElasticity, char exposureType, char fiberType) {
        this.diameter = diameter;
        this.tensileStrength = tensileStrength;
        this.tensileModulusOfElasticity = tensileModulusOfElasticity;
        this.exposureType = exposureType;
        this.fiberType = fiberType;
        time_quadrantList_TreeMap = new TreeMap<>();
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

    public char getExposureType() {
        return exposureType;
    }

    public void setExposureType(char exposureType) {
        this.exposureType = exposureType;
    }

    public char getFiberType() {
        return fiberType;
    }

    public void setFiberType(char fiberType) {
        this.fiberType = fiberType;
    }

    public TreeMap<Double, ArrayList<Quadrant>> getTime_quadrantList_TreeMap() {
        return time_quadrantList_TreeMap;
    }

    public void setTime_quadrantList_TreeMap(TreeMap<Double, ArrayList<Quadrant>> time_quadrantList_TreeMap) {
        this.time_quadrantList_TreeMap = time_quadrantList_TreeMap;
    }
}
