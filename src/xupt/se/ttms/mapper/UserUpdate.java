package xupt.se.ttms.mapper;

import java.util.Map;

import xupt.se.ttms.domain.User;

public class UserUpdate
{
    public String update(Map<String, Object> map)
    {
        User user=(User) map.get("user");
        String head_path=user.getHead_path();
        String sql="update user set emp_pass='" + user.getEmp_pass() + "', type=" + user.getType();
        if(head_path != null && !head_path.trim().equals(""))
        {
            sql+=", head_path='" + head_path + "' ";
        }
        sql+=" where emp_no='" + user.getEmp_no() + "'";
        System.out.println(sql);
        return sql;
    }

}
