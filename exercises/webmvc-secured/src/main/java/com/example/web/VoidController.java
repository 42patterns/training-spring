package com.example.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@Controller
public class VoidController {

    @RequestMapping(value = "/index.html", method = {RequestMethod.GET})
    public ModelAndView index() {
        ModelAndView model = new ModelAndView("index");
        model.addObject("time", System.currentTimeMillis());
        return model;
    }

    @RequestMapping(value = "/secured.html", method = {RequestMethod.GET})
    public ModelAndView secured(Principal principal) {
        System.out.println("principal = " + principal);

        ModelAndView model = new ModelAndView("secured");
        model.addObject("time", System.currentTimeMillis());
        return model;
    }

}
