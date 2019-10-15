package com.selffun.joys4fellow.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
@Accessors(chain = true)
public class Comments implements Serializable {

    private Integer id; // 主键

    private String username; // 用户名

    private String comments; // 评论内容

    private Timestamp commentsTime; // 对应评论时间
}
