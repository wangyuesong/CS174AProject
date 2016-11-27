package controllers;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import services.ChatGroupsService;
import services.MessagesService;
import services.UsersService;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by yuesongwang on 11/22/16.
 */
@Controller
@RequestMapping("/privateMessage/{userEmail}")
public class PrivateMessageController {

    private static final Logger logger = Logger.getLogger(PrivateMessageController.class);

    @Autowired
    UsersService usersService;

    @Autowired
    MessagesService messagesService;

    @RequestMapping(method= RequestMethod.GET)
    public ModelAndView getPrivateMessages(HttpServletRequest request, @PathVariable String userEmail){
        ModelAndView mav = new ModelAndView("privateMessage");
        String email = (String)request.getSession().getAttribute("email");
        addModels(email, userEmail, mav);
        return mav;
    }

    @RequestMapping(value = "/sendMessage",method = RequestMethod.POST)
    public ModelAndView sendMessage(@PathVariable String userEmail, HttpServletRequest request, String message){
        String email = (String)request.getSession().getAttribute("email");
        messagesService.createPrivateMessage(email, userEmail, message);
        ModelAndView mav = new ModelAndView("redirect:/privateMessage/" + userEmail);
        return mav;
    }

    @RequestMapping(value = "/deleteMessage",method = RequestMethod.POST)
    public ModelAndView deleteMessage(@PathVariable String userEmail, Integer messageId, HttpServletRequest request){
        String email = (String)request.getSession().getAttribute("email");
        messagesService.deletePrivateMessage(email, messageId);
        ModelAndView mav = new ModelAndView("redirect:/privateMessage/" + userEmail);
        return mav;
    }

    private void addModels(String email, String friendEmail, ModelAndView mav){
        mav.addObject("privateMessages", messagesService.getPrivateMessages(email, friendEmail));
        mav.addObject("friendEmail", friendEmail);
    }

}
