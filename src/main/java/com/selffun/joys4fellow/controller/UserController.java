package com.selffun.joys4fellow.controller;

import com.selffun.joys4fellow.entity.Visitor;
import com.selffun.joys4fellow.service.UserService;
import com.selffun.joys4fellow.util.WebUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.*;

@Api("用户留言板接口")
@Controller
@RequestMapping(value = {"/user"})
public class UserController {

    /***
     * 注入
     */

    @Autowired
    private UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    /***
     *跳转到用户登录页面
     * @return 登陆页面
     */
    @ApiOperation(value = "登陆页面", notes = "用户登录页面",httpMethod = "GET")
    @RequestMapping(value = {"/loginHtml"},method = RequestMethod.GET)
    public String loginHtml(HashMap<String, Object> map,HttpServletRequest request) {
        String ip = WebUtil.getRealIp(request);//得到用户请求的ip地址
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
    @ApiOperation(value = "保存用户名", notes = "用户第一次登陆页面，保存ip以及用户名",httpMethod = "POST")
    @ApiImplicitParam(name = "username",value = "用户名",required = true,dataType = "String",paramType = "path")
    @RequestMapping(value = {"/userLogin"},method = RequestMethod.POST)
    public String userLogin(HashMap<String, Object> map,HttpServletRequest request, @RequestParam("username") String username){
        String ip = WebUtil.getRealIp(request);
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
    @ApiOperation(value = "保存评论", notes = "用户提交评论后保存用户评论，评论不能为空",httpMethod = "POST")
    @ApiImplicitParam(name = "comments",value = "评论",required = true,dataType = "String",paramType = "path")
    @RequestMapping(value = {"/commentsCommit"},method = RequestMethod.POST)
    public String commentsCommit(HashMap<String, Object> map,HttpServletRequest request,@RequestParam("comments") String comments){
        String ip =WebUtil.getRealIp(request);//得到用户IP地址
        logger.info("==================================");
        logger.info("用户ip为："+ip+"的评论内容为"+comments);
        logger.info("==================================");
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
    @ApiOperation(value = "查看用户评论", notes = "根据接口传入的用户名来查询用户相关评论",httpMethod = "GET")
    @ApiImplicitParam(name = "username",value = "用户名",required = true,dataType = "String",paramType = "path")
    @RequestMapping(value = {"/viewUserComments"},method = RequestMethod.GET)
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
    @ApiOperation(value = "跳转", notes = "回到个人用户界面",httpMethod = "GET")
    @RequestMapping(value = {"/back2IndividualPage"},method = RequestMethod.GET)
    public String back2IndividualPage(HashMap<String, Object> map,HttpServletRequest request){
        String ip = WebUtil.getRealIp(request);
        Visitor visitor = userService.checkIP(ip);
        map.put("username",visitor.getUsername());
        return "IndividualPage";
    }

    /***
     * 回到用户列表界面
     * @param map
     * @return
     */
    @ApiOperation(value = "跳转", notes = "回到用户列表界面",httpMethod = "GET")
    @RequestMapping(value = {"/back2CommentsList"},method = RequestMethod.GET)
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
    @ApiOperation(value = "跳转", notes = "跳转到用户列表",httpMethod = "GET")
    @RequestMapping(value = {"/go2CommentsList"},method = RequestMethod.GET)
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
    @ApiOperation(value = "跳转", notes = "跳转到公共评论查看区域",httpMethod = "GET")
    @RequestMapping(value = {"/go2CommonArea"},method = RequestMethod.GET)
    public String searchLast50Comments(HashMap<String, Object> map){
        List<Map<String,Object>> resultList = userService.searchLast50Comments();
        if(resultList!=null&&resultList.size()>0){
            map.put("resultList",resultList);
        }
        return "CommonArea";
    }

}
