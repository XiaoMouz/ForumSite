package com.mou.gameforum.mapper.content;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.yulichang.base.MPJBaseMapper;
import com.mou.gameforum.entity.Post;
import com.mou.gameforum.entity.Section;
import com.mou.gameforum.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface PostMapper extends MPJBaseMapper<Post> {

    @Select("select * from users where users.id = (select publisher FROM posts WHERE posts.id = #{postId})")
    User getPostPublisher(Integer postId);

    /**
     * 发帖
     * @param post 帖子
     * @param section 板块
     * @return int
     */
    @Insert("insert into posts " +
            "(publisher,title,content,releasetime,modifytime,status,sid,headimage) " +
            "values " +
                "(#{post.publisher.id},#{post.title},#{post.content},#{post.releaseTime},#{post.modifyTime},#{post.status},#{section.id},#{post.headImage})")
    int insert(Post post, Section section);
}
