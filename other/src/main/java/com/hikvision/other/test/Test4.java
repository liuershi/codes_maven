package com.hikvision.other.test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zhangwei151
 * @description Test4
 * @date 2021/10/19 19:23
 */
public class Test4 {

    private static List<Dept> users = new ArrayList<>();

    static {
        users.add(new Dept("张三", "dev", 22));
        users.add(new Dept("李四", "test", 25));
        users.add(new Dept("王五", "dev", 55));
        users.add(new Dept("赵六", "dev", 34));
        users.add(new Dept("孙七", "product", 19));
    }

    public static void main(String[] args) {
        users.stream().
                filter(user -> "dev".equals(user.getDept()) || "test".equals(user.getDept()))
                .map(Dept::getName)
                .distinct()
                .collect(Collectors.toList());
    }

    static class Dept {
        private String name;
        private String dept;
        private Integer age;

        public Dept(String name, String dept, Integer age) {
            this.name = name;
            this.dept = dept;
            this.age = age;
        }

        @Override
        public String toString() {
            return "Dept{" +
                    "name='" + name + '\'' +
                    ", dept='" + dept + '\'' +
                    ", age=" + age +
                    '}';
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDept() {
            return dept;
        }

        public void setDept(String dept) {
            this.dept = dept;
        }

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }
    }
}
