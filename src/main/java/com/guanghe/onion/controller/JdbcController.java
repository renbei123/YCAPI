package com.guanghe.onion.controller;

/**
 * Created by renjie on 2018/12/5.
 */

import com.guanghe.onion.dao.ApiJPA;
import com.guanghe.onion.entity.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class JdbcController {


    @Autowired
    @Qualifier("secondaryJdbcTemplate")
    public JdbcTemplate jdbcTemplate;

    @RequestMapping(value = "/jdbc", method = RequestMethod.GET)
    public List<Map<String, Object>> queryUsers() {
        List<Map<String, Object>> list = jdbcTemplate.queryForList("select * from chapter ");
        return list;
    }

}

