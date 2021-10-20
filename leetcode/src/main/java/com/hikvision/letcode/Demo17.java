package com.hikvision.letcode;

import java.util.*;

/**
 * @author zhangwei151
 * @description @see https://leetcode-cn.com/problems/letter-combinations-of-a-phone-number
 * @date 2021/9/21 19:16
 */
public class Demo17 {
    public static void main(String[] args) {
        System.out.println(letterCombinations("5678"));
        System.out.println(letterCombinationsTwo("5678"));
    }

    /**
     * 使用队列Queue实现
     * @param digits
     * @return
     */
    public static List<String> letterCombinationsTwo(String digits) {
        List<String> result = new ArrayList<>();
        if (digits == null || digits.length() == 0) {
            return result;
        }
        Queue<String> queue = new LinkedList<>();

        Map<Character, String[]> map = new HashMap<Character, String[]>(){{
            put('2', new String[]{"a", "b", "c"});
            put('3', new String[]{"d", "e", "f"});
            put('4', new String[]{"g", "h", "i"});
            put('5', new String[]{"j", "k", "l"});
            put('6', new String[]{"m", "n", "o"});
            put('7', new String[]{"p", "q", "r", "s"});
            put('8', new String[]{"t", "u", "v"});
            put('9', new String[]{"w", "x", "y", "z"});
        }};

        char[] chars = digits.toCharArray();
        for (char c : chars) {
            String[] strings = map.get(c);
            int size = queue.size();
            if (size == 0) {
                for (String s : strings) {
                    queue.offer(s);
                }
            } else {
                for (int i = 0; i < size; i++) {
                    String poll = queue.poll();
                    for (String s : strings) {
                        queue.add(poll + s);
                    }
                }
            }
        }
        result.addAll(queue);
        return result;
    }

    /**
     * 原始方式，判断字符长度
     * @param digits
     * @return
     */
    public static List<String> letterCombinations(String digits) {
        List<String> result = new ArrayList<>();
        int length = digits.length();
        if (length == 0) {
            return result;
        }
        Map<Character, String[]> map = new HashMap<Character, String[]>(){{
            put('2', new String[]{"a", "b", "c"});
            put('3', new String[]{"d", "e", "f"});
            put('4', new String[]{"g", "h", "i"});
            put('5', new String[]{"j", "k", "l"});
            put('6', new String[]{"m", "n", "o"});
            put('7', new String[]{"p", "q", "r", "s"});
            put('8', new String[]{"t", "u", "v"});
            put('9', new String[]{"w", "x", "y", "z"});
        }};
        switch (length) {
            case 1:
                return Arrays.asList(map.get(digits.charAt(0)));
            case 2:
                String[] s1 = map.get(digits.charAt(0));
                String[] s2 = map.get(digits.charAt(1));
                for (String item1 : s1) {
                    for (String item2 : s2) {
                        result.add(item1 + item2);
                    }
                }
                break;
            case 3:
                String[] m1 = map.get(digits.charAt(0));
                String[] m2 = map.get(digits.charAt(1));
                String[] m3 = map.get(digits.charAt(2));
                for (String item1 : m1) {
                    for (String item2 : m2) {
                        for (String item3 : m3) {
                            result.add(item1 + item2 + item3);
                        }
                    }
                }
                break;
            case 4:
                String[] z1 = map.get(digits.charAt(0));
                String[] z2 = map.get(digits.charAt(1));
                String[] z3 = map.get(digits.charAt(2));
                String[] z4 = map.get(digits.charAt(3));
                for (String item1 : z1) {
                    for (String item2 : z2) {
                        for (String item3 : z3) {
                            for (String item4 : z4) {
                                result.add(item1 + item2 + item3 + item4);
                            }
                        }
                    }
                }
                break;
            default:
                break;
        }
        return result;
    }
}
