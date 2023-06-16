package com.mou.gameforum.entity.dto;

import com.mou.gameforum.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {
    String title;
    String content;
    String imagePath;
    Integer section;
    User publisher;
}
