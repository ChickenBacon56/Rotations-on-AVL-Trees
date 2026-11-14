/*
Rotations on AVL Trees
Noah Farr 
Date Created: 11/12/26
Date Edited: 11/14/26
*/



public class Main {

    // Node class
    private class Node {
        int key;
        int height;
        Node left;
        Node right;

        Node(int key) {
            this.key = key;
            this.height = 0;
            this.left = null;
            this.right = null;
        }
    }

    private Node root;

    // Get the height of a node
    private int height(Node node) {
        if (node == null) {
            return -1;
        }
        return node.height;
    }

    // Update the height of a node
    private void updateHeight(Node node) {
        node.height = 1 + Math.max(
            height(node.left),
            height(node.right)
        );
    }

    // Calculate balance factor
    // Positive means right-heavy, negative means left-heavy
    private int balanceFactor(Node node) {
        if (node == null) {
            return 0;
        }

        return height(node.right) - height(node.left);
    }

    // LL Rotation (Right Rotation)
    private Node rotateLL(Node node) {
        Node newRoot = node.left;
        Node middle = newRoot.right;

        newRoot.right = node;
        node.left = middle;

        updateHeight(node);
        updateHeight(newRoot);

        return newRoot;
    }

    // RR Rotation (Left Rotation)
    private Node rotateRR(Node node) {
        Node newRoot = node.right;
        Node middle = newRoot.left;

        newRoot.left = node;
        node.right = middle;

        updateHeight(node);
        updateHeight(newRoot);

        return newRoot;
    }

    // LR Rotation (Left-Right Rotation)
    private Node rotateLR(Node node) {
        node.left = rotateRR(node.left);
        return rotateLL(node);
    }

    // RL Rotation (Right-Left Rotation)
    private Node rotateRL(Node node) {
        node.right = rotateLL(node.right);
        return rotateRR(node);
    }

    // Insert a new key
    public void insert(int key) {
        root = insert(root, key);
    }

    // Recursive insertion method
    private Node insert(Node node, int key) {

        // Normal BST insertion
        if (node == null) {
            return new Node(key);
        }

        if (key < node.key) {
            node.left = insert(node.left, key);
        }
        else if (key > node.key) {
            node.right = insert(node.right, key);
        }
        else {
            return node;
        }

        // Update height after insertion
        updateHeight(node);

        // Check balance factor
        int balance = balanceFactor(node);

        // RR Case
        if (balance > 1 && key > node.right.key) {
            System.out.println("After RR rotation at node "
                               + node.key);

            return rotateRR(node);
        }

        // LL Case
        if (balance < -1 && key < node.left.key) {
            System.out.println("After LL rotation at node "
                               + node.key);

            return rotateLL(node);
        }

        // RL Case
        if (balance > 1 && key < node.right.key) {
            System.out.println("After RL rotation at node "
                               + node.key);

            return rotateRL(node);
        }

        // LR Case
        if (balance < -1 && key > node.left.key) {
            System.out.println("After LR rotation at node "
                               + node.key);

            return rotateLR(node);
        }

        return node;
    }

    // Print nodes in sorted order
    public void inorder() {
        inorder(root);
        System.out.println();
    }

    private void inorder(Node node) {
        if (node != null) {
            inorder(node.left);
            System.out.print(node.key + " ");
            inorder(node.right);
        }
    }

    // Print tree information
    public void printTree() {
        System.out.println();

        System.out.printf(
            "%-10s %-10s %-15s %-15s %-15s%n",
            "Node",
            "Height",
            "Balance Factor",
            "LeftChild",
            "RightChild"
        );

        printTree(root);
    }

    private void printTree(Node node) {
        if (node != null) {

            printTree(node.left);

            String leftChild = (node.left == null)
                ? "null" : String.valueOf(node.left.key);

            String rightChild = (node.right == null)
                ? "null" : String.valueOf(node.right.key);

            System.out.printf(
                "%-10d %-10d %-15d %-15s %-15s%n",
                node.key,
                node.height,
                balanceFactor(node),
                leftChild,
                rightChild
            );

            printTree(node.right);
        }
    }

    // Get root height
    public int getHeight() {
        return height(root);
    }

    // Main method
    public static void main(String[] args) {

        Main tree = new Main();

        int[] values = {25, 20, 5, 34, 50, 30, 10};

        for (int value : values) {
            tree.insert(value);
            tree.inorder();
        }

        tree.printTree();

        System.out.println("\nHeight of root: "
                           + tree.getHeight());
    }
}
