package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.FavoriteDao;
import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.dao.impl.FavoriteDaoImpl;
import cn.itcast.travel.dao.impl.RouteDaoImpl;
import cn.itcast.travel.domain.Favorite;
import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.service.FavoriteService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 祭音丶 on 2020/2/26.
 */
public class FavoriteServiceImpl implements FavoriteService {
    private FavoriteDao favoriteDao = new FavoriteDaoImpl();
    private RouteDao routeDao = new RouteDaoImpl();

    @Override
    public boolean isFavorite(String ridStr, int uid) {
        //1.转化rid为int类型
        int rid = Integer.parseInt(ridStr);
        //2.调用favoriteDao.findByRidAndUid()传入rid，uid
        Favorite favorite = favoriteDao.findByRidAndUid(rid, uid);
        //3.返回值
        return favorite != null;
    }

    @Override
    public void addFavorite(String ridStr, int uid) {
        int rid = Integer.parseInt(ridStr);
        //1.调用FavoriteDao的add方法添加
        favoriteDao.addFavorite(rid,uid);
    }

    @Override
    public PageBean<Route> findFavorite(int uid, int currentPage) {
        List list_rid = new ArrayList();
        int pageSize = 12;
        //1.根据uid查询商品id
        List<Favorite> list = favoriteDao.findByUid(uid);
        //int rid = favorite.getRoute().getRid();
        for (Favorite favorite : list) {
            list_rid.add(favorite.getRid());
        }
        //2.根据商品id查询商品信息并封装进pageBean对象中
        PageBean<Route> pb = new PageBean<Route>();
        pb.setCurrentPage(currentPage);
        int stars = (currentPage - 1) * pageSize;
        pb.setPageSize(pageSize);
        List<Route> routes = routeDao.findByRid(list_rid,stars,pageSize);
        pb.setList(routes);
        int totalCount = favoriteDao.findCountByUid(uid);
        int totalPage = totalCount % pageSize == 0 ? totalCount/pageSize : (totalCount/pageSize +1);
        pb.setTotalPage(totalPage);
        //3.完善pageBean对象，并返回
        return pb;
    }

    @Override
    public PageBean<Route> findRouteByFavorite(int currentPage, String rname, int priceStart, int priceEnd , int pageSize) {
        int cid = 0;
        PageBean<Route> pb = new PageBean<>();
        pb.setCurrentPage(currentPage);
        int start = (currentPage - 1)*pageSize;
        pb.setPageSize(pageSize);
        List<Route> routes = routeDao.findRouteByFavorite(rname, priceStart, priceEnd, start, pageSize);
        pb.setList(routes);
        int totalCount = routeDao.findTotalCountByRname(rname,priceStart,priceEnd);
        pb.setTotalCount(totalCount);
        int totalPage = totalCount % pageSize == 0 ? totalCount/pageSize : (totalCount/pageSize +1);
        pb.setTotalPage(totalPage);
        return pb;
    }
}
