package com.guanghe.onion.entity;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Data
@Table(name = "CrsMonitorLog")
//Entity中不映射成列的字段得加@Transient 注解，不加注解也会映射成列
public class CrsMonitorLog
{

    @Id
    @Column(name = "id",unique = true,nullable = false)
    @GeneratedValue
    private Long id;


    @Column(name = "api_id")
    private Long api_id;

    @Column(name = "host1")
    private String host1;

    @Column(name = "host2")
    private String host2;


    @Column(name = "differ", length = 10000)
    private String differ;



    @Column(name = "status")
    private Boolean status;   // true: ok   false：wrong
   

}