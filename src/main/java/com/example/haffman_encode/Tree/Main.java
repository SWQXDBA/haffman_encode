package com.example.haffman_encode.Tree;

import com.example.haffman_encode.Tree.Huffman.HuffmanTreeEncoder;

import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String s = scanner.nextLine();
        int length = s.getBytes().length*8;
        List<String> strings = Arrays.asList(s.split(" "));

        Map<String, Integer> map = new HashMap<>();
        strings.forEach(o -> map.put(o, map.getOrDefault(o, 0)));



//111 222 333 222 111 5
//11131 23131 1 1 2 2 3 33 11 22 22 111 333 11131 77


        HuffmanTreeEncoder<String> tree = null;
        try {
            tree = new HuffmanTreeEncoder<>(strings, map::get);
            String code = tree.getCode();
            System.out.println("编码后:"+code);
            Collection<String> strings1 = tree.deCode(code);
            StringBuilder builder = new StringBuilder();
            strings1.forEach(o-> builder.append(o).append(" "));
            String target = new String(builder);
            System.out.println("解码后:"+target);
            //编码后可以用bit存储
            BitSet set = new BitSet();
            char[] chars = code.toCharArray();
            for (int i = 0; i < chars.length; i++) {
                if(chars[i]=='1'){
                    set.set(i);
                }
            }

            System.out.println("原始长度"+length+"bit 编码后长度"+set.length()+"bit");
        } catch (HuffmanTreeEncoder.CantEncodeException | HuffmanTreeEncoder.CantDecodeException e) {
            e.printStackTrace();
        }



    }
}
