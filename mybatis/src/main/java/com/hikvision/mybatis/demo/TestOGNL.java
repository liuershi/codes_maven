package com.hikvision.mybatis.demo;

import com.hikvision.mybatis.model.Blog;
import com.hikvision.mybatis.model.TbContent;
import com.hikvision.mybatis.model.TbUser;
import org.apache.ibatis.scripting.xmltags.ExpressionEvaluator;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author zhangwei151
 * @description TestOGNL
 * @date 2021/9/22 23:23
 */
public class TestOGNL {

    @Test
    public void test1() {
        TbUser user = new TbUser("2", "法外为阿狂徒", 25, 180.0, "杭州", new Date());
        List<TbContent> contents = Arrays.asList(new TbContent("111", "今天是个好日子", "2", LocalDateTime.now()),
                new TbContent("222", "S11快开始了", "2", LocalDateTime.now()));
        Blog blog = Blog.builder().id("1").title("test").author(user).createTime(LocalDateTime.now())
                .contents(contents).build();


        ExpressionEvaluator evaluator = new ExpressionEvaluator();
        // 判断对象属性
        boolean b = evaluator.evaluateBoolean("id!=null", blog);
        System.out.println(b);
        boolean b1 = evaluator.evaluateBoolean("id==1", blog);
        System.out.println(b1);
        boolean b2 = evaluator.evaluateBoolean("author!=null&&author.userName=='法外为阿狂徒'", blog);
        System.out.println(b2);
        boolean b3 = evaluator.evaluateBoolean("contents.size>0", blog);
        System.out.println(b3);
        boolean b4 = evaluator.evaluateBoolean("contents.size>4", blog);
        System.out.println(b4);

        // 取属性迭代
        evaluator.evaluateIterable("contents", blog).forEach(System.out::println);
    }
}
