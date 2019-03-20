package com.guanghe.onion.dao;

import com.guanghe.onion.entity.Api;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MySqlJPA extends JpaRepository<Api, Long> {

    @Query(value = "SELECT id, name, path,method,headers,body,remarks from api "
            ,  nativeQuery = true)
    List<Object[]> selectmyapi2();

}