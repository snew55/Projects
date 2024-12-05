import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

public class WeightedGraph {
    HashMap<String, LinkedList<Edge>> adjList;//Our graph
    private HashSet<Edge> edges;//HashSet to keep track of all our edges

    public WeightedGraph(String filePath) throws FileNotFoundException {//file not found exception for when we call
        this.adjList = new HashMap<>();                                 //WeightedGraph in our demo class
        this.edges = new HashSet<>();
        loadGraph(filePath);//load the graph from the constructor
    }

    //Load Graph method to put the text file into our adjacency list and edges HashSet
    private void loadGraph(String filePath) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(filePath));
        HashMap<String, Set<String>> movieToActors = new HashMap<>();

        // First pass: build a map of movies to actors
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] parts = line.split("\\|");
            String actor = parts[0];
            String movie = parts[1];

            movieToActors.computeIfAbsent(movie, k -> new HashSet<>()).add(actor);
        }
        scanner.close();

        // Second pass: for each movie, connect every pair of actors
        for (String movie : movieToActors.keySet()) {
            List<String> actorsInMovie = new ArrayList<>(movieToActors.get(movie));
            for (int i = 0; i < actorsInMovie.size(); i++) {
                for (int j = i + 1; j < actorsInMovie.size(); j++) {
                    String actor1 = actorsInMovie.get(i);
                    String actor2 = actorsInMovie.get(j);
                    connectActors(actor1, actor2, movie);
                }
            }
        }
    }

    //helper method for loading the graph from the text file
    //Checks for duplicate edges to make sure there aren't double edges
    private void connectActors(String actor1, String actor2, String movie) {
        adjList.putIfAbsent(actor1, new LinkedList<>());
        adjList.putIfAbsent(actor2, new LinkedList<>());

        Edge edge = new Edge(actor1, actor2, 1, movie);
        Edge reverseEdge = new Edge(actor2, actor1, 1, movie);

        if (edges.add(edge)) {
            adjList.get(actor1).add(edge);
        }
        if (edges.add(reverseEdge)) {
            adjList.get(actor2).add(reverseEdge);
        }
    }

    //printGraph method in case anyone feels the need to modify the program and print off the entire graph
    public void printGraph() {
        for (String actor : adjList.keySet()) {
            System.out.print(actor + ": ");
            for (Edge edge : adjList.get(actor)) {
                System.out.print(edge.toString() + ", ");
            }
            System.out.println();
        }
    }

    public HashSet<Edge> getEdges() {
        return edges;
    }//getter method to return all the edges into a HashSet

    public int getNumVertices() {
        return adjList.size();
    }//getter method to find number of vertices


    //My custom option will be to rank and print actors and movies in the specified text files
    //It will rank the actors who have the most movies in the text files from greatest to least
    //It will rank movies with the most actors participating in the movie from greatest to least
    public void printMoviesAndActorsByPopularity() {
        HashMap<String, Integer> movieFrequency = new HashMap<>();
        HashMap<String, HashSet<String>> movieToActors = new HashMap<>();
        HashMap<String, Integer> actorMoviesCount = new HashMap<>();

        // Go through each edge once and map movies to actors without double counting
        for (LinkedList<Edge> edges : adjList.values()) {
            for (Edge edge : edges) {
                movieToActors.computeIfAbsent(edge.getMovie(), k -> new HashSet<>()).add(edge.getActor1());
                movieToActors.get(edge.getMovie()).add(edge.getActor2());
            }
        }

        // Now calculate the movie frequency and actor counts
        for (Map.Entry<String, HashSet<String>> entry : movieToActors.entrySet()) {
            String movie = entry.getKey();
            HashSet<String> actors = entry.getValue();

            movieFrequency.put(movie, actors.size());
            for (String actor : actors) {
                actorMoviesCount.put(actor, actorMoviesCount.getOrDefault(actor, 0) + 1);
            }
        }

        // Sort movies by popularity
        List<Map.Entry<String, Integer>> moviesRanked = movieFrequency.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .collect(Collectors.toList());

        // Sort actors by the number of movies they're in
        List<Map.Entry<String, Integer>> actorsRanked = actorMoviesCount.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .collect(Collectors.toList());


        //Print Statement loops, ints i and j are for numbering
        System.out.println("Movies ranked by popularity:");
        int i = 1;
        for (Map.Entry<String, Integer> entry : moviesRanked) {
            System.out.println( i++ + ". " + entry.getKey() + " - " + entry.getValue());
        }

        System.out.println("\nActors ranked by the number of movies they're in:");
        int j = 1;
        for (Map.Entry<String, Integer> entry : actorsRanked) {
            System.out.println(j++ + ". " + entry.getKey() + " - " + entry.getValue());
        }
    }
}
