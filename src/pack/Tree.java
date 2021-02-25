package pack;

import java.util.Arrays;
import java.util.Objects;

import static pack.Util.ls;


public class Tree {

    Node root = EmptyNode.INSTANCE;

    public boolean isValidBinarySearch() {
        return root.validate();
    }


    public int getSize() {
        return root.getSize();
    }

    public int getHeight() {
        return root.getHeight();
    }

    public float getAverageHeigth() {
        float size = root.getSize();
        int[] heights = root.getHeights();
        float total = 0;
        for (int height : heights) {
            total += height;
        }

        return total / size;
    }


    public Node find(int key) {
        Node current = root;
        if (current == null) return null;

        while (true) {

            if (current.isEmpty()) return null;

            if (current.getKey() < key) {
                current = current.getRight();
            } else if (current.getKey() > key) {
                current = current.getLeft();
            } else {
                return current;
            }
        }
    }


    public void insert(int key, String data) {
        Node node = new Node(key, data);
        if (root == EmptyNode.INSTANCE) {
            root = node;
        } else {
            Node current = root;
            Node prev = null;
            while (true) {
                prev = current;
                if (key < prev.getKey()) {
                    current = current.getLeft();
                    if (current.isEmpty()) {
                        prev.setLeft(node);
                        return;
                    }
                } else {
                    current = current.getRight();
                    if (current.isEmpty()) {
                        prev.setRight(node);
                        return;
                    }
                }
            }
        }
    }

    public Node insertAVL(Node p, int key, String data) {

        if( p == EmptyNode.INSTANCE ) return new Node(key, data);

        if( key < p.getKey() )
            p.setLeft( insertAVL(p.getLeft(), key, data) );
        else
            p.setRight( insertAVL(p.getRight(), key, data) );
        return balance(p);

/*

        Node node = new Node(key, data);
        if (root == EmptyNode.INSTANCE) {
            root = node;
        } else {
            Node current = root;
            Node prev = null;
            while (true) {
                prev = current;
                if (key < prev.getKey()) {
                    current = current.getLeft();
                    if (current.isEmpty()) {
                        prev.setLeft(node);
                        return;
                    }
                } else {
                    current = current.getRight();
                    if (current.isEmpty()) {
                        prev.setRight(node);
                        return;
                    }
                }
            }
        }*/
    }

    @Override
    public String toString() {
        return "pack.Tree{" + ls +
                "root=" + root + ls +
                '}';
    }

    private static Node subTree(int[] paramsInput) {
        if (paramsInput.length == 0)
            return EmptyNode.INSTANCE;
        int centerIndex = paramsInput.length / 2;
        int middle = paramsInput[centerIndex];

        int[] head = Arrays.copyOf(paramsInput, centerIndex);
        int[] tail = Arrays.copyOfRange(paramsInput, centerIndex + 1, paramsInput.length);
        Node left = subTree(head);
        Node right = subTree(tail);

        Node current = new Node(middle, Integer.toString(middle));
        current.setLeft(left);
        current.setRight(right);

        return current;

    }

    public static Tree ISDP(int[] input) {
        Tree tree = new Tree();
        Node root = subTree(input);
        tree.root = root;

        return tree;


    }

    public static Tree SDP(int[] input) {
        Tree tree = new Tree();
        for (int param :
                input) {
            tree.insert(param, Integer.toString(param));
        }

        return tree;
    }

    public static Tree AVL(int[] input) {
        Tree tree = new Tree();
        Node root = tree.root;
        for (int param : input) {
            root = tree.insertAVL(root, param, Integer.toString(param));
        }

        tree.root = root;

        return tree;
    }



    private Node rotateRight(Node nodeOrig) {
        Node origLeft = nodeOrig.getLeft();
        nodeOrig.setLeft(origLeft.getRight());
        origLeft.setRight(nodeOrig);

//        node* q = p->left;
//        p->left = q->right;
//        q->right = p;

//        fixheight(p);
//        fixheight(q);
        return origLeft;
    }

    private Node rotateLeft(Node q) {
        Node p = q.getRight();
        q.setRight(p.getLeft());
        p.setLeft(q);

//        node* p = q->right;
//        q->right = p->left;
//        p->left = q;

//        fixheight(q);
//        fixheight(p);
        return p;
    }

    private Node balance(Node p) {
//        fixheight(p);
        if( p.balanceFactor() == 2 ) {
            if( (p.getRight()).balanceFactor() < 0 )
                p.setRight(rotateRight(p.getRight()));
            return rotateLeft(p);
        }

        if( p.balanceFactor() == -2 ) {
            if( (p.getLeft()).balanceFactor() > 0 )
                p.setLeft(rotateLeft(p.getLeft()));
            return rotateRight(p);
        }
        return p; // балансировка не нужна
    }




}


class Node {


    private int key;
    private String data;

    private Node left = EmptyNode.INSTANCE;
    private Node right = EmptyNode.INSTANCE;

    public Node(int key, String value) {
        this.key = key;
        this.data = value;
    }

    public int getKey() {
        return key;
    }

    public String getData() {
        return data;
    }

    public Node getLeft() {
        return left;
    }

    public void setLeft(Node left) {
        this.left = left;
    }

    public Node getRight() {
        return right;
    }

    public void setRight(Node right) {
        this.right = right;
    }

    @Override
    public String toString() {
        return "pack.Node{" +
                "key=" + key +
                ", data='" + data + '\'' +
                ", left=" + left +
                ", right=" + right +
                ", hashCode = " + this.hashCode() +
                '}';
    }


    public int getHeight() {
        return 1 + Math.max(left.getHeight(), right.getHeight());
    }


    public boolean isEmpty() {
        return (this == EmptyNode.INSTANCE);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Node)) return false;
        Node node = (Node) o;
        return getKey() == node.getKey() && getData().equals(node.getData()) && getLeft().equals(node.getLeft()) && getRight().equals(node.getRight());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getKey(), getData(), getLeft(), getRight());
    }

    public int getSize() {
        return left.getSize() + right.getSize() + 1;
    }

    public int[] getHeights() {

        int[] thisHeight = new int[]{1};

        int[] lefts = left.getHeights();
        for (int i = 0; i < lefts.length; i++) {
            lefts[i]++;
        }
        int[] rights = right.getHeights();
        for (int i = 0; i < rights.length; i++) {
            rights[i]++;
        }
        return Util.concat(
                Util.concat(rights, lefts),
                thisHeight);

    }

    public int[] getKeys() {

        int[] thisKey = new int[]{this.key};

        int[] lefts = left.getKeys();

        int[] rights = right.getKeys();

        return Util.concat(
                Util.concat(rights, lefts),
                thisKey);

    }

    public boolean validateLevel() {
        int[] rightKeys = right.getKeys();
        for (int rightKey : rightKeys) {
            if (rightKey < this.key) return false;
        }
        int[] leftKeys = left.getKeys();
        for (int leftKey : leftKeys) {
            if (leftKey > this.key) return false;
        }
        return true;
    }

    public boolean validate() {
        return left.validate() && validateLevel() && right.validate();
    }

    public int balanceFactor() {

        return (right.getHeight() - left.getHeight());
    }
}

class EmptyNode extends Node {

    public static Node INSTANCE = new EmptyNode();


    public int getHeight() {
        return 0;
    }

    private EmptyNode() {
        super(0, null);
    }

    @Override
    public String toString() {
        return "pack.EmptyNode{ hashCode: " + this.hashCode() + " }";
    }

    @Override
    public int getKey() {
        throw new RuntimeException("Not supported");
    }

    @Override
    public String getData() {
        throw new RuntimeException("Not supported");
    }

    @Override
    public Node getLeft() {
        throw new RuntimeException("Not supported");
    }

    @Override
    public void setLeft(Node left) {
        throw new RuntimeException("Not supported");
    }

    @Override
    public Node getRight() {
        throw new RuntimeException("Not supported");
    }

    @Override
    public void setRight(Node right) {
        throw new RuntimeException("Not supported");
    }

    @Override
    public boolean equals(Object o) {
        return (this == o);
    }

    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    public int getSize() {
        return 0;
    }

    @Override
    public int[] getHeights() {
        return new int[]{};
    }

    @Override
    public int[] getKeys() {
        return new int[]{};
    }

    @Override
    public boolean validate() {
        return true;
    }

    @Override
    public boolean validateLevel() {
        return true;
    }

    @Override
    public int balanceFactor() {
        return 0;
    }
}