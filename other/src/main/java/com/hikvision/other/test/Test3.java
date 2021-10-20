package com.hikvision.other.test;

import org.junit.Test;

import java.util.Optional;

/**
 * @author zhangwei151
 * @description Test3
 * @date 2021/10/19 10:18
 */
public class Test3 {

    @Test
    public void testOptional() {
        User user = /*new User("张三", 22, "杭州")*/null;
        Optional<User> optional = Optional.ofNullable(user);
        optional.ifPresent(item -> System.out.println(item.toString()));
    }

    public static class User {
        private String name;
        private Integer age;
        private String address;

        public User(String name, Integer age, String address) {
            this.name = name;
            this.age = age;
            this.address = address;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }
    }
}
