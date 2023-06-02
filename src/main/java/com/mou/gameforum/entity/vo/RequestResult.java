package com.mou.gameforum.entity.vo;

import com.alibaba.fastjson2.JSON;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class RequestResult<T> {
    public Integer code;
    public String msg;
    public T data;

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
