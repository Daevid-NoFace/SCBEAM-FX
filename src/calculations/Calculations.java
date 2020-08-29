package calculations;

import source.Bar;
import source.Quadrant;
import source.Structure;

import java.util.*;

public class Calculations {

    public static TreeMap<Double, ArrayList<Double>> cuttingMethod(Structure structure, char methodType) {
        TreeMap<Double, ArrayList<Double>> timeVsResults = new TreeMap<>();
        ArrayList<Double> results = null;

        double d = structure.getHeight() - calculateDFs(structure);

        TreeMap<Double, ArrayList<ArrayList<Quadrant>>> temperatureMeshes = structure.getTemperatureMeshes();

        for(Map.Entry<Double, ArrayList<ArrayList<Quadrant>>> entry : temperatureMeshes.entrySet()) {
            double key = entry.getKey();
            System.out.println("Time = " + key);
            results = new ArrayList<>();

            if (key == 0.0000 && methodType == 'p') {
                structure.setOriginalWidth();

                double Vc = VcCalculation(structure, key, d, methodType);
                results.add(Vc);

                double Vfv = VfvCalculation(structure, key, d, methodType);
                results.add(Vfv);

                results.add(Vc + Vfv);
            } else if (key != 0.0000 && methodType == 'p') {
                structure.setNewWidth(key);
                structure.setEffectiveHeight(calculateEffectiveHeight(structure, d));

                double Vc = VcCalculation(structure, key, d, methodType);
                results.add(Vc);

                double Vfv = VfvCalculation(structure, key, d, methodType);
                results.add(Vfv);

                results.add(Vc + Vfv);
                structure.setOriginalWidth();
            }

            timeVsResults.put(key, results);
        }

        return timeVsResults;
    }

    private static double VcCalculation(Structure structure, double time, double d, char methodType) {

        double Rof = structure.getTotalCrossbarsArea() / (structure.getWidth() * d);

        double EprimaC = 4700 * Math.sqrt(structure.getCompressiveStrengthOfConcrete());

        double ke = structure.KeCalculation(time, methodType);

        double Nf = (50000 * ke) / EprimaC;

        double Ks = Math.sqrt(Math.pow(Nf * Rof, 2) + 2 * Nf * Rof) - Nf * Rof;

        return 0.040 * Ks * Math.sqrt(structure.getCompressiveStrengthOfConcrete()) * structure.getWidth() * d;
    }

    private static double VfvCalculation(Structure structure, double time, double d, char methodType) {
        double Vfv = -1;

        double Afv = 2 * Math.PI * Math.pow(structure.getLongitudinalBar().getDiameter() / 2, 2);

        double ffb = (0.05 * 3 + 0.3) * structure.getLongitudinalBar().getCurrentTensileStrength() * structure.KfCalculation(time, methodType);

        double ffv = -1;

        if (ffb <= structure.getLongitudinalBar().getCurrentTensileStrength()) {
            ffv = 0.004 * structure.getLongitudinalBar().getTensileModulusOfElasticity() * structure.KeCalculation(time, methodType);

            if (ffv <= ffb) {
                Vfv = ((Afv * ffv * d) / structure.getFenceSpacing()  * (Math.sin(1.5708) + Math.cos(1.5708))) / 10;
            } else {
                Vfv = ((Afv * ffb * d) / structure.getFenceSpacing()  * (Math.sin(1.5708) + Math.cos(1.5708))) / 10;
            }
        }

        return Vfv;
    }

    private static double calculateDFs(Structure structure) {
        ArrayList<Double> dfs = new ArrayList<>();

        for (Bar crossBar: structure.getCrossBars()) {
            double d = structure.getCovering() + structure.getLongitudinalBar().getDiameter() + crossBar.getDiameter() / 2;

            dfs.add(d);
        }

        double get = 0;

        for (double d: dfs)
            get += d;

        return get / structure.getCrossBars().size();
    }

    private static double calculateEffectiveHeight(Structure structure, double d) {
        double height = -1;

        double Rof = structure.getTotalCrossbarsArea() / (structure.getWidth() * d);

        double EprimaC = 4700 * Math.sqrt(structure.getCompressiveStrengthOfConcrete());

        double Ke = structure.KeCalculation(0.0000, 'p');

        double Nf = (50000 * Ke) / EprimaC;

        double Ks = Math.sqrt(Math.pow(Nf * Rof, 2) + 2 * Nf * Rof) - Nf * Rof;

        double height1 = 2.5 * (structure.getHeight() - (structure.getHeight() - calculateDFs(structure)));

        double height2 = (structure.getHeight() - Ks * (structure.getHeight() - calculateDFs(structure))) / 3;

        double height3 = structure.getHeight() / 2;

        height = height1;

        if (height > height2)
            height = height2;
        if (height > height3)
            height = height3;

        return Math.round(height);
    }
}
