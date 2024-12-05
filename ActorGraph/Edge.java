public class Edge implements Comparable<Edge> {
    private String actor1;//Vertex 1
    private String actor2;//Vertex 2
    private int weight;//Weight, even though it will be 1 for all edges
    private String movie;//movie as the connector for the 2 vertices

    public Edge(String actor1, String actor2, int weight, String movie) {//Constructor
        this.actor1 = actor1;
        this.actor2 = actor2;
        this.weight = weight;
        this.movie = movie;
    }

    public String[] getVertices() {//getter method to return vertices of edge
        return new String[] {actor1, actor2};
    }

    public int getWeight() {//returns the weight of 1 of the edge
        return weight;
    }

    public String getMovie() {//returns the edge's movie
        return movie;
    }

    @Override
    //toString method so we do not print memory values
    public String toString() {
        return actor1 + " - " + actor2 + " (" + movie + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Edge edge = (Edge) obj;
        return actor1.equals(edge.actor1) && actor2.equals(edge.actor2) && movie.equals(edge.movie);
    }

    @Override
    public int hashCode() {
        return (actor1 + actor2 + movie).hashCode();
    }

    @Override
    public int compareTo(Edge other) {
        return Integer.compare(this.weight, other.weight);
    }

    public String getActor1() {//getter method for first vertex
        return this.actor1;
    }

    public String getActor2() {//getter method for second vertex
        return this.actor2;
    }
}
