package calculations;

import java.util.ArrayList;

public class Node implements Comparable<Node> {

    private double x;
    private double output;
    private ArrayList<Connection> connections = new ArrayList<>();

    public Node(double x) {
        this.x = x;
    }

    public void calculate() {
        double s = 0;
        for(Connection c:connections){
            if(c.isEnabled()){
                s += c.getWeight() * c.getFrom().getOutput();
            }
        }
        output = activation_function(s);
    }

    private double activation_function(double x){
        return 1d / (1 + Math.exp(-x));
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setOutput(double output) {
        this.output = output;
    }

    public void setConnections(ArrayList<Connection> connections) {
        this.connections = connections;
    }

    public double getX() {
        return x;
    }

    public double getOutput() {
        return output;
    }

    public ArrayList<Connection> getConnections() {
        return connections;
    }


    @Override
    public int compareTo(Node o) {
        if(this.x > o.x) return -1;
        if(this.x < o.x) return 1;
        return 0;
    }
}
