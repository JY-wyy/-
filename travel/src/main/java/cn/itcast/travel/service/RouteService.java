package cn.itcast.travel.service;

import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;


/**
 * Created by 祭音丶 on 2020/2/24.
 */
public interface RouteService {
    /***
     *分页查询
     * @param cid 顶部状态栏id
     * @param currentPage 当前页码
     * @param pageSize  每页显示条数
     * @return
     */
    PageBean<Route> pageQuery(int cid, int currentPage, int pageSize,String rname);

    /**
     * 根据rid查找商品信息
     * @param rid
     * @return
     */
    Route findOne(int rid);
}
