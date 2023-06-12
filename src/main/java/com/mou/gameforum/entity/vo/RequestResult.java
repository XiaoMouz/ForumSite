package com.mou.gameforum.entity.vo;

import com.alibaba.fastjson2.JSON;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "统一请求结果")
public class RequestResult<T> {
    @Schema(description = "请求结果码")
    public Integer code;
    @Schema(description = "请求结果信息")
    public String msg;
    @Schema(description = "请求结果数据")
    public T data;

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
