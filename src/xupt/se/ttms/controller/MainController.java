package xupt.se.ttms.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 动态页面跳转控制器
 * @author zhangrong
 */
@Controller
public class MainController
{
    @RequestMapping("/login")
    public String login()
    {
        // 动态跳转页面
        return "login";
    }

    @RequestMapping(value="/exit")
    public String exit(HttpSession session)
    {
        System.out.println("--------->exit page");
        session.invalidate();
        // 动态跳转页面
        return "login";
    }
}
