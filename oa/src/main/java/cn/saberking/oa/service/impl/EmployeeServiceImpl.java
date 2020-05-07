package cn.saberking.oa.service.impl;

import cn.saberking.oa.dao.EmployeeRepository;
import cn.saberking.oa.domain.Department;
import cn.saberking.oa.domain.Employee;
import cn.saberking.oa.domain.Job;
import cn.saberking.oa.exception.NotFoundException;
import cn.saberking.oa.service.EmployeeService;
import cn.saberking.oa.util.common.MyBeanUtils;
import cn.saberking.oa.vo.EmpQuery;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Auther:luanzhaofei@outlook.com
 * @Date:2019/11/17
 * @Description:cn.saberking.oa.service.impl
 * @version:1.0
 */
@Transactional
@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Transactional(readOnly = true)
    @Override
    public Employee findById(Long id) {
        return employeeRepository.getOne(id);
    }

    @Transactional(readOnly = true)
    @Override
    public Employee findByEmpId(String empId) {
        return employeeRepository.findByEmpId(empId);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Employee> findAllByIds(EmpQuery empQuery) {
        List<String> idList = Arrays.asList(empQuery.getEmpIds().split(","));
        List<Long> ids = idList.stream().map(Long::parseLong).collect(Collectors.toList());
        return employeeRepository.findAllByIdIn(ids);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Employee> findAll() {
        return employeeRepository.listAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Page<Employee> findAll(EmpQuery empQuery, Pageable pageable) {
        return employeeRepository.findAll((Specification<Employee>) (root, cq, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.isNotBlank(empQuery.getName())) {
                predicates.add(cb.like(root.get("name"), "%" + empQuery.getName() + "%"));
            }
            if (StringUtils.isNotBlank(empQuery.getEmpId())) {
                predicates.add(cb.equal(root.<String>get("empId"), empQuery.getEmpId()));
            }
            if (empQuery.getDeptId()!=null) {
                predicates.add(cb.equal(root.<Department>get("department"), empQuery.getDeptId()));
            }
            if (empQuery.getJobId()!=null) {
                predicates.add(cb.equal(root.<Job>get("job"), empQuery.getJobId()));
            }
            if (StringUtils.isNotBlank(empQuery.getPhone())) {
                predicates.add(cb.equal(root.<String>get("phone"), empQuery.getPhone()));
            }
            if (empQuery.getSex()!=null) {
                predicates.add(cb.equal(root.<Integer>get("sex"), empQuery.getSex()));
            }
            cq.where(predicates.toArray(new Predicate[predicates.size()]));
            return null;
        }, pageable);
    }

    @Override
    public void deleteById(Long id) {
        employeeRepository.deleteById(id);
    }

    @Override
    public void batchDelById(String empIds) {
        List<String> idList = Arrays.asList(empIds.split(","));
        List<Long> ids = idList.stream().map(Long::parseLong).collect(Collectors.toList());
        employeeRepository.deleteEmployeesByIdIn(ids);
    }

    @Override
    public Employee update(Long id, Employee employee) {
        Employee e = employeeRepository.getOne(id);
        if (e == null) {
            throw new NotFoundException("该用户不存在！");
        }
        BeanUtils.copyProperties(employee, e, MyBeanUtils.getNullPropertyNames(employee));
        return employeeRepository.save(e);
    }

    @Override
    public Employee save(Employee employee) {
        employee.setCreateDate(new Date());
        return employeeRepository.save(employee);
    }
}
