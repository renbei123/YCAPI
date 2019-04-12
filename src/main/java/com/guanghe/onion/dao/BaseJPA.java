package com.guanghe.onion.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;
@NoRepositoryBean
public interface BaseJPA<T> extends JpaRepository<T, Long>, JpaSpecificationExecutor<T>, Serializable {

}