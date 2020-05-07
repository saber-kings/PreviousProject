package cn.saberking.oa.web;

import cn.saberking.oa.domain.Department;
import cn.saberking.oa.service.DepartmentService;
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
 * @Description:cn.saberking.oa.web 处理部门请求控制器
 * @version:1.0
 */
@Controller
@RequestMapping("hrm")
public class DepartmentController {
    @Autowired
    private DepartmentService departmentService;

    /**
     * 第一次访问页面，默认显示所有数据
     *
     * @param pageable 分页对象
     * @param queryName  模糊查询部门名称
     * @param model    向页面封装数据的对象
     * @return 返回页面
     */
    @GetMapping("/departments")
    public String departments(@PageableDefault(size = HrmConstants.PAGE_DEFAULT_SIZE, sort = {"createDate"}, direction = Sort.Direction.DESC)
                                Pageable pageable, String queryName, Model model) {
        //查询部门信息
        model.addAttribute("page", departmentService.findAll(queryName, pageable));
        return "departments";
    }

    /**
     * 处理查询请求
     *
     * @param pageable 分页对象
     * @param queryName  模糊查询部门名称
     * @param model    向页面封装数据的对象
     * @return 返回页面
     */
    @PostMapping(value = "/departments/search")
    public String selectDepartment(@PageableDefault(size = HrmConstants.PAGE_DEFAULT_SIZE, sort = {"createDate"}, direction = Sort.Direction.DESC)
                                     Pageable pageable, @RequestParam String queryName, Model model) {
        System.out.println("department = " + queryName);
        //查询部门信息
        model.addAttribute("page", departmentService.findAll(queryName, pageable));
        return "departments :: departmentList";

    }

    /**
     * 处理删除部门请求
     *
     * @param id 需要删除的部门id
     * @param attributes 向重定向后的页面封装数据的对象
     * @return 返回页面
     */
    @GetMapping(value = "/departments/{id}/delete")
    public String deleteDepartment(@PathVariable Long id, RedirectAttributes attributes) {
        departmentService.deleteById(id);
        attributes.addFlashAttribute("message", "删除成功");
        return "redirect:/hrm/departments";
    }

    /**
     * 路由新增请求的页面
     *
     * @param model 向页面封装数据的对象
     * @return 返回页面
     */
    @GetMapping("/departments/input")
    public String input(Model model) {
        initialDepartment(new Department(), model);
        return "departments-input";
    }

    private void initialDepartment(Department department, Model model) {
        model.addAttribute("department", department);
    }

    /**
     * 处理修改部门请求
     *
     * @param id    修改的部门id
     * @param model 向页面封装数据的对象
     * @return 返回页面
     */
    @GetMapping("/departments/{id}/input")
    public String editInput(@PathVariable Long id, Model model) {
        Department department = departmentService.findById(id);
        initialDepartment(department, model);
        return "departments-input";
    }

    /**
     * 处理添加或修改完之后的提交请求
     * @param department 新增或修改后的部门信息
     * @param attributes 向重定向后的页面封装数据的对象
     * @return 返回页面
     */
    @PostMapping("/departments")
    public String post(Department department, @RequestParam String oldDeptName, BindingResult result, RedirectAttributes attributes) {
        Department u;
        if (department.getId() == null) {
            if (verifyRepeat(department, null, result)) return "departments-input";
            u = departmentService.save(department);
        } else {
            if (verifyRepeat(department, oldDeptName, result)) return "departments-input";
            u = departmentService.update(department.getId(), department);
        }
        if (u == null) {
            attributes.addFlashAttribute("message", "操作失败");
        } else {
            attributes.addFlashAttribute("message", "操作成功");
        }
        return "redirect:/hrm/departments";
    }

    private boolean verifyRepeat(Department department, String oldDeptName, BindingResult result) {
        boolean ret = false;
        Department dbDepartment = departmentService.findByName(department.getDepartName());
        if (oldDeptName == null) {
            if (dbDepartment != null) {
                ret = true;
            }
        }else {
            if (dbDepartment != null && !oldDeptName.equals(department.getDepartName())) {
                ret = true;
            }
        }
        if (ret){
            result.rejectValue("departName", "departNameError", "该部门已存在，不能重复添加！");
        }
        return ret;
    }

}
