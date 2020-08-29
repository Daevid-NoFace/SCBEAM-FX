package source;

import java.util.ArrayList;

public class Quadrant {

    private ArrayList<Node> nodes;

    public Quadrant() {
        nodes = new ArrayList<>();
    }

    public Quadrant(ArrayList<Node> nodes) {
        this.nodes = nodes;
    }

    public ArrayList<Node> getNodes() {
        return nodes;
    }

    public Node getNodeAt(int index) {
        return nodes.get(index);
    }

    public void setNodesList(ArrayList<Node> nodes) {
        this.nodes = nodes;
    }

    public void addNode(Node node) {
        this.nodes.add(node);
    }

    //***************other methods***************\\

    /**
     *
     */
    public double calculateTemperature() {
        double summation = 0;

        for (Node node : nodes)
            summation += node.getTemperature();

        return summation / nodes.size();
    }
}
