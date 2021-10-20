package com.hikvision.letcode;

/**
 * <p>
 *     该题主要是考察使用二进制位运算实现除法，基于此，我们可以扩展通过位运算实现计算机中的加减乘除
 * </p>
 *
 * @author zhangwei151
 * @description @see https://leetcode-cn.com/problems/divide-two-integers/
 * @date 2021/9/23 15:04
 */
public class Demo29 {
    public static void main(String[] args) {
        System.out.println(sum(14, 84));
        System.out.println(substract(14, 84));
        System.out.println(intmultiply(-6, -7));
        System.out.println(intmultiplyTwo(-6, -7));
        System.out.println(divide(Integer.MIN_VALUE, 1));
        System.out.println(demo(10, 3));
    }

    /**
     * 题目解法
     * @param dividend
     * @param divisor
     * @return
     */
    public static int demo(int dividend, int divisor) {
        if (divisor == 0) {
            return 0;
        }
        if (dividend == Integer.MIN_VALUE & divisor == -1) {
            return Integer.MAX_VALUE;
        }
        long i1 = Math.abs((long) dividend);
        long i2 = Math.abs((long) divisor);
        int result = 0;
        for (int i = 31; i >= 0; i--) {
            if (i1 >> i >= i2) {
                result += 1<<i;
                i1 -= i2 << i;
            }
        }
        return (dividend ^ divisor) < 0 ? -result : result;
    }



    /**
     * 加法 @see https://www.jianshu.com/p/7bba031b11e7
     * @param i1
     * @param i2
     * @return
     */
    public static int sum(int i1, int i2) {
        if (i2 == 0) {
            return i1;
        }
        int sum = i1 ^ i2;
        int array = (i1 & i2) << 1;
        return sum(sum, array);
    }

    /**
     * 减法，类似于加法，只是减数设置成负数即可
     * @param i1 被减数
     * @param i2 减数
     * @return
     */
    public static int substract(int i1, int i2) {
        // 注意正数转负数时需要先取反码再加一
        i2 = sum(~i2, 1);
        return sum(i1, i2);
    }

    /**
     * 乘法，即多个相同数累加，只需注意乘数的符号位，即正数还是负数
     * 缺陷：如果乘数较大那么累加次数过多，导致效率不高
     * @param i1
     * @param i2
     * @return
     */
    public static int intmultiply(int i1, int i2) {
        // 1.先取绝对值
        int p1 = i1 < 0 ? sum(~i1, 1) : i1;
        int p2 = i2 < 0 ? sum(~i2, 1) : i2;
        int result = 0;
        // 2.取较小值做循环次数，避免循环次数过多
        if (p1 > p2) {
            int temp = p1;
            p1 = p2;
            p2 = temp;
        }
        while (p1 > 0) {
            result = sum(result, p2);
            p1 = sum(p1, -1);
        }
        // 3.异或取两个数是否存在一个为小于0
        if ((i1 ^ i2) < 0) {
            // 此时结果取负数
            result = sum(~result, 1);
        }
        return result;
    }

    /**
     * 方式二：乘法
     * @param i1
     * @param i2
     * @return
     */
    public static int intmultiplyTwo(int i1, int i2) {
        // 1.先取绝对值
        int p1 = i1 < 0 ? sum(~i1, 1) : i1;
        int p2 = i2 < 0 ? sum(~i2, 1) : i2;

        int result = 0;
        while (p2 > 0) {
            if ((p2 & 0x1) > 0) {
                result = sum(result, p1);
            }
            p2 = p2 >> 1;
            p1 = p1 << 1;
        }
        if ((i1 ^ i2) < 0) {
            result = sum(~result, 1);
        }
        return result;
    }

    /**
     * 除法
     * 方式一：通过累减实现
     * 缺陷，同样存在除数太小导致循环次数过多问题
     * @param dividend
     * @param divisor
     * @return
     */
    public static int divide(int dividend, int divisor) {
        if (dividend == Integer.MIN_VALUE && ((divisor == 1 || divisor == -1))) {
            return Integer.MAX_VALUE;
        }

        // 1.先去绝对值
        int i1 = dividend < 0 ? sum(~dividend, 1) : dividend;
        int i2 = divisor < 0 ? sum(~divisor, 1) : divisor;

        int result = 0;
        int remainder = i1;
        while (remainder >= i2) {
            remainder = sum(remainder, sum(~i2, 1));
            result = sum(result, 1);
        }
        //
        if ((dividend ^ divisor) < 0) {
            result = sum(~result, 1);
        }
        return result;
    }

    /**
     * 方式二：除法
     * @param dividend
     * @param divisor
     * @return
     */
    public static int divideTwo(int dividend, int divisor) {
        if (divisor == 1) {
            return dividend;
        }
        if (dividend == Integer.MIN_VALUE && divisor == -1) {
            return Integer.MAX_VALUE;
        }
        // 1.先去绝对值
        int i1 = dividend < 0 ? sum(~dividend, 1) : dividend;
        int i2 = divisor < 0 ? sum(~divisor, 1) : divisor;

        int quotient = 0;// 商
        int remainder = 0;// 余数
        for(int i = 31; i >= 0; i--) {
            //比较dividend是否大于divisor的(1<<i)次方，不要将dividend与(divisor<<i)比较，而是用(dividend>>i)与divisor比较，
            //效果一样，但是可以避免因(divisor<<i)操作可能导致的溢出，如果溢出则会可能dividend本身小于divisor，但是溢出导致dividend大于divisor
            if((i1 >> i) >= i2) {
                quotient = sum(quotient, 1 << i);
                i1 = substract(i1, i2 << i);
            }
        }
        if ((dividend ^ divisor) < 0) {
            quotient = sum(~quotient, 1);
        }
        return quotient;// 返回商
    }
}
