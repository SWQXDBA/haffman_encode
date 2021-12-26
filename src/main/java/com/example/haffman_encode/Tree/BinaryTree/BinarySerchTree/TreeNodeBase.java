package com.example.haffman_encode.Tree.BinaryTree.BinarySerchTree;


import com.example.haffman_encode.Tree.BinaryTree.TreeNodeInterface;

public class TreeNodeBase<T extends Comparable<T>> implements TreeNodeInterface<T>, Comparable<TreeNodeBase<T>> {
    public T val;
    public TreeNodeBase<T> left;
    public TreeNodeBase<T> right;

    public TreeNodeBase(T x) {
        val = x;
    }

    @Override
    public String getValString() {
        return val + "";
    }

    @Override
    public T getVal() {
        return val;
    }

    @Override
    public void setVal(T val) {
        this.val = val;
    }

    @Override
    public TreeNodeInterface<T> getLeft() {
        return left;
    }

    @Override
    public void setLeft(TreeNodeInterface<T> left) {
        this.left = (TreeNodeBase<T>) left;
    }

    @Override
    public TreeNodeInterface<T> getRight() {
        return right;
    }

    @Override
    public void setRight(TreeNodeInterface<T> right) {
        this.right = (TreeNodeBase<T>) right;
    }

    @Override
    public int compareTo(TreeNodeBase<T> o) {
        return val.compareTo(o.getVal());
    }
}
