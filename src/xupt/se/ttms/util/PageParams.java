package xupt.se.ttms.util;

/**
 * 保存分页参数类
 * @author zhangrong
 */
public class PageParams
{
    private int pageSize=10; // 每页显示条数
    private int allCount=0; // 数据库中条数
    private int allPageCount=0; // 总页数
    private int currentPage=1; // 当前页

    public int getPageSize()
    {
        return pageSize;
    }

    public void setPageSize(int pageSize)
    {
        this.pageSize=pageSize;
    }

    public int getAllCount()
    {
        return allCount;
    }

    public void setAllCount(int allCount)
    {
        this.allCount=allCount;
    }

    public int getAllPageCount()
    {
        return allPageCount;
    }

    public void setAllPageCount(int allPageCount)
    {
        this.allPageCount=allPageCount;
    }

    public int getCurrentPage()
    {
        return currentPage;
    }

    public void setCurrentPage(int currentPage)
    {
        this.currentPage=currentPage;
    }

}
