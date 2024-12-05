public class PriorityVertex implements Comparable<PriorityVertex> {

    private String vertex;//for declaring priority vertex
    private double priority;//hops

    public PriorityVertex(String vertex, double priority) {
        this.vertex = vertex;
        this.priority = priority;
    }

    public String getVertex() {//getter method
        return vertex;
    }

    @Override
    //uses Double object for comparable method
    public int compareTo(PriorityVertex other) {
        return Double.compare(this.priority, other.priority);
    }
}


