package api;

import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.LinkedList;

class DW_GraphDSTest {





    @Test
    void getNode() {

    }

    @Test
    void getEdge() {
        directed_weighted_graph graph =new DW_GraphDS();



    }

    @Test
    void addNode() {
        directed_weighted_graph graph =new DW_GraphDS();
        node_data n0=new NodeData();
        node_data n1=new NodeData();
        node_data n2=new NodeData();

        graph.addNode(n0);
        graph.addNode(n1);
        graph.addNode(n2);
        Collection<node_data> coll= new LinkedList();
        coll=graph.getV();
        for (node_data n:coll) {
            System.out.println("node key: "+n.getKey());
            System.out.println("Location: "+"x:"+n.getLocation().x()+"  y:"+n.getLocation().y()+"  z:"+n.getLocation().z());

        }

    }

    @Test
    void connect() {
    }

    @Test
    void getV() {
    }

    @Test
    void getE() {
    }

    @Test
    void removeNode() {
    }

    @Test
    void removeEdge() {
    }

    @Test
    void nodeSize() {
    }

    @Test
    void edgeSize() {
    }

    @Test
    void getMC() {
    }

    @Test
    void hasEdge() {
    }
}