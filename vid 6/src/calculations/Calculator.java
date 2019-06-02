package calculations;

import data_structures.RandomHashSet;
import genome.ConnectionGene;
import genome.Genome;
import genome.NodeGene;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

public class Calculator {

    private ArrayList<Node> input_nodes = new ArrayList<>();
    private ArrayList<Node> hidden_nodes = new ArrayList<>();
    private ArrayList<Node> output_nodes = new ArrayList<>();

    public Calculator(Genome g){
        RandomHashSet<NodeGene> nodes = g.getNodes();
        RandomHashSet<ConnectionGene> cons = g.getConnections();

        HashMap<Integer, Node> nodeHashMap = new HashMap<>();

        for(NodeGene n:nodes.getData()){
            Node node = new Node(n.getX());
            nodeHashMap.put(n.getInnovation_number(),node);

            if(n.getX() <= 0.1){
                input_nodes.add(node);
            }else if(n.getX() >= 0.9){
                output_nodes.add(node);
            }else{
                hidden_nodes.add(node);
            }
        }

        hidden_nodes.sort(new Comparator<Node>() {
            @Override
            public int compare(Node o1, Node o2) {
                return o1.compareTo(o2);
            }
        });

        for(ConnectionGene c:cons.getData()){
            NodeGene from = c.getFrom();
            NodeGene to = c.getTo();

            Node node_from = nodeHashMap.get(from.getInnovation_number());
            Node node_to = nodeHashMap.get(to.getInnovation_number());

            Connection con = new Connection(node_from, node_to);
            con.setWeight(c.getWeight());
            con.setEnabled(c.isEnabled());

            node_to.getConnections().add(con);
        }
    }

    public double[] calculate(double... input){
        if(input.length != input_nodes.size()) throw new RuntimeException("Data doesnt fit");
        for(int i = 0; i < input_nodes.size(); i++){
            input_nodes.get(i).setOutput(input[i]);
        }
        for(Node n:hidden_nodes){
            n.calculate();
        }

        double[] output = new double[output_nodes.size()];
        for(int i = 0; i < output_nodes.size(); i++){
            output_nodes.get(i).calculate();
            output[i] = output_nodes.get(i).getOutput();
        }
        return output;
    }

}
