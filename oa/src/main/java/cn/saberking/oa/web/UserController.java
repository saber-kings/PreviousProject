package cn.saberking.oa.web;

import cn.saberking.oa.domain.User;
import cn.saberking.oa.service.UserService;
import cn.saberking.oa.util.common.HrmConstants;
import cn.saberking.oa.vo.UserQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther:luanzhaofei@outlook.com
 * @Date:2019/11/9
 * @Description:cn.saberking.oa.web 处理用户请求控制器
 * @version:1.0
 */
@Controller
@RequestMapping("hrm")
public class UserController {
    @Autowired
    private UserService userService;

    @Value("${image.random.key}")
    private String avatarKey;

    @Value("${comment.avatar}")
    private String avatarPrefix;

    /**
     * 第一次访问页面，默认显示所有数据
     *
     * @param pageable 分页对象
     * @param user     模糊查询参数
     * @param model    向页面封装数据的对象
     * @return 返回页面
     */
    @GetMapping("/users")
    public String users(@PageableDefault(size = HrmConstants.PAGE_DEFAULT_SIZE, sort = {"createDate"}, direction = Sort.Direction.DESC)
                                Pageable pageable, UserQuery user, Model model) {
        //查询用户信息
        model.addAttribute("page", userService.findAll(user, pageable));
        return "users";
    }

    /**
     * 处理查询请求
     *
     * @param pageable 分页对象
     * @param user     模糊查询参数
     * @param model    向页面封装数据的对象
     * @return 返回页面
     */
    @PostMapping(value = "/users/search")
    public String selectUser(@PageableDefault(size = HrmConstants.PAGE_DEFAULT_SIZE, sort = {"createDate"}, direction = Sort.Direction.DESC)
                                     Pageable pageable, UserQuery user, Model model) {
        System.out.println("user = " + user);
        //查询用户信息
        model.addAttribute("page", userService.findAll(user, pageable));
        return "users :: userList";

    }

    /**
     * 处理删除用户请求
     *
     * @param id 需要删除的用户id
     * @param attributes 向重定向后的页面封装数据的对象
     * @return 返回页面
     */
    @GetMapping(value = "/users/{id}/delete")
    public String deleteUser(@PathVariable Long id, RedirectAttributes attributes) {
        userService.deleteById(id);
        attributes.addFlashAttribute("message", "删除成功");
        return "redirect:/hrm/users";
    }

    /**
     * 路由新增请求的页面
     *
     * @param model 向页面封装数据的对象
     * @return 返回页面
     */
    @GetMapping("/users/input")
    public String input(Model model) {
        initialUser(new User(), model);
        return "users-input";
    }

    private void initialUser(User user, Model model) {
        model.addAttribute("user", user);
        String[] avatarKeys = avatarKey.split(",");
        List<String> avatarUrls = new ArrayList<>();
        for (String key : avatarKeys) {
            avatarUrls.add(avatarPrefix + key);
        }
        model.addAttribute("avatarUrls", avatarUrls);
    }

    /**
     * 处理修改用户请求
     *
     * @param id    修改的用户id
     * @param model 向页面封装数据的对象
     * @return 返回页面
     */
    @GetMapping("/users/{id}/input")
    public String editInput(@PathVariable Long id, Model model) {
        User user = userService.findById(id);
        initialUser(user, model);
        return "users-input";
    }

    /**
     * 处理添加或修改完之后的提交请求
     * @param user 新增或修改后的用户信息
     * @param attributes 向重定向后的页面封装数据的对象
     * @return 返回页面
     */
    @PostMapping("/users")
    public String post(User user, @RequestParam String oldLoginName, BindingResult result, RedirectAttributes attributes){
        User u;
        if (user.getId() == null){
            if (verifyRepeat(user,null, result)) return "users-input";
            u = userService.save(user);
        } else {
            if (user.getNewPassword().equals(user.getConfirmPassword())){
                if (verifyRepeat(user, oldLoginName, result)) return "users-input";
                u = userService.update(user.getId(),user);
            }else {
                return checkPassword(attributes, "确认密码时，两次输入的密码不一致密码");
            }
        }
        if (u == null) {
            attributes.addFlashAttribute("message", "操作失败");
        }else {
            if (u.getCreateDate()==null){
                return checkPassword(attributes, "原密码不正确");
            }else {
                attributes.addFlashAttribute("message", "操作成功");
            }
        }
        return "redirect:/hrm/users";
    }

    private boolean verifyRepeat(User user, String oldLoginName, BindingResult result) {
        boolean ret = false;
        User dbUser = userService.findByLoginName(user.getLoginName());
        if (oldLoginName == null) {
            if (dbUser != null) {
                ret = true;
            }
        }else {
            if (dbUser != null && !oldLoginName.equals(user.getLoginName())) {
                ret = true;
            }
        }
        if (ret){
            result.rejectValue("loginName", "loginNameError", "该账号已存在，不能重复添加！");
        }
        return ret;
    }

    private String checkPassword(RedirectAttributes attributes, String s) {
        attributes.addFlashAttribute("error", s);
        return "redirect:/hrm/users";
    }

}
