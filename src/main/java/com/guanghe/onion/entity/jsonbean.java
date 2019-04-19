package com.guanghe.onion.entity;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Data
public class jsonbean {
    @Transient
    private int draw;
    @Transient
    private int recordsTotal;

    @Transient
    private int recordsFiltered;

    @Transient
    private List data;

}