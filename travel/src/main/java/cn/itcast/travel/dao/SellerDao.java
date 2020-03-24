package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Seller;

/**
 * Created by º¿“Ùÿº on 2020/2/25.
 */
public interface SellerDao {
    Seller findBySid(int sid);
}
