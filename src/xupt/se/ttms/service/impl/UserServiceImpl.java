package xupt.se.ttms.service.impl;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import xupt.se.ttms.domain.User;
import xupt.se.ttms.mapper.UserMapper;
import xupt.se.ttms.service.UserService;

/**
 * User服务层接口实现类 @Service("userService")用于将当前类注释为一个Spring的bean，名为userService
 * @author zhangrong
 */
@Transactional(propagation=Propagation.REQUIRED, isolation=Isolation.DEFAULT)
@Service("userService")
public class UserServiceImpl implements UserService
{
    // 自动注入UserMapper
    @Autowired
    private UserMapper userMapper;

    /**
     * UserService接口login方法实现
     * @see { UserService }
     */
    @Transactional(readOnly=true)
    @Override
    public User login(String loginName, String password)
    {
        return userMapper.findUser(loginName, password);
    }

    @Transactional(readOnly=true)
    @Override
    public ArrayList<User> searchByEmpNo(String empNo)
    {
        return userMapper.findUserByEmpNo(empNo);
    }

    @Transactional(readOnly=false)
    @Override
    public void insert(User user)
    {
        userMapper.insert(user);
    }

    @Transactional(readOnly=false)
    @Override
    public void delete(String empNo)
    {
        userMapper.delete(empNo);
    }

    @Transactional(readOnly=false)
    @Override
    public void update(User user)
    {
        userMapper.update(user);
    }
}
