package com.hahaen.MultiplayerBlogging.mapper;

import com.hahaen.MultiplayerBlogging.service.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    @Select("SELECT * FROM USER WHERE ID = #{ID}")
    User findUserById(@Param("ID") Integer ID);
}
