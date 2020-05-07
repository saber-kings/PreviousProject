package cn.saberking.oa.service;

import cn.saberking.oa.domain.Job;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @Auther:luanzhaofei@outlook.com
 * @Date:2019/11/16
 * @Description:cn.saberking.oa.service
 * @version:1.0
 */
public interface JobService {
    /**
     * 获得所有职位
     * @return Job集合
     */
    List<Job> findAll();

    /**
     * 获得所有职位
     * @param jobName 职位名称
     * @param pageable 分页集合
     * @return Job对象的分页集合
     */
    Page<Job> findAll(String jobName, Pageable pageable);

    /**
     * 根据id查询职位
     * @param id 职位编号
     * @return 职位对象
     * */
    Job findById(Long id);

    /**
     * 根据id查询职位
     * @param jobName 职位名称
     * @return 职位对象
     * */
    Job findByName(String jobName);

    /**
     * 根据id删除职位
     * @param id 职位编号
     * */
    void deleteById(Long id);

    /**
     * 添加职位
     * @param job 职位对象
     * */
    Job save(Job job);

    /**
     * 修改职位
     * @param job 职位对象
     * @return*/
    Job update(Long id, Job job);
}
