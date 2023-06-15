package com.mou.gameforum.mapper.content;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.yulichang.base.MPJBaseMapper;
import com.mou.gameforum.entity.Post;
import com.mou.gameforum.entity.Section;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PostMapper extends MPJBaseMapper<Post> {

    /**
     * 发帖
     * @param post 帖子
     * @param section 板块
     * @return int
     */
    @Insert("insert into post " +
            "(publisher,title,content,releasetime,modifytime,status,sid,headimage) " +
            "values " +
            "(#{post.publisher.id},#{post.title},#{post.content},#{post.releaseTime},#{post.modifyTime},#{post.status},#{section.id},#{post.headimage})")
    int insert(Post post, Section section);
}
