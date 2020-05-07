package cn.saberking.oa.service.impl;

import cn.saberking.oa.dao.UserRepository;
import cn.saberking.oa.domain.User;
import cn.saberking.oa.exception.NotFoundException;
import cn.saberking.oa.service.UserService;
import cn.saberking.oa.util.common.MyBeanUtils;
import cn.saberking.oa.util.encrypt.MD5Utils;
import cn.saberking.oa.vo.UserQuery;
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
import java.util.Date;
import java.util.List;

/**
 * @Auther:luanzhaofei@outlook.com
 * @Date:2019/11/9
 * @Description:cn.saberking.oa.service.impl
 * @version:1.0
 */
@Transactional
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Transactional(readOnly = true)
    @Override
    public User login(String loginName, String password) {
        User user = userRepository.findByLoginNameAndPassword(loginName, MD5Utils.code(password));
        return user;
    }

    @Transactional(readOnly = true)
    @Override
    public User findById(Long id) {
        return userRepository.getOne(id);
    }

    @Transactional(readOnly = true)
    @Override
    public User findByLoginName(String loginName) {
        return userRepository.findByLoginName(loginName);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<User> findAll(UserQuery userQuery, Pageable pageable) {
        return userRepository.findAll((Specification<User>) (root, cq, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.isNotBlank(userQuery.getUsername())) {
                predicates.add(cb.like(root.get("username"), "%" + userQuery.getUsername() + "%"));
            }
            if (userQuery.isStatus()) {
                predicates.add(cb.equal(root.<Boolean>get("status"), userQuery.isStatus()));
            }
            cq.where(predicates.toArray(new Predicate[predicates.size()]));
            return null;
        }, pageable);
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User update(Long id, User user) {
        User u = userRepository.getOne(id);
        if (u == null) {
            throw new NotFoundException("该用户不存在！");
        }
        if (u.getPassword().equals(MD5Utils.code(user.getPassword()))) {
            BeanUtils.copyProperties(user, u, MyBeanUtils.getNullPropertyNames(user));
            u.setPassword(MD5Utils.code(u.getConfirmPassword()));
            return userRepository.save(u);
        } else {
            return user;
        }
    }

    @Override
    public User save(User user) {
        user.setPassword(MD5Utils.code(user.getPassword()));
        user.setCreateDate(new Date());
        return userRepository.save(user);
    }
}
