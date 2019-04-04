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
    @GeneratedValue(strategy = GenerationType.TABLE )
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


    @Column(name = "startTime")
    //执行开始时间
    private String startTime;

    @Column(name = "elapsedTime")
    //执行接口的经历时间
    private long elapsedTime;

    @Column(name = "req_header", length = 2000, columnDefinition ="" )
    private String req_header;

    @Column(name = "req_body", length =10000)
    private String req_body;


    @Column(name = "res_body", length =30000)
    private String res_body;

    @Column(name = "res_code")
    private Integer res_code;

    @Column(name = "res_size")
    private Integer res_size;

    @Column(name = "res_header",length = 2000)
    private String res_header;

    @Column(name = "assert_result",length = 1000)
    private String assert_result;
}