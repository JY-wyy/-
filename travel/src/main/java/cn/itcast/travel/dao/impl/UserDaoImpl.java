package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * Created by 祭音丶 on 2020/2/21.
 */
public class UserDaoImpl implements UserDao {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());
    /***
     * 根据username查找用户
     * @param username
     * @return
     */
    @Override
    public User findByUsername(String username) {
        //创建封装对象
        User user = null;
        //定义sql
        String sql = "select * from tab_user where username = ?";
        try {
            //执行sql
            user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), username);
        } catch (Exception e) {

        }
        return user;
    }

    /***
     * 添加用户
     * @param user
     * @return
     */
    @Override
    public void save(User user) {
        //定义sql
        String sql = "insert into tab_user(username,password,name,birthday,sex,telephone,email,status,code) values (?,?,?,?,?,?,?,?,?)";
        //执行sql
        template.update(sql,user.getUsername(),
                        user.getPassword(),
                        user.getName(),
                        user.getBirthday(),
                        user.getSex(),
                        user.getTelephone(),
                        user.getEmail(),
                        user.getStatus(),
                        user.getCode());


    }

    /***
     * 根据激活码查询用户
     * @param code
     * @return
     */
    @Override
    public User findByCode(String code) {
        User user = null;
        String sql = "select * from tab_user where code=?";
        try {
            user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), code);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    /***
     * 修改激活状态
     * @param user
     */
    @Override
    public void updataStatus(User user) {
        String sql = " update tab_user set status = 'Y'where uid=?";
        template.update(sql,user.getUid());
    }

    /***
     * 根据用户户名密码查询用户
     * @param username
     * @param password
     * @return
     */
    @Override
    public User findByUsernameAndPassword(String username, String password) {
        //创建封装对象
        User user = null;
        //定义sql
        String sql = "select * from tab_user where username = ? and password = ?";
        try {
            //执行sql
            user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), username,password);
        } catch (Exception e) {

        }
        return user;
    }
}
