package com.mou.gameforum.service.content;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mou.gameforum.entity.Post;
import com.mou.gameforum.entity.dto.PostDto;

import java.util.List;

public interface PostService extends IService<Post> {

    List<Post> getPostListBySectionId(Integer sectionId, Integer page);

    Integer getTotalPageBySectionId(Integer sectionId);
    Post getPostById(Integer id);
    List<Post> getPostListByKeyword(String keyword);

    void addPost(PostDto postDto);

    int markDelete(Post post);
}
