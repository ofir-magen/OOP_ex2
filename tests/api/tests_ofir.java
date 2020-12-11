package api;

import api.*;

public class tests_ofir {
    public static void main(String[] args) {

        node_data n0=new NodeData(0);
        node_data n1=new NodeData(1);
        node_data n2=new NodeData(2);

        node_data n3=new NodeData(3);
        node_data n4=new NodeData(4);
        node_data n5=new NodeData(5);

        node_data n6=new NodeData(6);
        node_data n7=new NodeData(7);
        node_data n8=new NodeData(8);

        node_data n9=new NodeData(9);
        node_data n10=new NodeData(10);
        node_data n11=new NodeData(11);
        node_data n12=new NodeData(12);


        directed_weighted_graph graph1 =new DW_GraphDS();

        graph1.addNode(n0);
        graph1.addNode(n1);
        graph1.addNode(n2);
        graph1.connect(0,1,5);
        graph1.connect(1,2,5);
        graph1.connect(2,0,5);
        dw_graph_algorithms g1= new DWGraph_Algo();
        g1.init(graph1);

        System.out.println(g1.isConnected());

///////////
        directed_weighted_graph graph2 =new DW_GraphDS();
        graph2.addNode(n3);
        graph2.addNode(n4);
        graph2.addNode(n5);
        graph2.connect(3,4,5);
        graph2.connect(4,3,5);
        graph2.connect(4,5,5);
        graph2.connect(5,4,5);
        graph2.connect(5,6,5);
        graph2.connect(6,5,5);
        dw_graph_algorithms g2= new DWGraph_Algo();
        g2.init(graph2);
        System.out.println(g2.isConnected());

        ///////////
        directed_weighted_graph graph3 =new DW_GraphDS();
        graph3.addNode(n6);
        graph3.addNode(n7);
        graph3.addNode(n8);
        graph3.connect(6,7,5);
        graph3.connect(6,8,5);
        dw_graph_algorithms g3= new DWGraph_Algo();
        g3.init(graph3);
        System.out.println(g3.isConnected());

        ///////////
        directed_weighted_graph graph4 =new DW_GraphDS();
        graph4.addNode(n6);
        graph4.addNode(n7);
        graph4.addNode(n8);
        graph4.connect(9,10,5);
        graph4.connect(10,9,5);
        dw_graph_algorithms g4= new DWGraph_Algo();
        g4.init(graph4);
        System.out.println(g4.isConnected());




        directed_weighted_graph graph5 =new DW_GraphDS();
        graph5.addNode(n9);
        graph5.addNode(n10);
        graph5.addNode(n11);
        graph5.addNode(n12);
        graph5.connect(9,10,2);
        graph5.connect(10,11,1);
        graph5.connect(10,12,9);
        graph5.connect(11,9,1);
        graph5.connect(11,12,5);
        dw_graph_algorithms g5= new DWGraph_Algo();
        g5.init(graph5);
        System.out.println(g5.isConnected());




    }
}
