package com.example.haffman_encode.Tree.BinaryTree;

public interface BinaryTreeInterface<T extends Comparable<T>> {

    void insert(T v);

    T get(T v);

    boolean remove(T v);

    default void prev(TreeNodeInterface<T> node) {
        if (node == null) {
            return;
        }
        System.out.print(node.getVal() + " ");
        prev(node.getLeft());
        prev(node.getRight());
    }

    default void mid(TreeNodeInterface<T> node) {
        if (node == null) {
            return;
        }
        mid(node.getLeft());
        System.out.print(node.getVal() + " ");
        mid(node.getRight());
    }

    default void after(TreeNodeInterface<T> node) {
        if (node == null) {
            return;
        }
        after(node.getLeft());
        after(node.getRight());
        System.out.print(node.getVal() + " ");
    }

    default int deep(TreeNodeInterface<T> node) {
        if (node == null) {
            return 0;
        }
        return Math.max(deep(node.getLeft()), deep(node.getRight())) + 1;
    }

    default int nodeCount(TreeNodeInterface<T> node) {
        if (node == null) {
            return 0;
        }
        return nodeCount(node.getLeft()) + nodeCount(node.getRight()) + 1;
    }

    default T search(TreeNodeInterface<T> node, T key) {
        if (key == null || node == null) {
            return null;
        }
        if (node.getVal().equals(key)) {
            return node.getVal();
        }
        T left = search(node.getLeft(), key);
        T right = search(node.getRight(), key);
        if (left != null) {
            return left;
        }
        if (right != null) {
            return right;
        }
        return null;

    }

    default T binarySearch(TreeNodeInterface<T> root, T key) {
        if (key == null) {
            return null;
        }
        TreeNodeInterface<T> cur = root;
        while (cur != null) {
            if (key.compareTo(cur.getVal()) > 0) {
                cur = cur.getRight();
            } else if (key.compareTo(cur.getVal()) < 0) {
                cur = cur.getLeft();
            } else {
                return cur.getVal();
            }
        }
        return null;

    }
}
