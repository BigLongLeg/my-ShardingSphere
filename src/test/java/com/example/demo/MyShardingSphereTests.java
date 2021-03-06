package com.example.demo;

import com.example.demo.pojo.Order;
import org.apache.shardingsphere.core.strategy.keygen.SnowflakeShardingKeyGenerator;
import org.apache.shardingsphere.spi.keygen.ShardingKeyGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.List;
import java.util.Map;
import java.util.Random;
@ActiveProfiles("stand")
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
        ShardingKeyGenerator keyGenerator = new SnowflakeShardingKeyGenerator();

        Random random = new Random();
        for (int i = 0; i < 10000; i++) {
            Long aLong = (Long) keyGenerator.generateKey();
            jdbcTemplate.update("insert  into t_order (user_id,order_id) values (?,?)",
                    aLong ,Integer.valueOf(random.nextInt(10000)).longValue() );
        }
    }

    @Test
    void selectAll(){
        List<Map<String, Object>> maps = jdbcTemplate.queryForList("select user_id,order_id from t_order ");
        maps.forEach(e ->{
            System.out.println(e);
        });

    }
    @Test
    void generatorKey(){
        ShardingKeyGenerator generator = new SnowflakeShardingKeyGenerator();
        Comparable<?> comparable = generator.generateKey();
        System.out.println(comparable);
    }
}
