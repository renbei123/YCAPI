package com.guanghe.onion.dao;


import com.guanghe.onion.entity.user;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.io.Serializable;

public interface UserJPA extends
        JpaRepository<user, Long> ,
        JpaSpecificationExecutor<user> ,
        Serializable {

}