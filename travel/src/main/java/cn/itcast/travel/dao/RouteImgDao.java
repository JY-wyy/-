package cn.itcast.travel.dao;

import cn.itcast.travel.domain.RouteImg;

import java.util.List;

/**
 * Created by 祭音丶 on 2020/2/25.
 */
public interface RouteImgDao {
    /**
     * 根据rid查询商品图片集合
     * @param rid
     * @return
     */
    List<RouteImg> findByRid(int rid);
}
