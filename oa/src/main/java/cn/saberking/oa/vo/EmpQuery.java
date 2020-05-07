package cn.saberking.oa.vo;

import lombok.Data;

/**
 * @Auther:luanzhaofei@outlook.com
 * @Date:2019/11/9
 * @Description:cn.saberking.oa.vo
 * @version:1.0
 */
@Data
public class EmpQuery {
    private String name; // 姓名
    private String empId;       //员工编号
    private Long deptId;	 // 部门Id
    private Long jobId;	 // 职位Id
    private String phone;		// 手机
    private Integer sex;		// 性别
    private String empIds;
}
