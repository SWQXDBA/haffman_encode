package com.example.haffman_encode.Tree.BinaryTree.BinarySerchTree;



import com.example.haffman_encode.Tree.BinaryTree.BinaryTreeInterface;
import com.example.haffman_encode.Tree.BinaryTree.TreePrinter;

import java.util.ArrayList;
import java.util.List;

public class MyBinarySearchTree<T extends Comparable<T>> implements BinaryTreeInterface<T> {
    public TreeNodeBase<T> root;

    public MyBinarySearchTree(TreeNodeBase<T> root) {
        this.root = root;
    }

    public MyBinarySearchTree() {
    }

    public static void main(String[] args) {
        MyBinarySearchTree<Integer> tree = new MyBinarySearchTree<>();
        tree.insert(20);
        tree.insert(15);
        tree.insert(25);
        tree.insert(11);
        tree.insert(17);
        tree.insert(24);
        tree.insert(27);
        tree.insert(1);
        tree.insert(12);
        tree.insert(16);
        tree.insert(18);
        tree.insert(23);
        tree.insert(22);
        tree.insert(26);
        tree.insert(29);


        tree.showTree("...");
        System.out.println(tree.list());
        tree.remove(11);
        tree.showTree("  ");
        System.out.println(tree.list());
        tree.remove(15);
        tree.showTree("  ");
        System.out.println(tree.list());
        tree.remove(25);
        tree.showTree("  ");
    }

    @Override
    public T get(T key) {
        if (key == null) {
            return null;
        }
        TreeNodeBase<T> cur = root;
        while (cur != null) {
            if (key.compareTo(cur.val) > 0) {
                cur = cur.right;
            } else if (key.compareTo(cur.val) < 0) {
                cur = cur.left;
            } else {
                return cur.val;
            }
        }
        return null;
    }

    public TreeNodeBase<T> getMin(TreeNodeBase<T> root) {
        while (root != null && root.left != null) {
            root = root.left;
        }
        return root;
    }

    public TreeNodeBase<T> getMax(TreeNodeBase<T> root) {
        while (root != null && root.right != null) {
            root = root.right;
        }
        return root;
    }

    @Override
    public boolean remove(T key) {
        if (key == null) {
            return true;
        }
        TreeNodeBase<T> cur = root;
        TreeNodeBase<T> parent = cur;
        while (cur != null) {
            if (key.compareTo(cur.val) > 0) {
                parent = cur;
                cur = cur.right;
            } else if (key.compareTo(cur.val) < 0) {
                parent = cur;
                cur = cur.left;
            } else {
                break;
            }
        }
        if (cur == null) {
            return false;
        }
        if (cur == parent.left) {
            if (cur.left == null && cur.right == null) {
                parent.left = null;
                return true;
            }
            if (cur.left == null) {
                parent.left = cur.right;
                return true;
            }
            if (cur.right == null) {
                parent.left = cur.left;
                return true;
            }
            //left!+null&&right!=null
            TreeNodeBase<T> rightMin = getMin(cur.right);
            rightMin.left = cur.left;
            parent.left = cur.right;
        }


        if (cur == parent.right) {
            if (cur.left == null && cur.right == null) {
                parent.right = null;
                return true;
            }
            if (cur.left == null) {
                parent.right = cur.right;
                return true;
            }
            if (cur.right == null) {
                parent.right = cur.left;
                return true;
            }
            //left!+null&&right!=null
            TreeNodeBase<T> rightMin = getMin(cur.right);
            rightMin.left = cur.left;
            parent.right = cur.right;
        }


        return false;
    }

    @Override
    public void insert(T key) {
        if (key == null) {
            return;
        }
        if (root == null) {
            root = new TreeNodeBase<T>(key);
            return;
        }

        TreeNodeBase<T> cur = root;
        TreeNodeBase<T> parent = cur;
        while (cur != null) {
            if (key.compareTo(cur.val) > 0) {
                parent = cur;
                cur = cur.right;
            } else if (key.compareTo(cur.val) < 0) {
                parent = cur;
                cur = cur.left;
            } else {
                return;
            }
        }
        if (key.compareTo(parent.val) > 0) {
            parent.right = new TreeNodeBase<T>(key);
        } else {
            parent.left = new TreeNodeBase<T>(key);
        }
    }

    public List<T> list() {
        List<T> list = new ArrayList<>();
        getListHelper(list, root);
        return list;
    }

    private void getListHelper(List<T> list, TreeNodeBase<T> root) {
        if (root == null) {
            return;
        }
        getListHelper(list, root.left);
        list.add(root.val);
        getListHelper(list, root.right);
    }

    public void showTree(String raw) {
        TreePrinter.showTree(raw, root);
    }

    private int deep(TreeNodeBase<T> root) {
        return root == null ? 0 : Math.max(deep(root.left), deep(root.right)) + 1;
    }



}
