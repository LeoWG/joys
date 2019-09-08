package com.selffun.joys4fellow.entity;

import java.sql.Timestamp;

public class Comments {

    private Integer id; // 主键

    private String username; // 用户名

    private String comments; // 评论内容

    private Timestamp commentsTime; // 对应评论时间

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Timestamp getCommentsTime() {
        return commentsTime;
    }

    public void setCommentsTime(Timestamp commentsTime) {
        this.commentsTime = commentsTime;
    }
}
