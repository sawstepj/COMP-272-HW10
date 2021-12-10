import java.util.*;
import java.io.*;
public class DirectedGraph  {
    ArrayList<DirectedNodeList> dGraph;
    int numVertex;
    boolean [] marked;
    int[] finishing;
    HashMap<Integer, ArrayList<Integer>> map = new HashMap<>(); //arrayList of all vertices printed by DFT()
    ArrayList<Integer> scc = new ArrayList<>(); //create an arraylist to store each component coming out of recursive DFT
    // size of HashMap = number of scc
    // ArrayList = vertices to corresponding connected components
    int finishingNumber;


    public DirectedGraph() {
        dGraph = new ArrayList<>();
        numVertex = 0 ;
        finishingNumber = 0;
        finishing = new int[numVertex];
    }

    public DirectedGraph(int n) {
        numVertex = n;
        dGraph = new ArrayList<>(n);
        marked= new boolean[n];
        for (int i=0;i<numVertex;i++) {
            dGraph.add(new DirectedNodeList());
        }
        finishing = new int[numVertex];
        finishingNumber = 0;
    }

    public void addEdge(int u, int v) {
        // assume all vertices are created
        // directed edge u to v will cause outdegree of u to go up and indegree of v to go up.

        if (u>=0 && u<numVertex && v>=0 && v<numVertex) {
            if (u!=v) {
                getNeighborList(u).addToOutList(v);
                getNeighborList(v).addToInList(u);
            }
        }
        else throw new IndexOutOfBoundsException();
    }

    public DirectedNodeList getNeighborList(int u) {
        return dGraph.get(u);
    }

    public void printAdjacency(int u) {
        DirectedNodeList dnl = getNeighborList(u);
        System.out.println ("vertices going into "+u+"  "+dnl.getInList());
        System.out.println ("vertices going out of "+u+"  "+dnl.getOutList());
        System.out.println();
    }

    public void postOrderDepthFirstTraversal() {
        for (int i=0;i<numVertex;i++) {
            if (!marked[i]) {
                postOrderDFT(i);
            }
        }
        System.out.println(Arrays.toString(finishing)); //  finishing numbers array
    }
    public void postOrderDFT(int v){
        marked[v]=true;

        for (Integer u:dGraph.get(v).getInList()) {
            if (!marked[u]) {
                postOrderDFT(u);
            }
        }
        finishing[(finishingNumber++)] = v;
        // adding vertex, v, to
        // finishing # array where index # = finishing #
    }

    public void depthFirstTraversal() {
        for (int i = finishing.length - 1; i >= 0; i--) {
            if (!marked[i]) {
                // leaders come out of here; namely i
                scc = new ArrayList<>(); // initialize arraylist (empty)
                dFT(finishing[i]); //System.out.println(i);
                map.put(i, scc);
            }
        }
        System.out.println(map.size());
        // put the arraylist in the hashmap at key i
    }
    public void dFT(int v) {
        scc.add(v); // Strongly Connected Component
        marked[v] = true;

        for (Integer u:dGraph.get(v).getOutList()) { // for each integer in the outlist of vertex v
            if (!marked[u]) {
            dFT(u);
            }
        }
    }

    public  static DirectedGraph readAndStoreGraph(String fileName) {
        int maxV=0;
        int n = 1000000;
        DirectedGraph dg = new DirectedGraph(n);
        try{
            Scanner sc =new Scanner(new File(fileName));
            String s;
            // graph.add(new ArrayList<Integer>());
            while (sc.hasNextLine()) {
                s = sc.nextLine();
                if (s.isEmpty()) continue;
                String[] line= s.split("\t");
                // String[] line= s.split(",");
                int v1=  Integer.parseInt(line[0]);
                int v2=  Integer.parseInt(line[1]);
                int p= Math.max(v1,v2);
                if (p>maxV) maxV=p;
                dg.addEdge(v1, v2);// add to a data structure
            }
        }
        catch (FileNotFoundException e) {
        }
        //numEdges = edgeSet.size();
        return new DirectedGraph(maxV+1);
    }

    public static void main(String[] args) {
        int n = 5;
        String file = "Slashdot0902.txt";
        //DirectedGraph dg = readAndStoreGraph(file);
        DirectedGraph dg = new DirectedGraph(n);
        /*
        dg.addEdge(0,1);
        dg.addEdge(0,2);
        dg.addEdge(2,4);
        dg.addEdge(1,4);
        dg.addEdge(2,3);
        dg.addEdge(3,5);
        dg.addEdge(4,5);
        */
        // for (int i=0;i<dg.numVertex;i++)
        //  dg.printAdjacency(i);

        dg.addEdge(0, 1);
        dg.addEdge(1, 2);
        dg.addEdge(2, 3);
        dg.addEdge(3, 0);
        dg.addEdge(2, 4);
        dg.addEdge(4, 2);

        System.out.println("post order depth first traversal");
        dg.postOrderDepthFirstTraversal();
        System.out.println ("regular depth first traversal");
        dg.marked= new boolean[n];
        dg.depthFirstTraversal();
        dg.printAdjacency(1);
    }
}
