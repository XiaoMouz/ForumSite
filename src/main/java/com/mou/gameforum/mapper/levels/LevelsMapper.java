package com.mou.gameforum.mapper.levels;

import com.github.yulichang.base.MPJBaseMapper;
import com.mou.gameforum.entity.Levels;
import com.mou.gameforum.entity.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface LevelsMapper extends MPJBaseMapper<Levels> {
    /**
     * 获取默认验证后用户身份组
     * @return 权限组
     */
    @Select("select * from userlevels where verifylevel = 1")
    Levels getVerifyLevel();

    /**
     * 获取默认待验证用户身份组
     * @return 权限组
     */
    @Select("select * from userlevels where pendinglevel = 1")
    Levels getPendingLevel();


    /**
     * 获取对应用户的权限组
     * @param user 用户
     * @return 权限组列表
     */
    @Select("select * from userlevels where id in (select lId from users_levels where uId = #{id})")
    List<Levels> getUserLevels(User user);

    /**
     * 设置用户权限组
     * @param user 用户
     * @param levels 权限组
     * @return 更新的行数
     */
    @Insert("insert into users_levels (uId, lId) values (#{user.id}, #{levels.id})")
    int addUserLevel(User user, Levels levels);

    /**
     * 删除用户权限组
     * @param user 用户
     * @param levels 权限组
     * @return 更新的行数
     */
    @Delete("delete from users_levels where uId = #{user.id} and lId = #{levels.id}")
    int deleteUserLevel(User user, Levels levels);
}
