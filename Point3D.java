/**
 * This data structure is used to create a Point3D object that stores information
 * such as X, Y, Z coordinates and the cluster ID of the point as well as some other 
 * functions used
 * @author Aksh Babbar (300034042)
 */

import java.lang.Math;

public class Point3D{

    private double X; // X-Coordinate
    private double Y; // Y-Coordinate
    private double Z; // Z-Coordinate
    private int clusterID; // cluster id to which the Point belongs

/** 
 * Creates a Point object by taking X,Y,Z values and initialize the point
 * as UNDEFINED (-1) 
 * @param X  the  X-Coordinate
 * @param Y  the  Y-Coordinate
 * @param Z  the  Z-Coordinate
 */
    public Point3D(double X, double Y, double Z){ 
        this.X = X;
        this.Y = Y;
        this.Z = Z;
        this.clusterID = -1; // -1: Undefined
    }


/** 
 * Getter for X-coordinate
 * @return X-Coordinate of type double
 */
    public double getX(){ 
        return this.X;
    }

/** 
 * Getter for Y-coordinate
 * @return Y-Coordinate of type double
 */
    public double getY(){ 
        return this.Y;
    }

/** 
 * Getter for Z-coordinate
 * @return Z-Coordinate of type double
 */
    public double getZ(){ 
        return this.Z;
    }

/** 
 * Gets the cluster ID that the point belongs to
 * @return the cluster identifier of type int
 */
    public int getClusterId(){ 
        return this.clusterID;
    }


/** 
 * Sets the cluster ID of the point
 * @param id of type int
 */
    public void setClusterId(int id){ 
        this.clusterID = id;
    }

    public double get(int axis) {
      
      switch(axis) {
          case 0: return getX();
          case 1: return getY();
          case 2: return getZ();
          default: return 0.0;
      }       
  }

@Override
/** 
 * Overrides the .equals() method to compare 2 points using their x,y,z coordinates and cluster ID
 * @param o the Object to which we will compare the point
 * @return boolean -> True if both points being compared are same, False otherwise
 */
    public boolean equals(Object o){ 
        //Check if Object o is null or not an instance of Point3D
        if(o == null || !(o instanceof Point3D)){
            return false;
        }
        //If object being compared to itself, then true
        if(o == this){
            return true;
        }
        
        Point3D pt = (Point3D) o;
        
        return 
        this.getX() == pt.getX() &&
        this.getY()== pt.getY() &&
        this.getZ() == pt.getZ() &&
        this.getClusterId() == pt.getClusterId();
    }

/** 
 * Computes the Euclidean distance between 2 points
 * @param pt  the pt from which we want to calculate distance
 * @return double -> distance between 2 points
 */
    public double distance(Point3D pt){ 
        return Math.sqrt(Math.pow(pt.getX() - getX(), 2) + Math.pow(pt.getY() - getY(), 2) + Math.pow(pt.getZ() - getZ(), 2));
    }
}
