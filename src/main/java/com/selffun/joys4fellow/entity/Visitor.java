package com.selffun.joys4fellow.entity;

import java.sql.Timestamp;

public class Visitor {

    private Integer id; // 主键

    private String username; // 登陆名

    private String ip; // 请求ip地址，用来标记唯一用户

    private Long totalVisitTimes; // 用户总访问次数

    private Timestamp latestTime; // 用户最近访问时间

    public Integer getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getIp() {
        return ip;
    }

    public Long getTotalVisitTimes() {
        return totalVisitTimes;
    }

    public Timestamp getLatestTime() {
        return latestTime;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public void setTotalVisitTimes(Long totalVisitTimes) {
        this.totalVisitTimes = totalVisitTimes;
    }

    public void setLatestTime(Timestamp latestTime) {
        this.latestTime = latestTime;
    }
}
