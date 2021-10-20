package com.hikvision.mybatis.dao;

import com.hikvision.mybatis.model.TbUser;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 *
 * @description TbUserMapper
 * @author zhangwei151
 * @date 2021/8/31 22:47
 */

public interface TbUserMapper {
    int deleteByPrimaryKey(String id);

    int insert(TbUser record);

    int insertSelective(TbUser record);

    TbUser selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(TbUser record);

    int updateByPrimaryKey(TbUser record);

    @Select("select * from tb_user")
    List<TbUser> listAll();

    @Select("select * from tb_user where id = #{id} ")
    TbUser getUserById(String id);

    @Select("select * from tb_user where id = #{id} ")
    TbUser getUserByIdTwo(@Param("id") String id);

    @Select("select * from tb_user where user_name = #{name} and age = #{age}")
    TbUser getUserByNameAndAge( Integer age, String name);

    @Select("select * from tb_user where user_name = #{name} and age = #{age}")
    TbUser getUserByNameAndAgeTwo(@Param("name") String name, Integer age);

    @Select("select * from tb_user where user_name = #{name} and age = #{param2.age}")
    TbUser getUserByNameAndAgeThree(@Param("name") String name, TbUser user);
}