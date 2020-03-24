package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Favorite;

import java.util.List;

/**
 * Created by ����ؼ on 2020/2/26.
 */
public interface FavoriteDao {
    /***
     * ����rid��uid��ѯ�ղ����
     * @param rid
     * @param uid
     * @return
     */
    Favorite findByRidAndUid(int rid, int uid);

    /**
     * ����rid��ѯ�ղش���
     * @param rid
     * @return
     */
    int findByCountRid(int rid);
    /***
     * ����ղ�
     */
    void addFavorite(int rid,int uid);

    /**
     * ����uid��ѯ�ղ����
     * @param uid
     * @return
     */
    List<Favorite> findByUid(int uid);

    /***
     * ����uid����������
     */
    int findCountByUid(int uid);
}
