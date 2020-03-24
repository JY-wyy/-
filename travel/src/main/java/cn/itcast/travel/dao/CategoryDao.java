package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Category;

import java.util.List;

/**
 * Created by 祭音丶 on 2020/2/23.
 */
public interface CategoryDao {
    List<Category> findAll();
}
