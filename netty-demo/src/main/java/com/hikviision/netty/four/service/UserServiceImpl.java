package com.hikviision.netty.four.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author milindeyu
 * @Date 2021/12/8 10:23 下午
 * @Version 1.0
 */
public class UserServiceImpl implements UserService {

    /**
     * 用假数据测试即可
     */
    private Map<String, String> allUserMap = new ConcurrentHashMap<>();
    {
        allUserMap.put("zhangsan", "123");
        allUserMap.put("lisi", "123");
        allUserMap.put("wangwu", "123");
        allUserMap.put("zhaoliu", "123");
        allUserMap.put("qianqi", "123");
    }

    @Override
    public boolean login(String username, String password) {
        String realPassword = allUserMap.get(username);
        if (realPassword == null) {
            return false;
        }
        return realPassword.equals(password);
    }
}
