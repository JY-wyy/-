package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.dao.impl.UserDaoImpl;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.util.MailUtils;
import cn.itcast.travel.util.UuidUtil;

/**
 * Created by 祭音丶 on 2020/2/21.
 */
public class UserServiceImpl implements UserService {
    private UserDao userDao = new UserDaoImpl();

    /***
     * 注册用户
     * @param user
     * @return
     */
    @Override
    public boolean regist(User user) {
        //1.调用dao层根据username查找
        User u = userDao.findByUsername(user.getUsername());
        //2.判断是否查找到
        if (u != null){
        //3.用户名存在
         return false;
        }
        //4.用户名不存在,保存用户信息
            //4.1设置激活状态
        user.setStatus("N");
            //4.2设置激活码
        user.setCode(UuidUtil.getUuid());
        userDao.save(user);
        //5.发送邮件
            //5.1编辑邮件正文
        String content = "<a href='http://localhost/travel/user/active?code="+user.getCode()+"'>点击注册【黑马旅游网】</a>";
        MailUtils.sendMail(user.getEmail(),content,"激活邮件");
        //6.返回成功与否
        return true;
    }

    @Override
    public boolean active(String code) {
        User user = userDao.findByCode(code);
        if(user != null){
            //用户存在
            userDao.updataStatus(user);
            return true;
        }else {
            //用户不存在
        return false;
        }
    }

    /***
     * 用户登录
     * @param user
     * @return
     */
    @Override
    public User login(User user) {
        return userDao.findByUsernameAndPassword(user.getUsername(),user.getPassword());
    }

}
