package xupt.se.ttms.service;

import java.util.ArrayList;

import xupt.se.ttms.domain.User;

/**
 * User服务层接口
 * @author zhangrong
 */
public interface UserService
{
    /**
     * 判断用户登录
     * @param String
     *        loginName
     * @param String
     *        password
     * @return 找到返回User对象，没有找到返回null
     */
    User login(String loginName, String password);

    /**
     * 查询用户信息，关联员工对象
     * @param String
     *        empNo
     * @return 找到返回User列表
     */
    ArrayList<User> searchByEmpNo(String empNo);

    void insert(User user);

    void delete(String empNo);

    void update(User user);
}
