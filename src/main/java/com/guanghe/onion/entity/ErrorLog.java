package com.guanghe.onion.entity;


import com.guanghe.onion.base.BaseEntity;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Getter
@Setter
@ToString
@Table(name = "ErrorLog")
//Entity中不映射成列的字段得加@Transient 注解，不加注解也会映射成列
public class ErrorLog extends BaseEntity
{

    @Id
    @Column(name = "id",unique = true,nullable = false)
    @GeneratedValue
    private Long id;


    @Column(name = "planId")
    private Long planId;

    @Column(name = "apiId")
    private Long apiId;

    @Column(name = "apiName")
    private String apiName;

    @Column(name = "method", nullable = false, length = 10)
    private String method;

    @Column(name = "url",  nullable = false)
    // http://host:8080/path
    private String url;


    @Column(name = "execTime")
    //执行时间
    private Date execTime;

    @Column(name = "req_header", length = 1000)
    private String req_header;

    @Column(name = "req_body", length =10000)
    private String req_body;


    @Column(name = "res_body", length =20000)
    private String res_body;

    @Column(name = "res_code")
    private Integer res_code;

    @Column(name = "res_size")
    private String res_size;

    @Column(name = "res_header")
    private String res_header;

    @Column(name = "assert_result")
    private String assert_result;
}