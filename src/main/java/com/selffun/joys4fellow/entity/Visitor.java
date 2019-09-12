package com.selffun.joys4fellow.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.sql.Timestamp;

@Data
@Accessors(chain = true)
public class Visitor {

    private Integer id; // 主键

    private String username; // 登陆名

    private String ip; // 请求ip地址，用来标记唯一用户

    private Long totalVisitTimes; // 用户总访问次数

    private Timestamp latestTime; // 用户最近访问时间

}
