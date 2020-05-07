package cn.saberking.oa.service;

import cn.saberking.oa.domain.Notice;
import cn.saberking.oa.vo.NotQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @Auther:luanzhaofei@outlook.com
 * @Date:2019/11/7
 * @Description:cn.saberking.oa.service
 * @version:1.0
 */
public interface NoticeService {

    /**
     * 根据id查询公告
     * @param id 公告id
     * @return 公告对象
     * */
    Notice findById(Long id);

    /**
     * 根据id查询公告，得到格式化后的公告
     * @param id 公告id
     * @return 公告对象
     * */
    Notice getAndConvert(Long id);

    /**
     * 根据标题查询公告，得到格式化后的公告
     * @param title 公告标题
     * @return 公告对象
     * */
    Notice findByTitle(String title);
    
    /**
     * 获得所有公告
     * @return Notice对象的分页集合
     * */
    Page<Notice> findAll(NotQuery notQuery, Pageable pageable);

    /**
     * 根据id删除公告
     * @param id 公告id
     * */
    void deleteById(Long id);

    /**
     * 根据一组id批量删除公告
     * @param notIds 公告id字符串，以‘,’分割
     * */
    void batchDelById(String notIds);

    /**
     * 修改公告
     * @param notice 公告对象
     * */
    Notice update(Long id, Notice notice);

    /**
     * 添加公告
     * @param notice 公告对象
     * */
    Notice save(Notice notice);
}
