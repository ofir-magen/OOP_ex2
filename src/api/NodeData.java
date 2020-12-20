package api;
import gameClient.util.Point3D;

import java.util.Locale;

public class NodeData implements node_data {
    static int id = 0;
     int tag,key;
     String info;
     double weight;
     geo_location location;
     public NodeData(int key){
         this.key = key;
         this.location = new Point3D(0, 0, 0);
     }
    public NodeData(int key , geo_location location){
        this.key = key;
        this.location = location;
    }
    public NodeData(){
        this.key = id++;
        this.location = new Point3D(0, 0, 0);
    }
    /**
     * Returns the key (id) associated with this node.
     *
     * @return key - the unique id of the node
     */
    @Override
    public int getKey() {
        return this.key;
    }

    /**
     * Returns the location of this node, if
     * none return null.
     *
     * @return geo_location - the (x,y,z) geometrical location of the node
     */
    @Override
    public geo_location getLocation() {
        return location;
    }

    /**
     * Allows changing this node's location.
     *
     * @param p - new new location  (position) of this node.
     */
    @Override
    public void setLocation(geo_location p) {
        this.location = p;
    }

    /**
     * Returns the weight associated with this node.
     *
     * @return weight - the weight of the node
     */
    @Override
    public double getWeight() {
        return weight;
    }

    /**
     * Allows changing this node's weight.
     *
     * @param w - the new weight
     */
    @Override
    public void setWeight(double w) {
    this.weight = w;
    }

    /**
     * Returns the remark (meta data) associated with this node.
     *
     * @return info - the info of the node
     */
    @Override
    public String getInfo() {
        return info;
    }

    /**
     * Allows changing the remark (meta data) associated with this node
     * @param s
     */
    @Override
    public void setInfo(String s) {
    this.info = s;
    }

    /**
     * Temporal data (aka color: e,g, white, gray, black)
     * which can be used be algorithms
     * @return the new tag of the node
     */
    @Override
    public int getTag() {
        return this.tag;
    }

    /**
     * Allows setting the "tag" value for temporal marking an node - common
     * practice for marking by algorithms.
     *
     * @param t - the new value of the tag
     */
    @Override
    public void setTag(int t) {
    this.tag = t;
    }
}
