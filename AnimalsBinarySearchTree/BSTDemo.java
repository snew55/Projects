import java.io.File;

public class BSTDemo {


    public static void main(String[] args){

        BST bitree = new BST();

        bitree.loadTree();
        bitree.printTree();

        bitree.identify();

        bitree.saveTree();



    }
}
