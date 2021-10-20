package com.hikvision.mybatis.dao;

import com.hikvision.mybatis.model.DoorDto;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author zhangwei151
 * @description DoorDao
 * @date 2021/7/23 10:23
 */


/**
 * 说明：
 *      1. @CacheNamespace注解是缓存的命名空间声明，它等同于xml文件中的<cache/>标签，声明的命名空间为dao的全路径路径。
 *      需要注意，不不可同时在xml和dao接口中都声明缓存命名空间，否则抛出“Caused by: java.lang.IllegalArgumentException: Caches collection already contains value for com.hikvision.mybatis.dao.DoorDao”
 *      异常。
 *      2、xml文件中的sql和dao接口中使用注解sql其不能共享缓存空间，即若只声明@CacheNamespace则xml中的sql无法使用缓存
 *      3.解决2中的问题，可以通过声明@CacheNamespace，而在xml中通过声明
 */
@CacheNamespace
public interface DoorDao {

    List<DoorDto> listAll();

    List<DoorDto> listAllTwo();

    @Select("select acs_door_id, door_index_code, door_name, acs_dev_index_code,\n" +
            "            region_index_code, region_name, door_no, channel_no, channel_type,\n" +
            "            install_location, remark, status, create_time, update_time\n" +
            "        from tb_acs_door where acs_door_id = #{id}")
    DoorDto getDoorById(@Param("id") String id);

    boolean updateNameById(@Param("name") String name, @Param("id") String id);


    /**
     * 通过使用${}占位符的方式简化根据多属性查询的业务
     * @link https://mybatis.org/mybatis-3/zh/sqlmap-xml.html#select
     * @param attr
     * @param value
     * @return
     */
    List<DoorDto> getValueByAttr(@Param("attr") String attr, @Param("value") String value);

    List<DoorDto> getDoorByLike(@Param("name") String name, @Param("regionName") String regionName);
}
