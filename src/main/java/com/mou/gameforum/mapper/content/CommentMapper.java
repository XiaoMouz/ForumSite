package com.mou.gameforum.mapper.content;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.yulichang.base.MPJBaseMapper;
import com.mou.gameforum.entity.Comments;
import com.mou.gameforum.entity.Post;
import com.mou.gameforum.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CommentMapper extends MPJBaseMapper<Comments> {
    /**
     * Post 新评论
     * @param comments 评论
     * @param post 帖子
     * @return int
     */
    @Insert("insert into comments (pid,uid,content,releaseTime,status) values (#{post.id},#{comments.publisher.id},#{comments.content},#{comments.releaseTime},#{comments.status})")
    int insert(Comments comments, Post post);

    /**
     * Post 评论下新评论
     * @param comments 评论
     * @param post 帖子
     * @param father 父评论
     * @return int
     */
    @Insert("insert into comments (pid,uid,content,releaseTime,status,fid) values (#{post.id},#{comments.publisher.id},#{comments.content},#{comments.releaseTime},#{comments.status},#{father.id})")
    int insert(Comments comments, Post post,Comments father);

    /**
     * 根据帖子id查询评论
     * @param id 帖子id
     * @return List<Comments>
     */
    @Select("select * from comments where pid = #{id}")
    List<Comments> selectByPostId(Integer id);

    /**
     * 根据评论父 id 查询评论
     * @param id 评论父 id
     * @return List<Comments>
     */
    @Select("select * from comments where fid = #{id}")
    List<Comments> selectByFatherCommentId(Integer id);

    @Select("select * from users where users.id = (select uid FROM comments WHERE comments.id = #{commentId})")
    User getCommentPublisher(Integer commentId);
}
