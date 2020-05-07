package cn.saberking.oa.dao;

import cn.saberking.oa.domain.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * @Auther:luanzhaofei@outlook.com
 * @Date:2019/11/6
 * @Description:cn.saberking.oa.dao
 * @version:1.0
 */
public interface DocumentRepository extends JpaRepository<Document, Long>, JpaSpecificationExecutor<Document> {

    List<Document> findAllByIdIn(List<Long> ids);

    void deleteDocumentByIdIn(List<Long> ids);

    Document findByFileName(String fileName);
}
