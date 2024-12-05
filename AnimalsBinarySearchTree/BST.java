import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class BST {

    private Node root;
    private int currentNodeValue = 0;

    public BST(){
        this.root = null;
    }

    public void loadTree(){



        try {
            File trees = new File("tree.txt");
            Scanner s = new Scanner(trees);

            while (s.hasNextLine()) {
                String data = s.nextLine().trim();
                if (!data.isEmpty() && data.contains(",")) {
                    String[] line = data.split(",");
                    int key = Integer.parseInt(line[0]);
                    String value = line[1];
                    insert(key, value);
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    public void insert(int newValue, String newCharacteristic) {

        if(root == null) {
            root = new Node(newValue, newCharacteristic);
        }
        else {

            Node currentNode = root;
            boolean placed = false;

            while(!placed) {

                if(currentNode.getValue() == newValue) {
                    placed = true;
                    System.out.println("No duplicate values allowed");
                }
                else if(newValue < currentNode.getValue()) {
                    //move left
                    if(currentNode.getYesChild() == null) {
                        // cant move left, so we found insertion spot
                        //insert Node
                        currentNode.setYesChild(new Node(newValue, newCharacteristic));
                        currentNode.getYesChild().setParent(currentNode);
                        placed = true;
                    }
                    else {
                        // otherwise move left
                        currentNode = currentNode.getYesChild();
                    }
                }
                else {
                    //move right
                    if(currentNode.getNoChild() == null) {
                        //cant move right, insert new node
                        currentNode.setNoChild(new Node(newValue, newCharacteristic));
                        currentNode.getNoChild().setParent(currentNode);
                        placed = true;
                    }
                    else {
                        //move right
                        currentNode = currentNode.getNoChild();
                    }
                }


            }



        }



    }
    public void printTree() {
        if (root == null) {
            System.out.println("The tree is empty");
            return;
        }

        Queue<Node> queue = new LinkedList<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            Node current = queue.poll();
            System.out.println(current.getValue() + ", " + current.getCharacteristic());

            if (current.getYesChild() != null) {
                queue.add(current.getYesChild());
            }

            if (current.getNoChild() != null) {
                queue.add(current.getNoChild());
            }
        }
    }

    public void identify() {
        LinkedList<String> traits = new LinkedList<>();
        Node current = root;
        boolean stop = false;

        while (!stop) {
            Scanner s = new Scanner(System.in);
            System.out.println("Do you have another animal to identify? (Y/N) > ");
            String input = s.nextLine();
            System.out.println("You entered: '" + input + "'");
            if (input.trim().equalsIgnoreCase("Y")) {
                while (current.getNoChild() != null && current.getYesChild() != null) {
                    Scanner sc = new Scanner(System.in);
                    System.out.println("Is it " + current.getCharacteristic());
                    String answer = sc.nextLine();

                    if (answer.trim().equalsIgnoreCase("Y")) {
                        current = current.getYesChild();
                        traits.add(current.getCharacteristic());
                    } else if (answer.trim().equalsIgnoreCase("N")) {
                        current = current.getNoChild();
                    }
                }
                System.out.println("Hmm... I think I know.");
                Scanner sc2 = new Scanner(System.in);
                System.out.println("Is it a " + current.getCharacteristic());
                String answer2 = sc2.nextLine();
                if (answer2.trim().equalsIgnoreCase("Y")) {
                    System.out.println("Good! All done.");
                } else if (answer2.trim().equalsIgnoreCase("N")) {
                    System.out.print("I don't know any animals that are ");
                    for (String t : traits) {
                        System.out.print(t + "  ");


                    }
                    Scanner sc3 = new Scanner(System.in);
                    System.out.println("What is the new Animal > ");
                    String newAnimal = sc3.nextLine();

                    Scanner sc4 = new Scanner(System.in);
                    System.out.println("What characteristic does " + newAnimal + "have that a " + current.getCharacteristic() + "does not > ");
                    String newTrait = sc4.nextLine().trim();


                    int newValueForTrait, newValueForAnimal;
                    if (current.getParent() != null) {
                        Node parent = current.getParent();
                        if (parent.getYesChild() == current) {
                            newValueForTrait = (parent.getValue() + current.getValue()) / 2;
                        } else {

                            newValueForTrait = (parent.getValue() + current.getValue() + 1) / 2;
                        }
                    } else {
                        newValueForTrait = current.getValue() - 1;
                    }
                    newValueForAnimal = newValueForTrait + 1;

                    Node newNode = new Node(newValueForTrait, newTrait);
                    Node newAnimalNode = new Node(newValueForAnimal, newAnimal);

                    newNode.setYesChild(newAnimalNode);
                    newNode.setNoChild(current);
                    newAnimalNode.setParent(newNode);

                    if (current.getParent() != null) {
                        Node parent = current.getParent();
                        if (parent.getYesChild() == current) {
                            parent.setYesChild(newNode);
                        } else {
                            parent.setNoChild(newNode);
                        }
                        newNode.setParent(parent);
                    } else {
                        this.root = newNode;
                    }

                    current.setParent(newNode);

                }


            } else if (input.trim().equalsIgnoreCase("N")) {

            }
            {
                System.out.println("Exiting...");
                stop = true;
            }
        }
    }

    public void saveTree() {

        currentNodeValue = 0;
        reassignValues(root);

        if (root == null) {
            System.out.println("The tree is empty, nothing to save.");
            return;
        }

        Queue<Node> queue = new LinkedList<>();
        queue.add(root);
        StringBuilder sb = new StringBuilder();

        while (!queue.isEmpty()) {
            Node current = queue.poll();

            sb.append(current.getValue()).append(",").append(current.getCharacteristic()).append("\n");

            if (current.getYesChild() != null) {
                queue.add(current.getYesChild());
            }

            if (current.getNoChild() != null) {
                queue.add(current.getNoChild());
            }
        }


        try (FileWriter writer = new FileWriter("tree.txt")) {
            writer.write(sb.toString());
            System.out.println("Tree saved successfully.");
        } catch (IOException e) {
            System.err.println("An error occurred while saving the tree: " + e.getMessage());
        }
    }

    public void reassignValues(Node node) {
        if (node == null) {
            return;
        }
        reassignValues(node.getYesChild());

        node.setValue(currentNodeValue++);

        reassignValues(node.getNoChild());
    }


}


