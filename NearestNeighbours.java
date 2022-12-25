import java.util.ArrayList;

/**
 * This data structure is used to create a NearestNeighbours object that
 * contains function RangeQuery to calculate the points in eps distance to
 * the given point
 * @author Aksh Babbar (300034042)
 */
public class NearestNeighbours {

    private ArrayList<Point3D> pointList;

/** 
 * Constructor that takes an ArrayList of type Point3D
 * @param pointList  the point list containing all points in dataset
 */
    public NearestNeighbours(ArrayList<Point3D> pointList){     
        this.pointList = pointList;
    }


/** 
 * Range query function that finds the nearest neighbours of a 3D point
 *
 * @param point  the point for which we need to find neighbours
 * @param eps  the minimum acceptable distance to consider 2 points as neighbours
 * @return ArrayList<Point3D> of points closest to the given point
 */
    public ArrayList<Point3D> RangeQuery(Point3D point, double eps){    
        ArrayList<Point3D> neighbours = new ArrayList<Point3D>();
        for(Point3D pt : this.pointList){
            if(point.distance(pt) <= eps){
                neighbours.add(pt);
            }
        }
        return neighbours;
    }
}
