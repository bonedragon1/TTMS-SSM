package xupt.se.ttms.mapper;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.mapping.FetchType;

import xupt.se.ttms.domain.User;

/**
 * UserMapper接口
 * @author zhangrong
 */
public interface UserMapper
{
    /**
     * 根据登录名查询用户密码
     * @param String
     *        loginName
     * @param password
     *        password
     * @return 找到返回User对象，没有找到返回null
     */
    // 用户表和员工表1对1
    @Select("select * from user where emp_no = #{loginName} and emp_pass = #{password}")
    @Results(
    { @Result(id=true, column="emp_no", property="emp_no"), @Result(column="emp_name", property="emp_name"),
            @Result(column="emp_tel_num", property="emp_tel_num"), @Result(column="emp_addr", property="emp_addr"),
            @Result(column="emp_email", property="emp_email"),
            @Result(column="emp_no", property="employee", one=@One(select="xupt.se.ttms.mapper.EmployeeMapper.findEmployeeByNo", fetchType=FetchType.EAGER)) })
    User findUser(@Param("loginName") String loginName, @Param("password") String password);

    /**
     * 根据员工编号模糊查询
     * @param String
     *        empNo
     * @return 找到返回User对象，没有找到返回null
     */
    @Select("select * from user where emp_no like concat('%', #{empNo} , '%')")
    @Results(
    // id标明主键是后面的column对应的emp_no
    { @Result(id=true, column="emp_no", property="emp_no"), @Result(column="emp_name", property="emp_name"),
            @Result(column="emp_tel_num", property="emp_tel_num"), @Result(column="emp_addr", property="emp_addr"),
            @Result(column="emp_email", property="emp_email"),
            // 通过emp_no传递给findEmployeeByNo，查找关联表数据
            @Result(column="emp_no", property="employee", one=@One(select="xupt.se.ttms.mapper.EmployeeMapper.findEmployeeByNo", fetchType=FetchType.EAGER)) })
    ArrayList<User> findUserByEmpNo(String empNo);

    @Insert("insert into user(emp_no, emp_pass, type, head_path) values(#{emp_no},#{emp_pass},#{type},#{head_path})")
    boolean insert(User user);

    @Delete("delete from user where emp_no=#{empNo}")
    boolean delete(@Param("empNo") String empNo);

    // 通过类来反射sql语句，其中头像路径如果没提交就不修改
    @UpdateProvider(type=UserUpdate.class, method="update")
    boolean update(@Param("user") User user);

}
