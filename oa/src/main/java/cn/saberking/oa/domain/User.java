package cn.saberking.oa.domain;

import cn.saberking.oa.util.common.HrmConstants;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * @Auther:luanzhaofei@outlook.com
 * @Date:2019/11/6
 * @Description:cn.saberking.oa.dao
 * @version:1.0
 */
@Data
@Entity
@Table(name = HrmConstants.USER_TABLE)
public class User {
    @Id
    @GeneratedValue
    private Long id;

    private String username; // 用户名
    private String loginName; // 登录名
    private String password; // 密码
    private boolean status;	 // 状态
    private String avatar;  //头像
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;	// 建档日期

    @OneToMany(mappedBy = "user")
    private List<Document> document;
    @OneToMany(mappedBy = "user")
    private List<Notice> notice;

    @Transient
    private String newPassword;
    @Transient
    private String confirmPassword;
}
