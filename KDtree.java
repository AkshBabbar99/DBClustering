import java.util.ArrayList;

 
/**
 * This data structure is used to create a KD Tree and is implemented
 * from the instructions provided in the Programming Assignment P2 guide
 * @author Aksh Babbar (300034042)
 */
public class KDtree {
    /**
     * This internal class is used to create a KD Node and is implemented
     * from the instructions provided in the Programming Assignment P2 guide
     */
    public class KDnode{
        public Point3D point;
        public int axis; //Axis of split
        public double value; //Coordinate value according to the split axis
        public KDnode left; //left child: node with value less than this
        public KDnode right; //right child: node with value greater than this

        public KDnode(Point3D pt, int axis){
            this.point= pt;
            this.axis= axis;
            this.value= pt.get(axis);
            this.left = this.right = null;
        }
    }

    private KDnode root; //Root node of the KD Tree

    /** 
     * Constructor that initializes an empty tree with root node null
     */
    public KDtree(){
        this.root = null;
    }

    /** 
     * Helper method that returns the root of the KD tree
     * @return KDnode root -> root of the tree
     */
    public KDnode getRoot(){
        return this.root;
    }

    /** 
     * This method is used to add a point to the KD Tree.
     * If the root node is null, then this method will set the root as the first incoming node
     * This method finds the appropriate position/parent of the node at which the incoming
     * point should be inserted. The node is inseted using the insert method
     */
    public void add(Point3D point){
        if(this.root == null){ //Check if there is no root
            this.root = new KDnode(point, 0);
            return ;
        }
        KDnode insertAtNode = this.root; //Start at root and traverse to required position in the tree
        while(true){
            int axis = insertAtNode.axis; //Axis of split used for value comparison
            if(point.get(axis) < insertAtNode.value){
                if(insertAtNode.left == null){ //if True means that parent node has been found
                    break;
                }
                insertAtNode = insertAtNode.left; //replace the value of insertNode with its left child
            }
            else{
                if(insertAtNode.right == null){ //if True means that parent node has been found
                    break;
                }
                insertAtNode = insertAtNode.right; //replace the value of insertNode with its left child
            }
        }
        insert(point, insertAtNode, insertAtNode.axis); //method to insert the node once parent is found
    }

    /** 
     * This recursive method is used to insert the point as the child of the the parent KDnode provided in the parameter
     * Checks if the point should be a left child or right child of the parent node.
     * This method has been implemented using the pseudo-code provided in Programming Assignment P2 guide
     * @param Point3D point to be inserted
     * @param KDnode node at which point should be inserted
     * @param axis axis of comparison or split
     * 
     * @return KDnode parent node to which the child is inserted
     */
    public KDnode insert(Point3D point, KDnode node, int axis){
        if(node == null){
            node = new KDnode(point, axis); //if the node is null, create a node with the incoming point
        }
        else if(point.get(axis) <= node.value){ //if the node is not null, then compare the value to check if the node should be a left child
            node.left = insert(point, node.left, (node.axis + 1) % 3); //insert point as the left child and change the axis of split as we are going one level deeper in the tree
        }
        else{ //if the node is not null, then compare the value to check if the node should be a right child
            node.right = insert(point, node.right, (node.axis + 1) % 3); //insert point as the right child and change the axis of split as we are going one level deeper in the tree
        }
        return node;
    }

    /** 
     * This recursive method is used to find the nearest point to the reference point that is not more than eps distance away
     * This method has been implemented using the pseudo-code provided in Programming Assignment P2 guide
     * @param Point3D point for which we will find neighbours
     * @param double eps: minimum acceptable distance to be called neighbour
     * @param ArrayList<Point3D> containing neighbours
     * @param KDnode node to check distance from
     * 
     * @return ArrayList<Point3D> containing neighbours of reference point
     */
    public ArrayList<Point3D> rangeQuery(Point3D ref, double eps, ArrayList<Point3D> neighbours, KDnode node) {
        if (node == null){
            return null;
        }
        if(ref.distance(node.point) < eps){
            neighbours.add(node.point);
        }
        if(ref.get(node.axis) - eps <= node.value){
            rangeQuery(ref, eps, neighbours, node.left);
        }
        if(ref.get(node.axis) + eps > node.value){
            rangeQuery(ref, eps, neighbours, node.right);
        }
        return neighbours;
    }
}
