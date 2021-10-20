package com.hikvision.mybatis.demo;

import com.hikvision.mybatis.model.Blog;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.HashMap;

/**
 * @author zhangwei151
 * @description TestMetaObject
 * @date 2021/9/20 17:05
 */
public class TestMetaObject {
    @Test
    public void test1() {
        Object blog = new Blog();

        Configuration configuration = new Configuration();
        MetaObject metaObject = configuration.newMetaObject(blog);
        metaObject.setValue("createTime", LocalDateTime.now());

        // 通过MetaObject我们可以做到：
        // 1.可以直接操作属性
        System.out.println(metaObject.getValue("createTime"));
        // 2.可以操作子属性，还可以自动创建对象
        metaObject.setValue("author.userName", "张三");
        System.out.println(metaObject.getValue("author.userName"));
        // 3.自动查询属性名，支持驼峰转换
        System.out.println(metaObject.findProperty("author.user_name", true));

        // 4.map需要手动创建
        metaObject.setValue("labels", new HashMap<String, Object>());
        metaObject.setValue("labels[hello]", "世界");
        System.out.println(metaObject.getValue("labels[hello]"));
    }
}
