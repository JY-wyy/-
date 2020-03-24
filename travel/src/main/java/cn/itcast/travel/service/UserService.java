package cn.itcast.travel.service;

import cn.itcast.travel.domain.User;

/**
 * Created by ¼ÀÒôØ¼ on 2020/2/21.
 */
public interface UserService {
    /***
     * ×¢²áÓÃ»§
     * @param user
     * @return
     */
    boolean regist(User user);

    boolean active(String code);

    User login(User user);
}
