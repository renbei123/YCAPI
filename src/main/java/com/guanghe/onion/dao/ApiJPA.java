package com.guanghe.onion.dao;

import com.guanghe.onion.entity.Api;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.io.Serializable;
import java.util.List;

public interface ApiJPA extends BaseJPA<Api> {

    @Query(value = "SELECT id, name, path,method,headers,body,remarks from api "
            ,  nativeQuery = true)
    List<Object[]> selectmyapi();


}