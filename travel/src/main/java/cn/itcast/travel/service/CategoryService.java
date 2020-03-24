package cn.itcast.travel.service;

import cn.itcast.travel.domain.Category;

import java.util.List;

/**
 * Created by 祭音丶 on 2020/2/23.
 */
public interface CategoryService {
    List<Category> findAll();
}
