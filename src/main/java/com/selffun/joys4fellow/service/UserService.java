package com.selffun.joys4fellow.service;

import com.selffun.joys4fellow.entity.Visitor;
import com.selffun.joys4fellow.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

@Service
public class UserService {

    /***
     * 注入dao
     */
    @Autowired
    private UserMapper userMapper;

    //查询指定ip是否已经存在数据库
    public Visitor checkIP(String ip){
        Visitor visitor = userMapper.checkIP(ip);
        return visitor;
    }

    //保存首次访问页面的用户的用户名和IP地址
    public int addUser(String username,String ip){
        int res = userMapper.addUser(username,ip);
        return res;
    }

    //保存用户的评论时间到visitors表
    public int addCommentsTime(String ip, Timestamp timestamp){
        int res = userMapper.addCommentsTime(ip,timestamp);
        return res;
    }

    //保存用户的用户名、评论时间、评论内容到comments表
    public int add2Comments(String username,String comments,Timestamp timestamp){
        int res = userMapper.add2Comments(username,comments,timestamp);
        return res;
    }

    //保存用户访问次数
    public int updateTotalVisitTimes(String ip,long totalVisitTimes){
        int res = userMapper.updateTotalVisitTimes(ip,totalVisitTimes);
        return res;
    }

    //校验用户名是否重复
    public Visitor checkUsername(String username){
        Visitor visitor = userMapper.checkUsername(username);
        return visitor;
    }

    //查询用户列表
    public List<Map<String,Object>> searchUserList(){
        List<Map<String,Object>> list = userMapper.searchUserList();
        return list;
    }

    //通过指定username查找用户的所有评论
    public List<String> searchCommentsByUsername(String username){
        List resultList = userMapper.searchCommentsByUsername(username);
        return resultList;
    }

    //查询前50个用户评论searchLast50Comments
    public  List<Map<String,Object>> searchLast50Comments(){
        List<Map<String,Object>> list = userMapper.searchLast50Comments();
        return list;
    }

}
