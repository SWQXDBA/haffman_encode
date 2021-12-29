package com.example.haffman_encode.Tree.Huffman;

import com.example.haffman_encode.Tree.BinaryTree.BinarySerchTree.MyBinarySearchTree;
import com.example.haffman_encode.Tree.BinaryTree.BinarySerchTree.TreeNodeBase;
import com.example.haffman_encode.Tree.BinaryTree.TreePrinter;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class HuffmanTreeEncoder<T extends Comparable<T>> {
    PriorityQueue<TreeNodeBase<CodeNode<T>>> heap = new PriorityQueue<>();
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

        public String code = "";
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
            heap.offer(new TreeNodeBase<>(new CodeNode<>(t, mapper.apply(t))));
        }
        build();
    }

    private void build() {
        while (!heap.isEmpty()) {
            TreeNodeBase<CodeNode<T>> left = heap.poll();
            if (heap.isEmpty()) {
                tree.root = left;
                break;
            }
            TreeNodeBase<CodeNode<T>> right = heap.poll();
            TreeNodeBase<CodeNode<T>> sum = new TreeNodeBase<>(new CodeNode<>(null, left.getVal().priority + right.getVal().priority));
            sum.left = left;
            sum.right = right;
            heap.offer(sum);
        }
        startEncode(tree.root);
    }

    private void startEncode(TreeNodeBase<CodeNode<T>> root) {
        if (root == null) {
            return;
        }
        Stack<TreeNodeBase<CodeNode<T>>> stack = new Stack<>();
        stack.add(root);
        while(!stack.isEmpty()){
            root=stack.pop();
            //非叶子结点
            if (root.val.val == null) {

                if (root.right!=null){
                    root.right.val.code = root.val.code + "1";
                    stack.push(root.right);
                }
                if (root.left!=null){
                    root.left.val.code = root.val.code + "0";
                    stack.push(root.left);
                }
            }else {
                //    System.out.println(root.val.val + "编码为:" + code);
                encodeMapper.put(root.val.val, root.val.code);
                decodeMapper.put(root.val.code, root.val.val);
            }


        }
    }

    @Override
    public String toString() {
        return TreePrinter.asString("   ", tree.root);
    }
}
