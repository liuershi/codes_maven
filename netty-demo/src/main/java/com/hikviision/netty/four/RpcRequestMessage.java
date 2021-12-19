package com.hikviision.netty.four;

import com.google.gson.annotations.Expose;
import lombok.Data;
import lombok.ToString;

/**
 * @author zhangwei151
 * @date 2021/12/18 16:02
 */
@Data
@ToString(callSuper = true)
public class RpcRequestMessage extends Message{

    /**
     * 接口名称，使用全限定名
     */
    @Expose
    private String interfaceName;

    /**
     * 调用方法
     */
    @Expose
    private String methodName;

    /**
     * 返回值类型
     */
    @Expose
    private Class<?> returnType;

    /**
     * 方法参数类型
     */
    @Expose
    private Class[] parameterTypes;

    /**
     * 方法参数值
     */
    @Expose
    private Object[] parameterValue;

    public RpcRequestMessage(Integer id, String interfaceName, String methodName, Class<?> returnType, Class[] parameterTypes, Object[] parameterValue) {
        this.sequenceId = id;
        this.interfaceName = interfaceName;
        this.methodName = methodName;
        this.returnType = returnType;
        this.parameterTypes = parameterTypes;
        this.parameterValue = parameterValue;
    }

    @Override
    public int getMessageType() {
        return RPC_MESSAGE_TYPE_REQUEST;
    }
}
