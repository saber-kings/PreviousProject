package cn.saberking.oa.web;

import cn.saberking.oa.domain.Notice;
import cn.saberking.oa.service.NoticeService;
import cn.saberking.oa.service.UserService;
import cn.saberking.oa.util.common.HrmConstants;
import cn.saberking.oa.vo.NotQuery;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * @Auther:luanzhaofei@outlook.com
 * @Date:2019/11/9
 * @Description:cn.saberking.oa.web 处理公告请求控制器
 * @version:1.0
 */
@Controller
@RequestMapping("hrm")
public class NoticeController {

    @Autowired
    private NoticeService noticeService;

    /**
     * 第一次访问页面，默认显示所有数据
     *
     * @param pageable 分页对象
     * @param notice 模糊查询参数
     * @param model    向页面封装数据的对象
     * @return 返回页面
     */
    @GetMapping("/notices")
    public String notices(@PageableDefault(size = HrmConstants.PAGE_DEFAULT_SIZE, sort = {"createDate"}, direction = Sort.Direction.DESC)
                                    Pageable pageable, NotQuery notice, Model model) {
        //查询公告信息
        model.addAttribute("page", noticeService.findAll(notice, pageable));
        return "notices";
    }

    /**
     * 处理查询单个公告的请求
     *
     * @param notice 模糊查询参数
     * @param model    向页面封装数据的对象
     * @return 返回页面
     */
    @PostMapping(value = "/notices/preview")
    public String preview(NotQuery notice, Model model) {
        //查询公告信息
        model.addAttribute("preNotice", noticeService.getAndConvert(notice.getId()));
        return "notices :: preview";
    }

    /**
     * 处理查询单个公告的请求
     *
     * @param model    向页面封装数据的对象
     * @return 返回页面
     */
    @GetMapping(value = "/help")
    public String help(Model model) {
        //查询公告信息
        model.addAttribute("help", noticeService.findByTitle("help"));
        return "help";
    }

    /**
     * 处理查询请求
     *
     * @param pageable 分页对象
     * @param notice 模糊查询参数
     * @param model    向页面封装数据的对象
     * @return 返回页面
     */
    @PostMapping(value = "/notices/search")
    public String selectNotice(@PageableDefault(size = HrmConstants.PAGE_DEFAULT_SIZE, sort = {"createDate"}, direction = Sort.Direction.DESC)
                                         Pageable pageable, NotQuery notice, Model model) {
        System.out.println("notice = " + notice);
        //查询公告信息
        model.addAttribute("page", noticeService.findAll(notice, pageable));
        return "notices :: noticeList";

    }

    /**
     * 处理删除公告请求
     *
     * @param pageable 分页对象
     * @param notQuery 模糊查询参数
     * @param model    向页面封装数据的对象
     * @return 返回页面
     */
    @PostMapping(value = "/notices/delete")
    public String batchDelNot(@PageableDefault(size = HrmConstants.PAGE_DEFAULT_SIZE, sort = {"createDate"}, direction = Sort.Direction.DESC)
                                          Pageable pageable, NotQuery notQuery, Model model) {
        if (StringUtils.isNotBlank(notQuery.getNotIds())){
            noticeService.batchDelById(notQuery.getNotIds());
        }
        //查询公告信息
        model.addAttribute("page", noticeService.findAll(notQuery, pageable));
        return "notices :: noticeList";
    }

    /**
     * 路由新增请求的页面
     *
     * @param model 向页面封装数据的对象
     * @return 返回页面
     */
    @GetMapping("/notices/input")
    public String input(Model model) {
        initialNotice(new Notice(), model);
        return "notices-input";
    }

    private void initialNotice(Notice notice, Model model) {
        model.addAttribute("notice", notice);
    }

    /**
     * 处理修改公告请求
     *
     * @param id    修改的公告id
     * @param model 向页面封装数据的对象
     * @return 返回页面
     */
    @GetMapping("/notices/{id}/input")
    public String editInput(@PathVariable Long id, Model model) {
        Notice notice = noticeService.findById(id);
        initialNotice(notice, model);
        return "notices-input";
    }

    /**
     * 处理添加或修改完之后的提交请求
     *
     * @param notice   新增或修改后的公告信息
     * @param attributes 向重定向后的页面封装数据的对象
     * @return 返回页面
     */
    @PostMapping("/notices")
    public String post(Notice notice, RedirectAttributes attributes) {
        Notice e;
        if (notice.getId() == null) {
            e = noticeService.save(notice);
        } else {
            e = noticeService.update(notice.getId(), notice);
        }
        if (e == null) {
            attributes.addFlashAttribute("message", "操作失败");
        } else {
            attributes.addFlashAttribute("message", "操作成功");
        }
        return "redirect:/hrm/notices";
    }

}
