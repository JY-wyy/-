package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.util.JDBCUtils;
import org.junit.Test;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 祭音丶 on 2020/2/24.
 */
public class RouteDaoImpl implements RouteDao {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());
    @Override
    public int findTotalCount(int cid, String rname) {
       /* String sql = "select count(*) from tab_route where cid = ?";*/
        //初始化sql语句
        String sql = "select count(*) from tab_route where 1=1 ";
        StringBuilder sb = new StringBuilder(sql);
        List list = new ArrayList();//条件们
        //判断是否传入有效的cid
        if(cid != 0){
            //拼接sql
            sb.append(" and cid = ? ");
            list.add(cid);
        }
        //判断是否传入有效的rname
        if("null".equals(rname)){
            rname = null;
        }
        if(rname != null && rname.length() > 0){
            sb.append(" and rname like ? ");
            list.add("%"+rname+"%");
        }
        //将拼接好的StringBuilder类型转化为字符串,并赋值给sql
        sql = sb.toString();


        return template.queryForObject(sql,Integer.class,list.toArray());
    }

    @Override
    public List<Route> findPageCount(int cid, int start, int pageSize, String rname) {
//        String sql = "select * from tab_route where cid = ? and rname like ?  limit ? , ?";
        String sql = "select * from tab_route where 1=1 ";
        StringBuilder sb = new StringBuilder(sql);
        List list = new ArrayList();//条件们
        //判断是否传入有效的cid
        if(cid != 0){
            //拼接sql
            sb.append(" and cid = ? ");
            list.add(cid);
        }
        //判断是否传入有效的rname
        if("null".equals(rname)){
            rname = null;
        }
        if(rname != null && rname.length()>0){
            sb.append(" and rname like ? ");
            list.add("%"+rname+"%");
        }
        sb.append(" limit ? , ?");
        list.add(start);
        list.add(pageSize);

        //将拼接好的StringBuilder类型转化为字符串,并赋值给sql
        sql = sb.toString();

        return template.query(sql,new BeanPropertyRowMapper<Route>(Route.class),list.toArray());
    }

    @Override
    public List<Route> findByRid(List rid ,int start,int pageSize) {
        String sql = "select * from tab_route where 1=1 ";
        StringBuilder sb = new StringBuilder(sql);
        sb.append("and rid in (");
        for (int i = 0; i <rid.size() ; i++) {
            sb.append("?,");
        }
        sb.deleteCharAt(sb.length()-1);
        sb.append(")");
        sb.append(" limit ? , ?");
        sql = sb.toString();
        rid.add(start);
        rid.add(pageSize);
        return template.query(sql,new BeanPropertyRowMapper<Route>(Route.class),rid.toArray());
    }

    @Override
    public Route findOne(int rid) {
        String sql ="select * from tab_route where rid = ?";
        return template.queryForObject(sql,new BeanPropertyRowMapper<Route>(Route.class),rid);
    }

    @Override
    public List<Route> findRouteByFavorite(String rname, int priceStart, int priceEnd, int start, int pageSize) {
        //select *from student where age between 12 and 18; 范围查询
        //select *from tab_route where 1=1 and (price between ? and ?) and rname like ? order by count desc limit ? , ? ;
        String sql = "select * from tab_route where 1=1 and (price between ? and ?) ";
        StringBuilder sb = new StringBuilder(sql);
        List list = new ArrayList();
        list.add(priceStart);
        list.add(priceEnd);
        //判断是否传入有效的rname
        if("null".equals(rname)){
            rname = null;
        }
        if(rname != null && rname.length()>0){
            sb.append(" and rname like ? ");
            list.add("%"+rname+"%");
        }
        sb.append("order by count desc limit ? , ? ");
        list.add(start);
        list.add(pageSize);
        sql = sb.toString();
        List<Route> routes = template.query(sql, new BeanPropertyRowMapper<Route>(Route.class), list.toArray());
        return routes;
    }

    @Override
    public int findTotalCountByRname(String rname , int priceStart,int priceEnd) {
        String sql = "select count(*) from tab_route where 1=1 and (price between ? and ?)";
        StringBuilder sb = new StringBuilder(sql);
        List list = new ArrayList();
        list.add(priceStart);
        list.add(priceEnd);
        //判断是否传入有效的rname
        if("null".equals(rname)){
            rname = null;
        }
        if(rname != null && rname.length()>0){
            sb.append(" and rname like ? ");
            list.add("%"+rname+"%");
        }
        sql = sb.toString();
        return template.queryForObject(sql,Integer.class,list.toArray());
    }


    @Test
    public void test() {
        //select *from student where age between 12 and 18; 范围查询
        //select *from tab_route where 1=1 and (price between ? and ?) and rname like ? order by count desc limit ? , ? ;
        String rname = "西安";
        int priceStart = 0;
        int priceEnd = 9999999;
        int start = 1;
        int pageSize = 5;
        String sql = "select * from tab_route where 1=1 and (price between ? and ?) ";
        StringBuilder sb = new StringBuilder(sql);
        List list = new ArrayList();
        list.add(priceStart);
        list.add(priceEnd);
        if(rname != null && rname.length()>0){
            sb.append(" and rname like ? ");
            list.add("%"+rname+"%");
        }
        sb.append("order by count desc limit ? , ? ");
        list.add(start);
        list.add(pageSize);
        sql = sb.toString();
        List<Route> routes = template.query(sql, new BeanPropertyRowMapper<Route>(Route.class), list.toArray());
        System.out.println(routes.size());
    }
}
