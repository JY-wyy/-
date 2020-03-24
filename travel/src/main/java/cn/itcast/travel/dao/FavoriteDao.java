package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Favorite;

import java.util.List;

/**
 * Created by 祭音丶 on 2020/2/26.
 */
public interface FavoriteDao {
    /***
     * 根据rid，uid查询收藏情况
     * @param rid
     * @param uid
     * @return
     */
    Favorite findByRidAndUid(int rid, int uid);

    /**
     * 根据rid查询收藏次数
     * @param rid
     * @return
     */
    int findByCountRid(int rid);
    /***
     * 添加收藏
     */
    void addFavorite(int rid,int uid);

    /**
     * 根据uid查询收藏情况
     * @param uid
     * @return
     */
    List<Favorite> findByUid(int uid);

    /***
     * 根据uid查数据条数
     */
    int findCountByUid(int uid);
}
