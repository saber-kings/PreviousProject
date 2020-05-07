package cn.saberking.oa.service.impl;

import cn.saberking.oa.dao.DocumentRepository;
import cn.saberking.oa.domain.Document;
import cn.saberking.oa.exception.NotFoundException;
import cn.saberking.oa.service.DocumentService;
import cn.saberking.oa.util.common.MyBeanUtils;
import cn.saberking.oa.vo.DocQuery;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Auther:luanzhaofei@outlook.com
 * @Date:2019/11/29
 * @Description:cn.saberking.oa.service.impl
 * @version:1.0
 */
@Transactional
@Service
public class DocumentServiceImpl implements DocumentService {

    @Autowired
    private DocumentRepository documentRepository;

    @Transactional(readOnly = true)
    @Override
    public Document findById(Long id) {
        return documentRepository.getOne(id);
    }

    @Override
    public Document findByFileName(String fileName) {
        return documentRepository.findByFileName(fileName);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<Document> findAll(DocQuery docQuery, Pageable pageable) {
        return documentRepository.findAll((Specification<Document>) (root, cq, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.isNotBlank(docQuery.getTitle())) {
                predicates.add(cb.like(root.get("title"), "%" + docQuery.getTitle() + "%"));
            }
            cq.where(predicates.toArray(new Predicate[predicates.size()]));
            return null;
        }, pageable);
    }

    @Override
    public List<Document> findAllByIdIn(String docIds) {
        List<String> idList = Arrays.asList(docIds.split(","));
        List<Long> ids = idList.stream().map(Long::parseLong).collect(Collectors.toList());
        return documentRepository.findAllByIdIn(ids);
    }

    @Override
    public void deleteById(Long id) {
        documentRepository.deleteById(id);
    }

    @Override
    public void batchDelById(String docIds) {
        List<String> idList = Arrays.asList(docIds.split(","));
        List<Long> ids = idList.stream().map(Long::parseLong).collect(Collectors.toList());
        documentRepository.deleteDocumentByIdIn(ids);
    }

    @Override
    public Document update(Long id, Document document) {
        Document d = documentRepository.getOne(id);
        if (d == null) {
            throw new NotFoundException("该用户不存在！");
        }
        BeanUtils.copyProperties(document, d, MyBeanUtils.getNullPropertyNames(document));
        return documentRepository.save(d);
    }

    @Override
    public Document save(Document document) {
        document.setCreateDate(new Date());
        return documentRepository.save(document);
    }
}
