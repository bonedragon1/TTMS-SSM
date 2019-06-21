package xupt.se.ttms.controller;

import java.net.URLEncoder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import xupt.se.ttms.domain.Employee;
import xupt.se.ttms.service.EmployeeService;
import xupt.se.ttms.util.PageParams;

/**
 * 处理登陆用户请求控制器
 * @author zhangrong
 */
@Controller
public class EmployeeController
{
    // 自动注入EmployeeService
    @Autowired
    @Qualifier("employeeService")
    private EmployeeService employeeService;

    // 处理/employee/link请求，打开员工模块首页
    @RequestMapping(value="/employee/link")
    public String link()
    {
        return "/employee/index";
    }

    // 处理/employee/searchByPage请求，处理分页请求
    // 如果删除数据，也会调用此方法再次查询，这里会显示delResult删除结果(此参数查询时不用，所以这里设置非必须提供)
    @RequestMapping(value="/employee/searchByPage")
    public ModelAndView searchByPage(String employeeName, int currentPage, ModelAndView mv,
            @RequestParam(required=false) String delResult)
    {
        // System.out.println("employeeName=" + employeeName + " ; currentPage" + currentPage);

        // 由findEmployeePageByName方法将分页信息写进去
        PageParams params=new PageParams();
        mv.addObject("allEmployee",
                     employeeService.findEmployeePageByName(employeeName, currentPage, params));
        mv.addObject("currentPage", params.getCurrentPage());
        mv.addObject("allCount", params.getAllCount());
        mv.addObject("allPageCount", params.getAllPageCount());
        mv.addObject("searchEmployeeName", employeeName);
        mv.addObject("delResult", delResult);
        mv.setViewName("/employee/index");

        return mv;
    }

    // 处理/employee/openAdd请求，打开增加员工页面
    @RequestMapping(value="/employee/openAdd")
    public String openAdd()
    {
        return "/employee/add";
    }

    // 处理/employee/save请求，增加员工信息
    @SuppressWarnings("finally")
    @RequestMapping(value="/employee/save")
    public ModelAndView save(Employee employee, ModelAndView mv)
    {
        try
        {
            employeeService.insert(employee);
            mv.addObject("message", "添加成功，请继续添加！");
        }
        catch(Exception e)
        {
            mv.addObject("message", "添加失败，用户编号已存在或数据格式有误！");
        }
        finally
        {
            mv.setViewName("/employee/add");
            return mv;
        }
    }

    // 处理/employee/openUpdate请求，打开更新员工页面
    @RequestMapping(value="/employee/openUpdate")
    public ModelAndView openUpdate(int empId, ModelAndView mv)
    {
        Employee employee=employeeService.findEmployeeById(empId);
        mv.addObject("employee", employee);
        mv.setViewName("/employee/update");
        return mv;
    }

    // 处理/employee/update请求，更新员工信息
    @SuppressWarnings("finally")
    @RequestMapping(value="/employee/update")
    public ModelAndView update(Employee employee, ModelAndView mv)
    {
        // System.out.println(employee.toString());
        try
        {
            employeeService.update(employee);
            mv.addObject("message", "更新成功！");
        }
        catch(Exception e)
        {
            mv.addObject("message", "更新失败，用户编号已存在或数据格式有误！");
        }
        finally
        {
            mv.addObject("employee", employee);
            mv.setViewName("/employee/update");
            return mv;
        }
    }

    // 处理/employee/delete请求，删除员工信息
    @SuppressWarnings("finally")
    @RequestMapping(value="/employee/delete")
    public ModelAndView delete(int empId, String employeeName, int currentPage, ModelAndView mv)
    {
        // System.out.println(empId + " " + employeeName + " " + currentPage);
        String delResult="";
        try
        {
            employeeService.delete(empId);
            delResult=URLEncoder.encode("删除成功！", "utf-8");
        }
        catch(Exception e)
        {
            delResult=URLEncoder.encode("删除失败，此用户为登录用户！", "utf-8");
        }
        finally
        {
            String url="../employee/searchByPage?employeeName=" + employeeName + "&currentPage="
                    + currentPage + "&delResult=" + delResult;
            mv.setView(new RedirectView(url));
            return mv;
        }
    }
}
