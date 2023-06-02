package com.mou.gameforum.service.user.impl;

import com.mou.gameforum.entity.Levels;
import com.mou.gameforum.entity.User;
import com.mou.gameforum.entity.dto.NetworkRequestDto;
import com.mou.gameforum.entity.dto.UserLoginDto;
import com.mou.gameforum.mapper.levels.LevelsMapper;
import com.mou.gameforum.mapper.user.UserMapper;
import com.mou.gameforum.service.user.UserService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    UserMapper userMapper;

    @Resource
    LevelsMapper levelsMapper;

    @Override
    public User loginDto(UserLoginDto loginDto, NetworkRequestDto requestDto) {
        User user =userMapper.getUserByUserDto(loginDto);
        if(user!=null){
            userMapper.updateUserLoginTimeAndIp(user,requestDto);
            ArrayList<Levels> list = new ArrayList<>();
            userMapper.getUserLevels(user).forEach(
                    u -> {
                        list.add(levelsMapper.selectById(u.getId()));
                    }
            );
            user.setLevels(list);
        }
        return user;
    }
}
