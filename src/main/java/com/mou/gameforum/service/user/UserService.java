package com.mou.gameforum.service.user;

import com.mou.gameforum.entity.User;
import com.mou.gameforum.entity.dto.NetworkRequestDto;
import com.mou.gameforum.entity.dto.UserLoginDto;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    User loginDto(UserLoginDto loginDto, NetworkRequestDto requestDto);
}
