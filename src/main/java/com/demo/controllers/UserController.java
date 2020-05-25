package com.demo.controllers;

import com.demo.entities.User;
import com.demo.json.Bean;
import com.demo.service.IUserService;
import com.demo.util.MD5Utils;
import com.demo.util.StaticFinal;
import com.demo.util.TestValid;
import com.demo.util.Tool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

//ok
@RestController
@RequestMapping("/")
public class UserController {
    //    @Autowired
//    //根据yml中的多值和entity注入进来
//    private BaseEntity baseEntity;
//
//    @Value("${page.size}")
//    private Integer value;
    @Autowired
    private IUserService userService;

    //
    @RequestMapping(value = "login", method = RequestMethod.POST)
    public String login(@RequestBody User user, HttpServletRequest request) {
        List<Bean<User>> list = new ArrayList<>();
        User u = userService.login(user.getId(), user.getPassword());
        if (null != u) {
            u.setLastLoginTime(Tool.createNewTimeStamp());
            userService.updateById(u);
            HttpSession s = request.getSession(false);
            if (s == null) {
                HttpSession session = request.getSession();
                session.setAttribute("user", u);
                Bean<User> bean = new Bean<>();
                bean.setData(u);
                list.add(bean);
                return Tool.makeJsonResultString(true, StaticFinal.CODE_REQUEST_SUCCESS, session.getId(), "用户登录成功", list);
            } else {
                u.setLastLoginTime(Tool.createNewTimeStamp());
                userService.updateById(u);
                return Tool.makeJsonResultString(false, StaticFinal.CODE_REQUEST_FAIL, null, "该账户已被登录，请重新验证登录", null);
            }
        } else {
            return Tool.makeJsonResultString(false, StaticFinal.CODE_REQUEST_FAIL, null, "账号或密码错误，请重新输入", null);
        }
    }

    @RequestMapping(value = "register", method = RequestMethod.POST)
    public String register(@RequestBody User user) {
        boolean is = TestValid.use(user);
        if (is) {
            user.setRegisterTime(Tool.createNewTimeStamp());
            user.setWorkTime(Tool.getDeFaultTaskWorkTime());
            user.setRestTime(Tool.getDeFaultTargetRelaxTime());
            user.setIsHide(StaticFinal.HIDE);
            user.setPassword(new MD5Utils().MD5(user.getPassword()));
            if (userService.save(user)) {
                List<Bean<User>> list = new ArrayList<>();
                Bean<User> bean = new Bean<>();
                bean.setData(user);
                list.add(bean);
                return Tool.makeJsonResultString(true, StaticFinal.CODE_REQUEST_SUCCESS, null, "用户注册成功", list);
            } else {
                return Tool.makeJsonResultString(false, StaticFinal.CODE_REQUEST_FAIL, null, "注册失败", null);
            }
        } else {
            return Tool.makeJsonResultString(false, StaticFinal.CODE_REQUEST_FAIL, null, "无效的用户信息", null);
        }

    }
}
