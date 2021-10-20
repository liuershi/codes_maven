package com.hikvision.spring.model.dto;

/**
 * @author zhangwei151
 * @description UserDto
 * @date 2021/7/28 16:09
 */
public class UserDto {
    private String name;
    private Integer age;
    private AddressDto address;

    public AddressDto getAddress() {
        return address;
    }

    public void setAddress(AddressDto address) {
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

    @Override
    public String toString() {
        return "UserDto{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", address=" + address.toString() +
                '}';
    }

    public void init() {
        System.out.println("init...");
    }

    public void destroy(){
        System.out.println("Destory...");
    }
}
