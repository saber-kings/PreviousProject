package cn.saberking.oa.dao;

import cn.saberking.oa.domain.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @Auther:luanzhaofei@outlook.com
 * @Date:2019/11/6
 * @Description:cn.saberking.oa.dao
 * @version:1.0
 */
public interface JobRepository extends JpaRepository<Job, Long>, JpaSpecificationExecutor<Job> {
    Job findByName(String name);
}
