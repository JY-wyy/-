package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.CategoryDao;
import cn.itcast.travel.domain.Category;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

/**
 * Created by 祭音丶 on 2020/2/23.
 */
public class CategoryDaoImp implements CategoryDao {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());
    /***
     * 分页信息的查询
     */
    @Override
    public List<Category> findAll() {
        String sql = "select * from tab_category";
        List<Category> query = template.query(sql, new BeanPropertyRowMapper<Category>(Category.class));
        return query;
    }
}
