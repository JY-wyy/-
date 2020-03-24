package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.service.impl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * Created by 祭音丶 on 2020/2/23.
 */
@WebServlet("/user/*")
public class UserServlet extends BaseServlet {
    UserService userService = new UserServiceImpl();
    /***
     * 注册功能
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void regist(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.校验验证码
        String user_check = request.getParameter("check");
        HttpSession session = request.getSession();
        String checkcode = (String) session.getAttribute("CHECKCODE_SERVER");
        session.removeAttribute("CHECKCODE_SERVER");
        //创建用于统计信息的ResultInfo对象
        ResultInfo info = new ResultInfo();
        //创建用于将对象转化为json的ObjectMapper对象
        ObjectMapper mapper = new ObjectMapper();
        if(checkcode == null || !checkcode.equalsIgnoreCase(user_check)){
            info.setFlag(false);
            info.setErrorMsg("验证码错误！");
            //调用抽象好的发送方法，发送
            writeValue(info,response);
            return;
        }
        //2.获取用户键入
        Map<String, String[]> map = request.getParameterMap();
        //3.封装用户键入为对象
        User user = new User();
        try{
            BeanUtils.populate(user,map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        //4.调用service传入对象
        boolean flag = userService.regist(user);
        //5.根据返回值输出相关信息
        if (flag){
            //注册成功
            info.setFlag(true);
        }else{
            info.setFlag(false);
            info.setErrorMsg("用户名重复!");
        }
        writeValue(info,response);


    }

    /***
     * 登录功能
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.获取验证码并校验
        String check = request.getParameter("check");
        HttpSession session = request.getSession();
        String checkcode = (String) session.getAttribute("CHECKCODE_SERVER");
        session.removeAttribute("CHECKCODE_SERVER");
        ResultInfo info = new ResultInfo();
        if (checkcode == null || !checkcode.equalsIgnoreCase(check)){
            info.setFlag(false);
            info.setErrorMsg("验证码错误!");
        }else {
            //验证码正确
            //2.获取map集合信息并封装对象
            Map<String, String[]> map = request.getParameterMap();
            User user = new User();
            try {
                BeanUtils.populate(user,map);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            //3.调用service方法查询用户是否存在
            User u = userService.login(user);
            //4.判断
            if (u == null){
                //登录名或密码错误
                info.setFlag(false);
                info.setErrorMsg("登录名或密码错误");
            }
            if (u !=null && !"Y".equals(u.getStatus())){
                //尚未激活
                info.setFlag(false);
                info.setErrorMsg("您尚未激活!");
            }
            if (u !=null && "Y".equals(u.getStatus())){
                //登陆成功
                info.setFlag(true);
                session.setAttribute("user",u);
            }
        }
        //5.响应提示信息
       writeValue(info,response);
    }

    /***
     * 查询单个对象
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void findOne(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.获取session对象
        HttpSession session = request.getSession();
        //2.从session中拿到user对象
        User user = (User) session.getAttribute("user");
        //3.封装user为json，并发送
        writeValue(user,response);
    }

    /***
     * 退出功能
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void exit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//1.销毁session
        request.getSession().invalidate();
        //2.重定向到首页
        response.sendRedirect(request.getContextPath()+"/login.html");
    }

    /***
     * 激活功能
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void active(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String code = request.getParameter("code");
        if(code != null){
            boolean falg = userService.active(code);
            String msg = null;
            if (falg){
                //用户存在
                msg = "激活成功请<a href='http://localhost/travel/login.html'>登陆</a>";
            }else {
                //用户不存在
                msg = "激活失败，请联系管理员！";
            }
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write(msg);
        }

    }
}
