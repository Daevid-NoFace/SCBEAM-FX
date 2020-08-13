package source;

public class Node {

    private int number;
    private double temperature;

    public Node() {
    }

    public Node(int number) {
        this.number = number;
    }

    public Node(int number, double temperature) {
        this.number = number;
        this.temperature = temperature;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }
}
