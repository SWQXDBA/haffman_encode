package com.example.haffman_encode.Tree.Huffman;

import com.example.haffman_encode.Tree.BinaryTree.BinarySerchTree.MyBinarySearchTree;
import com.example.haffman_encode.Tree.BinaryTree.BinarySerchTree.TreeNodeBase;
import com.example.haffman_encode.Tree.BinaryTree.TreePrinter;
import com.example.haffman_encode.Tree.Exceptions.CantDecodeException;
import com.example.haffman_encode.Tree.Exceptions.CantEncodeException;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class HuffmanTreeEncoder<T extends Comparable<T>> {
    PriorityQueue<TreeNodeBase<CodeNode<T>>> stack = new PriorityQueue<>();
    MyBinarySearchTree<CodeNode<T>> tree = new MyBinarySearchTree<>();
    Function<? super T, ? extends Integer> mapper;

    public Map<T, String> getEncodeMapper() {
        return encodeMapper;
    }

    public Map<String, T> getDecodeMapper() {
        return decodeMapper;
    }


    Map<T, String> encodeMapper = new HashMap<>();
    Map<String, T> decodeMapper = new HashMap<>();

    String code;


    static class CodeNode<T> implements Comparable<CodeNode<T>> {
        public T val;
        public Integer priority;//优先级 权重

        public CodeNode(T val, Integer code) {
            this.val = val;
            this.priority = code;
        }

        @Override
        public int compareTo(CodeNode<T> o) {
            return priority - o.priority;
        }
    }

    public HuffmanTreeEncoder(Collection<T> collection, Function<? super T, ? extends Integer> mapper) {
        //这个是原始的
        Collection<T>  oldCollection = collection;
        this.mapper = mapper;

        //这个是去重后的 编码的时候要先去重
        collection = collection
                .stream()
                .distinct()
                .collect(Collectors.toList());
        for (T t : collection) {
            stack.offer(new TreeNodeBase<>(new CodeNode<>(t, mapper.apply(t))));
        }
        build();
    }









    private void build() {
        while (!stack.isEmpty()) {
            TreeNodeBase<CodeNode<T>> left = stack.poll();
            if (stack.isEmpty()) {
                tree.root = left;
                break;
            }
            TreeNodeBase<CodeNode<T>> right = stack.poll();
            TreeNodeBase<CodeNode<T>> sum = new TreeNodeBase<>(new CodeNode<>(null, left.getVal().priority + right.getVal().priority));
            sum.left = left;
            sum.right = right;
            stack.offer(sum);
        }
        startEncode(tree.root, "");
    }

    private void startEncode(TreeNodeBase<CodeNode<T>> root, String code) {
        if (root == null) {
            return;
        }
        if (root.val.val == null) {
            startEncode(root.left, code + "0");
            startEncode(root.right, code + "1");
        } else {
        //    System.out.println(root.val.val + "编码为:" + code);
            encodeMapper.put(root.val.val, code);
            decodeMapper.put(code, root.val.val);
        }
    }

    @Override
    public String toString() {
        return TreePrinter.asString("   ", tree.root);
    }
}
