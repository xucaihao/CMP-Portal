package com.cmp.portal.user.controller;

import com.cmp.portal.common.ResponseData;
import com.cmp.portal.common.SessionCounter;
import com.cmp.portal.common.WebUtil;
import com.cmp.portal.user.model.User;
import com.cmp.portal.user.model.req.ReqAddMapping;
import com.cmp.portal.user.model.req.ReqModMapping;
import com.cmp.portal.user.model.req.ReqUser;
import com.cmp.portal.user.model.res.ResUser;
import com.cmp.portal.user.model.res.ResUserMappings;
import com.cmp.portal.user.model.res.ResUsers;
import com.cmp.portal.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import static com.cmp.portal.common.Constance.TIME_OUT_SECONDS;
import static java.util.stream.Collectors.toList;

@Controller
@RequestMapping("")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    /**
     * 跳转到登录页面
     *
     * @return 登录页面
     */
    @RequestMapping("")
    public ModelAndView index() {
        return new ModelAndView("login.html");
    }

    @RequestMapping("index")
    public ModelAndView toIndexPage() {
        return new ModelAndView("index.html");
    }

    @RequestMapping("index_v1")
    public ModelAndView toIndexV1Page() {
        return new ModelAndView("index_v1.html");
    }

    @RequestMapping("/user/userListHtml")
    public ModelAndView userListHtml() {
        return new ModelAndView("pages/user/user.html");
    }

    @RequestMapping("/userMapping/userMappingListHtml")
    public ModelAndView userMappingListHtml() {
        return new ModelAndView("pages/user/user-mapping.html");
    }

    /**
     * 获取在线用户列表
     *
     * @return 在线用户列表
     */
    @SuppressWarnings("unchecked")
    private List<User> describeOnlineUser() {
        HttpSession session = WebUtil.session();
        ServletContext application = session.getServletContext();
        return (List<User>) application.getAttribute("onlineUserList");
    }

    /**
     * 用户登录
     *
     * @param account  用户账号
     * @param password 密码
     * @return 成功页面/失败页面
     */
    @RequestMapping("/login")
    @ResponseBody
    public ResponseData login(String account, String password) {
        try {
            ReqUser user = new ReqUser();
            user.setUserName(account);
            user.setPassword(password);
            ResUser loginUser = userService.describeLoginUser(user).getBody();
            //将登录用户信息存入session
            HttpSession session = WebUtil.session();
            session.setAttribute("user", loginUser.getUser());
            //若用户首次登录，将登录用户添加进在线用户列表
            boolean flag = 0 == SessionCounter.onlineUsers.size()
                    || SessionCounter.onlineUsers.stream().noneMatch(vo ->
                    vo.getUserId().equals(loginUser.getUser().getUserId()));
            if (flag) {
                SessionCounter.onlineUsers.add(loginUser.getUser());
                ServletContext application = session.getServletContext();
                application.setAttribute("onlineUserList", SessionCounter.onlineUsers);
            }
            return ResponseData.success();
        } catch (Exception e) {
            logger.info(e.getMessage());
            return ResponseData.failure(e.getMessage());
        }
    }

    /**
     * 用户登出
     *
     * @return 登录页面/失败页面
     */
    @RequestMapping("/logout")
    public ModelAndView logout() {
        try {
            HttpSession session = WebUtil.session();
            session.invalidate();
            return new ModelAndView("login.html");
        } catch (Exception e) {
            return new ModelAndView("error.html");
        }
    }

    /**
     * 注册用户
     *
     * @param user 用户
     * @return 操作结果
     */
    @RequestMapping(value = "/users/register")
    @ResponseBody
    public ResponseData registerUser(ReqUser user) {
        try {
            userService.registerUser(user);
            return ResponseData.success();
        } catch (Exception e) {
            logger.info(e.getMessage());
            return ResponseData.failure(e.getMessage());
        }
    }

    /**
     * 修改用户
     *
     * @param reqUser 用户
     * @return 操作结果
     */
    @RequestMapping(value = "/users/{userId}/update")
    @ResponseBody
    public ResponseData updateUser(ReqUser reqUser, @PathVariable String userId) {
        try {
            User user = WebUtil.getCurrentUser();
            ResponseEntity<ResUser> response = userService.updateUser(user, reqUser, userId);
            ResUser resUser = response.getBody();
            if (userId.equals(user.getUserId())) {
                HttpSession session = WebUtil.session();
                session.setAttribute("user", resUser.getUser());
            }
            return ResponseData.success();
        } catch (Exception e) {
            logger.info(e.getMessage());
            return ResponseData.failure(e.getMessage());
        }
    }

    /**
     * 查询用户列表
     *
     * @return 用户列表
     */
    @RequestMapping("/users")
    @ResponseBody
    public ResponseData<ResUsers> describeUsers() {
        try {
            User user = WebUtil.getCurrentUser();
            List<User> users = userService.describeUsers(user)
                    .getBody()
                    .getUsers();
            List<User> onlineUser = describeOnlineUser();
            users.forEach(u -> {
                if (onlineUser.stream().anyMatch(vo -> vo.getUserId().equals(u.getUserId())))
                    u.setOnline(true);
            });
            return ResponseData.success(new ResUsers(users));
        } catch (Exception e) {
            logger.info(e.getMessage());
            return ResponseData.failure(e.getMessage());
        }
    }

    /**
     * 根据id查询指定用户
     *
     * @param userId 用户id
     * @return 指定用户
     */
    @RequestMapping("/users/{userId}")
    @ResponseBody
    public ResponseData<ResUser> describeUserAttribute(@PathVariable String userId) {
        try {
            User user = WebUtil.getCurrentUser();
            ResponseEntity<ResUser> response = userService.describeUserAttribute(user, userId);
            return ResponseData.success(response.getBody());
        } catch (Exception e) {
            logger.info(e.getMessage());
            return ResponseData.failure(e.getMessage());
        }
    }

    /**
     * 批量删除用户
     *
     * @param userIds 删除用户id列表
     * @return 操作结果
     */
    @RequestMapping("/users/delete")
    @ResponseBody
    public ResponseData deleteUsers(@RequestParam("userIds[]") List<String> userIds) {
        try {
            User user = WebUtil.getCurrentUser();
            List<CompletableFuture<Map<String, String>>> futures = userIds.stream().map(userId ->
                    CompletableFuture.supplyAsync(() -> {
                        Map<String, String> response = new HashMap<>(16);
                        try {
                            userService.deleteUser(user, userId);
                            response.put("code", "success");
                            response.put("msg", "");
                            return response;
                        } catch (Exception e) {
                            response.put("code", "fail");
                            response.put("msg", e.getMessage());
                            return response;
                        }
                    })).collect(toList());
            List<Map<String, String>> failedDel = futures.stream().map(vo -> {
                try {
                    return vo.get(TIME_OUT_SECONDS, TimeUnit.SECONDS);
                } catch (Exception e) {
                    return null;
                }
            }).filter(x -> null != x && "fail".equals(x.get("code"))).collect(toList());
            int delNum = userIds.size();
            int failedNum = failedDel.size();
            int successNum = delNum - failedNum;
            Map<String, Object> resMap = new HashMap<>(16);
            //删除一个用户失败，直接返回失败信息
            if (1 == delNum && 1 == failedNum) {
                return ResponseData.failure(failedDel.get(0).get("msg"));
            } else {
                resMap.put("successNum", successNum);
                resMap.put("failNum", failedNum);
                //全部删除成功
                if (delNum == successNum) {
                    return ResponseData.success(resMap);
                } else if (delNum == failedNum) {
                    //删除多个用户，全部删除失败
                    return ResponseData.failure(resMap);
                } else {
                    //删除多个用户，部分成功
                    return ResponseData.warning(resMap);
                }
            }
        } catch (Exception e) {
            logger.info(e.getMessage());
            return ResponseData.failure(e.getMessage());
        }
    }

    /**
     * 添加用户映射关系
     *
     * @param mapping 请求体
     * @return 操作结果
     */
    @RequestMapping(value = "/userMappings/add")
    @ResponseBody
    public ResponseData addUserMapping(ReqAddMapping mapping) {
        try {
            User user = WebUtil.getCurrentUser();
            userService.addUserMapping(user, mapping);
            return ResponseData.success();
        } catch (Exception e) {
            logger.info(e.getMessage());
            return ResponseData.failure(e.getMessage());
        }
    }

    @RequestMapping("/userMappings")
    @ResponseBody
    public ResponseData<ResUserMappings> describeUserMappings() {
        try {
            User user = WebUtil.getCurrentUser();
            ResponseEntity<ResUserMappings> response = userService.describeUserMappings(user);
            return ResponseData.success(response.getBody());
        } catch (Exception e) {
            logger.info(e.getMessage());
            return ResponseData.failure(e.getMessage());
        }
    }


    /**
     * 修改用户映射关系
     *
     * @param mapping   请求体
     * @param mappingId 映射id
     * @return 操作结果
     */
    @RequestMapping(value = "/userMappings/{mappingId}/update")
    @ResponseBody
    public ResponseData updateUserMapping(ReqModMapping mapping, @PathVariable String mappingId) {
        try {
            User user = WebUtil.getCurrentUser();
            userService.updateUserMapping(user, mapping, mappingId);
            return ResponseData.success();
        } catch (Exception e) {
            logger.info(e.getMessage());
            return ResponseData.failure(e.getMessage());
        }
    }

    /**
     * 删除用户映射关系
     *
     * @param mappingId 映射id
     * @return 操作结果
     */
    @RequestMapping("/userMappings/{mappingId}/delete")
    @ResponseBody
    public ResponseData deleteUserMappingById(@PathVariable String mappingId) {
        try {
            User user = WebUtil.getCurrentUser();
            userService.deleteUserMappingById(user, mappingId);
            return ResponseData.success();
        } catch (Exception e) {
            logger.info(e.getMessage());
            return ResponseData.failure(e.getMessage());
        }
    }

}
