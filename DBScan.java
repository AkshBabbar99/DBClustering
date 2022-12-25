import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;
import java.lang.System;


 /**
 * The class  DBScan algorithm is the main file for this project
 * DBScan Algorithm stands for Density Based Spatial Clustering Algorithm.
 * Given a large set of data points in a space of arbitrary dimensions and
 * a diven distance metric, this algorithm can discover clusters of different
 * shapes and sizes and marking outliers in low-density regions.
 * It requires 2 parameters: 
 * - minPts: Minimum number of points in the neighbourhood of a point to be considered
 *           as a dense region.
 * - eps: A distance measure used to identify points in the neighbourhood of any point.
 * 
 * Reference - Programming Exercise P2 Description
 * 
 * This class is the author's implementation of the DBScan algorithm according to the
 * description given in the Programming Exercise P1 Description
 * 
 * The NearestNeighbourKD class is used to find the nearest neighbours of a point
 * 
 * @author Aksh Babbar (300034042)
 */ 
public class DBScan {

    private ArrayList<Point3D> pointList;
    private ArrayList<Cluster> clusters;
    private Noise noise;
    private double eps;
    private double minPts;

/** 
 * Constructor for DBScan class that initializes the list of points,
 * an empty list of clusters, and noise
 * @param pointList list containing 3D points
 */
    public DBScan(ArrayList<Point3D> pointList){ 
        this.pointList = pointList;
        clusters = new ArrayList<Cluster>();
        noise = new Noise(0);
    }

/** 
 * Sets the value of eps
 * @param eps minimum distance between points
 */
    public void setEps(double eps){ 
        this.eps = eps;
    }

/** 
 * Sets the min pts for a region to be considered high-density
 * @param minPts minimum number of points
 */
    public void setMinPts(double minPts){ 
        this.minPts = minPts;
    }

/** 
 * Find clusters method is the algorithm that creates the list of clusters
 * and determine noise points.
 * Its implementation is based on the document: Programming Exercise P2
 * provided by the professor
 * The newly created NearestNeighbourKD class is used to find neighbours
 * The Stack class used here is from the Java API
 */
    public void findClusters(){ 
        NearestNeighboursKD finder = new NearestNeighboursKD(getPoints()); //Nearest Neighbour KD class
        for(Point3D point: getPoints()){
            if(point.getClusterId() != -1){
                continue;
            }
            ArrayList<Point3D> firstNeighbours = finder.RangeQuery(point, this.eps);
            if(firstNeighbours.size() < this.minPts){
                this.noise.addPoint(point);
                continue;
            }
            Cluster cluster = new Cluster(this.clusters.size() + 1);
            cluster.addPoint(point);
            Stack<Point3D> neighbourStack = new Stack<Point3D>();
            neighbourStack.addAll(firstNeighbours);
            while(!neighbourStack.isEmpty()){
                Point3D neighbourElem = neighbourStack.pop();
                if(this.noise.hasPoint(neighbourElem)){
                    cluster.addPoint(neighbourElem);
                    this.noise.removePoint(neighbourElem);
                }
                if(neighbourElem.getClusterId() != -1){
                    continue;
                }
                cluster.addPoint(neighbourElem);
                ArrayList<Point3D> secondNeighbours = finder.RangeQuery(neighbourElem, eps);
                if(secondNeighbours.size() >= this.minPts){
                    neighbourStack.addAll(secondNeighbours);
                }
            }
            this.clusters.add(cluster);
        }
    }


/** 
 * Getter for number of clusters
 * @return the number of clusters
 */
    public int getNumberOfClusters(){ 
        return this.clusters.size();
    }

/** 
 * Getter for 3D points list
 * @return the list of 3D points
 */
    public ArrayList<Point3D> getPoints(){ 
        return this.pointList;
    }

/** 
 * Read the '.csv' file and creates a list of 3D points
 * @param filename  the input '.csv' file
 * @return ArrayList<Point3D> of 3D points from the file
 */
    public static ArrayList<Point3D> read(String filename){ 

        ArrayList<Point3D> pointList = new ArrayList<Point3D>();
        try{
            File file = new File(filename);
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line = " ";
            String[] dataArr;
            line = br.readLine();
            line = br.readLine();
            while ((line = br.readLine()) != null) {
                dataArr = line.split(",");
                double X = Double.parseDouble(dataArr[0]);
                double Y = Double.parseDouble(dataArr[1]);
                double Z = Double.parseDouble(dataArr[2]);
                pointList.add(new Point3D(X, Y, Z));
            }
            br.close();
        }catch(FileNotFoundException e){
            System.out.println("FileNotFoundException!");
            System.exit(0);
        }catch(IOException e){
            System.out.println("IOException!");
            System.exit(0);
        }
        return pointList;
    }

/** 
 * This method creates an output file and writes the cluster information
 * along with RGB values. We add the noise to the cluster information
 * so that it is printed out in the output file and then the noise points 
 * is removed.
 * @param filename  the output '.csv' filename
 */
    public void save(String filename) { 
        ArrayList<Cluster> clusters = this.clusters;
        clusters.add(noise);
        try{
            FileWriter csvWriter = new FileWriter(filename);
            csvWriter.append("x,y,z,C,R,G,B\n");
            csvWriter.append("0,0,0,0,0,0,0\n");
            for(Cluster c: clusters){
                if(c.getSize() == 0){
                    continue;
                }
                String[] rgb = c.getRGB();
                for(Point3D point: c.getPointsInCluster()){
                    String[] out = new String[7];
                    out[0] = Double.toString(point.getX());
                    out[1] = Double.toString(point.getY());
                    out[2] = Double.toString(point.getZ());
                    out[3] = Integer.toString(point.getClusterId());
                    out[4] = rgb[0];
                    out[5] = rgb[1]; 
                    out[6] = rgb[2];
                    csvWriter.append(String.join(",", out));
                    csvWriter.append("\n");
                }
            }
            csvWriter.flush();
            csvWriter.close();
            clusters.remove(noise);
        }catch(Exception e){
            e.printStackTrace();
        }    
    }


/** 
 * This is a helped method which sorts the Clusters according to 
 * their size and prints it to the console in decending order
 * of size. It also prints the number of noise points
 */
    public void displayClusters(){ 
        Collections.sort(this.clusters);
        for(Cluster c: this.clusters){
            if(c.getClusterID() == 0){
                continue;
            }
            System.out.println("Cluster Number: " + 
            c.getClusterID() +
            ". Size: " + c.getSize());
        }
        if(this.noise.getSize() != 0){
            System.out.println("\nNumber of Noise Points: " + this.noise.getSize());
        }
    }

    public static void main(String[] args) { 
        //Default Values
        String fileName = "Point_Cloud_1.csv";
        double eps = 1.8;
        int minPts = 12;
        try{
            //override default values with user inputs values
            fileName = args[0];
            eps = Double.parseDouble(args[1]);
            minPts = Integer.parseInt(args[2]);
        }
        catch(Exception e){
            System.out.println(
                "Running program using default values! \n" +
                "If you want to use custom values, you can run the program as: \n" +
                "java DBScan <file-name.csv> <epsilon> <minimum-points>"
                );
        }
        DBScan db = new DBScan(read("PointClouds/" + fileName));
        db.setEps(eps);
        db.setMinPts(minPts);
        System.out.println("\nRunning DBScan with eps = " + eps + " and minpts = " + minPts);
        db.findClusters();
        String outFileName = fileName.substring(0, fileName.length()-4)
        +"_clusters_"+eps+"_"+minPts+ "_" + db.getNumberOfClusters() +".csv";
        db.save("Output/" + outFileName);
        System.out.println("\nOutput file created: "+ outFileName);
        System.out.println("\nAfter running the algorithm:\nNumber or clusters found= " + db.getNumberOfClusters());
        System.out.println("\nHere are the Clusters arranged according to size:");
        db.displayClusters();
    }
}
