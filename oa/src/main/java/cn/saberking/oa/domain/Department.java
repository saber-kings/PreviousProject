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
@Table(name = HrmConstants.DEPARTMENT_TABLE)
public class Department {
    @Id
    @GeneratedValue
    private Long id;// id

    @OneToMany(mappedBy = "department")
    private List<Employee> employees = new ArrayList<>();

    private String departName;// 部门名称
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;	// 建档日期
    private String description;// 详细描述
}
