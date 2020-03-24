package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Route;

import java.util.List;

/**
 * Created by 祭音丶 on 2020/2/24.
 */
public interface RouteDao {
    /**
     * 根据cid查询总记录数
     * @param cid
     * @return
     */
    int findTotalCount(int cid,String rname);

    /**
     * 分页显示的数据集合
     * @param cid
     * @param start
     * @param pageSize
     * @return
     */
    List<Route> findPageCount(int cid,int start,int pageSize,String rname);

    /**
     * 根据rid查询商品对象
     * @param rid
     * @return
     */
    List<Route> findByRid(List rid,int start ,int pageSize);

    /***
     * 根据商品id查询单个商品
     * @param rid
     * @return
     */
    Route findOne(int rid);

    /**
     * “收藏排行”显示的数据集合
     * @param rname
     * @param priceStart
     * @param priceEnd
     * @param start
     * @param pageSize
     * @return
     */
    List<Route> findRouteByFavorite(String rname , int priceStart , int priceEnd ,int start ,int pageSize );

    /**
     * 根据rname查价格范围内的记录数
     * @param rname
     * @return
     */
    int findTotalCountByRname(String rname , int priceStart,int priceEnd);

}
