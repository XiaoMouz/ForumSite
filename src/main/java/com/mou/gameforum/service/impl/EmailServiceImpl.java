package com.mou.gameforum.service.impl;

import com.mou.gameforum.entity.vo.EmailTemplate;
import com.mou.gameforum.service.EmailService;
import io.pebbletemplates.pebble.PebbleEngine;
import io.pebbletemplates.pebble.template.PebbleTemplate;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.StringWriter;
import java.util.HashMap;

@Service
public class EmailServiceImpl implements EmailService {
    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String sender;

    @Autowired
    private PebbleEngine pebbleEngine;

    @Override
    public boolean sendEmail(String to, EmailTemplate content) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            // 渲染 Pebble 模板后的邮件内容作为邮件正文
            StringWriter writer = new StringWriter();
            PebbleTemplate template = pebbleEngine.getTemplate("email/index");
            template.evaluate(writer, new HashMap<String, Object>() {{
                put("data", content);
            }});
            helper.setSubject(content.getTitle());
            helper.setText(writer.toString(), true);
            helper.setFrom(sender);
            helper.setTo(to);
            javaMailSender.send(mimeMessage);
            return true;
        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }

    }
}
