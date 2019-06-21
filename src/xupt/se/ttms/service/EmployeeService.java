package xupt.se.ttms.service;

import java.util.ArrayList;

import xupt.se.ttms.domain.Employee;
import xupt.se.ttms.util.PageParams;

/**
 * Employee服务层接口
 * @author zhangrong
 */
public interface EmployeeService
{
    void insert(Employee employee);

    void delete(int employeeId);

    void update(Employee employee);

    Employee findEmployeeById(int employeeId);

    // 根据用户名和当前页分页查找雇员信息，给上面几个变量赋值
    ArrayList<Employee> findEmployeePageByName(String employeeName, int currentPage,
            PageParams params);

    // 查找未授权登陆员工
    ArrayList<Employee> findEmployeeByNoType();
}
