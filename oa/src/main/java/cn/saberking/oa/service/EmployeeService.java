package cn.saberking.oa.service;

import cn.saberking.oa.domain.Employee;
import cn.saberking.oa.vo.EmpQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @Auther:luanzhaofei@outlook.com
 * @Date:2019/11/7
 * @Description:cn.saberking.oa.service
 * @version:1.0
 */
public interface EmployeeService {

    /**
     * 根据id查询员工
     * @param id 员工id
     * @return 员工对象
     * */
    Employee findById(Long id);
    
    /**
     * 根据员工编号查询员工
     * @param empId 员工编号
     * @return 员工对象
     * */
    Employee findByEmpId(String empId);

    /**
     * 根据一组id批量查询员工
     * @return Employee对象的分页集合
     * */
    List<Employee> findAllByIds(EmpQuery empQuery);

    /**
     * 获得所有员工
     * @return Employee对象的集合
     * */
    List<Employee> findAll();

    /**
     * 获得所有员工
     * @return Employee对象的分页集合
     * */
    Page<Employee> findAll(EmpQuery empQuery, Pageable pageable);

    /**
     * 根据id删除员工
     * @param id 员工id
     * */
    void deleteById(Long id);

    /**
     * 根据一组id批量删除员工
     * @param empIds 员工id字符串，以‘,’分割
     * */
    void batchDelById(String empIds);

    /**
     * 修改员工
     * @param employee 员工对象
     * */
    Employee update(Long id, Employee employee);

    /**
     * 添加员工
     * @param employee 员工对象
     * */
    Employee save(Employee employee);
}
