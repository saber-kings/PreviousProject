package cn.saberking.oa.domain;

import cn.saberking.oa.util.common.HrmConstants;
import lombok.Data;

import javax.persistence.*;

/**
 * @Auther:luanzhaofei@outlook.com
 * @Date:2019/11/6
 * @Description:cn.saberking.oa.domain
 * @version:1.0
 */
@Data
@Entity
@Table(name = HrmConstants.NOTICE_TABLE)
public class Notice {
    @Id
    @GeneratedValue
    private Long id;		// 编号
    private String title;   // 标题
    @Basic(fetch = FetchType.LAZY)
    @Lob
    private String content; // 内容
    private java.util.Date createDate;  // 发布日期
    @ManyToOne
    private User user;		// 发布人
}
