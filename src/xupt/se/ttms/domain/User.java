package xupt.se.ttms.domain;

import java.io.Serializable;

/**
 * POJO、VO、PO类（封装数据类-对应user表）
 * @author zhangrong
 */
public class User implements Serializable
{
    private String emp_no;
    private String emp_pass;
    private int type;
    private String head_path;
    private Employee employee;

    public String getEmp_no()
    {
        return emp_no;
    }

    public void setEmp_no(String emp_no)
    {
        this.emp_no=emp_no;
    }

    public String getEmp_pass()
    {
        return emp_pass;
    }

    public void setEmp_pass(String emp_pass)
    {
        this.emp_pass=emp_pass;
    }

    public int getType()
    {
        return type;
    }

    public void setType(int type)
    {
        this.type=type;
    }

    public String getHead_path()
    {
        return head_path;
    }

    public void setHead_path(String head_path)
    {
        this.head_path=head_path;
    }

    public Employee getEmployee()
    {
        return employee;
    }

    public void setEmployee(Employee employee)
    {
        this.employee=employee;
    }

    @Override
    public String toString()
    {
        return "User [emp_no=" + emp_no + ", emp_pass=" + emp_pass + ", type=" + type
                + ", head_path=" + head_path + ", employee=" + employee + "]";
    }

}
