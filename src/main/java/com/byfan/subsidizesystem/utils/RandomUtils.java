package com.byfan.subsidizesystem.utils;

import java.awt.*;
import java.util.Random;

/**
 * @ClassName: RandomUtils
 * @Description: 随机类
 * @Author: byfan
 * @Date: 2022/4/6 21:17
 */
public class RandomUtils extends org.apache.commons.lang3.RandomUtils {
    private static final char[] CODE_SEQ = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'J',
            'K', 'L', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W',
            'X', 'Y', 'Z', '2', '3', '4', '5', '6', '7', '8', '9' };

    private static final char[] NUMBER_ARRAY = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };

    private static Random random = new Random();

    /**
     * @Description 随机生成指定长度的字符串
     * @Author byfan
     * @Date 2022/4/6 21:19
     * @param length
     * @return java.lang.String
     * @throws
     */
    public static String randomString(int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(String.valueOf(CODE_SEQ[random.nextInt(CODE_SEQ.length)]));
        }
        return sb.toString();
    }

    /**
     * @Description 随机生成指定长度的数字字符串
     * @Author byfan
     * @Date 2022/4/6 21:19
     * @param length
     * @return java.lang.String
     * @throws
     */
    public static String randomNumberString(int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(String.valueOf(NUMBER_ARRAY[random.nextInt(NUMBER_ARRAY.length)]));
        }
        return sb.toString();
    }

    /**
     * @Description 随机生成颜色
     * @Author byfan
     * @Date 2022/4/6 21:20
     * @param fc
     * @param bc
     * @return java.awt.Color
     * @throws
     */
    public static Color randomColor(int fc, int bc) {
        int f = fc;
        int b = bc;
        Random random = new Random();
        if (f > 255) {
            f = 255;
        }
        if (b > 255) {
            b = 255;
        }
        return new Color(f + random.nextInt(b - f), f + random.nextInt(b - f), f + random.nextInt(b - f));
    }

    /**
     * @Description 随机生成器序列的下一个伪随机、均匀分布的int值，介于零（包括）和bound （不包括）之间
     * @Author byfan
     * @Date 2022/4/6 21:20
     * @param bound
     * @return int
     * @throws
     */
    public static int nextInt(int bound) {
        return random.nextInt(bound);
    }
}
