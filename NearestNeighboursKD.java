import java.util.ArrayList;

/**
 * This data structure is used to create a NearestNeighboursKD object that saves point in a KDTree object and
 * contains function RangeQuery to calculate the points in eps distance to the given point
 * @author Aksh Babbar (300034042)
 */

public class NearestNeighboursKD {

    private ArrayList<Point3D> pointList; //containing all points
    private KDtree kdtree; //KDtree object

/** 
 * Constructor that takes an ArrayList of type Point3D and initializes a KDTree and adds points
 * from pointList to the tree
 * @param pointList  the point list containing all points in dataset
 */
    public NearestNeighboursKD(ArrayList<Point3D> pointList){     
        this.pointList = pointList;
        this.kdtree = new KDtree();
        for(Point3D point: this.pointList){
            kdtree.add(point);
        }
    }

/** 
 * Range query function that finds the nearest neighbours of a 3D point in a KDTree
 *
 * @param point  the point for which we need to find neighbours
 * @param eps  the minimum acceptable distance to consider 2 points as neighbours
 * @return ArrayList<Point3D> of points closest to the given point
 */
    public ArrayList<Point3D> RangeQuery(Point3D point, double eps){    
        ArrayList<Point3D> neighbours = new ArrayList<Point3D>();
        neighbours = this.kdtree.rangeQuery(point, eps, neighbours, this.kdtree.getRoot()); //Call to the rangeQuery function in KDTree
        return neighbours;
    } 
}