package com.liuwen.controller;

import com.liuwen.entity.Menu;
import com.liuwen.entity.MenuVO;
import com.liuwen.feign.MenuFeign;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;


//@RestController
@Controller
@RequestMapping("/menu")
public class MenuHandler {

    @Resource
    private MenuFeign menuFeign;


    //为了适应前端Layui框架，需要返回符合传递参数格式的数据。  封装成MenuVO
    @GetMapping("/findAll")
    @ResponseBody
    public MenuVO findAll(@RequestParam("page") int page, @RequestParam("limit") int limit){
       //分页
        int index = (page-1)*limit;
        return menuFeign.findAll(index,limit);
    }

    //    后台映射转换（thymeleaf需要映射）  index页面的异步刷新
    //    http://localhost:8030/menu/redirect/index
    @GetMapping("/redirect/{index}")
    public String redirect(@PathVariable("index") String index){
        return index;
    }


    //页面删除请求→（8030）MenuHandler→Feign→（8020）MenuHandler→MenuRepository→MenuRepository.xml→数据库
    //删除成功之后，页面重定向，刷新页面
    @GetMapping("/deleteById/{id}")
    public String deleteById(@PathVariable("id") long id){
        menuFeign.deleteById(id);
        return "redirect:/menu/redirect/menu_manage";   /* 刷新到菜单管理页面*/
    }

    @GetMapping("/findTypes")
    public ModelAndView findTypes(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("menu_add");
        modelAndView.addObject("list",menuFeign.findTypes());
        return modelAndView;  /*返回menu_add页面*/
    }

    @PostMapping("/save")
    public String save(Menu menu){
        menuFeign.save(menu);
        return "redirect:/menu/redirect/index";   /* 刷新页面*/
    }

    @GetMapping("/findById/{id}")
    public ModelAndView findById(@PathVariable("id") long id){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("menu_update");
        modelAndView.addObject("menu",menuFeign.findById(id));
        modelAndView.addObject("list",menuFeign.findTypes());
        return modelAndView;
    }

    @PostMapping("/update")
    public String update(Menu menu){
        menuFeign.update(menu);
        return "redirect:/menu/redirect/menu_manage";   /* 刷新页面*/
    }

}
