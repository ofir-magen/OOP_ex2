package api;

import com.google.gson.InstanceCreator;
import org.w3c.dom.Node;

import java.lang.reflect.Type;
import java.util.*;

public class DW_GraphDS implements directed_weighted_graph {
    HashMap<Integer,node_data> Vertices;
    HashMap<Integer,HashMap<Integer,edge_data>> ExitEdges;// all the edges that src is the key
    HashMap<Integer,HashSet<Integer>> EntryEdges; // all the edges that dest is the key
    int Edgesize,MC;


   public DW_GraphDS(){
       this.Vertices = new HashMap<Integer, node_data>();
       this.ExitEdges = new HashMap<Integer,HashMap<Integer,edge_data>>();
       this.EntryEdges = new HashMap<Integer,HashSet<Integer>>();

   }
    /**
     * returns the node_data by the node_id,
     *
     * @param key - the node_id
     * @return the node_data by the node_id, null if none.
     */
    @Override
    public node_data getNode(int key) {
        if (Vertices.containsKey(key)){ return Vertices.get(key);}
        return null;
    }

    /**
     * returns the data of the edge (src,dest), null if none.
     * Note: this method should run in O(1) time.
     * @param src
     * @param dest
     * @return
     */
    @Override
    public edge_data getEdge(int src, int dest) {
        if(ExitEdges.get(src).containsKey(dest)){
            return ExitEdges.get(src).get(dest);
        }
        return null;
    }

    /**
     * adds a new node to the graph with the given node_data.
     * Note: this method should run in O(1) time.
     *
     * @param n
     */
    @Override
    public void addNode(node_data n) {
        if(!Vertices.containsKey(n.getKey())) {
            Vertices.put(n.getKey(), n);
            ExitEdges.put(n.getKey(), new HashMap<Integer, edge_data>());
            EntryEdges.put(n.getKey(), new HashSet<Integer>());

        }
    }

    /**
     * Connects an edge with weight w between node src to node dest.
     * * Note: this method should run in O(1) time.
     *
     * @param src  - the source of the edge.
     * @param dest - the destination of the edge.
     * @param w    - positive weight representing the cost (aka time, price, etc) between src-->dest.
     */
    @Override
    public void connect(int src, int dest, double w) {
        if(Vertices.containsKey(src) && Vertices.containsKey(dest) && src != dest){
            if(!ExitEdges.get(src).containsKey(dest)) {Edgesize++;}
                ExitEdges.get(src).put(dest, new EdgeData(src, dest, w));
                EntryEdges.get(dest).add(src);
        }
    }

    /**
     * This method returns a pointer (shallow copy) for the
     * collection representing all the nodes in the graph.
     * Note: this method should run in O(1) time.
     *kk
     * @return Collection<node_data>
     */
    @Override
    public Collection<node_data> getV() {
        return Vertices.values();
    }

    /**
     * This method returns a pointer (shallow copy) for the
     * collection representing all the ExitEdges getting out of
     * the given node (all the ExitEdges starting (source) at the given node).
     * Note: this method should run in O(k) time, k being the collection size.
     *
     * @param node_id
     * @return Collection<edge_data>
     */
    @Override
    public Collection<edge_data> getE(int node_id) {
        LinkedList<edge_data> EdgeCollection = new LinkedList<edge_data>();
        if(Vertices.containsKey(node_id)){
           for (Map.Entry<Integer,edge_data> entry : ExitEdges.get(node_id).entrySet()){
            EdgeCollection.addFirst(entry.getValue());
           }
        }
        return EdgeCollection;
      //  return this.ExitEdges.get(node_id).values();
    }

    /**
     * Deletes the node (with the given ID) from the graph -
     * and removes all ExitEdges which starts or ends at this node.
     * This method should run in O(k), V.degree=k, as all the ExitEdges should be removed.
     *
     * @param key
     * @return the data of the removed node (null if none).
     */
    @Override
    public node_data removeNode(int key) {
        if(Vertices.containsKey(key)){
            for (edge_data edgy: getE(key)){
                removeEdge(key,edgy.getDest());
            }
            for (int ni: EntryEdges.get(key)){
                removeEdge(ni,key);
            }
            return Vertices.remove(key);
        }
        return null;
    }


    /**
     * Deletes the edge from the graph,
     * Note: this method should run in O(1) time.
     *
     * @param src
     * @param dest
     * @return the data of the removed edge (null if none).
     */
    @Override
    public edge_data removeEdge(int src, int dest) {
        if(HasEdge(src,dest)){
            edge_data Fading = ExitEdges.get(src).get(dest);
            ExitEdges.get(src).remove(dest);
            Edgesize--;
            MC++;
            return Fading;
        }
        return null;
    }

    /**
     * Returns the number of vertices (nodes) in the graph.
     * Note: this method should run in O(1) time.
     *
     * @return
     */
    @Override
    public int nodeSize() {
        return Vertices.size();
    }

    /**
     * Returns the number of ExitEdges (assume directional graph).
     * Note: this method should run in O(1) time.
     *
     * @return
     */
    @Override
    public int edgeSize() {
        return Edgesize;
    }

    /**
     * Returns the Mode Count - for testing changes in the graph.
     *
     * @return
     */
    @Override
    public int getMC() {
        return MC;
    }
    public boolean HasEdge(int src,int dest){
        return Vertices.containsKey(src)&&
         Vertices.containsKey(dest)&&
         ExitEdges.containsKey(src)&&
         ExitEdges.get(src).containsKey(dest)&&
         src != dest;

    }
    public class EdgeData implements edge_data {
        int src,dest;
        double weight;
        int tag;
        String info;

        public EdgeData(int src,int dest,double weight){
            this.src = src;
            this.dest = dest;
            this.weight = weight;
        }
        /**
         * The id of the source node of this edge.
         *
         * @return
         */
        @Override
        public int getSrc() {
            return src;
        }

        /**
         * The id of the destination node of this edge
         *
         * @return
         */
        @Override
        public int getDest() {
            return dest;
        }

        /**
         * @return the weight of this edge (positive value).
         */
        @Override
        public double getWeight() {
            return this.weight;
        }

        /**
         * Returns the remark (meta data) associated with this edge.
         *
         * @return
         */
        @Override
        public String getInfo() {
            return this.info;
        }

        /**
         * Allows changing the remark (meta data) associated with this edge.
         *
         * @param s
         */
        @Override
        public void setInfo(String s) {
            this.info = s;
        }

        /**
         * Temporal data (aka color: e,g, white, gray, black)
         * which can be used be algorithms
         *
         * @return
         */
        @Override
        public int getTag() {
            return this.tag;
        }

        /**
         * This method allows setting the "tag" value for temporal marking an edge - common
         * practice for marking by algorithms.
         *
         * @param t - the new value of the tag
         */
        @Override
        public void setTag(int t) {
        this.tag = t;
        }


    }
    public class EdgeLocation implements edge_location{
        edge_data edge;
        /**
         * Returns the edge on which the location is.
         *
         * @return
         */
        @Override
        public edge_data getEdge() {
            return edge;
        }

        /**
         * Returns the relative ration [0,1] of the location between src and dest.
         *
         * @return
         */
        @Override
        public double getRatio() {
            return 0;
        }
    }
}
