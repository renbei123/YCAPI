package com.guanghe.onion.controller;

/**
 * Created by renjie on 2018/12/5.
 */

import com.guanghe.onion.dao.ApiJPA;
import com.guanghe.onion.dao.MySqlJPA;
import com.guanghe.onion.entity.Api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Random;

@Controller
@CacheConfig(cacheNames = "Api")
public class ApiController {


    @Autowired
    private ApiJPA apiJPA;

    //@Cacheable
    //@Cacheable(cacheNames="users", condition="#result.name.length < 32")
    @RequestMapping(value = "/apilist",method = RequestMethod.GET)
    public String list(Model model){
        List<Api> list=apiJPA.findAll();
        model.addAttribute("apilist",list);
        return "api_list";
    }




    @RequestMapping(value = "/apiselect",method = RequestMethod.GET)
    public String apiselect(Model model){
        List<Api> list=apiJPA.findAll();
        model.addAttribute("apilist",list);
        return "api_select";
    }

    @RequestMapping(value = "/apiselect2",method = RequestMethod.POST)
    public String apiselect2(Model model,@RequestParam(value="api_id", required=false) String apiId){
        System.out.println("apiId*******************:"+apiId);
        model.addAttribute("api_id",apiId);

        return "api_select2";
    }


    @RequestMapping(value = "/apisave",method = RequestMethod.POST)
    public String save(Api api){
         apiJPA.save(api);
         return "redirect:/apilist";
    }

    @RequestMapping(value = "/apieidt")
    public String edit(Model model,Long id)
    {
        Api api=apiJPA.findOne(id);

        if (api.getAssert_hasString()!=null)  api.setAssert_hasStringArray(api.getAssert_hasString().split(","));
        if(api.getAssert_jsonCheck()!=null)   api.setAssert_jsonCheckArray(api.getAssert_jsonCheck().split(","));
        model.addAttribute("api",api);
        return "api_edit";
    }

    @RequestMapping(value = "/apiadd")
    public String add()
    {
        return "api_add";
    }

    @RequestMapping(value = "/apidelete",method = RequestMethod.GET)
    public String delete(Long id)
    {
        apiJPA.delete(id);
        return "redirect:/apilist";
    }


//    /**
//     * 分页查询测试
//     * @param page 传入页码，从1开始
//     * @return
//     */

//    @RequestMapping(value = "/apicurpage")
//    public List<Api> curPage(int page)
//    {
//        Api user = new Api();
//        user.setSize(2);
//        user.setSord("desc");
//        user.setPage(page);
//
//        //获取排序对象
//        Sort.Direction sort_direction = Sort.Direction.ASC.toString().equalsIgnoreCase(user.getSord()) ? Sort.Direction.ASC : Sort.Direction.DESC;
//        //设置排序对象参数
//        Sort sort = new Sort(sort_direction, user.getSidx());
//        //创建分页对象
//        PageRequest pageRequest = new PageRequest(user.getPage() - 1,user.getSize(),sort);
//        //执行分页查询
//        return apiJPA.findAll(pageRequest).getContent();
//    }


    @RequestMapping(value = "/apicurpage2")
        public void testPageQuery() throws Exception {
        int page=1,size=10;
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Pageable pageable = new PageRequest(page, size, sort);
        apiJPA.findAll(pageable);

    }

}

