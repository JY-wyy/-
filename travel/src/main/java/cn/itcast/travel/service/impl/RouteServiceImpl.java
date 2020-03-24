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
 * Created by ����ؼ on 2020/2/24.
 */
public class RouteServiceImpl implements RouteService {
    private RouteDao routeDao = new RouteDaoImpl();
    private RouteImgDao routeImgDao = new RouteImgDaoImpl();
    private SellerDao sellerDao = new SellerDaoImpl();
    private FavoriteDao favoriteDao = new FavoriteDaoImpl();
    @Override
    public PageBean<Route> pageQuery(int cid, int currentPage, int pageSize,String rname) {
        //totalCount;//�ܼ�¼��
        //totalPage;//��ҳ��
        //currentPage;//��ǰҳ��
        //pageSize;//ÿҳ��ʾ������
        //list;//ÿҳ��ʾ�����ݼ���
        PageBean<Route> pb = new PageBean<>();
        //1.�����ܼ�¼��
        pb.setTotalCount(routeDao.findTotalCount(cid,rname));
        //3.���õ�ǰҳ��
        pb.setCurrentPage(currentPage);
        //4.����ÿҳ��ʾ������
        pb.setPageSize(pageSize);
        //5.����ÿҳ��ʾ�����ݼ���
        int stars = (currentPage - 1) * pageSize;
        pb.setList(routeDao.findPageCount(cid,stars,pageSize,rname));
        //2.������ҳ��
        pb.setTotalPage(routeDao.findTotalCount(cid,rname) % pageSize == 0? routeDao.findTotalCount(cid,rname) / pageSize : (routeDao.findTotalCount(cid,rname) / pageSize)+1);
        return pb;
    }

    @Override
    public Route findOne(int rid) {
        Jedis jedis = JedisUtil.getJedis();
        //1.����rid����Ʒ������Ʒ��Ϣ
        Route route = routeDao.findOne(rid);
        //2.����rid��ѯ��ƷͼƬ��鵽ͼƬ���ϣ�����װ
        route.setRouteImgList(routeImgDao.findByRid(rid));
        //3.���ݵ�һ���鵽����Ʒ��Ϣ�е�sid����ѯ�̼���Ϣ
        int sid = route.getSid();
        Seller seller = sellerDao.findBySid(sid);
        route.setSeller(seller);
        //4.����cid�鵽������������ƣ�����װ
        int cid = route.getCid();
        Set<Tuple> categorySet = jedis.zrangeWithScores("category", cid-1, cid-1);
        Category category = new Category();
        for (Tuple tuple : categorySet) {
            category.setCname(tuple.getElement());
            category.setCid((int) tuple.getScore());
        }
        //5.����rid��ѯ�ղش���,����װ
        int count = favoriteDao.findByCountRid(route.getRid());
        route.setCount(count);
        route.setCategory(category);
        return route;
    }
}
