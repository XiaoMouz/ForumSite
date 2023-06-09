package com.mou.gameforum;

import com.mou.gameforum.entity.Levels;
import com.mou.gameforum.entity.User;
import com.mou.gameforum.entity.dto.NetworkRequestDto;
import com.mou.gameforum.entity.dto.UserLoginDto;
import com.mou.gameforum.entity.enums.UserStatusEnum;
import com.mou.gameforum.entity.vo.EmailTemplate;
import com.mou.gameforum.mapper.user.UserMapper;
import com.mou.gameforum.service.EmailService;
import com.mou.gameforum.service.user.UserService;
import com.mou.gameforum.utils.StringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

@SpringBootTest
class GameForumApplicationTests {

    @Autowired
    UserMapper userMapper;

    @Autowired
    UserService userService;

    @Autowired
    EmailService emailService;

    @Test
    void testMySQlInsert(){
        User user = new User();
        user.setId(null);
        user.setEmail("gixaomouz@gmail.com");
        user.setUsername("XiaoMouz");
        user.setNickname("XiaoMouz");
        user.setStatus(UserStatusEnum.ACTIVE);
        user.setPassword(StringUtils.getMD5Result("123456"));
        user.setRegisterIp("::1");
        user.setRegisterTime(new Date());
        user.setLoginIp("::1");
        user.setLoginTime(new Date());
        if (userMapper.insert(user) == 1) {
            System.out.println("插入成功");
        } else {
            System.out.println("插入失败");
        }
    }

    @Test
    void selectAllUser(){
        System.out.println(userMapper.selectList(null));
    }

    @Test
    void testUserServiceLogin(){
        User user = userService.loginByDto(new UserLoginDto("Yukii","123456"), new NetworkRequestDto("::1",new Date()));
        System.out.println(user);
        for(Levels level : user.getLevels()){

            System.out.println(level);
        }
    }

    @Test
    void testEmailSender(){
        if(emailService.sendEmail("gxiaomouz@gmail.com", new EmailTemplate("测试邮件", "测试邮件内容","按钮","http://127.0.0.1:1000","Hello test"))){
            System.out.println("发送成功");
        }else {
            System.out.println("发送失败");
        }
    }
}

