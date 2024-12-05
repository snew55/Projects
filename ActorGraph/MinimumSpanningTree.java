import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;

public class MinimumSpanningTree {


    //Kruskals Algorithm to find minimum spanning tree
    public HashSet<Edge> kruskalsAlgorithm(WeightedGraph graph) {
        HashSet<Edge> mst = new HashSet<>();//final HashSet to return
        HashMap<String, String> ccm = new HashMap<>();//connected component marker
        PriorityQueue<Edge> edgeQueue = new PriorityQueue<>(graph.getEdges());//edgeQueue for retrieving edges later

        for (String vertex : graph.adjList.keySet()) {//for loop to fill up the ccm using the keyset from our adjlist
            ccm.put(vertex, vertex);
        }

        while (!edgeQueue.isEmpty() && mst.size() < graph.getNumVertices() - 1) {//traversing a while loop until there
            Edge edge = edgeQueue.poll();                                        //no more edges
            //find each end of the edges
            String root1 = find(ccm, edge.getVertices()[0]);
            String root2 = find(ccm, edge.getVertices()[1]);

            if (!root1.equals(root2)) {
                mst.add(edge);
                ccm.put(root2, root1);  // Union operation
            }
        }
        return mst;
    }

    //helper method for KruskalsAlgorithm
    private String find(HashMap<String, String> ccm, String vertex) {
        if (vertex.equals(ccm.get(vertex))) {//ifstatement for basecase
            return vertex;
        } else {//else statement using recursion to find which vertex to use
            String root = find(ccm, ccm.get(vertex));
            ccm.put(vertex, root);  // Path compression
            return root;
        }
    }
}
