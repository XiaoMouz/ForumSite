package com.mou.gameforum.service;

import com.mou.gameforum.entity.vo.EmailTemplate;

public interface EmailService {
    boolean sendEmail(String to, EmailTemplate content);
}
