package cn.itcast.travel.service;

import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;

import java.util.List;

/**
 * Created by 祭音丶 on 2020/2/26.
 */
public interface FavoriteService {
    /**
     * 查询用户是否收藏
     * @return
     */
    boolean isFavorite(String rid,int uid);

    /**
     * 添加收藏
     */
    void addFavorite(String ridStr,int uid);

    /**
     * 查询个人收藏信息
     * @param uid
     * @param currentPage
     * @return
     */
    PageBean<Route> findFavorite(int uid, int currentPage);

    /**
     * 查询全站收藏信息
     * @param currentPage
     * @param rname
     * @param priceStar
     * @param priceEnd
     * @return
     */
    PageBean<Route> findRouteByFavorite(int currentPage,String rname , int priceStar , int priceEnd ,int pageSize);
}
