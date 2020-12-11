package api;

import com.google.gson.Gson;

import java.util.LinkedList;
import java.util.List;

public class test {
    public static void main(String[] args) {
//        directed_weighted_graph g1 = new DW_GraphDS();
//        node_data n1 = new NodeData();
//        node_data n2 = new NodeData();
//        node_data n3 = new NodeData();
//        node_data n4 = new NodeData();
//        g1.addNode(n1);
//        g1.addNode(n2);
//        g1.addNode(n3);
//        g1.addNode(n4);
//        g1.connect(n1.getKey(),n2.getKey(),3.0);
//        g1.connect(n1.getKey(),n2.getKey(),4.0);
//        g1.connect(n2.getKey(),n1.getKey(),5.0);
        //System.out.println(g1.getEdge(n1.getKey(),n2.getKey()).getWeight());
        //System.out.println(g1.getE(n1.getKey()));
        //g1.removeEdge(n1.getKey(),n2.getKey());
        //System.out.println(g1.getEdge(n2.getKey(),n1.getKey()).getWeight());
        //g1.removeNode(n2.getKey());
        String file = "/Users/yuval/Desktop/g3.txt";
        String file2 = "/Users/yuval/Downloads/Ariel_OOP_2020-master/Assignments/Ex2/data/A0";
//        dw_graph_algorithms algo = new DWGraph_Algo();
//        algo.init(g1);
//        algo.save(file);
//        dw_graph_algorithms testAlgo = new DWGraph_Algo();
//        testAlgo.load(file);
//        directed_weighted_graph testG = testAlgo.getGraph();
//
//        for (node_data n : testG.getV()){
//            System.out.println("the key is: " + n.getKey() + "-> ");
//            for (edge_data edgy : testG.getE(n.getKey())){
//                System.out.println(edgy.getDest()+",");
//            }
        node_data n8 = new NodeData();
        node_data n9=new NodeData();
        node_data n10=new NodeData();
        node_data n11=new NodeData();
        node_data n12=new NodeData();

        directed_weighted_graph graph5 =new DW_GraphDS();
        graph5.addNode(n9);
        graph5.addNode(n10);
        graph5.addNode(n11);
        graph5.addNode(n12);
        graph5.connect(1,2,2);
        graph5.connect(2,3,1);
        graph5.connect(2,4,9);
        graph5.connect(3,1,1);
        graph5.connect(3,4,5);
        dw_graph_algorithms g5= new DWGraph_Algo();
        g5.init(graph5);
//        System.out.println(g5.isConnected());
//        dw_graph_algorithms algoT = new DWGraph_Algo();
//        algoT.load(file2);
        System.out.println(g5.shortestPathDist(1,4)); //should be 8
//        }
        LinkedList<node_data> list = (LinkedList) g5.shortestPath(1,4);
        for (node_data n : list){
            System.out.print(n.getKey() + ",");
        }

    }
}
