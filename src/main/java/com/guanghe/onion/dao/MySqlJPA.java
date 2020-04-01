package com.guanghe.onion.dao;

import com.guanghe.onion.entity.Api;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MySqlJPA extends JpaRepository<Api, Long> {

    @Query(value = "SELECT id, name, path,method,headers,body,remarks from api "
            , nativeQuery = true)
    List<Object[]> selectmyapi2();

}


//@Query配合@Modifying
//从名字上可以看到我们的@Query注解好像只是用来查询的，但是如果配合@Modifying注解一共使用，则可以完成数据的删除、添加、更新操作。下面我们来测试下自定义SQL完成删除数据的操作，我根据名字、密码字段共同删除一个数据，接口代码如下图21所示：

