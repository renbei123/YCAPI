package com.guanghe.onion.dao;

import com.guanghe.onion.entity.Api;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ApiJPA extends BaseJPA<Api> {

    @Query(value = "SELECT id, name, path,method,headers,body,remarks from api "
            ,  nativeQuery = true)
    List<Object[]> selectmyapi();


    @Query(value = "SELECT  path from api where creater=:creater", nativeQuery = true)
    List<String> findPathByCreater(@Param("creater") String creater);



}