package com.selffun.joys4fellow.mapper;

import com.selffun.joys4fellow.entity.Comments;
import com.selffun.joys4fellow.entity.Visitor;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

@Mapper
@Component
public interface UserMapper {

    //查询用户请求ip是否已经存在数据库
    Visitor checkIP(@Param("ip") String ip);

    //保存用户的用户名和IP地址到visitors表
    int addUser(@Param("username") String username,@Param("ip") String ip);

    //保存用户的评论时间到visitors表
    int addCommentsTime(@Param("ip") String ip,@Param("timestamp") Timestamp timestamp);

    //保存用户的用户名、评论内容、评论时间到comments表
    int add2Comments(@Param("username") String username,@Param("comments") String comments,@Param("timestamp") Timestamp timestamp);

    //保存用户访问次数
    int updateTotalVisitTimes(@Param("ip") String ip,@Param("totalVisitTimes") long totalVisitTimes);

    //校验用户名是否重复
    Visitor checkUsername(@Param("username") String username);

    //查询用户列表
    List<Map<String,Object>> searchUserList();

    //通过指定username查找用户的所有评论
    List<String> searchCommentsByUsername(@Param("username") String password);

    //查询前50个用户评论
    List<Map<String,Object>> searchLast50Comments();
}
