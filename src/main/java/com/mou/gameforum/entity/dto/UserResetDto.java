package com.mou.gameforum.entity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "用户重置密码实体")
public class UserResetDto {
    @Schema(description = "用户名")
    String username;
    @Schema(description = "重置密码的 token")
    String token;
    @Schema(description = "密码")
    String password;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
