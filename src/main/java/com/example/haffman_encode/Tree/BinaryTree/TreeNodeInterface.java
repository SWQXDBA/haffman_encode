package com.example.haffman_encode.Tree.BinaryTree;

public interface TreeNodeInterface<T extends Comparable<T>> {
    String getValString();

    T getVal();

    void setVal(T val);

    TreeNodeInterface<T> getLeft();

    void setLeft(TreeNodeInterface<T> left);

    TreeNodeInterface<T> getRight();

    void setRight(TreeNodeInterface<T> right);



}
