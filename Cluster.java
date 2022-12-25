import java.util.ArrayList;
import java.util.Random;


/**
 * This data structure is used to create a Cluster object that
 * contains the points in the cluster, the id of the cluster
 * and the color associated with the cluster
 * @author Aksh Babbar (300034042)
 */
public class Cluster implements Comparable<Cluster> {

    private ArrayList<Point3D> clusterPoints; //List of points in the cluster
    private int clusterID;
    private String[] rgb; //Color associated with the cluster

    /** 
     * Constructor that initializes an empty Cluster with the given ID
     * and generates a unique color value for the cluster
     *
     * @param clusterID  the cluster identifier
     */
    public Cluster(int clusterID){ 
        this.clusterID = clusterID;
        this.clusterPoints = new ArrayList<Point3D>();
        this.rgb = generateRGB();
    }

    /** 
     * Getter for cluster ID
     * @return cluster ID of type int
     */
    public int getClusterID(){  
        return this.clusterID;
    }

    /** 
     *
     * Getter for number of 3D points that are part of this Cluster
     * @return integer size of the cluster
     */
    public int getSize(){ 
        return this.clusterPoints.size();
    }

    /** 
     * Geter for all points that belong to this Cluster
     * @return ArrayList<Point3D> of points in cluster
     */
    public ArrayList<Point3D> getPointsInCluster(){ 
        return this.clusterPoints;
    }

    /** 
     * Helper Function to add a point to this Cluster
     * Checks if the point already exists in the Cluster
     * if Yes then does nothing, else adds the point to the Cluster
     * @param point  the point to be added to the cluster
     */
    public void addPoint(Point3D point){ 
        if(!hasPoint(point)){
            this.clusterPoints.add(point);
            point.setClusterId(this.clusterID);
        }
    }

    /** 
     * Checks if the 3D point is already a part of this Cluster
     * @param point  the 3D point to be checked
     * @return boolean -> true if the 3D point is a part of the Cluster, else false
     */
    public boolean hasPoint(Point3D point){ 
        for(Point3D pt: this.clusterPoints){
            if(point.equals(pt)){
                return true;
            }
        }
        return false;
    }

    /** 
     * Checks if the 3D point is already a part of this Cluster and then 
     * removes it
     * @param point  the 3D point to be removed
     */
    public void removePoint(Point3D point){ 
        if(hasPoint(point)){
            this.clusterPoints.remove(point);
        }
    }

    /** 
     * Getter for the RGB value associated with the cluster
     * @return the String[] RGB colors associated with the cluster
     */
    public String[] getRGB(){ 
        return this.rgb;
    }

    /** 
     * Generate Random RGB values for the cluster
     * @return String[] containing RGB values for the cluster
     */
    public String[] generateRGB(){ 
        String[] gen_rgb = new String[3];
        if(this.clusterID == 0){
            gen_rgb[0] = "1";
            gen_rgb[1] = "1";
            gen_rgb[2] = "1";
            return gen_rgb;
        }
        Random rand = new Random();
        gen_rgb[0] = Double.toString(rand.nextDouble());
        gen_rgb[1] = Double.toString(rand.nextDouble());
        gen_rgb[2] = Double.toString(rand.nextDouble());
        return gen_rgb;
    }

    @Override
    /** 
     * Overrides the compareTo method in Comparable Class to sort Clusters by size
     * @param c2  the Cluster to be compared to 
     * @return difference of cluster size.
     */
    public int compareTo(Cluster c2){ 
        int compareSize = ((Cluster) c2).getSize();
        return compareSize - this.getSize();
    }
}
