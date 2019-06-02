package neat;

import data_structures.RandomHashSet;
import genome.ConnectionGene;
import genome.Genome;
import genome.NodeGene;
import visual.Frame;

import java.util.HashMap;

public class Neat {

    public static final int MAX_NODES = (int)Math.pow(2,20);


    private double C1 = 1, C2 = 1, C3 = 1;

    private double WEIGHT_SHIFT_STRENGTH = 0.3;
    private double WEIGHT_RANDOM_STRENGTH = 1;

    private double PROBABILITY_MUTATE_LINK = 0.4;
    private double PROBABILITY_MUTATE_NODE = 0.4;
    private double PROBABILITY_MUTATE_WEIGHT_SHIFT = 0.4;
    private double PROBABILITY_MUTATE_WEIGHT_RANDOM= 0.4;
    private double PROBABILITY_MUTATE_TOGGLE_LINK = 0.4;

    private HashMap<ConnectionGene, ConnectionGene> all_connections = new HashMap<>();
    private RandomHashSet<NodeGene> all_nodes = new RandomHashSet<>();
    private int max_clients;
    private int output_size;
    private int input_size;

    public Neat(int input_size, int output_size, int clients){
        this.reset(input_size, output_size, clients);
    }

    public Genome empty_genome(){
        Genome g = new Genome(this);
        for(int i = 0; i < input_size + output_size; i++){
            g.getNodes().add(getNode(i + 1));
        }
        return g;
    }
    public void reset(int input_size, int output_size, int clients){
        this.input_size = input_size;
        this.output_size = output_size;
        this.max_clients = clients;

        all_connections.clear();
        all_nodes.clear();

        for(int i = 0;i < input_size; i++){
            NodeGene n = getNode();
            n.setX(0.1);
            n.setY((i + 1) / (double)(input_size + 1));
        }

        for(int i = 0; i < output_size; i++){
            NodeGene n = getNode();
            n.setX(0.9);
            n.setY((i + 1) / (double)(output_size + 1));
        }

    }

    public static ConnectionGene getConnection(ConnectionGene con){
        ConnectionGene c = new ConnectionGene(con.getFrom(), con.getTo());
        c.setWeight(con.getWeight());
        c.setEnabled(con.isEnabled());
        return c;
    }
    public ConnectionGene getConnection(NodeGene node1, NodeGene node2){
        ConnectionGene connectionGene = new ConnectionGene(node1, node2);

        if(all_connections.containsKey(connectionGene)){
            connectionGene.setInnovation_number(all_connections.get(connectionGene).getInnovation_number());
        }else{
            connectionGene.setInnovation_number(all_connections.size() + 1);
            all_connections.put(connectionGene, connectionGene);
        }

        return connectionGene;
    }

    public NodeGene getNode() {
        NodeGene n = new NodeGene(all_nodes.size() + 1);
        all_nodes.add(n);
        return n;
    }
    public NodeGene getNode(int id){
        if(id <= all_nodes.size()) return all_nodes.get(id - 1);
        return getNode();
    }

    public static void main(String[] args) {
        Neat neat = new Neat(2,2,0);
        new Frame(neat.empty_genome());
    }

    public double getC1() {
        return C1;
    }

    public double getC2() {
        return C2;
    }

    public double getC3() {
        return C3;
    }

    public double getWEIGHT_SHIFT_STRENGTH() {
        return WEIGHT_SHIFT_STRENGTH;
    }

    public double getWEIGHT_RANDOM_STRENGTH() {
        return WEIGHT_RANDOM_STRENGTH;
    }

    public double getPROBABILITY_MUTATE_LINK() {
        return PROBABILITY_MUTATE_LINK;
    }

    public double getPROBABILITY_MUTATE_NODE() {
        return PROBABILITY_MUTATE_NODE;
    }

    public double getPROBABILITY_MUTATE_WEIGHT_SHIFT() {
        return PROBABILITY_MUTATE_WEIGHT_SHIFT;
    }

    public double getPROBABILITY_MUTATE_WEIGHT_RANDOM() {
        return PROBABILITY_MUTATE_WEIGHT_RANDOM;
    }

    public double getPROBABILITY_MUTATE_TOGGLE_LINK() {
        return PROBABILITY_MUTATE_TOGGLE_LINK;
    }

    public int getOutput_size() {
        return output_size;
    }

    public int getInput_size() {
        return input_size;
    }


}
