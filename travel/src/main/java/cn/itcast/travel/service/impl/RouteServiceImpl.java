package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.FavoriteDao;
import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.dao.RouteImgDao;
import cn.itcast.travel.dao.SellerDao;
import cn.itcast.travel.dao.impl.FavoriteDaoImpl;
import cn.itcast.travel.dao.impl.RouteDaoImpl;
import cn.itcast.travel.dao.impl.RouteImgDaoImpl;
import cn.itcast.travel.dao.impl.SellerDaoImpl;
import cn.itcast.travel.domain.*;
import cn.itcast.travel.service.RouteService;
import cn.itcast.travel.util.JedisUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by 祭音丶 on 2020/2/24.
 */
public class RouteServiceImpl implements RouteService {
    private RouteDao routeDao = new RouteDaoImpl();
    private RouteImgDao routeImgDao = new RouteImgDaoImpl();
    private SellerDao sellerDao = new SellerDaoImpl();
    private FavoriteDao favoriteDao = new FavoriteDaoImpl();
    @Override
    public PageBean<Route> pageQuery(int cid, int currentPage, int pageSize,String rname) {
        //totalCount;//总记录数
        //totalPage;//总页数
        //currentPage;//当前页码
        //pageSize;//每页显示的条数
        //list;//每页显示的数据集合
        PageBean<Route> pb = new PageBean<>();
        //1.设置总记录数
        pb.setTotalCount(routeDao.findTotalCount(cid,rname));
        //3.设置当前页码
        pb.setCurrentPage(currentPage);
        //4.设置每页显示的条数
        pb.setPageSize(pageSize);
        //5.设置每页显示的数据集合
        int stars = (currentPage - 1) * pageSize;
        pb.setList(routeDao.findPageCount(cid,stars,pageSize,rname));
        //2.设置总页数
        pb.setTotalPage(routeDao.findTotalCount(cid,rname) % pageSize == 0? routeDao.findTotalCount(cid,rname) / pageSize : (routeDao.findTotalCount(cid,rname) / pageSize)+1);
        return pb;
    }

    @Override
    public Route findOne(int rid) {
        Jedis jedis = JedisUtil.getJedis();
        //1.根据rid查商品表查出商品信息
        Route route = routeDao.findOne(rid);
        //2.根据rid查询商品图片表查到图片集合，并封装
        route.setRouteImgList(routeImgDao.findByRid(rid));
        //3.根据第一步查到的商品信息中的sid，查询商家信息
        int sid = route.getSid();
        Seller seller = sellerDao.findBySid(sid);
        route.setSeller(seller);
        //4.根据cid查到所属分类的名称，并封装
        int cid = route.getCid();
        Set<Tuple> categorySet = jedis.zrangeWithScores("category", cid-1, cid-1);
        Category category = new Category();
        for (Tuple tuple : categorySet) {
            category.setCname(tuple.getElement());
            category.setCid((int) tuple.getScore());
        }
        //5.根据rid查询收藏次数,并封装
        int count = favoriteDao.findByCountRid(route.getRid());
        route.setCount(count);
        route.setCategory(category);
        return route;
    }
}
