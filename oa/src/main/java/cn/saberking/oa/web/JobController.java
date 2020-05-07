package cn.saberking.oa.web;

import cn.saberking.oa.domain.Job;
import cn.saberking.oa.service.JobService;
import cn.saberking.oa.util.common.HrmConstants;
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
 * @Description:cn.saberking.oa.web 处理职位请求控制器
 * @version:1.0
 */
@Controller
@RequestMapping("hrm")
public class JobController {
    @Autowired
    private JobService jobService;

    /**
     * 第一次访问页面，默认显示所有数据
     *
     * @param pageable 分页对象
     * @param queryName  模糊查询职位名称
     * @param model    向页面封装数据的对象
     * @return 返回页面
     */
    @GetMapping("/jobs")
    public String jobs(@PageableDefault(size = HrmConstants.PAGE_DEFAULT_SIZE, sort = {"createDate"}, direction = Sort.Direction.DESC)
                                Pageable pageable, String queryName, Model model) {
        //查询职位信息
        model.addAttribute("page", jobService.findAll(queryName, pageable));
        return "jobs";
    }

    /**
     * 处理查询请求
     *
     * @param pageable 分页对象
     * @param queryName  模糊查询职位名称
     * @param model    向页面封装数据的对象
     * @return 返回页面
     */
    @PostMapping(value = "/jobs/search")
    public String selectJob(@PageableDefault(size = HrmConstants.PAGE_DEFAULT_SIZE, sort = {"createDate"}, direction = Sort.Direction.DESC)
                                     Pageable pageable, @RequestParam String queryName, Model model) {
        //查询职位信息
        model.addAttribute("page", jobService.findAll(queryName, pageable));
        return "jobs :: jobList";

    }

    /**
     * 处理删除职位请求
     *
     * @param id 需要删除的职位id
     * @param attributes 向重定向后的页面封装数据的对象
     * @return 返回页面
     */
    @GetMapping(value = "/jobs/{id}/delete")
    public String deleteJob(@PathVariable Long id, RedirectAttributes attributes) {
        jobService.deleteById(id);
        attributes.addFlashAttribute("message", "删除成功");
        return "redirect:/hrm/jobs";
    }

    /**
     * 路由新增请求的页面
     *
     * @param model 向页面封装数据的对象
     * @return 返回页面
     */
    @GetMapping("/jobs/input")
    public String input(Model model) {
        initialJob(new Job(), model);
        return "jobs-input";
    }

    private void initialJob(Job job, Model model) {
        model.addAttribute("job", job);
    }

    /**
     * 处理修改职位请求
     *
     * @param id    修改的职位id
     * @param model 向页面封装数据的对象
     * @return 返回页面
     */
    @GetMapping("/jobs/{id}/input")
    public String editInput(@PathVariable Long id, Model model) {
        Job job = jobService.findById(id);
        initialJob(job, model);
        return "jobs-input";
    }

    /**
     * 处理添加或修改完之后的提交请求
     * @param job 新增或修改后的职位信息
     * @param attributes 向重定向后的页面封装数据的对象
     * @return 返回页面
     */
    @PostMapping("/jobs")
    public String post(Job job, @RequestParam String oldName, BindingResult result, RedirectAttributes attributes) {
        Job u;
        if (job.getId() == null) {
            if (verifyRepeat(job, null, result)) return "jobs-input";
            u = jobService.save(job);
        } else {
            if (verifyRepeat(job, oldName, result)) return "jobs-input";
            u = jobService.update(job.getId(), job);
        }
        if (u == null) {
            attributes.addFlashAttribute("message", "操作失败");
        } else {
            attributes.addFlashAttribute("message", "操作成功");
        }
        return "redirect:/hrm/jobs";
    }

    private boolean verifyRepeat(Job job, String oldName, BindingResult result) {
        boolean ret = false;
        Job dbJob = jobService.findByName(job.getName());
        if (oldName == null) {
            if (dbJob != null) {
                ret = true;
            }
        }else {
            if (dbJob != null && !oldName.equals(job.getName())) {
                ret = true;
            }
        }
        if (ret){
            result.rejectValue("name", "nameError", "该职位已存在，不能重复添加！");
        }
        return ret;
    }

}
