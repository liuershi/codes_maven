package com.hikviision.netty.four.group;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

/**
 * @author zhangwei151
 * @date 2021/12/11 15:10
 */
@Data
@AllArgsConstructor
public class Group {
    private String name;
    private Set<String> members;
}
