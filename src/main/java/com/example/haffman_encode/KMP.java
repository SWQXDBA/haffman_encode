package com.example.haffman_encode;

import java.util.Arrays;

public class KMP {


    /**
     * @param str1 源字符串
     * @param str2 子串
     * @param next 部分匹配表, 是子串对应的部分匹配表
     * @return 如果是-1 就是没有匹配到，否则返回第一个匹配的位置
     */
    public static int kmpSearch(String str1, String str2, int[] next) {
        //遍历
        for (int i = 0, j = 0; i < str1.length(); i++) {
            //需要处理 str1.charAt(i) != str2.charAt(j), 去调整 j 的大小
            //跳转
            while (j > 0 && str1.charAt(i) != str2.charAt(j)) {
                j = next[j - 1];
            }

            if (str1.charAt(i) == str2.charAt(j)) {
                j++;
            }
            //找到了
            if (j == str2.length()) {
                return i - j + 1;
            }
        }
        return -1;
    }

    //获取到一个字符串(子串) 的部分匹配值表
    public static int[] kmpNext(String subStr) {
        //创建一个 next 数组保存部分匹配值
        int[] next = new int[subStr.length()];
        next[0] = 0; //如果字符串是长度为 1 部分匹配值就是 0
        for (int i = 1, j = 0; i < subStr.length(); i++) {
            //当 dest.charAt(i) != dest.charAt(j) ，我们需要从 next[j-1]获取新的 j
            //直到我们发现 有 dest.charAt(i) == dest.charAt(j)成立才退出
            while (j > 0 && subStr.charAt(i) != subStr.charAt(j)) {
                j = next[j - 1];
            }
            //当 dest.charAt(i) == dest.charAt(j) 满足时，部分匹配值就是+1
            if(subStr.charAt(i) == subStr.charAt(j)) {
                j++;
            }
            next[i] = j;
        }
        return next;
    }
}