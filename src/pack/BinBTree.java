package pack;

import java.util.Objects;

import static pack.Util.ls;

public class BinBTree {

    static boolean HR = false, VR = false;

    BinBNode root = EmptyBinBNode.INSTANCE;

    public int getSize() {
        return root.getSize();
    }
    //определение высоты двойного бинарного дерева
    public int getHeightBinB() {
        return root.getHeightBinB();
    }
    //определение высоты
    public int getHeight() {
        return root.getHeight();
    }



    public BinBNode find(int key) {
        BinBNode current = root;
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

    public void insert(int key) {
        root = insert(key, root);
    }

    private BinBNode rotateRight(BinBNode node) {

        BinBNode other = node.getLeft();
        node.setLeft(other.getRight());
        other.setRight(node);

        return other;
    }

    private BinBNode rotateLeft(BinBNode node) {

        BinBNode other = node.getRight();
        node.balance = 0;
        other.balance = 0;

        node.setRight(other.getLeft());
        other.setLeft(node);

        return other;
    }
    //вставка
    private BinBNode insert(int key, BinBNode current) {

        if (current == EmptyBinBNode.INSTANCE) {
            VR = true;
            return new BinBNode(key);
        }
        else if (current.getKey() >= key) {
            current.setLeft(insert(key, current.getLeft()));
            if(VR) {
                if (current.balance == 0) { //3
                    BinBNode nextRoot = rotateRight(current);
                    nextRoot.balance = 1;

                    VR = false;
                    HR = true;

                    return nextRoot;
                }
                else {   //4
                    current.balance = 0;
                    VR = true;
                    HR = false;

                    return current;
                }
            }
            else {
                HR = false;
                return current;
            }
        }
        else if( current.getKey() < key ) {
            current.setRight(insert(key, current.getRight()));
            if(VR) {
                current.balance = 1;
                VR = false;
                HR = true;
                return current;
            }
            else if (HR) {
                if (current.balance == 1) { //2

                    BinBNode nextRoot = rotateLeft(current);
                    VR = true;
                    HR = false;
                    return nextRoot;
                }
                else {
                    HR = false;
                    return current;
                }
            }
        }

        return current;


    }


    @Override
    public String toString() {
        return "pack.Tree{" + ls +
                "root=" + root + ls +
                '}';
    }



    private int medianHeight = 0;
    public int sumHeightBinB() {
        medianHeight = 0;
        sumHeightBinB(root, 1);
        return medianHeight;
    }
    private void sumHeightBinB(BinBNode p, int h) {
        if (p == EmptyBinBNode.INSTANCE) return;
        if (p.balance == 1) {
            sumHeightBinB(p.getRight(), h);
            sumHeightBinB(p.getLeft(), h + 1);
        }
        else {
            sumHeightBinB(p.getLeft(), h + 1);
            sumHeightBinB(p.getRight(), h + 1);
        }
        medianHeight += h;
    }


    public int sumHeight() {
        return sumHeight(root, 1);
    }

    private int sumHeight(BinBNode p, int L) {
        int sd;
        if (p == EmptyBinBNode.INSTANCE)
            sd = 0;
        else
            sd = L + sumHeight(p.getLeft(), L + 1) + sumHeight(p.getRight(), L + 1);
        return sd;
    }

}


class BinBNode {


    int balance = 0;


    private int key;


    private BinBNode left = EmptyBinBNode.INSTANCE;
    private BinBNode right = EmptyBinBNode.INSTANCE;

    public BinBNode(int key) {
        this.key = key;
    }

    public int getKey() {
        return key;
    }

    public BinBNode getLeft() {
        return left;
    }

    public void setLeft(BinBNode left) {
        this.left = left;
    }

    public BinBNode getRight() {
        return right;
    }

    public void setRight(BinBNode right) {
        this.right = right;
    }

    @Override
    public String toString() {
        return "pack.Node{" +
                "left=" + left +
                ",  key=" + key +
                ", right=" + right +
                ", hashCode = " + this.hashCode() +
                '}';
    }


    public int getHeightBinB() {

        int leftH = left.getHeightBinB();
        int rightH = 0;
        if(BinBTree.VR) {
            rightH = right.getHeightBinB();
        }
        return 1 + Math.max(leftH, rightH);
    }

    public int getHeight() {

        int leftH = left.getHeight();
        int rightH = right.getHeight();

        return 1 + Math.max(leftH, rightH);
    }


    public boolean isEmpty() {
        return (this == EmptyBinBNode.INSTANCE);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BinBNode)) return false;
        BinBNode bNode = (BinBNode) o;
        return getKey() == bNode.getKey() && getLeft().equals(bNode.getLeft()) && getRight().equals(bNode.getRight());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getKey(), getLeft(), getRight());
    }

    public int getSize() {
        return left.getSize() + right.getSize() + 1;
    }

}

class EmptyBinBNode extends BinBNode {

    public static BinBNode INSTANCE = new EmptyBinBNode();


    public int getHeightBinB() {
        return 0;
    }

    public int getHeight() {
        return 0;
    }

    private EmptyBinBNode() {
        super(0);
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
    public BinBNode getLeft() {
        throw new RuntimeException("Not supported");
    }

    @Override
    public void setLeft(BinBNode left) {
        throw new RuntimeException("Not supported");
    }

    @Override
    public BinBNode getRight() {
        throw new RuntimeException("Not supported");
    }

    @Override
    public void setRight(BinBNode right) {
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

}