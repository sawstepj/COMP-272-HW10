import java.util.*;
import java.io.*;
public class HashedDirectedGraph {
    HashMap<Integer, DirectedNodeList> hDGraph;
    int numVertex;
    boolean[] marked;
    int[] finishing;
    HashMap<Integer, ArrayList<Integer>> map = new HashMap<>(); //arrayList of all vertices printed by DFT()
    ArrayList<Integer> leaders = new ArrayList<>(); //create an arraylist to store each component coming out of recursive DFT
    int numEdges;

    public HashedDirectedGraph() {
        hDGraph = new HashMap<>();
        numVertex = 10559;

    }

    // n is the number of components
    public HashedDirectedGraph(int n) {
        numVertex = n;
        hDGraph = new HashMap<>(n);
    }
    // using the keySet of the other HashMap
    // k is the leader
    public void addVertex(int k){
        if (k>=0 && k<numVertex) {
            hDGraph.put(k, new DirectedNodeList());
        }
        // k is within the bounds of 0 and max Vertex label of the original directed graph
        // hDgraph,add(k, new DirectedNodeList());
    }

    public void addEdge(int u, int v) {
        // assume all vertices are created
        // directed edge u to v will cause outdegree of u to go up and indegree of v to go up.

        // change conditional to correspond to above bound condition
        if (u >= 0 && u < numVertex && v >= 0 && v < numVertex) {
            if (!isEdgePresent(u, v)) {
                if (u != v) {
                    getNeighborList(u).addToOutList(v);
                    getNeighborList(v).addToInList(u);
                }
            } else throw new IndexOutOfBoundsException();
            //do throw indexout of bounds exception if u or v is outside of the bounds
        }
    }

    public DirectedNodeList getNeighborList(int u) {
        return hDGraph.get(u);
    }

    // TODO getter methods for numVertex and numEdges

    public int getNumVertex(){
        return numVertex;
    }

    public int getNumEdges(){
        return numEdges;
    }

    public boolean isEdgePresent(int u, int v){
        // check if (u,v) is already present in the reduced graph hDGraph
        return true;
    }

    public static void main(String[] args){
        int n = 100000;
        HashedDirectedGraph HDG = new HashedDirectedGraph(n);

        HDG.addVertex(1);
        HDG.addVertex(2);
        HDG.addEdge(1, 2);
        // create vertices
        // run through edge list and add edges

    }

}