package api;
import java.util.Locale;

public class NodeData implements node_data {
    static int id = 0;
     int tag,key;
     String info;
     double weight;
     geo_location location;
     public NodeData(int key){
         this.key = key;
         this.location = new GeoLocation(0, 0, 0);
     }
    public NodeData(int key , geo_location location){
        this.key = key;
        this.location = location;
    }
    public NodeData(){
        this.key = id++;
        this.location = new GeoLocation(0, 0, 0);
    }
    /**
     * Returns the key (id) associated with this node.
     *
     * @return
     */
    @Override
    public int getKey() {
        return this.key;
    }

    /**
     * Returns the location of this node, if
     * none return null.
     *
     * @return
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
     * @return
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
     * @return
     */
    @Override
    public String getInfo() {
        return info;
    }

    /**
     * Allows changing the remark (meta data) associated with this node.
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
     * Allows setting the "tag" value for temporal marking an node - common
     * practice for marking by algorithms.
     *
     * @param t - the new value of the tag
     */
    @Override
    public void setTag(int t) {
    this.tag = t;
    }
    public static class GeoLocation implements geo_location{
        double x,y,z;
        public GeoLocation(double x,double y,double z){
            this.x = x;
            this.y = y;
            this.z = z;
        }
        @Override
        public double x() {
            return x;
        }

        @Override
        public double y() {
            return y;
        }

        @Override
        public double z() {
            return z;
        }

        @Override
        public double distance(geo_location g) {

            return Math.sqrt(Math.pow((x-g.x()),2)+Math.pow((y-g.y()),2)+Math.pow((z-g.z()),2));
        }
    }
}
