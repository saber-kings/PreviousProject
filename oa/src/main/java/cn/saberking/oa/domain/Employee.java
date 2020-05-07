package cn.saberking.oa.domain;

import cn.saberking.oa.util.common.HrmConstants;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Auther:luanzhaofei@outlook.com
 * @Date:2019/11/6
 * @Description:cn.saberking.oa.domain
 * @version:1.0
 */
@Data
@Entity
@Table(name = HrmConstants.EMPLOYEE_TABLE)
public class Employee {
    @Id
    @GeneratedValue
    private Long id;			// id

    @ManyToOne
    private Department department;	// 部门
    @ManyToOne
    private Job job;			// 职位

    private String empId;       //员工编号
    private String name;		// 姓名
    private Integer age;		// 年龄
    @Column(columnDefinition = "int default 1")
    private Integer sex;		// 性别
    private String phone;		// 手机
    private String email;		// 邮箱
    private String education;	// 学历
    private BigDecimal wage;	// 工资
    @Basic(fetch = FetchType.LAZY)
    @Lob
    private String remark;		// 备注
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;	// 建档日期
    @Transient
    private String deptId;
    @Transient
    private String jobId;
}
