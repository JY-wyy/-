package cn.itcast.travel.dao;

import cn.itcast.travel.domain.RouteImg;

import java.util.List;

/**
 * Created by ����ؼ on 2020/2/25.
 */
public interface RouteImgDao {
    /**
     * ����rid��ѯ��ƷͼƬ����
     * @param rid
     * @return
     */
    List<RouteImg> findByRid(int rid);
}
