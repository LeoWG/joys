package com.selffun.joys4fellow.controller;

import com.selffun.joys4fellow.entity.Visitor;
import com.selffun.joys4fellow.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.*;

@Controller
@RequestMapping(value = {"/user"})
public class UserController {

    /***
     * 注入
     */

    @Autowired
    private UserService userService;

    /***
     *跳转到用户登录页面
     * @return 登陆页面
     */
    @RequestMapping(value = {"/loginHtml"})
    public String loginHtml(HashMap<String, Object> map,HttpServletRequest request) {
        String ip = getRealIp(request);//得到用户请求的ip地址
        Visitor visitor = userService.checkIP(ip);//通过ip来查询数据库看用户是否已经登陆注册过
        if(visitor!=null){
            long totalVisitTimes = visitor.getTotalVisitTimes();;//查询用户访问次数
            userService.updateTotalVisitTimes(ip,++totalVisitTimes);//自加1进行存储
            map.put("username",visitor.getUsername());
            return "IndividualPage";
        }
        return "welcomePage";
    }

    /***
     * 用户第一次登陆页面，保存ip以及用户名
     */
    @RequestMapping(value = {"/userLogin"})
    public String userLogin(HashMap<String, Object> map,HttpServletRequest request, @RequestParam("username") String username){
        String ip = getRealIp(request);
        Visitor visitor1 = userService.checkIP(ip);//通过ip来查询数据库看用户是否已经登陆注册过
        Visitor visitor2 = userService.checkUsername(username);
        if(visitor1==null&&visitor2 == null){//如果不存在用户名并且不存在IP地址
            int res = userService.addUser(username,ip);
            if(res>0){
                long totalVisitTimes = 1L;
                userService.updateTotalVisitTimes(ip,totalVisitTimes);//用户第一次访问次数为1
                map.put("username",username);
                return "IndividualPage";
            }else{
                return "ErrorPage";
            }
        }else{
           map.put("message",true);
           return "welcomePage";
        }
    }

    /***
     * 用户提交评论
     */
    @RequestMapping(value = {"/commentsCommit"})
    public String commentsCommit(HashMap<String, Object> map,HttpServletRequest request,@RequestParam("comments") String comments){
        String ip = getRealIp(request);//得到用户IP地址
        Date date = new Date(System.currentTimeMillis());
        Timestamp timestamp = new Timestamp(date.getTime());
        int res1 = userService.addCommentsTime(ip,timestamp);
        Visitor visitor = userService.checkIP(ip);
        String username = "";
        if(visitor!=null){
            username = visitor.getUsername();
        }
        if(comments.length()==0||comments.replaceAll("\\s*","").length()==0){
            map.put("flag",true);
            map.put("username",username);
            return "IndividualPage";
        }
        int res2 = userService.add2Comments(username,comments,timestamp);
        if(res1>0&&res2>0){
            List<Map<String,Object>> resultList = userService.searchLast50Comments();
            map.put("resultList",resultList);
            return "CommonArea";
        }
        return "ErrorPage";
    }
    /***
     * 查看用户详细评论 
     */
    @RequestMapping(value = {"/viewUserComments"})
    public String viewUserComments(HashMap<String, Object> map,@RequestParam("username") String username){
        List<String> resultList = new ArrayList<String>();
        resultList = userService.searchCommentsByUsername(username);
        if(resultList!=null&&resultList.size()>0){
            map.put("resultList",resultList);
            map.put("username",username);
        }
        return "DetailPage";
    }

    /***
     * 回到个人用户界面
     * @param map
     * @return
     */
    @RequestMapping(value = {"/back2IndividualPage"})
    public String back2IndividualPage(HashMap<String, Object> map,HttpServletRequest request){
        String ip = getRealIp(request);
        Visitor visitor = userService.checkIP(ip);
        map.put("username",visitor.getUsername());
        return "IndividualPage";
    }

    /***
     * 回到用户列表界面
     * @param map
     * @return
     */
    @RequestMapping(value = {"/back2CommentsList"})
    public String back2CommentsList(HashMap<String, Object> map){
        List<Map<String,Object>> list = userService.searchUserList();
        if(list!=null&&list.size()>0){
            map.put("resultList",list);
        }
        return "CommentsList";
    }

    /***
     * 跳转到用户列表
     * @param map
     * @return
     */
    @RequestMapping(value = {"/go2CommentsList"})
    public String go2CommentsList(HashMap<String, Object> map){
        List<Map<String,Object>> list = userService.searchUserList();
        if(list!=null&&list.size()>0){
            map.put("resultList",list);
        }
        return "CommentsList";
    }

    /***
     * 跳转到公共评论查看区域
     * @param map
     * @return
     */
    @RequestMapping(value = {"/go2CommonArea"})
    public String searchLast50Comments(HashMap<String, Object> map){
        List<Map<String,Object>> resultList = userService.searchLast50Comments();
        if(resultList!=null&&resultList.size()>0){
            map.put("resultList",resultList);
        }
        return "CommonArea";
    }

    //***************以下是不对外的接口******************

    /**
     * 获取客户端真实IP
     * @param request request本体
     * @return 真实ip
     */
    public String getRealIp(HttpServletRequest request){
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
