package com.mymiaosha.demo.redis;

public interface KeyPrefix {
    public int expireSeconds();
    public String getPrefix();
}
