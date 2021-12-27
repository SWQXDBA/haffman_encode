package com.example.haffman_encode.Tree.BinaryTree;

import java.util.LinkedList;
import java.util.Queue;

public class TreePrinter {


    //获取树的高度
    private static <T extends Comparable<T>> int deep(TreeNodeInterface<T> root) {
        return root == null ? 0 : Math.max(deep(root.getLeft()), deep(root.getRight())) + 1;
    }

    //raw为占位符 同一行不同节点之间以占位符分割 占位符的长度影响节点之间的间距 row.length必须>val.toString.length
    public static <T extends Comparable<T>> void showTree(String raw, TreeNodeInterface<T> root) {

        if (root == null) {
            System.out.println("Tree is null.");
            return;
        }
        Queue<TreeNodeInterface<T>> queue = new LinkedList<>();
        queue.offer(root);
        int curLevel = 1;//当前层数
        int deep = deep(root);
        while (!queue.isEmpty() && curLevel <= deep) {
            int length = (int) (Math.pow(2, (deep - curLevel)) - 1);
            //每一层最前面的空格
            for (int j = 0; j <= length + 1; j++) {
                System.out.print(raw);
            }
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                TreeNodeInterface<T> cur = queue.poll();
                if (cur == null) {//空节点和它的“子节点”也需要占位
                    for (int j = 0; j <= length * 2 + 1; j++) {
                        System.out.print(raw);
                    }
                    queue.offer(null);
                    queue.offer(null);
                    continue;
                }
                //节点不为null
                System.out.print(cur.getValString());
                int t = raw.length() - (cur.getValString().toString().length());
                //补足数据长度和占位符的差值
                for (int j = 0; j < t; j++) {
                    System.out.print(raw.charAt(0));
                }
                //两个节点之间的占位符
                for (int j = 0; j <= length * 2; j++) {
                    System.out.print(raw);
                }
                queue.offer(cur.getLeft());
                queue.offer(cur.getRight());
            }
            System.out.println("\n");
            curLevel++;
        }
    }

    public static <T extends Comparable<T>> String asString(String raw, TreeNodeInterface<T> root) {
        StringBuilder stringBuilder = new StringBuilder();


        if (root == null) {
            stringBuilder.append("Tree is null.\n");
            return stringBuilder.toString();
        }
        Queue<TreeNodeInterface<T>> queue = new LinkedList<>();
        queue.offer(root);
        int curLevel = 1;//当前层数
        int deep = deep(root);
        while (!queue.isEmpty() && curLevel <= deep) {
            int length = (int) (Math.pow(2, (deep - curLevel)) - 1);
            //每一层最前面的空格
            for (int j = 0; j <= length + 1; j++) {
                stringBuilder.append(raw);
            }
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                TreeNodeInterface<T> cur = queue.poll();
                if (cur == null) {//空节点和它的“子节点”也需要占位
                    for (int j = 0; j <= length * 2 + 1; j++) {
                        stringBuilder.append(raw);
                    }
                    queue.offer(null);
                    queue.offer(null);
                    continue;
                }
                //节点不为null
                stringBuilder.append(cur.getValString());
                int t = raw.length() - (cur.getValString().toString().length());
                //补足数据长度和占位符的差值
                for (int j = 0; j < t; j++) {
                    stringBuilder.append(raw.charAt(0));
                }
                //两个节点之间的占位符
                for (int j = 0; j <= length * 2; j++) {
                    stringBuilder.append(raw);
                }
                queue.offer(cur.getLeft());
                queue.offer(cur.getRight());
            }
            stringBuilder.append("\n\n");
            curLevel++;

        }
        return stringBuilder.toString();
    }
}
