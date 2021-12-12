package com.hikvision.other.test;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;

import java.nio.charset.StandardCharsets;

/**
 * @author zhangwei151
 * @date 2021/12/9 19:40
 */
public class TestBloomFilter {
    public static void main(String[] args) {
        BloomFilter<String> bloomFilter = BloomFilter.create(Funnels.stringFunnel(StandardCharsets.UTF_8), 1000000L, 0.03d);
        bloomFilter.put("10086");

        System.out.println(bloomFilter.test("1234566"));
        System.out.println(bloomFilter.test("10086"));
    }
}
