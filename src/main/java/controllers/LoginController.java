package controllers;

import beans.LoginBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import services.UsersService;

import javax.servlet.http.HttpServletRequest;
import java.sql.*;
/**
 * Created by yuesongwang on 11/9/16.
 */
@Controller
@RequestMapping("/login")
public class LoginController {

    @Autowired
    UsersService usersService;

    @RequestMapping( method= RequestMethod.GET)
    public ModelAndView getLogin(){
        ModelAndView model = new ModelAndView("login");
        LoginBean loginBean = new LoginBean();
        model.addObject("loginBean",loginBean);
        return model;
    }

    @RequestMapping( method= RequestMethod.POST)
    public ModelAndView doLogin(
            HttpServletRequest request,
            @ModelAttribute("loginBean")LoginBean loginBean, BindingResult bindingResult){
        if(!usersService.isValidUser(loginBean.getEmail(),loginBean.getPassword())){
            bindingResult.rejectValue("email","Cannot login");
            ModelAndView model = new ModelAndView("login");
            model.addObject("loginBean",loginBean);
            return model;
        }
        else{
            request.getSession().setAttribute("email",loginBean.getEmail());
            return new ModelAndView("redirect:/main");
        }

    }


}
