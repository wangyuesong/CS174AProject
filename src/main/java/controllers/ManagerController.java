package controllers;

import beans.UserCreateBean;
import beans.UserSearchBean;
import com.sun.tools.corba.se.idl.constExpr.Times;
import entities.UsersEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import services.UsersService;

import javax.servlet.http.HttpServletRequest;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;

/**
 * Created by yuesongwang on 11/24/16.
 */
@Controller
@RequestMapping("/manage")
public class ManagerController {

    @Autowired
    UsersService usersService;


    @RequestMapping( method= {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView getConsolePage(HttpServletRequest request){
        ModelAndView mav = new ModelAndView("manage");
        String email = (String)request.getSession().getAttribute("email");
        UserSearchBean ub = request.getAttribute("userSearchBean") == null ? new UserSearchBean(): (UserSearchBean)request.getAttribute("userSearchBean");
        if(request.getAttribute("resultUsers") != null){
            mav.addObject("resultUsers",(List<UsersEntity>)request.getAttribute("resultUsers"));
        }
        addObjects(email, mav, ub);
        return mav;
    }

    @RequestMapping(value="/searchUser", method = RequestMethod.POST)
    public ModelAndView searchUser(HttpServletRequest request, @ModelAttribute("userSearchBean") UserSearchBean ub){
        String name = ub.getEmail();
        ModelAndView mav = new ModelAndView("forward:/manage");
        String[] topicWords = (empty(ub.getTopicWords())) ? new String[0]: ub.getTopicWords().split(",");
        int numOfMessageWithin7Days = (empty(ub.getNumOfMessagesWithin7Days())? 200: Integer.parseInt(ub.getNumOfMessagesWithin7Days()));
        Timestamp timestampOfMostRecentMessage = empty(ub.getTimestampOfMostRecentMessage()) ? Timestamp.valueOf("2000-01-01 00:00:00") : Timestamp.valueOf(ub.getTimestampOfMostRecentMessage());
        List<UsersEntity> users = usersService.findAllUsersAndRelatedInfo(name, topicWords, numOfMessageWithin7Days, timestampOfMostRecentMessage);
        mav.addObject("resultUsers", users);
        return mav;
    }

    @RequestMapping(value="/createUser", method = RequestMethod.POST)
    public ModelAndView createUser(HttpServletRequest request, @ModelAttribute("userCreateBean") UserCreateBean ub){
        ModelAndView mav = new ModelAndView("redirect:/manage");
        usersService.createUser(ub);
        return mav;
    }

    private void addObjects(String email, ModelAndView mav, UserSearchBean ub) {
        mav.addObject("userSearchBean", ub);
        mav.addObject("userCreateBean", new UserCreateBean());
    }


    private boolean empty(String s){
        return s == null || s.trim().length() == 0;
    }
}

