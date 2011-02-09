package org.nigajuan.controller;

import org.nigajuan.domain.Account;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Controller
@RequestMapping(value="/test")
public class TestController {


    @RequestMapping(method=RequestMethod.GET , value = "/1")
    public ModelAndView getCreateForm(Model model) {

        Map map = new HashMap();

        Account account = new Account();
        account.setName("John Doe");


        map.put("foo" , "ca marche");
        map.put("account" , account);

        return new ModelAndView("test" , map);
    }

    @RequestMapping(method=RequestMethod.GET , value = "/2")
    public String processSubmit(@Valid  Account account, BindingResult results, ModelMap mmap) {
        return "test";
    }

}
