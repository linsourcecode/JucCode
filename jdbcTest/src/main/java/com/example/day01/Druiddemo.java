package com.example.day01;


import com.alibaba.druid.pool.DruidDataSourceFactory;
import org.junit.Test;

import javax.sql.DataSource;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Druid连接池的使用
 */
public class Druiddemo {



    public static void main(String[] args) throws Exception {

        for(int i=0;i<100;i++){
            new Thread(()->{
                Druiddemo druiddemo=new Druiddemo();
                try {
                    List<String> array = new ArrayList<>();
                    array.add("北京");
                    array.add("南京");
                    array.add("张家口");
                    array.add("承德");
                    array.add("沧州");
                    array.add("廊坊");
                    array.add("衡水");
                    for(int j=0;j<7;j++){
                        druiddemo.testJdbc(array.get(j));
                    }


                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }).start();
        }


    }

    public void testJdbc(String CityName) throws Exception {
        Connection conn=null;
        PreparedStatement prep=null;
        ResultSet rs=null;

        //从druid.properties中读取数据
        Properties properties=new Properties();
        properties.load(new FileInputStream("D:\\Java\\juc\\JucCode\\jdbcTest\\src\\main\\resources\\druid.properties"));
        DataSource dataSource= DruidDataSourceFactory.createDataSource(properties);
        conn=dataSource.getConnection();
        prep=conn.prepareStatement(String.format("select * from city where CityName= '%s' ",CityName));
        rs=prep.executeQuery();
        while (rs.next()){
            System.out.println(rs.getString("cityName"));
            // System.out.println(rs.getString("flag"));
        }
        JDBCUtils.release(rs,prep,conn);
    }
}

