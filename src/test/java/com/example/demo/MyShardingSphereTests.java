package com.example.demo;

import com.example.demo.pojo.Order;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.List;
import java.util.Map;
import java.util.Random;

@SpringBootTest(classes = MyShardingSphereApplication.class)
class MyShardingSphereTests {
    @Resource
    private DataSource dataSource;
    @Resource
    private JdbcTemplate jdbcTemplate;


    @Test
    void getDataSource() {
        System.out.println(dataSource);
    }

    @Test
    void insertData(){
        Random random = new Random();
        for (int i = 0; i < 10000; i++) {
            jdbcTemplate.update("insert ignore into t_order (user_id,order_id) values (?,?)",i,random.nextInt(10000));
        }
    }

    @Test
    void selectAll(){
        List<Map<String, Object>> maps = jdbcTemplate.queryForList("select user_id,order_id from t_order ");
        maps.forEach(e ->{
            System.out.println(e);
        });

    }
}
