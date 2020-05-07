package cn.saberking.oa.service.impl;

import cn.saberking.oa.dao.DepartmentRepository;
import cn.saberking.oa.domain.Department;
import cn.saberking.oa.exception.NotFoundException;
import cn.saberking.oa.service.DepartmentService;
import cn.saberking.oa.util.common.MyBeanUtils;
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
import java.util.Date;
import java.util.List;

/**
 * @Auther:luanzhaofei@outlook.com
 * @Date:2019/11/16
 * @Description:cn.saberking.oa.service.impl
 * @version:1.0
 */
@Transactional
@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Override
    public List<Department> findAll() {
        return departmentRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Page<Department> findAll(String departName, Pageable pageable) {
        return departmentRepository.findAll((Specification<Department>) (root, cq, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.isNotBlank(departName)) {
                predicates.add(cb.like(root.get("departName"), "%" + departName + "%"));
            }
            cq.where(predicates.toArray(new Predicate[predicates.size()]));
            return null;
        }, pageable);
    }

    @Transactional(readOnly = true)
    @Override
    public Department findById(Long id) {
        return departmentRepository.getOne(id);
    }

    @Transactional(readOnly = true)
    @Override
    public Department findByName(String departName) {
        return departmentRepository.findByDepartName(departName);
    }

    @Override
    public void deleteById(Long id) {
        departmentRepository.deleteById(id);
    }

    @Override
    public Department save(Department dept) {
        dept.setCreateDate(new Date());
        return departmentRepository.save(dept);
    }

    @Override
    public Department update(Long id, Department dept) {
        Department department = departmentRepository.getOne(id);
        if (department == null) {
            throw new NotFoundException("该部门不存在！");
        }
        BeanUtils.copyProperties(dept, department, MyBeanUtils.getNullPropertyNames(dept));
        return departmentRepository.save(department);
    }
}
