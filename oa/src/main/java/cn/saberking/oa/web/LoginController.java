package cn.saberking.oa.web;

import cn.saberking.oa.domain.User;
import cn.saberking.oa.service.UserService;
import cn.saberking.oa.util.common.HrmConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

/**
 * @Auther:luanzhaofei@outlook.com
 * @Date:2019/11/11
 * @Description:cn.saberking.oa.web
 * @version:1.0
 */
@Controller
public class LoginController {
    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String loginPage(){
        return "login";
    }

    @GetMapping("/hrm/index")
    public String indexPage(){
        return "index";
    }

    /**
     * 处理登录请求
     * @param loginName  登录名
     * @param password 密码
     * @return 跳转的视图
     * */
    @RequestMapping(value="/login")
    public String login(@RequestParam("loginName") String loginName,
                        @RequestParam("password") String password,
                        HttpSession session,
                        RedirectAttributes attributes){
        // 调用业务逻辑组件判断用户是否可以登录
        User user = userService.login(loginName, password);
        if(user != null){
            // 将用户保存到HttpSession当中
            session.setAttribute(HrmConstants.USER_SESSION, user);
            // 客户端跳转到main页面
            return "index";
        }else{
            // 设置登录失败提示信息
            attributes.addFlashAttribute("message", "登录名或密码错误!请重新输入");
            // 服务器内部跳转到登录页面
            return "redirect:/";
        }
    }
    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.removeAttribute(HrmConstants.USER_SESSION);
        return "redirect:/";
    }
}
