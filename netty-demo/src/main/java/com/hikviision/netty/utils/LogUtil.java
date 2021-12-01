package com.hikviision.netty.utils;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;

import static io.netty.util.internal.MathUtil.isOutOfBounds;
import static io.netty.util.internal.StringUtil.NEWLINE;

/**
 * @author zhangwei151
 * @date 2021/11/27 15:14
 */
public class LogUtil {
    private static final char[] BYTE2CHAR = new char[256];
    private static final char[] HEXDUMP_TABLE = new char[256 * 4];
    private static final String[] HEXPADDING = new String[16];
    private static final String[] HEXDUMP_ROWPREFIXES = new String[65536 >>> 4];
    private static final String[] BYTE2HEX = new String[256];
    private static final String[] BYTEPADDING = new String[16];


    public static void log(ByteBuf buf) {
        StringBuilder dump = new StringBuilder();
        ByteBufUtil.appendPrettyHexDump(dump, buf);
        System.out.println(dump.toString());
    }

}
