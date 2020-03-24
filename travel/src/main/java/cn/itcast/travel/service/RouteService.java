package cn.itcast.travel.service;

import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;


/**
 * Created by ����ؼ on 2020/2/24.
 */
public interface RouteService {
    /***
     *��ҳ��ѯ
     * @param cid ����״̬��id
     * @param currentPage ��ǰҳ��
     * @param pageSize  ÿҳ��ʾ����
     * @return
     */
    PageBean<Route> pageQuery(int cid, int currentPage, int pageSize,String rname);

    /**
     * ����rid������Ʒ��Ϣ
     * @param rid
     * @return
     */
    Route findOne(int rid);
}
