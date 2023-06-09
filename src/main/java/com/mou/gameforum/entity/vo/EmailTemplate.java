package com.mou.gameforum.entity.vo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailTemplate {
    String title;
    String context;
    String button;
    String buttonUrl;
    String tips;

    public EmailTemplate(String title, String context) {
        this.title = title;
        this.context = context;
    }
    public EmailTemplate(String title, String context,String button,String buttonUrl) {
        this.title = title;
        this.context = context;
        this.button = button;
        this.buttonUrl = buttonUrl;
    }

    public EmailTemplate(String title,String context,String tips){
        this.title = title;
        this.context = context;
        this.tips = tips;
    }
}
