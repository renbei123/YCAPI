package com.guanghe.onion.dao;

import com.guanghe.onion.entity.Api;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ApiJPA extends JpaRepository<Api, Long> {
}