package cn.saberking.oa.web;

import cn.saberking.oa.domain.Employee;
import cn.saberking.oa.service.DepartmentService;
import cn.saberking.oa.service.EmployeeService;
import cn.saberking.oa.service.JobService;
import cn.saberking.oa.util.common.HrmConstants;
import cn.saberking.oa.vo.EmpQuery;
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
 * @Description:cn.saberking.oa.web 处理员工请求控制器
 * @version:1.0
 */
@Controller
@RequestMapping("hrm")
public class EmployeeController {
    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private JobService jobService;

    @Autowired
    private EmployeeService employeeService;

    /**
     * 第一次访问页面，默认显示所有数据
     *
     * @param pageable 分页对象
     * @param employee 模糊查询参数
     * @param model    向页面封装数据的对象
     * @return 返回页面
     */
    @GetMapping("/employees")
    public String employees(@PageableDefault(size = HrmConstants.PAGE_DEFAULT_SIZE, sort = {"createDate"}, direction = Sort.Direction.DESC)
                                    Pageable pageable, EmpQuery employee, Model model) {
        model.addAttribute("departments", departmentService.findAll());
        model.addAttribute("jobs", jobService.findAll());
        //查询员工信息
        model.addAttribute("page", employeeService.findAll(employee, pageable));
        return "employees";
    }

    /**
     * 处理查询请求
     *
     * @param pageable 分页对象
     * @param employee 模糊查询参数
     * @param model    向页面封装数据的对象
     * @return 返回页面
     */
    @PostMapping(value = "/employees/search")
    public String selectEmployee(@PageableDefault(size = HrmConstants.PAGE_DEFAULT_SIZE, sort = {"createDate"}, direction = Sort.Direction.DESC)
                                         Pageable pageable, EmpQuery employee, Model model) {
        System.out.println("employee = " + employee);
        //查询员工信息
        model.addAttribute("page", employeeService.findAll(employee, pageable));
        return "employees :: employeeList";

    }

    /**
     * 处理查看详细信息请求
     *
     * @param employee 模糊查询参数
     * @param model    向页面封装数据的对象
     * @return 返回页面
     */
    @PostMapping(value = "/employees/batchSelect")
    public String batchSelect(EmpQuery employee, Model model) {
        //查询员工信息
        model.addAttribute("batchPage", employeeService.findAllByIds(employee));
        return "employees :: batchList";

    }

    /**
     * 处理删除单个员工请求
     *
     * @param id         需要删除的员工id
     * @param attributes 向重定向后的页面封装数据的对象
     * @return 返回页面
     */
    @GetMapping(value = "/employees/{id}/delete")
    public String deleteEmployee(@PathVariable Long id, RedirectAttributes attributes) {
        employeeService.deleteById(id);
        attributes.addFlashAttribute("message", "删除成功");
        return "redirect:/hrm/employees";
    }

    /**
     * 处理删除多个员工请求
     *
     * @param pageable 分页对象
     * @param empQuery 模糊查询参数
     * @param model    向页面封装数据的对象
     * @return 返回页面
     */
    @PostMapping(value = "/employees/delete")
    public String batchDelEmp(@PageableDefault(size = HrmConstants.PAGE_DEFAULT_SIZE, sort = {"createDate"}, direction = Sort.Direction.DESC)
                                          Pageable pageable, EmpQuery empQuery, Model model) {
        if (StringUtils.isNotBlank(empQuery.getEmpIds())){
            employeeService.batchDelById(empQuery.getEmpIds());
        }
        //查询员工信息
        model.addAttribute("page", employeeService.findAll(empQuery, pageable));
        return "employees :: employeeList";
    }

    /**
     * 路由新增请求的页面
     *
     * @param model 向页面封装数据的对象
     * @return 返回页面
     */
    @GetMapping("/employees/input")
    public String input(Model model) {
        initialEmployee(new Employee(), model);
        return "employees-input";
    }

    private void initialEmployee(Employee employee, Model model) {
        model.addAttribute("departments", departmentService.findAll());
        model.addAttribute("jobs", jobService.findAll());
        model.addAttribute("employee", employee);
    }

    /**
     * 处理修改员工请求
     *
     * @param id    修改的员工id
     * @param model 向页面封装数据的对象
     * @return 返回页面
     */
    @GetMapping("/employees/{id}/input")
    public String editInput(@PathVariable Long id, Model model) {
        Employee employee = employeeService.findById(id);
        initialEmployee(employee, model);
        return "employees-input";
    }

    /**
     * 处理添加或修改完之后的提交请求
     *
     * @param employee   新增或修改后的员工信息
     * @param attributes 向重定向后的页面封装数据的对象
     * @return 返回页面
     */
    @PostMapping("/employees")
    public String post(Employee employee, @RequestParam(required = false) String oldEmpId, BindingResult result, RedirectAttributes attributes, Model model) {
        Employee e;
        if (employee.getId() == null) {
            if (verifyRepeat(employee, null, result)) {
                model.addAttribute("departments", departmentService.findAll());
                model.addAttribute("jobs", jobService.findAll());
                return "employees-input";
            }
            e = employeeService.save(employee);
        } else {
            if (verifyRepeat(employee, oldEmpId, result)) {
                model.addAttribute("departments", departmentService.findAll());
                model.addAttribute("jobs", jobService.findAll());
                return "employees-input";
            }
            e = employeeService.update(employee.getId(), employee);
        }
        if (e == null) {
            attributes.addFlashAttribute("message", "操作失败");
        } else {
            attributes.addFlashAttribute("message", "操作成功");
        }
        return "redirect:/hrm/employees";
    }

    private boolean verifyRepeat(Employee employee, String oldEmpId, BindingResult result) {
        boolean ret = false;
        Employee dbEmployee = employeeService.findByEmpId(employee.getEmpId());
        if (oldEmpId == null) {
            if (dbEmployee != null) {
                ret = true;
            }
        } else {
            if (dbEmployee != null && !oldEmpId.equals(employee.getEmpId())) {
                ret = true;
            }
        }
        if (ret) {
            result.rejectValue("empId", "empIdError", "该员工编号已存在，不能重复添加！");
        }
        return ret;
    }

}
