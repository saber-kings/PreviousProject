package cn.saberking.oa.service.impl;

import cn.saberking.oa.dao.NoticeRepository;
import cn.saberking.oa.domain.Notice;
import cn.saberking.oa.exception.NotFoundException;
import cn.saberking.oa.service.NoticeService;
import cn.saberking.oa.util.common.MarkdownUtils;
import cn.saberking.oa.util.common.MyBeanUtils;
import cn.saberking.oa.vo.NotQuery;
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
 * @Date:2019/11/28
 * @Description:cn.saberking.oa.service.impl
 * @version:1.0
 */
@Transactional
@Service
public class NoticeServiceImpl implements NoticeService {
    
    @Autowired
    private NoticeRepository noticeRepository;

    @Transactional(readOnly = true)
    @Override
    public Notice findById(Long id) {
        return noticeRepository.getOne(id);
    }

    @Transactional(readOnly = true)
    @Override
    public Notice getAndConvert(Long id) {
        Notice notice = noticeRepository.getOne(id);
        return getConvertOrMessage(notice);
    }

    @Override
    public Notice findByTitle(String title) {
        Notice notice = noticeRepository.findByTitle(title);
        return getConvertOrMessage(notice);
    }

    private Notice getConvertOrMessage(Notice notice) {
        if (notice == null) {
            throw new NotFoundException("该公告跑到火星去了！");
        }
        Notice n = new Notice();
        BeanUtils.copyProperties(notice,n);
        String content = n.getContent();
        n.setContent(MarkdownUtils.markdownToHtmlExtensions(content));
        return n;
    }

    @Transactional(readOnly = true)
    @Override
    public Page<Notice> findAll(NotQuery notQuery, Pageable pageable) {
        return noticeRepository.findAll((Specification<Notice>) (root, cq, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.isNotBlank(notQuery.getTitle())) {
                predicates.add(cb.like(root.get("title"), "%" + notQuery.getTitle() + "%"));
            }
            if (StringUtils.isNotBlank(notQuery.getContent())) {
                predicates.add(cb.like(root.get("content"), "%" + notQuery.getContent() + "%"));
            }
            cq.where(predicates.toArray(new Predicate[predicates.size()]));
            return null;
        }, pageable);
    }

    @Override
    public void deleteById(Long id) {
        noticeRepository.deleteById(id);
    }

    @Override
    public void batchDelById(String notIds) {
        List<String> idList = Arrays.asList(notIds.split(","));
        List<Long> ids = idList.stream().map(Long::parseLong).collect(Collectors.toList());
        noticeRepository.deleteNoticeByIdIn(ids);
    }

    @Override
    public Notice update(Long id, Notice notice) {
        Notice n = noticeRepository.getOne(id);
        if (n == null) {
            throw new NotFoundException("该用户不存在！");
        }
        BeanUtils.copyProperties(notice, n, MyBeanUtils.getNullPropertyNames(notice));
        return noticeRepository.save(n);
    }

    @Override
    public Notice save(Notice notice) {
        notice.setCreateDate(new Date());
        return noticeRepository.save(notice);
    }
}
