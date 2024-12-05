import java.util.HashMap;
import java.util.LinkedList;
import java.util.PriorityQueue;

public class ShortestPath {//Constructor
    //HashMaps to track amount of hops and edges
    private HashMap<String, Double> distance;
    private HashMap<String, Edge> previous;

    public void computeShortestPath(WeightedGraph graph, String startActor) {
        //Set up HashMaps to total up the distance plus another HashMap for printing out
        distance = new HashMap<>();
        previous = new HashMap<>();

        //load our distance hashMap
        for (String actor : graph.adjList.keySet()) {
            distance.put(actor, Double.POSITIVE_INFINITY);
        }
        //Declare our starting actor with a double of 0.0 hops
        distance.put(startActor, 0.0);


        //Set up a priority queue to track, as well as for our while loop
        PriorityQueue<PriorityVertex> visitQueue = new PriorityQueue<>();
        visitQueue.add(new PriorityVertex(startActor, 0));

        while (!visitQueue.isEmpty()) {//loop until the priority queue is empty
            PriorityVertex currentVertex = visitQueue.poll();
            String currentActor = currentVertex.getVertex();

            if (graph.adjList.containsKey(currentActor)) {//only hop to an edge with one matching vertex
                for (Edge edge : graph.adjList.get(currentActor)) {
                    String neighbor = edge.getActor2();//get new second actor
                    double newDistance = distance.get(currentActor) + edge.getWeight();//add weight to total distance

                    if (newDistance < distance.get(neighbor)) {
                        distance.put(neighbor, newDistance);
                        previous.put(neighbor, edge);

                        visitQueue.remove(new PriorityVertex(neighbor, 0));
                        visitQueue.add(new PriorityVertex(neighbor, newDistance));
                    }
                }
            }
        }


    }

    //Helper method for computing the shortest path, determines if there is a path in the graph
    public boolean hasPath(String destination) {
        return distance.get(destination) < Double.POSITIVE_INFINITY;
    }

    //Helper Method for computing the shortest path, mainly used for printing out all the hops in order
    public LinkedList<String> getPathTo(String destination) {
        if (!hasPath(destination)) {//if statement to check if there is a path
            return null;
        } else {
            //declare a LinkedList of Strings for printing out the path
            LinkedList<String> path = new LinkedList<>();
            for (Edge edge = previous.get(destination); edge != null; edge = previous.get(edge.getActor1())) {
                // Create a string for the edge in the desired format.
                String pathStep = edge.getActor1() + " acted with " + edge.getActor2() + " in " + edge.getMovie();
                path.addFirst(pathStep);//LinkedList documentation method
            }
            return path;
        }
    }
}
