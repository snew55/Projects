public class Node {
    private int value;
    private String characteristic;
    private Node parent;
    private Node yesChild;
    private Node noChild;


    public Node(int value, String characteristic) {
        this.value = value;
        this.characteristic = characteristic;

    }


    public int getValue() {
        return this.value;
    }

    public String getCharacteristic() {
        return this.characteristic;
    }

    public Node getParent() {
        return this.parent;
    }

    public Node getYesChild() {
        return this.yesChild;
    }

    public Node getNoChild() {
        return this.noChild;
    }

    // Setter methods
    public void setValue(int value) {
        this.value = value;
    }

    public void setCharacteristic(String characteristic) {
        this.characteristic = characteristic;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public void setYesChild(Node yesChild) {
        this.yesChild = yesChild;

    }

    public void setNoChild(Node noChild) {
        this.noChild = noChild;

    }

    public void printInfo(){
        System.out.println(value + ", " + characteristic);
    }
}









