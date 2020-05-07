package cn.saberking.oa.domain;

import cn.saberking.oa.util.common.HrmConstants;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.util.Date;

/**
 * @Auther:luanzhaofei@outlook.com
 * @Date:2019/11/6
 * @Description:cn.saberking.oa.domain
 * @version:1.0
 */
@Data
@Entity
@Table(name = HrmConstants.DOCUMENT_TABLE)
public class Document {
    @Id
    @GeneratedValue
    private Long id;					// 编号
    private String title;			// 标题
    private String fileName;		// 文件名
    @Transient
    private MultipartFile file;		// 文件
    private String remark;			// 描述
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;	// 上传时间
    @ManyToOne
    private User user;				// 上传人
}
