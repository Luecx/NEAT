package neat;

import data_structures.RandomHashSet;
import genome.ConnectionGene;
import genome.Genome;
import genome.NodeGene;

import java.util.HashMap;

public class Neat {

    public static final int MAX_NODES = (int)Math.pow(2,20);


    private double C1 = 1;
    private double C2 = 1;
    private double C3 = 1;

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
        Neat neat = new Neat(2,1,100);

        NodeGene in1 = neat.getNode(1);
        NodeGene in2 = neat.getNode(2);
        NodeGene out1 = neat.getNode(3);

        ConnectionGene con11 = neat.getConnection(in1, out1);
        ConnectionGene con12 = neat.getConnection(in2, out1);

        System.out.println(con11.getInnovation_number());
        System.out.println(con12.getInnovation_number());


        ConnectionGene con11_2 = neat.getConnection(in1, out1);
        con11_2.setWeight(3);

        System.out.println(con11_2.getWeight());


        //Genome g = neat.empty_genome();
        //System.out.println(g.getNodes().size());

    }


    public int getOutput_size() {
        return output_size;
    }

    public int getInput_size() {
        return input_size;
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
}
