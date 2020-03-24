package cn.itcast.travel.dao;

import cn.itcast.travel.domain.User;

/**
 * Created by 祭音丶 on 2020/2/21.
 */
public interface UserDao {
    /***
     * 根据用户名查找
     * @return
     */
    User findByUsername(String username);

    /***
     * 保存注册
     * @param user
     */
    void save(User user);

    /***
     * 按激活码查找
     * @param code
     * @return
     */
    User findByCode(String code);

    /***
     * 修改激活状态
     */
    void updataStatus(User user);

    User findByUsernameAndPassword(String username, String password);
}
