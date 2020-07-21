package com.mymiaosha.demo.util;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * 前端MD5,防止直接彩虹表破表
 * MD5(（前端MD5）,slat)) 防止数据库被盗
 */
public class MD5Util {
    public static String md5(String src){
        return DigestUtils.md5Hex(src);
    }

    private static final String salt="1a2b3c4d";
    public static String inputPassToFormPass(String inputPass){
        String str= salt.charAt(0)+salt.charAt(2)+inputPass+salt.charAt(5)+salt.charAt(4);
        return md5(str);
    }

    public static String formPassToDBPass(String inputPass,String salt){
        String str= salt.charAt(0)+salt.charAt(2)+inputPass+salt.charAt(5)+salt.charAt(4);
        return md5(str);
    }
    public static String inputPassToDbPass(String input,String saltDb){
        String formPass = inputPassToFormPass(input);
        String dbPass = formPassToDBPass(formPass,saltDb);
        return dbPass;
    }


    public static void main(String[] args) {
        System.out.println("inputPassFormPass(\"123456\") = " + inputPassToFormPass("123456"));
        System.out.println("formPassToDBPass(\"123456\") = " + formPassToDBPass(inputPassToFormPass("123456"),"1a2b3c4d"));
        System.out.println("inputPassToDbPass(\"123456\",\"1a2b3c4d\") = " + inputPassToDbPass("123456","1a2b3c4d"));
    }
}
