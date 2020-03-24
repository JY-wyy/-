package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.CategoryDao;
import cn.itcast.travel.dao.impl.CategoryDaoImp;
import cn.itcast.travel.domain.Category;
import cn.itcast.travel.service.CategoryService;
import cn.itcast.travel.util.JedisUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by 祭音丶 on 2020/2/23.
 */
public class CategoryServiceImp implements CategoryService {
    private CategoryDao categoryDao = new CategoryDaoImp();

    /**
     * 查询分页数据并缓存
     * @return
     */
    @Override
    public List<Category> findAll() {
        //1.查询redis是否有数据
        //1.1获取jedis连接
        Jedis jedis = JedisUtil.getJedis();
       // Set<String> set = jedis.zrange("category", 0, -1);
        Set<Tuple> set = jedis.zrangeWithScores("category", 0, -1);
        List<Category> ls = null;
        if (set == null || set.size() == 0){
        //2.无数据则查询数据库返回，并存储进redis
            System.out.println("从数据库查询");
            ls = categoryDao.findAll();
            for (Category category : ls) {
                jedis.zadd("category",category.getCid(),category.getCname());
            }
        }else {
        //3.有数据转换返回
            System.out.println("从缓存中查询");
            ls = new ArrayList<Category>();
            for (Tuple s : set) {
                Category category = new Category();
                category.setCname(s.getElement());
                category.setCid((int)s.getScore());
                ls.add(category);
            }

        }
        return ls;
    }
}
