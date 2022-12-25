/**
 * This data structure is used to create a Noise object that stores the
 * noise 3D points and extends the Cluster class
 * @author Aksh Babbar (300034042)
 */
public class Noise extends Cluster {

    /** 
     * Constructor that initializes an empty Cluster with the given ID
     * @param clusterID  the cluster identifier
     * We are setting the noise to have a clusterID = 
     */
    public Noise(int clusterID) { 
        super(clusterID); 
    }
    
    @Override
    /** 
     * Generate RGB for the Noise Cluster.
     * By assignment description, we are setting Noise to RGB value of 0, 0, 0
     * @return String[] containing RGB values for the Noise
     */
    public String[] generateRGB(){ 
        String[] rgb = {"0", "0", "0"};
        return rgb;
    }
}
    