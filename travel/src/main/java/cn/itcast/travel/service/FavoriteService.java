package cn.itcast.travel.service;

import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;

import java.util.List;

/**
 * Created by ����ؼ on 2020/2/26.
 */
public interface FavoriteService {
    /**
     * ��ѯ�û��Ƿ��ղ�
     * @return
     */
    boolean isFavorite(String rid,int uid);

    /**
     * ����ղ�
     */
    void addFavorite(String ridStr,int uid);

    /**
     * ��ѯ�����ղ���Ϣ
     * @param uid
     * @param currentPage
     * @return
     */
    PageBean<Route> findFavorite(int uid, int currentPage);

    /**
     * ��ѯȫվ�ղ���Ϣ
     * @param currentPage
     * @param rname
     * @param priceStar
     * @param priceEnd
     * @return
     */
    PageBean<Route> findRouteByFavorite(int currentPage,String rname , int priceStar , int priceEnd ,int pageSize);
}
