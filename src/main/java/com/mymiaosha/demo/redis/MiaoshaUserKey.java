package com.mymiaosha.demo.redis;

public class MiaoshaUserKey extends  BasePrefix {
    public MiaoshaUserKey(String prefix) {
        super(prefix);
    }

    public static final int TOKEN_EXPIRE = 3600*24*2;
    public MiaoshaUserKey(int expireSeconds,String prefix){
        super(expireSeconds,prefix);
    }
    public static MiaoshaUserKey token = new MiaoshaUserKey(TOKEN_EXPIRE,"tk");
}
