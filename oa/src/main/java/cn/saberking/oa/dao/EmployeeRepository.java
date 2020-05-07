package cn.saberking.oa.dao;

import cn.saberking.oa.domain.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @Auther:luanzhaofei@outlook.com
 * @Date:2019/11/6
 * @Description:cn.saberking.oa.dao
 * @version:1.0
 */
public interface EmployeeRepository extends JpaRepository<Employee, Long>, JpaSpecificationExecutor<Employee> {

    Employee findByEmpId(String empId);

    void deleteEmployeesByIdIn(List<Long> ids);

    List<Employee> findAllByIdIn(List<Long> ids);

    @Query("select e from  Employee  e")
    List<Employee> listAll();
}
