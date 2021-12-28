package com.example.haffman_encode.Pojo;

import com.example.haffman_encode.Pojo.CodeMapper;
import com.example.haffman_encode.Tree.Exceptions.CantDecodeException;
import com.example.haffman_encode.Tree.Exceptions.CantEncodeException;
import com.example.haffman_encode.Tree.Huffman.HuffmanTreeEncoder;

import java.io.Serializable;
import java.util.*;

public class Data implements Serializable {

    transient public Map<String, String> encodeMapper;
    transient public Map<String, String> decodeMapper;
    private BitSet data;

    public CodeMapper getMapper(){
        return new CodeMapper(encodeMapper,decodeMapper);
    }
    public void setMapper(CodeMapper mapper){
        this.encodeMapper = mapper.encodeMapper;
        this.decodeMapper = mapper.decodeMapper;
    }
    //原文件名
    public String fileName;
    public String mapKey;
    //数据的位数 除了data中标记的以外，其他都是0
    private int length;

    public String decode() {
        StringBuilder builder = new StringBuilder();
        try {
            deCode().forEach(o -> builder.append(o).append(" "));
        } catch (CantDecodeException e) {
            e.printStackTrace();
        }
        return builder.toString();
    }

    public void encoder(String s) {

        List<String> strings = Arrays.asList(s.split(" "));

        Map<String, Integer> map = new HashMap<>();
        //设置权重为频率
        strings.forEach(o -> map.put(o, map.getOrDefault(o, 0)));
        try {
            HuffmanTreeEncoder<String> tree;
            //通过哈夫曼树构建映射表
            tree = new HuffmanTreeEncoder<>(strings, map::get);
            encodeMapper = tree.getEncodeMapper();
            decodeMapper = tree.getDecodeMapper();

            String code = genCode(strings);
            length = code.length();
            //编码后可以用bit存储
            data = new BitSet();
            char[] chars = code.toCharArray();
            for (int i = 0; i < chars.length; i++) {
                if (chars[i] == '1') {
                    data.set(i);
                }
            }

        } catch (CantEncodeException e) {
            e.printStackTrace();
        }
    }

    //生成编码
    private <T> String genCode(Collection<T> collection) throws CantEncodeException {
        StringBuilder stringBuilder = new StringBuilder();
        //根据原始数据进行编码
        for (T t : collection) {
            String s = encodeMapper.get(t);
            if (s == null) {
                throw new CantEncodeException();
            }
            stringBuilder.append(s);
        }
        return stringBuilder.toString();
    }
    @SuppressWarnings({"unchecked"})
    private <T> Collection<T> deCode() throws CantDecodeException {
        List<T> list = new ArrayList<>();

        String current = "";
        System.out.println(length);
        for (int i = 0; i < length; i++) {
            current += data.get(i) ? "1" : "0";
            T t = (T) decodeMapper.get(current);
            if (t != null) {
                list.add(t);
                current = "";
            }
        }

        if (!"".equals(current)) {
            throw new CantDecodeException();
        }
        return list;
    }

}
