package cn.saberking.oa.vo;

import lombok.Data;

/**
 * @Auther:luanzhaofei@outlook.com
 * @Date:2019/11/9
 * @Description:cn.saberking.oa.vo
 * @version:1.0
 */
@Data
public class NotQuery {
    private Long id;		// 编号
    private String title;   // 标题
    private String content; // 内容
    private String notIds;
}
