package com.example.libjava;

public class NodeText {

    public static int num = 0;
    public static void main(String[] args) {
        int n = 5;

        Node node = creatTree(n);
        System.out.println(node.toString());;
    }

    public static Node creatTree(int n) {
        if (n <= 0) return null;

        Node root = new Node(null);
        if (n == 1) return root;
        num = 1;
        createNode(root, n);
        return root;
    }

    public static void createNode(Node parent,int maxTreeLevel) {
        Node leftNode = new Node(null);
        Node rightNode = new Node(null);

        parent.setLeftChild(leftNode);
        parent.setRightChild(rightNode);

        num ++;

        if (num >= maxTreeLevel) return;
        createNode(leftNode, maxTreeLevel);
        createNode(rightNode, maxTreeLevel);
    }
}
