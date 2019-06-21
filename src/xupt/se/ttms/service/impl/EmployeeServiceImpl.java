package xupt.se.ttms.service.impl;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import xupt.se.ttms.domain.Employee;
import xupt.se.ttms.mapper.EmployeeMapper;
import xupt.se.ttms.service.EmployeeService;
import xupt.se.ttms.util.PageParams;

/**
 * Employee服务层接口实现类 @Service("employeeService")用于将当前类注释为一个Spring的bean，名为employeeService
 * @author zhangrong
 */
@Transactional(propagation=Propagation.REQUIRED, isolation=Isolation.DEFAULT)
@Service("employeeService")
public class EmployeeServiceImpl implements EmployeeService
{
    // 自动注入EmployeeMapper
    @Autowired
    private EmployeeMapper employeeMapper;

    /**
     * EmployeeService接口insert方法实现
     * @see { EmployeeService }
     */
    @Transactional(readOnly=false)
    @Override
    public void insert(Employee employee)
    {
        employeeMapper.insert(employee);
    }

    @Transactional(readOnly=false)
    @Override
    public void delete(int employeeId)
    {
        employeeMapper.delete(employeeId);
    }

    @Transactional(readOnly=false)
    @Override
    public void update(Employee employee)
    {
        employeeMapper.update(employee);
    }

    @Transactional(readOnly=true)
    @Override
    public Employee findEmployeeById(int employeeId)
    {
        return employeeMapper.findEmployeeById(employeeId);
    }

    @Transactional(readOnly=true)
    @Override
    public ArrayList<Employee> findEmployeePageByName(String employeeName, int currentPage,
            PageParams params)
    {
        if(employeeName == null) employeeName="";

        // 获取总记录数，并保存到params中
        params.setAllCount(employeeMapper.getRecordNum(employeeName));
        // System.out.println("总条数：" + params.getAllCount());
        // 记算总页数，并保存到params中
        params.setAllPageCount((params.getAllCount() + params.getPageSize() - 1)
                / params.getPageSize());
        // System.out.println("总页数：" + params.getAllPageCount());

        // 如果当前页数大于总页数，则赋值为总页数，并保存到params中
        if(params.getAllPageCount() > 0 && currentPage > params.getAllPageCount())
            params.setCurrentPage(params.getAllPageCount());
        // 如果当前页数小于1，则设置为1
        else
            if(currentPage < 1)
                params.setCurrentPage(1);
            else
                params.setCurrentPage(currentPage);
        // System.out.println("当前页 ：" + params.getCurrentPage());

        // 设置数据读取位置
        int startPos=(params.getCurrentPage() - 1) * params.getPageSize();
        // 设置一页长度
        int length=params.getPageSize();
        ArrayList<Employee> list=employeeMapper.findEmployeePageByName(employeeName, startPos,
                                                                       length);

        return list;
    }

    @Override
    public ArrayList<Employee> findEmployeeByNoType()
    {
        return employeeMapper.findEmployeeByNoType();
    }

}
