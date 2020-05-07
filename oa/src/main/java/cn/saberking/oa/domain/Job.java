package cn.saberking.oa.domain;

import cn.saberking.oa.util.common.HrmConstants;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Auther:luanzhaofei@outlook.com
 * @Date:2019/11/6
 * @Description:cn.saberking.oa.domain
 * @version:1.0
 */
@Data
@Entity
@Table(name = HrmConstants.JOB_TABLE)
public class Job {
    @Id
    @GeneratedValue
    private Long id;			// id

    private String name;		// 职位名称
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;	// 建档日期
    private String remark;		// 详细描述

    @OneToMany(mappedBy = "job")
    private List<Employee> employees = new ArrayList<>();
}
