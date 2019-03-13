package com.guanghe.onion.entity;


import com.guanghe.onion.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;


import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
@Table(name = "api")
//Entity中不映射成列的字段得加@Transient 注解，不加注解也会映射成列
public class Api extends BaseEntity
{

    @Id
    @Column(name = "id",unique = true,nullable = false)
    @GeneratedValue
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "path",  nullable = false)
    private String path;

    @Column(name = "method", nullable = false, length = 10)
    private String method;

    @Column(name = "headers", length = 500)
    private String headers;

    @Column(name = "body", length =10000)
    private String body;

    @Column(name = "remarks")
    private String remarks;

    @Column(name = "label")
    private String label;

/*    // response body
    @Column(name = "re_body")
    private String re_body;

    @Column(name = "rs_Cookies")
    private String rs_Cookies;

    @Column(name = "rs_headers")
    private String rs_headers;*/

    // assert
    @Column(name = "assert_Code")
    private String assert_Code;

    @Column(name = "assert_hasString")
    private String assert_hasString;

    @Column(name = "assert_jsonCheck")
    private String assert_jsonCheck;
}