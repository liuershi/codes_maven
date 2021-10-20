package com.hikvision.mybatis.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * @author zhangwei151
 * @description Blog
 * @date 2021/9/20 17:03
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Blog {
    private String id;
    private String title;
    private TbUser author;
    private LocalDateTime createTime;
    private List<TbContent> contents;
//    private Map<String, Object> labels;
}
