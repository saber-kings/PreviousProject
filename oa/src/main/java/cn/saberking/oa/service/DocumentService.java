package cn.saberking.oa.service;

import cn.saberking.oa.domain.Document;
import cn.saberking.oa.vo.DocQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @Auther:luanzhaofei@outlook.com
 * @Date:2019/11/7
 * @Description:cn.saberking.oa.service
 * @version:1.0
 */
public interface DocumentService {

    /**
     * 根据id查询文件
     * @param id 文件id
     * @return 文件对象
     * */
    Document findById(Long id);

    /**
     * 根据文件名称查询文件
     * @param fileName 文件名称
     * @return 文件对象
     * */
    Document findByFileName(String fileName);

    /**
     * 获得所有文件
     * @return Document对象的分页集合
     * */
    Page<Document> findAll(DocQuery docQuery, Pageable pageable);

    /**
     * 根据一组id获得所有文件
     * @param docIds 文件id字符串，以‘,’分割
     * @return Document对象的分页集合
     * */
    List<Document> findAllByIdIn(String docIds);

    /**
     * 根据一组id删除文件
     * @param id 文件id
     * */
    void deleteById(Long id);

    /**
     * 根据一组id批量删除文件
     * @param docIds 文件id字符串，以‘,’分割
     * */
    void batchDelById(String docIds);

    /**
     * 修改文件
     * @param document 文件对象
     * */
    Document update(Long id, Document document);

    /**
     * 添加文件
     * @param document 文件对象
     * */
    Document save(Document document);

}
