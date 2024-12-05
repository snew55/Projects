import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.LinkedList;

public class WeightedGraphDemo {
    public static void main(String[] args) throws FileNotFoundException {


        Scanner scanner = new Scanner(System.in);//Scanner for print menu
        int choice = -1;

        while (choice != 4) { //While loop to keep program open until user enters 4

            System.out.println("Enter your choice");
            System.out.println("1. Print out MST information");
            System.out.println("2. Find Shortest Path from one Actor to another");
            System.out.println("3. Find the most actors per movie and busiest actors (actors with the most movies)");
            System.out.println("4. Exit");

            try {//try catch statements to make sure there is a valid input
                choice = Integer.parseInt(scanner.nextLine());

            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number 1-4.");
                continue;
            }

            switch (choice) {
                case 1://MST information option
                    try { //try catch to make sure there is a valid file to run WeightedGraph with

                        WeightedGraph actorGraph = new WeightedGraph("actors.txt");
                        MinimumSpanningTree mst = new MinimumSpanningTree();
                        HashSet<Edge> resultMST = mst.kruskalsAlgorithm(actorGraph);

                        System.out.println("Edges in MST:");
                        System.out.println("-------------");

                        //i and j variables for numbering edges and movies
                        int i = 1;
                        for (Edge edge: resultMST){
                            System.out.println(i + ". " + edge);
                            i++;
                        }

                        System.out.println("List of movies that cover all actors:");
                        System.out.println("-------------");

                        int j = 1;
                        for (Edge edge: resultMST){
                            System.out.println(j + ". " + edge.getMovie());
                            j++;
                        }


                    } catch (FileNotFoundException e) {
                        System.out.println("File not found: " + e.getMessage());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                    break;
                case 2: //Shortest Path option
                    try {//try catch to make sure there is a valid input file for WeightedGraph
                        WeightedGraph actorGraph = new WeightedGraph("actors.txt");
                        ShortestPath shortPath = new ShortestPath();

                        System.out.println("Enter a starting actor:");
                        String startingActor = scanner.nextLine();
                        System.out.println("Enter a destination actor:");
                        String destinationActor = scanner.nextLine();

                        shortPath.computeShortestPath(actorGraph, startingActor);

                        if (shortPath.hasPath(destinationActor)) {
                            System.out.println("Path from " + startingActor + " to " + destinationActor + ":");
                            LinkedList<String> path = shortPath.getPathTo(destinationActor);
                            for (String step : path) {
                                System.out.println(step);
                            }
                            System.out.println("Total hops: " + path.size());
                        } else {
                            System.out.println("Path does not exist to " + destinationActor);
                        }

                    } catch (FileNotFoundException e) {
                        System.out.println("File not found: " + e.getMessage());
                    }


                    break;
                case 3://My custom method option which ranks actors with the most movies, and movies with the most actors
                    try {//another try catch statement to make sure that there is a valid input file for WeightedGraph
                        WeightedGraph actorGraph = new WeightedGraph("actors.txt");
                        actorGraph.printMoviesAndActorsByPopularity();
                    } catch (FileNotFoundException e) {
                        System.out.println("File not found: " + e.getMessage());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    break;
                case 4://exit program option
                    System.out.println("Exiting program.");
                    break;
                default:
                    System.out.println("Invalid choice. Please choose a valid option.");
            }
        }
        scanner.close();


    }


}
