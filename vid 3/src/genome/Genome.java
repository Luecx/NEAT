package genome;

import data_structures.RandomHashSet;
import neat.Neat;

import java.util.Random;

public class Genome {

    private RandomHashSet<ConnectionGene> connections = new RandomHashSet<>();
    private RandomHashSet<NodeGene> nodes = new RandomHashSet<>();

    private Neat neat;

    public Genome(Neat neat) {
        this.neat = neat;
    }


    public double distance(Genome g2){

        Genome g1 = this;


        int last_innovation_g1 = g1.getConnections().size() == 0 ? 0:
                g1.getConnections().get(g1.getConnections().size() - 1).getInnovation_number();

        int last_innovation_g2 = g2.getConnections().size() == 0 ? 0:
                g2.getConnections().get(g2.getConnections().size() - 1).getInnovation_number();

        if(last_innovation_g1 < last_innovation_g2){
            Genome g = g1;
            g1 = g2;
            g2 = g;
        }

        int index_g1 = 0;
        int index_g2 = 0;

        int excess = 0;
        int disjoint = 0;
        int weight_diff = 0;

        while(index_g1 < g1.getConnections().size() && index_g2 < g2.getConnections().size()){

            ConnectionGene con_1 = g1.getConnections().get(index_g1);
            ConnectionGene con_2 = g2.getConnections().get(index_g2);

            int id_1 = con_1.getInnovation_number();
            int id_2 = con_2.getInnovation_number();

            if(id_1 < id_2){
                index_g1 ++;
                disjoint ++;
            }else if(id_1 > id_2){
                index_g2 ++;
                disjoint++;
            }else{
                weight_diff += Math.abs(con_1.getWeight() - con_2.getWeight());
                index_g1 ++;
                index_g2 ++;
            }
        }

        if(index_g1 == g1.getConnections().size()){
            excess = g2.getConnections().size()-index_g2;
        }else{
            excess = g1.getConnections().size() -index_g1;
        }

        double N = Math.max(g1.getConnections().size(), g2.getConnections().size());
        N = N < 20 ? 1: N;

        return neat.getC1() * excess / N + neat.getC2() * disjoint / N + neat.getC3() * weight_diff;
    }

    public static Genome crossOver(Genome g1, Genome g2){
        Genome g = g1.getNeat().empty_genome();

        int index_g1 = 0;
        int index_g2 = 0;

        while(index_g1 < g1.getConnections().size() && index_g2 < g2.getConnections().size()){

            ConnectionGene con_1 = g1.getConnections().get(index_g1);
            ConnectionGene con_2 = g2.getConnections().get(index_g2);

            int id_1 = con_1.getInnovation_number();
            int id_2 = con_2.getInnovation_number();

            if(id_1 < id_2){
                g.getConnections().add(Neat.getConnection(con_1));
                index_g1 ++;
            }else if(id_1 > id_2){
                g.getConnections().add(Neat.getConnection(con_2));
                index_g2 ++;
            }else{
                if(Math.random() > 0.5){
                    g.getConnections().add(Neat.getConnection(con_1));
                }else{
                    g.getConnections().add(Neat.getConnection(con_2));
                }
                index_g1 ++;
                index_g2 ++;
            }
        }

        while(index_g1 < g1.getConnections().size()){
            g.getConnections().add(g1.getConnections().get(index_g1));
            index_g1 ++;
        }

        for(ConnectionGene c:g.getConnections().getData()){
            g.getNodes().add(c.getFrom());
            g.getNodes().add(c.getTo());
        }

        return g;
    }

    public void mutate(){

    }





    public RandomHashSet<ConnectionGene> getConnections() {
        return connections;
    }

    public RandomHashSet<NodeGene> getNodes() {
        return nodes;
    }

    public Neat getNeat() {
        return neat;
    }
}
