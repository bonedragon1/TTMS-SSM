package xupt.se.ttms.mapper;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import xupt.se.ttms.domain.Employee;

/**
 * EmployeeMapper接口
 * @author zhangrong
 */
public interface EmployeeMapper
{
    @Insert("insert into Employee(emp_no, emp_name, emp_tel_num, emp_addr, emp_email) values(#{emp_no},#{emp_name},#{emp_tel_num},#{emp_addr},#{emp_email})")
    boolean insert(Employee employee);

    @Delete("delete from employee where emp_id=#{empId}")
    boolean delete(@Param("empId") int empId);

    @Update("update employee set emp_no=#{emp_no},emp_name=#{emp_name},emp_tel_num=#{emp_tel_num},emp_addr=#{emp_addr},emp_email=#{emp_email} where emp_id=#{emp_id}")
    boolean update(Employee employee);

    /**
     * 根据员工id查找员工
     * @param empId
     * @return 找到返回Employee对象，没有找到返回null
     */
    @Select("select * from employee where emp_id=#{empId}")
    Employee findEmployeeById(@Param("empId") int empId);

    /**
     * 根据用户编号查找员工
     * @param empNo
     * @return 找到返回Employee对象，没有找到返回null
     */
    @Select("select * from employee where emp_no=#{empNo}")
    Employee findEmployeeByNo(@Param("empNo") String empNo);

    /**
     * 根据用户名模糊查找符合条件员工数量
     * @param employeeName
     * @return 返回符合条件员工数量
     */
    @Select("select count(emp_no) as AllRecord from Employee where emp_name like concat('%',#{employeeName},'%')")
    int getRecordNum(@Param("employeeName") String employeeName);

    /**
     * 根据用户名模糊查找员工，分页
     * @param employeeName
     * @param startPos
     * @param length
     * @return 找到返回Employee列表
     */
    @Select("select * from Employee where emp_name like concat('%',#{employeeName},'%') limit #{startPos}, #{length}")
    ArrayList<Employee> findEmployeePageByName(@Param("employeeName") String employeeName, @Param("startPos") int startPos,
            @Param("length") int length);

    /**
     * 查找未授权登陆员工
     * @return 找到返回Employee对象，没有找到返回null
     */
    @Select("select * from employee where emp_no not in(select emp_no from user)")
    ArrayList<Employee> findEmployeeByNoType();
}
