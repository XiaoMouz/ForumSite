package com.mou.gameforum.utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.UUID;

public class StringUtils {
    /**
     * 生成 UUID
     * @return UUID 字符串
     */
    public static synchronized String getUUID() {
        UUID uuid = UUID.randomUUID();
        String str = uuid.toString();
        return str.replace("-", "");
    }

    /**
     * MD5 加密方法
     * 不得在数据库存入用户明文密码因此使用 MD5 加密后存入
     * 在登陆时将用户输入的密码进行 MD5 加密后与数据库中的密码进行比对
     * @param inputStr 输入的字符串
     * @return 加密后的字符串
     */
    public static synchronized String getMD5Result(String inputStr) {
        BigInteger bigInteger = null;

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] inputData = inputStr.getBytes();
            md.update(inputData);
            bigInteger = new BigInteger(md.digest());
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(bigInteger != null) {
            return bigInteger.toString(16);
        } else {
            System.out.println("Faild in 'EncryptionUtil.getMD5Result()' method, the convert value is null.");
            return null;
        }
    }
}
