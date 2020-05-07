package cn.saberking.oa.service.impl;

import cn.saberking.oa.dao.JobRepository;
import cn.saberking.oa.domain.Job;
import cn.saberking.oa.exception.NotFoundException;
import cn.saberking.oa.service.JobService;
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
public class JobServiceImpl implements JobService {

    @Autowired
    private JobRepository jobRepository;

    @Override
    public List<Job> findAll() {
        return jobRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Page<Job> findAll(String jobName, Pageable pageable) {
        return jobRepository.findAll((Specification<Job>) (root, cq, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.isNotBlank(jobName)) {
                predicates.add(cb.like(root.get("name"), "%" + jobName + "%"));
            }
            cq.where(predicates.toArray(new Predicate[predicates.size()]));
            return null;
        }, pageable);
    }

    @Transactional(readOnly = true)
    @Override
    public Job findById(Long id) {
        return jobRepository.getOne(id);
    }

    @Transactional(readOnly = true)
    @Override
    public Job findByName(String jobName) {
        return jobRepository.findByName(jobName);
    }

    @Override
    public void deleteById(Long id) {
        jobRepository.deleteById(id);
    }

    @Override
    public Job save(Job job) {
        job.setCreateDate(new Date());
        return jobRepository.save(job);
    }

    @Override
    public Job update(Long id, Job job) {
        Job dbJob = jobRepository.getOne(id);
        if (dbJob == null) {
            throw new NotFoundException("该部门不存在！");
        }
        BeanUtils.copyProperties(job, dbJob, MyBeanUtils.getNullPropertyNames(job));
        return jobRepository.save(dbJob);
    }
}
