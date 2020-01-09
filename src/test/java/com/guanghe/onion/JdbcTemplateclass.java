package com.guanghe.onion;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest
public class JdbcTemplateclass {

//    @Autowired
//    @Qualifier("primaryJdbcTemplate")
//    protected JdbcTemplate jdbcTemplate1;
//

    @Autowired
    @Qualifier("secondaryJdbcTemplate")
    protected JdbcTemplate jdbcTemplate2;

    @Before
    public void setUp() {
//        jdbcTemplate1.update("DELETE  FROM  USER ");
//        jdbcTemplate2.update("DELETE  FROM  USER ");
    }

    @Test
    public void test() throws Exception {
        String sql = "select  '||id||' from ability limit 1";
        List<String> exesqlresult = jdbcTemplate2.queryForList(sql, String.class);
        System.out.println("******* exesqlresult: " + exesqlresult);

    }


}
