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
 * Created by ����ؼ on 2020/2/24.
 */
@WebServlet("/route/*")
public class RouteServlet extends BaseServlet {
    private RouteService routeService = new RouteServiceImpl();
    FavoriteService favoriteService = new FavoriteServiceImpl();
    /***
     * ��ҳ��ѯ
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void pageQuery(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.��ȡ����
        String cidStr = request.getParameter("cid");
        String currentPageStr = request.getParameter("currentPage");
        String pageSizeStr = request.getParameter("pageSize");

        //��ȡ��������rname
        String rname = request.getParameter("rname");
        //rnameת��
        rname = new String(rname.getBytes("iso-8859-1"),"utf-8");
        //1.1�������
        int cid =0;
        if(cidStr != null && cidStr.length()>0 && !"null".equals(cidStr)){
            cid = Integer.parseInt(cidStr);
        }
        //��ǰҳ�룬���������Ĭ��1
        int currentPage = 0;
        if("null".equals(currentPageStr)){
            currentPageStr = null;
        }
        if(currentPageStr != null && currentPageStr.length()>0){
            currentPage = Integer.parseInt(currentPageStr);
        }else {
            currentPage = 1;
        }

        //ÿҳ��ʾ����,�������Ĭ��5
        int pageSize = 0;
        if(pageSizeStr != null && pageSizeStr.length()>0){
            pageSize = Integer.parseInt(pageSizeStr);
        }else {
            pageSize = 5;
        }
        //2.����service
        PageBean<Route> routePageBean = routeService.pageQuery(cid, currentPage, pageSize,rname);
        //3.��װ���󲢷���
        writeValue(routePageBean,response);
    }

    /**
     * ��ѯ��Ʒ��ϸ��Ϣ
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void findOne(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.��ȡ����
        String ridStr = request.getParameter("rid");
        //1.1�������
        int rid = Integer.parseInt(ridStr);
        //2.����service
        Route route = routeService.findOne(rid);
        //3.��װ����Ϊjson������
        writeValue(route,response);
    }

    /**
     * ��ѯ�Ƿ��ղ�
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void isFavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.��ȡǰ̨������rid
        String rid = request.getParameter("rid");
        //2.��ȡsession�е�user����
        User user = (User) request.getSession().getAttribute("user");
        int uid;
        //3.�ж��Ƿ��¼
        if(user == null){
        //3.1�� Ϊuid����ֵΪ0
            uid = 0;
        }else {
            uid = user.getUid();
        }
        //3.2�� ����service��ѯ ���ز����ͳ���falg
        boolean falg = favoriteService.isFavorite(rid, uid);
        //4.��װ����������
        writeValue(falg,response);

    }

    /**
     * ����ղ�
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void addFavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.��ȡrid
        String rid = request.getParameter("rid");
        //2.��ȡsession�е�user����
        User user = (User)request.getSession().getAttribute("user");
        //3.�ж�user�Ƿ�Ϊ��(��ֹ��ָ���쳣)
        if (user == null) {
            //δ��¼
            return;
        }else {
        //4.����Favoriteservice��add����������ղ���Ϣ
            favoriteService.addFavorite(rid,user.getUid());
        }
    }

    /**
     * ��ѯ�û��ղ����
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void findAllByUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String currentPageStr = request.getParameter("currentPage");
        //1.��ȡsession�е�user����
        User user = (User)request.getSession().getAttribute("user");
        PageBean<Route> favoriteRoute;
        //3.�ж��Ƿ�Ϊ��
        if(user != null){
        //3.1��Ϊ�գ�֤�����ڵ�½״̬�²�ѯ�����ղ�
        //3.1.1����service��ѯ�����ղ����
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
        //3.2Ϊ�գ����ڲ�ѯȫվ�ղ�
        //3.2.1����service��ѯȫվ�ղ����
            return;
        }
        //5.������Ʒ���
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
