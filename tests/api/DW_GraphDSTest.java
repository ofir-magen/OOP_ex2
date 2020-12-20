package api;

import com.google.gson.internal.bind.util.ISO8601Utils;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Collection;
import java.util.LinkedList;

class DW_GraphDSTest {






    @Test
    void getEdge() {
        directed_weighted_graph graph1 =new DW_GraphDS();
        node_data n0=new NodeData(0);
        node_data n1=new NodeData(1);
        graph1.addNode(n0);
        graph1.addNode(n1);
        System.out.println("need to be null(0->1)"+graph1.getEdge(n0.getKey(),n1.getKey()));
        System.out.println("need to be null(1->0)"+graph1.getEdge(n1.getKey(),n0.getKey()));
        graph1.connect(n0.getKey(), n1.getKey(), 10);
        System.out.println("connect 0->1");
        System.out.println("need to be 20(0->1):rsc is- "+graph1.getEdge(n0.getKey(),n1.getKey()).getSrc()+" dest is- "+graph1.getEdge(n0.getKey(),n1.getKey()).getDest()+" the Weight is- "+graph1.getEdge(n0.getKey(),n1.getKey()).getWeight());
        System.out.println("need to be null(1->0)"+graph1.getEdge(n1.getKey(),n0.getKey()));
        graph1.connect(n1.getKey(), n0.getKey(), 20);
        System.out.println("connect 1->0");
        System.out.println("need to be 10(0->1):rsc is- "+graph1.getEdge(n0.getKey(),n1.getKey()).getSrc()+" dest is- "+graph1.getEdge(n0.getKey(),n1.getKey()).getDest()+" the Weight is- "+graph1.getEdge(n0.getKey(),n1.getKey()).getWeight());
        System.out.println("need to be 20(1->0):rsc is- "+graph1.getEdge(n1.getKey(),n0.getKey()).getSrc()+" dest is- "+graph1.getEdge(n1.getKey(),n0.getKey()).getDest()+" the Weight is- "+graph1.getEdge(n1.getKey(),n0.getKey()).getWeight());

    }

    @Test
    void addNode() {
        directed_weighted_graph graph =new DW_GraphDS();
        node_data n2=new NodeData(2);
        node_data n3=new NodeData(3);
        node_data n4=new NodeData(4);

        graph.addNode(n2);
        graph.addNode(n3);
        graph.addNode(n4);
        Collection<node_data> coll= new LinkedList();
        coll=graph.getV();
        for (node_data n:coll) {
            System.out.println("node key: "+n.getKey());
            System.out.println("Location: "+"x:"+n.getLocation().x()+"  y:"+n.getLocation().y()+"  z:"+n.getLocation().z());

        }

    }

    @Test
    void connect() {
        directed_weighted_graph graph2 =new DW_GraphDS();
        node_data n5=new NodeData(5);
        node_data n6=new NodeData(6);
        graph2.addNode(n5);
        graph2.addNode(n6);
        assertNull(graph2.getEdge(n5.getKey(),n6.getKey()),"need to be null(0->1)");
        assertNull(graph2.getEdge(n6.getKey(),n5.getKey()),"need to be null(1->0)");
        graph2.connect(n5.getKey(), n6.getKey(), 10);
        System.out.println("connect 0->1");
        assertEquals(10,graph2.getEdge(n5.getKey(),n6.getKey()).getWeight(),"need to be 10(0->1)");
        assertNull(graph2.getEdge(n6.getKey(),n5.getKey()),"need to be null(1->0)");

        graph2.connect(n6.getKey(), n5.getKey(), 20);
        System.out.println("connect 1->0");
        assertEquals(10,graph2.getEdge(n5.getKey(),n6.getKey()).getWeight(),"need to be 10(0->1)");
        System.out.println("need to be 20(1->0):rsc is- "+graph2.getEdge(n6.getKey(),n5.getKey()).getSrc()+" dest is- "+graph2.getEdge(n6.getKey(),n5.getKey()).getDest()+" the Weight is- "+graph2.getEdge(n6.getKey(),n5.getKey()).getWeight());
        assertEquals(20,graph2.getEdge(n6.getKey(),n5.getKey()).getWeight(),"need to be 20(1->0)");


    }

    @Test
    void getV() {
        directed_weighted_graph graph3 =new DW_GraphDS();
        assertEquals(0,graph3.getV().size(),"no nodse in the graph");

        node_data n0=new NodeData(0);
        node_data n1=new NodeData(1);
        node_data n2=new NodeData(2);
        node_data n3=new NodeData(3);
        graph3.addNode(n0);
        graph3.addNode(n1);
        graph3.addNode(n2);
        graph3.addNode(n3);
        assertEquals(4,graph3.getV().size(),"4 nodse in the graph");
        graph3.removeNode(0);
        graph3.removeNode(1);
        graph3.removeNode(3);
        assertEquals(1,graph3.getV().size(),"1 nodse in the graph");
        assertEquals(true,graph3.getV().contains(n2),"the node in the graph is 2");

    }

    @Test
    void getE() {
        directed_weighted_graph graph4 =new DW_GraphDS();
        node_data n0=new NodeData(0);
        node_data n1=new NodeData(1);
        node_data n2=new NodeData(2);
        node_data n3=new NodeData(3);
        graph4.addNode(n0);
        graph4.addNode(n1);
        graph4.addNode(n2);
        graph4.addNode(n3);
        assertEquals(0,graph4.getE(0).size(),"no connect at all");
        graph4.connect(0,1,10);
        graph4.connect(0,2,20);
        assertEquals(2,graph4.getE(0).size(),"connected to 2");
       int i=0;
        for (edge_data n:graph4.getE(0)) {
            if(i==0) {
                assertEquals(2, n.getDest(),"need to show node (2)");
            }
            if(i==1) {
                assertEquals(1, n.getDest(),"need to show node (1)");
            }
            i++;
        }
        assertEquals(false,graph4.getE(1).contains(n0),"1 not connect to 0");
        assertEquals(false,graph4.getE(2).contains(n0),"2 not connect to 0");


        graph4.removeEdge(0,1);
        assertEquals(1,graph4.getE(0).size(),"connected to 1");




    }

    @Test
    void removeNode() {
        directed_weighted_graph graph5 =new DW_GraphDS();
        node_data n0=new NodeData(0);
        node_data n1=new NodeData(1);
        graph5.addNode(n0);
        graph5.addNode(n1);
        assertEquals(true,graph5.getV().contains(n1),"is contains 1");
        graph5.connect(0,1,40);
        for (edge_data n :graph5.getE(0)) {
            assertEquals(1,n.getDest(),"need to be connect to 1");
        }
        graph5.removeNode(1);
        assertEquals(false,graph5.getV().contains(n1),"is not contains 1");
        assertEquals(false,graph5.getE(0).contains(1),"not connect to node(1) ");

    }

    @Test
    void removeEdge() {
        directed_weighted_graph graph6 =new DW_GraphDS();
        node_data n0=new NodeData(0);
        node_data n1=new NodeData(1);
        graph6.addNode(n0);
        graph6.addNode(n1);
        assertNull(graph6.getEdge(0,1));
        assertNull(graph6.getEdge(1,0));
        graph6.connect(0,1,10);
        assertEquals(null,graph6.getEdge(1,0),"was connect to one side(1->0)");
        assertEquals(10,graph6.getEdge(0,1).getWeight(),"need to be connect 10(0->1)");
        graph6.connect(1,0,20);
        assertEquals(20,graph6.getEdge(1,0).getWeight(),"need to be connect 20(1->0)");
        assertEquals(10,graph6.getEdge(0,1).getWeight(),"need to be connect 10(0->1)");
        graph6.removeEdge(0,1);
        assertEquals(20,graph6.getEdge(1,0).getWeight(),"need to be connect 20(1->0)");
        assertEquals(null,graph6.getEdge(0,1),"need to be connect {null} !(0->1)");
        graph6.removeEdge(1,0);
        assertEquals(null,graph6.getEdge(0,1),"need to be connect {null} !(0->1)");
        assertEquals(null,graph6.getEdge(1,0),"need to be connect {null} !(1->0)");

    }

    @Test
    void nodeSize() {
        directed_weighted_graph graph7 =new DW_GraphDS();
        node_data n0=new NodeData(0);
        node_data n1=new NodeData(1);
        node_data n2=new NodeData(2);
        graph7.addNode(n0);
        graph7.addNode(n1);

        assertEquals(2, graph7.nodeSize(),"graph has no connectes");
        graph7.addNode(n2);
        assertEquals(3, graph7.nodeSize(),"graph has one connectes");
        graph7.removeNode(1);
        assertEquals(2, graph7.nodeSize(),"graph has one connectes");
       graph7.removeNode(0);
        assertEquals(1, graph7.nodeSize(),"graph has one connectes after remove one");
    }

    @Test
    void edgeSize() {
        directed_weighted_graph graph8 =new DW_GraphDS();
        node_data n0=new NodeData(0);
        node_data n1=new NodeData(1);
        graph8.addNode(n0);
        graph8.addNode(n1);

        assertEquals(0, graph8.edgeSize(),"graph has no connectes");
        graph8.connect(0,1,10);
        assertEquals(1, graph8.edgeSize(),"graph has one connectes");
        graph8.connect(1,0,20);
        assertEquals(2, graph8.edgeSize(),"graph has one connectes");
        graph8.removeEdge(1,0);
        assertEquals(1, graph8.edgeSize(),"graph has one connectes after remove one");
    }

    @Test
    void getMC() {
        directed_weighted_graph graph9 =new DW_GraphDS();
        node_data n0=new NodeData(0);
        node_data n1=new NodeData(1);
        assertEquals(0,graph9.getMC(),"no moves");
        graph9.addNode(n0);
        assertEquals(1,graph9.getMC(),"add node");
        graph9.addNode(n1);
        assertEquals(2,graph9.getMC(),"add node");
graph9.connect(0,1,10);
        assertEquals(3,graph9.getMC(),"add connect");
        graph9.connect(1,0,110);
        assertEquals(4,graph9.getMC(),"add connect");
graph9.removeEdge(1,0);
        assertEquals(5,graph9.getMC(),"remove edge");
        graph9.removeNode(0);
        assertEquals(6,graph9.getMC(),"remove node");
graph9.addNode(n0);
        assertEquals(7,graph9.getMC(),"add node");
graph9.getEdge(1,0);
        assertEquals(7,graph9.getMC(),"add node");
        graph9.getV();
        assertEquals(7,graph9.getMC(),"add node");
graph9.getE(1);
        assertEquals(7,graph9.getMC(),"add node");
graph9.getNode(1);
        assertEquals(7,graph9.getMC(),"add node");
graph9.nodeSize();
        assertEquals(7,graph9.getMC(),"add node");
graph9.edgeSize();
        assertEquals(7,graph9.getMC(),"add node");
graph9.connect(0,1,12);
        assertEquals(8,graph9.getMC(),"add node");
        graph9.connect(0,1,112);
        assertEquals(9,graph9.getMC(),"add node");

    }
}