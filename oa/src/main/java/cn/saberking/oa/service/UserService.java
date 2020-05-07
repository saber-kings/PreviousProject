package cn.saberking.oa.service;

import cn.saberking.oa.domain.User;
import cn.saberking.oa.vo.UserQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @Auther:luanzhaofei@outlook.com
 * @Date:2019/11/7
 * @Description:cn.saberking.oa.service
 * @version:1.0
 */
public interface UserService {
    /**
     * 用户登录
     * @param  loginName
     * @param  password
     * @return User对象
     * */
    User login(String loginName, String password);

    /**
     * 根据id查询用户
     * @param id
     * @return 用户对象
     * */
    User findById(Long id);

    /**
     * 根据账号查询用户
     * @param loginName 账号
     * @return 用户对象
     * */
    User findByLoginName(String loginName);

    /**
     * 获得所有用户
     * @return User对象的List集合
     * */
    Page<User> findAll(UserQuery userQuery, Pageable pageable);

    /**
     * 根据id删除用户
     * @param id
     * */
    void deleteById(Long id);

    /**
     * 修改用户
     * @param user 用户对象
     * */
    User update(Long id, User user);

    /**
     * 添加用户
     * @param user 用户对象
     * @return*/
    User save(User user);
}
