package api;

import com.google.gson.*;

import java.io.*;
import java.util.*;

public class DWGraph_Algo implements dw_graph_algorithms {
    private directed_weighted_graph graph;


    public DWGraph_Algo(){
        this.graph = new DW_GraphDS();
    }
    /**
     * Init the graph on which this set of algorithms operates on.
     *
     * @param g
     */
    @Override
    public void init(directed_weighted_graph g) {
        this.graph = g;
    }

    /**
     * Return the underlying graph of which this class works.
     *
     * @return
     */
    @Override
    public directed_weighted_graph getGraph() {
        return this.graph;
    }

    /**
     * Compute a deep copy of this weighted graph.
     *
     * @return
     */
    @Override
    public directed_weighted_graph copy() {
        directed_weighted_graph Newg = new DW_GraphDS();
        for (node_data n: this.graph.getV()){
            node_data NewNode = new NodeData(n.getKey(),n.getLocation());
            NewNode.setInfo(n.getInfo());
            NewNode.setTag(n.getTag());
            NewNode.setWeight(n.getWeight());
            Newg.addNode(NewNode);
        }
        for (node_data n : this.graph.getV()){
            for (edge_data edge : this.graph.getE(n.getKey())){
                Newg.connect(edge.getSrc(),edge.getDest(),edge.getWeight());
            }
        }
        return Newg;
    }

    /**
     * Returns true if and only if (iff) there is a valid path from each node to each
     * other node. NOTE: assume directional graph (all n*(n-1) ordered pairs).
     *
     * @return
     */
    @Override
    public boolean isConnected() {
        Queue<node_data> bfsq = new LinkedList<>();
        int sizeG = graph.getV().size();

        int counter = 0;
        node_data first = null; // pointer for node_data
        reset(-1);
        boolean flag = true;
        for (node_data n : graph.getV()) { // run on all the nodes in the graph and check if is connected to all.
            first = n;
            if (first == null) {// if the graph is empty
                return true;
            }
            bfsq.add(first);
            first.setTag(0);
            counter++;
            while (!bfsq.isEmpty()) {
                node_data tmp = bfsq.poll();
                for (edge_data neighbor_node : graph.getE(tmp.getKey())) { // run on the neighbors node and tag them
                    int dest_node = neighbor_node.getDest();
                    if (graph.getNode(dest_node).getTag() != 0) {
                        counter++;
                        graph.getNode(dest_node).setTag(0);
                        bfsq.add(graph.getNode(dest_node));
                    }
                }
            }
            if (sizeG != counter) {
                flag = false;
                break;
            }
            reset(-1);
            counter = 0;
        }


        return flag;
    }

    /**
     * returns the length of the shortest path between src to dest
     * Note: if no such path --> returns -1
     *
     * @param src  - start node
     * @param dest - end (target) node
     * @return
     */
    @Override
    public double shortestPathDist(int src, int dest) {
        if(graph.getNode(src) == null || graph.getNode(dest) == null) return -1;
        if(src == dest) return 0;
        for (node_data n : graph.getV()){
            n.setInfo("");
        }
        HashMap<Integer,Double> MininumDistanceMap = new HashMap<Integer, Double>();
        Queue<node_data> queue = new LinkedList<node_data>();
        MininumDistanceMap.put(src,0.0);
        queue.add(graph.getNode(src));
        graph.getNode(src).setInfo(graph.getNode(src).getKey()+"");
        while(!queue.isEmpty()){
            node_data tmp = queue.poll();
            for (edge_data edge : graph.getE(tmp.getKey())){
                if(!MininumDistanceMap.containsKey(edge.getDest())
                   || MininumDistanceMap.get(edge.getDest()) > (MininumDistanceMap.get(edge.getSrc()) + edge.getWeight())){
                    MininumDistanceMap.put(edge.getDest(),(MininumDistanceMap.get(edge.getSrc()) + edge.getWeight()));
                    graph.getNode(edge.getDest()).setInfo(tmp.getInfo() +","+ graph.getNode(edge.getDest()).getKey());
                    queue.add(graph.getNode(edge.getDest()));
                }
            }
        }

        if(!MininumDistanceMap.containsKey(dest)) return -1;
        return MininumDistanceMap.get(dest);
    }

    /**
     * returns the the shortest path between src to dest - as an ordered List of nodes:
     * src--> n1-->n2-->...dest
     * see: https://en.wikipedia.org/wiki/Shortest_path_problem
     * Note if no such path --> returns null;
     *
     * @param src  - start node
     * @param dest - end (target) node
     * @return
     */
    @Override
    public List<node_data> shortestPath(int src, int dest) {
        List<node_data> path = new LinkedList<node_data>();
        double shortest = shortestPathDist(src,dest);
        if(shortest == -1){ return null;}
        else if(shortest == 0){
            path.add(graph.getNode(src));
            return path;
        }
        else{
            String[] ThePath = graph.getNode(dest).getInfo().split(",");
            for (int i = 0;i<ThePath.length;i++){
                Integer.parseInt(ThePath[i]);
                path.add(graph.getNode((Integer.parseInt(ThePath[i]))));
            }
        }

        return path;
    }

    /**
     * Saves this weighted (directed) graph to the given
     * file name - in JSON format
     *
     * @param file - the file name (may include a relative path).
     * @return true - iff the file was successfully saved
     */
    @Override
    public boolean save(String file) {
        try {
            File myObj = new File(file);
            if (myObj.createNewFile()) {
                FileWriter myWriter = new FileWriter(file);
                Gson g = new Gson();
                 JsonObject json = new JsonObject();//{}
                JsonArray jsonEdges = new JsonArray();
                JsonArray jsonNodes = new JsonArray();
                for (node_data n : this.graph.getV() ){//iterate to make Nodes -> Json
                    JsonObject nodeInfo = new JsonObject();
                    nodeInfo.addProperty("pos",n.getLocation().x()+","+n.getLocation().y()+","+n.getLocation().z());
                    nodeInfo.addProperty("id",n.getKey());
                    jsonNodes.add(nodeInfo);
                    for (edge_data edge : this.graph.getE(n.getKey())){//iterate to make Edges -> Json
                        JsonObject edgeInfo = new JsonObject();
                        edgeInfo.addProperty("src",edge.getSrc());
                        edgeInfo.addProperty("w",edge.getWeight());
                        edgeInfo.addProperty("dest",edge.getDest());
                        jsonEdges.add(edgeInfo);
                    }
                }
                json.add("Edges",jsonEdges);
                json.add("Nodes",jsonNodes);
//                System.out.println(json);
               myWriter.write(g.toJson(json));
               myWriter.close();
            } else {
                System.out.println("File already exists.");
                return false;
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
            return false;
        }

        return true;
    }

    /**
     * This method load a graph to this graph algorithm.
     * if the file was successfully loaded - the underlying graph
     * of this class will be changed (to the loaded one), in case the
     * graph was not loaded the original graph should remain "as is".
     *
     * @param file - file name of JSON file
     * @return true - iff the graph was successfully loaded.
     */
    @Override
    public boolean load(String file) {
        try {
            Gson g = new Gson();
            directed_weighted_graph g1 = new DW_GraphDS();
            JsonObject json = new JsonParser().parse(new FileReader(file)).getAsJsonObject(); //read file as JsonObject
            JsonArray edges = json.getAsJsonArray("Edges");
            JsonArray nodes = json.getAsJsonArray("Nodes");
            System.out.println(json);
            for (JsonElement node: nodes){ //iterate to convert Json -> Nodes
               String[] pos = ((JsonObject) node).get("pos").getAsString().split(",");
                geo_location location = new NodeData.GeoLocation(Double.parseDouble(pos[0]),Double.parseDouble(pos[1]),Double.parseDouble(pos[2]));
                node_data NewNode = new NodeData(((JsonObject) node).get("id").getAsInt(),location);
                g1.addNode(NewNode);
            }
            for (JsonElement edge : edges){ //iterate to convert Json -> Edges
                JsonObject e = (JsonObject) edge;
                g1.connect(e.get("src").getAsInt(),e.get("dest").getAsInt(),e.get("w").getAsDouble());
            }
            this.graph = g1;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }



    private void reset(int a) {
        for (node_data v : this.graph.getV()) {
            v.setTag(a);
        }
    }

}