package com.guanghe.onion.controller;

/**
 * Created by renjie on 2018/12/5.
 */

import com.guanghe.onion.dao.ApiJPA;
import com.guanghe.onion.entity.Api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Random;

@Controller
@CacheConfig(cacheNames = "Api")
@RequestMapping(value = "/api")
public class ApiController {


    @Autowired
    private ApiJPA apiJPA;

    @Cacheable
    //@Cacheable(cacheNames="users", condition="#result.name.length < 32")
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public String list(Model model){
        List list=apiJPA.findAll();
        model.addAttribute("apilist",list);
        return "api_list";
    }


    @RequestMapping(value = "/save",method = RequestMethod.GET)
    public Api save(Api api){
        return apiJPA.save(api);
    }

    @RequestMapping(value = "/add")
    public String add()
    {
        Api Api = new Api();

        Api.setName("测试"+new Random().toString());
        Api.setHeaders("tou");
        Api.setMethod("post");
        Api.setPath("/ss/"+new Random().toString());
        apiJPA.save(Api);
        return "信息添加成功";
    }

    @RequestMapping(value = "/delete",method = RequestMethod.GET)
    public List<Api> delete(Long id)
    {
        apiJPA.delete(id);
        return apiJPA.findAll();
    }


    /**
     * 分页查询测试
     * @param page 传入页码，从1开始
     * @return
     */

    @RequestMapping(value = "/curpage")
    public List<Api> curPage(int page)
    {
        Api user = new Api();
        user.setSize(2);
        user.setSord("desc");
        user.setPage(page);

        //获取排序对象
        Sort.Direction sort_direction = Sort.Direction.ASC.toString().equalsIgnoreCase(user.getSord()) ? Sort.Direction.ASC : Sort.Direction.DESC;
        //设置排序对象参数
        Sort sort = new Sort(sort_direction, user.getSidx());
        //创建分页对象
        PageRequest pageRequest = new PageRequest(user.getPage() - 1,user.getSize(),sort);
        //执行分页查询
        return apiJPA.findAll(pageRequest).getContent();
    }


    @RequestMapping(value = "/curpage2")
        public void testPageQuery() throws Exception {
        int page=1,size=10;
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Pageable pageable = new PageRequest(page, size, sort);
        apiJPA.findAll(pageable);

    }

}

