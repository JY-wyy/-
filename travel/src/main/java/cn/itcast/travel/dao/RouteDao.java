package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Route;

import java.util.List;

/**
 * Created by ����ؼ on 2020/2/24.
 */
public interface RouteDao {
    /**
     * ����cid��ѯ�ܼ�¼��
     * @param cid
     * @return
     */
    int findTotalCount(int cid,String rname);

    /**
     * ��ҳ��ʾ�����ݼ���
     * @param cid
     * @param start
     * @param pageSize
     * @return
     */
    List<Route> findPageCount(int cid,int start,int pageSize,String rname);

    /**
     * ����rid��ѯ��Ʒ����
     * @param rid
     * @return
     */
    List<Route> findByRid(List rid,int start ,int pageSize);

    /***
     * ������Ʒid��ѯ������Ʒ
     * @param rid
     * @return
     */
    Route findOne(int rid);

    /**
     * ���ղ����С���ʾ�����ݼ���
     * @param rname
     * @param priceStart
     * @param priceEnd
     * @param start
     * @param pageSize
     * @return
     */
    List<Route> findRouteByFavorite(String rname , int priceStart , int priceEnd ,int start ,int pageSize );

    /**
     * ����rname��۸�Χ�ڵļ�¼��
     * @param rname
     * @return
     */
    int findTotalCountByRname(String rname , int priceStart,int priceEnd);

}
