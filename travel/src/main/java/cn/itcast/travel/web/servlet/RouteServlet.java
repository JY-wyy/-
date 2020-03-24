package cn.itcast.travel.web.servlet;

import cn.itcast.travel.dao.FavoriteDao;
import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.FavoriteService;
import cn.itcast.travel.service.RouteService;
import cn.itcast.travel.service.impl.FavoriteServiceImpl;
import cn.itcast.travel.service.impl.RouteServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by 祭音丶 on 2020/2/24.
 */
@WebServlet("/route/*")
public class RouteServlet extends BaseServlet {
    private RouteService routeService = new RouteServiceImpl();
    FavoriteService favoriteService = new FavoriteServiceImpl();
    /***
     * 分页查询
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void pageQuery(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.获取参数
        String cidStr = request.getParameter("cid");
        String currentPageStr = request.getParameter("currentPage");
        String pageSizeStr = request.getParameter("pageSize");

        //获取搜索条件rname
        String rname = request.getParameter("rname");
        //rname转码
        rname = new String(rname.getBytes("iso-8859-1"),"utf-8");
        //1.1处理参数
        int cid =0;
        if(cidStr != null && cidStr.length()>0 && !"null".equals(cidStr)){
            cid = Integer.parseInt(cidStr);
        }
        //当前页码，如果不传递默认1
        int currentPage = 0;
        if("null".equals(currentPageStr)){
            currentPageStr = null;
        }
        if(currentPageStr != null && currentPageStr.length()>0){
            currentPage = Integer.parseInt(currentPageStr);
        }else {
            currentPage = 1;
        }

        //每页显示条数,如果不传默认5
        int pageSize = 0;
        if(pageSizeStr != null && pageSizeStr.length()>0){
            pageSize = Integer.parseInt(pageSizeStr);
        }else {
            pageSize = 5;
        }
        //2.调用service
        PageBean<Route> routePageBean = routeService.pageQuery(cid, currentPage, pageSize,rname);
        //3.封装对象并返回
        writeValue(routePageBean,response);
    }

    /**
     * 查询商品详细信息
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void findOne(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.获取参数
        String ridStr = request.getParameter("rid");
        //1.1处理参数
        int rid = Integer.parseInt(ridStr);
        //2.调用service
        Route route = routeService.findOne(rid);
        //3.封装数据为json并返回
        writeValue(route,response);
    }

    /**
     * 查询是否收藏
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void isFavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.获取前台发来的rid
        String rid = request.getParameter("rid");
        //2.获取session中的user对象
        User user = (User) request.getSession().getAttribute("user");
        int uid;
        //3.判断是否登录
        if(user == null){
        //3.1否 为uid赋初值为0
            uid = 0;
        }else {
            uid = user.getUid();
        }
        //3.2是 调用service查询 返回布尔型常量falg
        boolean falg = favoriteService.isFavorite(rid, uid);
        //4.封装参数并返回
        writeValue(falg,response);

    }

    /**
     * 添加收藏
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void addFavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.获取rid
        String rid = request.getParameter("rid");
        //2.获取session中的user对象
        User user = (User)request.getSession().getAttribute("user");
        //3.判断user是否为空(防止空指针异常)
        if (user == null) {
            //未登录
            return;
        }else {
        //4.调用Favoriteservice的add方法，添加收藏信息
            favoriteService.addFavorite(rid,user.getUid());
        }
    }

    /**
     * 查询用户收藏情况
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void findAllByUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String currentPageStr = request.getParameter("currentPage");
        //1.获取session中的user对象
        User user = (User)request.getSession().getAttribute("user");
        PageBean<Route> favoriteRoute;
        //3.判断是否为空
        if(user != null){
        //3.1不为空，证明是在登陆状态下查询个人收藏
        //3.1.1调用service查询个人收藏情况
            int currentPage = 0;
            if("null".equals(currentPageStr)){
                currentPageStr = null;
            }
            if(currentPageStr != null && currentPageStr.length()>0){
                currentPage = Integer.parseInt(currentPageStr);
            }else {
                currentPage = 1;
            }
            favoriteRoute = favoriteService.findFavorite(user.getUid(), currentPage);
        }else{
        //3.2为空，是在查询全站收藏
        //3.2.1调用service查询全站收藏情况
            return;
        }
        //5.返回商品情况
        writeValue(favoriteRoute,response);
    }

    public void findAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String currentPageStr = request.getParameter("currentPage");
        String priceStartStr = request.getParameter("priceStart");
        String priceEndStr = request.getParameter("priceEnd");
        String pageSizeStr = request.getParameter("pageSize");

        String rname = request.getParameter("rname");
        if(rname == null){
            rname = "";
        }
        rname = new String(rname.getBytes("iso-8859-1"),"utf-8");
        int currentPage;
        if("null".equals(currentPageStr)){
            currentPageStr = null;
        }
        if(currentPageStr != null && currentPageStr.length()>0){
            currentPage = Integer.parseInt(currentPageStr);
        }else {
            currentPage = 1;
        }


        int priceStart;
        if("null".equals(priceStartStr)){
            priceStartStr = null;
        }
        if(priceStartStr != null && priceStartStr.length()>0){
            priceStart = Integer.parseInt(priceStartStr);
        }else {
            priceStart = 0;
        }


        int priceEnd;
        if("null".equals(priceEndStr)){
            priceEndStr = null;
        }
        if(priceEndStr != null && priceEndStr.length()>0){
            priceEnd = Integer.parseInt(priceEndStr);
        }else {
            priceEnd = 999999;
        }

        int pageSize;
        if("null".equals(pageSizeStr)){
            pageSizeStr = null;
        }
        if(pageSizeStr != null && pageSizeStr.length()>0){
            pageSize = Integer.parseInt(pageSizeStr);
        }else {
            pageSize = 8;
        }


        PageBean<Route> pb = favoriteService.findRouteByFavorite(currentPage, rname, priceStart, priceEnd,pageSize);
        writeValue(pb,response);

    }
}
