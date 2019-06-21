package xupt.se.ttms.controller;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import xupt.se.ttms.domain.User;
import xupt.se.ttms.service.EmployeeService;
import xupt.se.ttms.service.UserService;

/**
 * 处理登陆用户请求控制器
 * @author zhangrong
 */
@Controller
public class UserController
{
    // 自动注入UserService
    @Autowired
    @Qualifier("userService")
    private UserService userService;

    // 自动注入EmployeeService
    @Autowired
    @Qualifier("employeeService")
    private EmployeeService employeeService;

    // 处理/user/login登陆请求
    @RequestMapping(value="/user/main")
    public ModelAndView login(String loginName, String password, ModelAndView mv, HttpSession session)
    {
        // 根据登录名和密码查找用户，判断用户登录
        User user=userService.login(loginName, password);
        if(user != null)
        {
            // 登录成功，将user对象设置到HttpSession作用范围域
            session.setAttribute("user", user);
            // 转发到Controller:main请求
            // mv.setView(new RedirectView("/ttms-ssm/main"));
            mv.setViewName("/main/main");
        }
        else
        {
            // 登录失败，设置失败提示信息，并跳转到登录页面
            mv.addObject("message", "登录名或密码错误，请重新输入!");
            mv.setViewName("login");
        }
        return mv;
    }

    // 处理/user/link请求，由导航跳转到user主页
    @RequestMapping(value="/user/link")
    public String link()
    {
        return "/user/index";
    }

    // 处理/user/searchByEmpNo请求
    @RequestMapping(value="/user/searchByEmpNo")
    public ModelAndView searchByEmpNo(String empNo, ModelAndView mv, @RequestParam(required=false) String delResult)
    {
        // System.out.println("empNo = " + empNo);
        ArrayList<User> list=userService.searchByEmpNo(empNo);
        // for(User user : list)
        // System.out.println(user);
        mv.addObject("allUser", list);
        mv.addObject("searchEmpNo", empNo); // 返回查询关键字
        mv.addObject("delResult", delResult);
        mv.setViewName("/user/index");
        return mv;
    }

    // 处理/user/openAdd请求，打开增加员工页面
    @RequestMapping(value="/user/openAdd")
    public ModelAndView openAdd(ModelAndView mv)
    {
        mv.addObject("allEmployee", employeeService.findEmployeeByNoType());
        mv.setViewName("/user/add");
        return mv;
    }

    // 设置用户类型
    @RequestMapping(value="/user/setUserType")
    public ModelAndView setUserType(String[] emp_no_list, int type, ModelAndView mv)
    {
        // 1.设置用户类型
        // System.out.println(Arrays.toString(emp_no_list));
        // System.out.println(type);
        User user=null;
        for(String emp_no : emp_no_list)
        {
            user=new User();
            user.setEmp_no(emp_no);
            user.setEmp_pass("123456"); // 默认密码
            user.setType(type);
            user.setHead_path("head/default.png");// 默认头像
            userService.insert(user);
        }
        // 2.再次查找未设置类型用户
        mv.addObject("allEmployee", employeeService.findEmployeeByNoType());
        mv.setViewName("/user/add");
        // 3.返回设置用户类型页面
        return mv;
    }

    // 处理/user/openUpdate请求，打开更新员工页面
    @RequestMapping(value="/user/openUpdate")
    public ModelAndView openUpdate(String empNo, ModelAndView mv)
    {
        System.out.println(empNo);
        // 1.调用service查empNo对应的人，包括employee表中的名字（关联查询），此empNo是实际值，非模糊匹配，所以结果只有一个值
        ArrayList<User> list=userService.searchByEmpNo(empNo);
        // 2.返回此人数据到前端update.jsp
        System.out.println(list.get(0));
        mv.addObject("user", list.get(0));
        mv.setViewName("/user/update");
        return mv;
    }

    // 处理/user/update请求，更新员工数据
    @RequestMapping(value="/user/update")
    public ModelAndView update(String emp_no, String emp_pass, int type, String head_path, MultipartFile headPic,
            ModelAndView mv, HttpSession session) throws IllegalStateException, IOException
    {
        System.out.println(emp_no + " " + emp_pass + " " + type + " " + head_path);
        String ext="";
        String message="修改失败，请检查后重新修改。";

        // 1.更新登录用户数据
        User user=new User();
        user.setEmp_no(emp_no);
        user.setEmp_pass(emp_pass);
        user.setType(type);
        // 如果上传了头像
        System.out.println("------->是否头像上传过来：" + !headPic.isEmpty());
        if(!headPic.isEmpty())
        {
            String fileName=headPic.getOriginalFilename();
            // 提取扩展名
            ext=fileName.substring(fileName.lastIndexOf("."), fileName.length());
            String newFileName=UUID.randomUUID().toString() + ext;
            // 设置保存路径
            String savePath=session.getServletContext().getRealPath("/") + "head/" + newFileName;
            System.out.println(savePath);
            // 保存头像到本地目录
            headPic.transferTo(new File(savePath));
            // 在数据库中只存路径
            user.setHead_path("head/" + newFileName);
        }

        // 2.保存到数据库
        try
        {
            userService.update(user);
            // 如果上传了新头像，并且原头像不是默认头像
            if(head_path != null && !head_path.equals("") && !headPic.isEmpty() && !head_path.equals("head/default.png"))
            {
                File delHeadPic=new File(session.getServletContext().getRealPath("/") + head_path);
                delHeadPic.delete();
            }
            message="修改成功！";

            // 3.查询该用户
            // 关联查询该用户对应的employee数据，emp_no为确定值，所以只有一个结果
            user=userService.searchByEmpNo(emp_no).get(0);

            // 4.如果修改的用户是当前登陆用户，则替换session中user对象
            User sessionUser=(User) session.getAttribute("user");
            if(emp_no.equals(sessionUser.getEmp_no()))
                session.setAttribute("user", user);
        }
        catch(Exception e)
        {}

        // 4.返回此人数据到前端update.jsp
        System.out.println(user);
        mv.addObject("user", user);
        mv.addObject("message", message);
        mv.setViewName("/user/update");
        return mv;
    }

    // 处理/user/delete请求，删除员工信息
    /**
     * 删除用户
     * @param empNo
     *        要删除用户的编号：user表主键
     * @param headPath
     *        删除用户后要删掉该用户的物理路径上的头像
     * @param searchEmpNo
     *        搜索框中的模糊用户编号(通过搜索得到的用户列表，在这个列表上选择删除某个用户)
     * @param mv
     * @param session
     *        为了获取物理路径
     * @return ModelAndView
     */
    @SuppressWarnings("finally")
    @RequestMapping(value="/user/delete")
    public ModelAndView delete(String empNo, String headPath, String searchEmpNo, ModelAndView mv, HttpSession session)
    {
        System.out.println("emp_no : " + empNo);
        System.out.println("headPath : " + headPath);
        System.out.println("searchEmpNo : " + searchEmpNo);
        String delResult="";
        User user=(User) session.getAttribute("user");
        try
        {
            // 如果删除自己，则通知不能删除(前端已经设限，防止通过URL传递删除自己情况)
            if(empNo.equals(user.getEmp_no()))
            {
                delResult=URLEncoder.encode("不能删除当前用户！", "utf-8");
                String url="../user/searchByEmpNo?empNo=" + searchEmpNo + "&delResult=" + delResult;
                mv.setView(new RedirectView(url));
                return mv;
            }
            // 1.删除该用户
            userService.delete(empNo);
            // 2.删除该用户的头像
            File delHeadPic=new File(session.getServletContext().getRealPath("/") + headPath);
            if(delHeadPic.exists() && !headPath.equals("head/default.png"))
                delHeadPic.delete();
            delResult=URLEncoder.encode("删除成功！", "utf-8");
        }
        catch(Exception e)
        {
            delResult=URLEncoder.encode("删除失败，此用户为登录用户！", "utf-8");
        }
        finally
        {
            String url="../user/searchByEmpNo?empNo=" + searchEmpNo + "&delResult=" + delResult;
            mv.setView(new RedirectView(url));
            return mv;
        }
    }
}
