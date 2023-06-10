package com.mou.gameforum.service.user.impl;

import com.mou.gameforum.entity.Levels;
import com.mou.gameforum.entity.User;
import com.mou.gameforum.entity.dto.NetworkRequestDto;
import com.mou.gameforum.entity.dto.UserLoginDto;
import com.mou.gameforum.entity.dto.UserRegisterDto;
import com.mou.gameforum.entity.enums.UserStatusEnum;
import com.mou.gameforum.entity.vo.EmailTemplate;
import com.mou.gameforum.mapper.levels.LevelsMapper;
import com.mou.gameforum.mapper.user.UserMapper;
import com.mou.gameforum.service.EmailService;
import com.mou.gameforum.service.user.UserService;
import com.mou.gameforum.utils.StringUtils;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserServiceImpl implements UserService {

    @Value("${domain}")
    String domain;

    @Resource
    UserMapper userMapper;

    @Resource
    EmailService emailService;

    @Resource
    LevelsMapper levelsMapper;

    @Override
    public User loginByDto(UserLoginDto loginDto, NetworkRequestDto requestDto) {
        // match if loginByDto have @ char search email by mapper
        User user;
        loginDto.password = StringUtils.getMD5Result(loginDto.password);
        if(loginDto.username.contains("@")){
           user = userMapper.getUserByUserDtoEmail(loginDto);
        }else{
            user =userMapper.getUserByUserDto(loginDto);
        }


        if(user!=null){
            userMapper.updateUserLoginTimeAndIp(user,requestDto);
            ArrayList<Levels> list = new ArrayList<>();
            levelsMapper.getUserLevels(user).forEach(
                    u -> list.add(levelsMapper.selectById(u.getId()))
            );
            user.setLevels(list);
        }
        return user;
    }

    @Override
    public String registerByDto(UserRegisterDto registerDto, NetworkRequestDto requestDto) {
        User user = new User();
        user.setUsername(registerDto.username);
        user.setNickname(registerDto.nickname);
        user.setPassword(StringUtils.getMD5Result(registerDto.password));
        user.setEmail(registerDto.email);
        user.setLoginIp(requestDto.ip);
        user.setLoginTime(requestDto.time);
        user.setRegisterIp(requestDto.ip);
        user.setRegisterTime(requestDto.time);
        user.setStatus(UserStatusEnum.PENDING);

        User existing = userMapper.getUserByRegisterDto(registerDto);
        if (existing != null) {
            return null;
        }

        user.setToken(StringUtils.getUUID());
        userMapper.insert(user);
        levelsMapper.addUserLevel(userMapper.selectUserByUsername(user.getUsername()),levelsMapper.getPendingLevel());
        new Thread(() -> emailService.sendEmail(
                user.getEmail(),
                new EmailTemplate(
                        "Register verify",
                        "Your register verify code is "+user.getToken()+", you can click button to verify your account",
                        "Verify Account",
                        "http://"+ domain+"/register/verify?username="+user.getUsername()+"&token="+user.getToken(),
                        "We didn't have verify time limit, but we suggest you verify your account as soon as possible"
                )
        )).start();
        return user.getToken();
    }

    @Override
    public User verifyRegisterUser(String username, String token) {
        int effect = userMapper.updateUserStatus(username, token, UserStatusEnum.ACTIVE);
        if(effect == 1){
            User user = userMapper.selectUserByUsername(username);
            userMapper.cleanUserToken(user);
            levelsMapper.deleteUserLevel(user,levelsMapper.getPendingLevel());
            levelsMapper.addUserLevel(user,levelsMapper.getVerifyLevel());
            return user;
        }else if(effect > 1){
            // 回滚
            userMapper.updateUserStatus(username, token, UserStatusEnum.PENDING);
            throw new RuntimeException("更新用户状态时出现多个用户被更新,用户名:"+username+",token:"+token);
        }
        return null;
    }

    @Override
    public User getUserById(Integer id) {
        User user = userMapper.selectById(id);
        if(user!=null){
            ArrayList<Levels> list = new ArrayList<>();
            levelsMapper.getUserLevels(user).forEach(
                    u -> list.add(levelsMapper.selectById(u.getId()))
            );
            user.setLevels(list);
        }
        return user;
    }
}
