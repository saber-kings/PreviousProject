package cn.saberking.oa.service;

import cn.saberking.oa.domain.Department;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @Auther:luanzhaofei@outlook.com
 * @Date:2019/11/16
 * @Description:cn.saberking.oa.service
 * @version:1.0
 */
public interface DepartmentService {
    /**
     * 获得所有部门
     * @return Department集合
     */
    List<Department> findAll();

    /**
     * 获得所有部门
     * @param departName 部门名称
     * @param pageable 分页集合
     * @return Department对象的分页集合
     */
    Page<Department> findAll(String departName, Pageable pageable);

    /**
     * 根据id查询部门
     * @param id 部门编号
     * @return 部门对象
     * */
    Department findById(Long id);

    /**
     * 根据id查询部门
     * @param departName 部门名称
     * @return 部门对象
     * */
    Department findByName(String departName);

    /**
     * 根据id删除部门
     * @param id 部门编号
     * */
    void deleteById(Long id);

    /**
     * 添加部门
     * @param dept 部门对象
     * */
    Department save(Department dept);

    /**
     * 修改部门
     * @param dept 部门对象
     * @return*/
    Department update(Long id, Department dept);
}
