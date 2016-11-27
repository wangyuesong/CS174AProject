package controllers;

import beans.LoginBean;
import beans.view.*;
import entities.ChatGroupsEntity;
import entities.InvitationMessagesEntity;
import entities.UsersEntity;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import services.UsersService;

import javax.persistence.PersistenceException;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by yuesongwang on 11/16/16.
 */
@Controller
@RequestMapping("/main")
public class MainController {

    private static final Logger logger = Logger.getLogger(MainController.class);

    @Autowired
    UsersService usersService;

    @RequestMapping( method= {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView getMainPage(HttpServletRequest request){
        ModelAndView model = new ModelAndView("main");
        String email = (String)request.getSession().getAttribute("email");
        ModelAndView mav = new ModelAndView("main");
        if(request.getAttribute("searchedFriend") != null){
            mav.addObject("searchedFriend", (UsersEntity) request.getAttribute("searchedFriend"));
        }
        addObjects(email, mav);
        return mav;
    }


    @RequestMapping(value="/sendFriendRequest", method = RequestMethod.POST)
    public ModelAndView sendFriendRequest(String friendEmail,HttpServletRequest request){
        String email = (String)request.getSession().getAttribute("email");
        usersService.sendFriendRequest(email, friendEmail);
        ModelAndView mav = new ModelAndView("redirect:/main");
        return mav;
    }

    @RequestMapping(value="/acceptFriendRequest", method = RequestMethod.POST)
    public ModelAndView acceptFriendRequest(String friendEmail,HttpServletRequest request){
        String email = (String)request.getSession().getAttribute("email");
        usersService.acceptFriendRequest(email, friendEmail);
        ModelAndView mav = new ModelAndView("redirect:/main");
        return mav;
    }

    @RequestMapping(value="/acceptInvitation", method = RequestMethod.POST)
    public ModelAndView acceptInvitation(Integer id, Integer groupId, HttpServletRequest request){
        String email = (String)request.getSession().getAttribute("email");
        usersService.acceptInvitation(id, email, groupId);
        ModelAndView mav = new ModelAndView("redirect:/main");
        return mav;
    }

    @RequestMapping(value="/searchFriend", method = RequestMethod.POST)
    public ModelAndView searchFriend(String friendEmail, HttpServletRequest request){
        String email = (String)request.getSession().getAttribute("email");
        ModelAndView mav = new ModelAndView("forward:/main");
        mav.addObject("searchedFriend",usersService.searchUser(friendEmail));
        return mav;
    }



    private void addObjects(String email, ModelAndView mav) {
//        List<CircleMessage> myCircleMessages = usersService.getMyCircleMessages(email);
        List<UsersEntity> friendRequests = usersService.getFriendRequests(email);
        List<UsersEntity> friends = usersService.getFriends(email);
        List<ChatGroupsEntity> chatGroups = usersService.getChatGroups(email);
        List<InvitationMessage> invitationMessages = usersService.getInvitationMessages(email);
//        mav.addObject("myCircleMessages",myCircleMessages);
        mav.addObject("isManager", usersService.isManager(email));
        mav.addObject("friends",friends);
        mav.addObject("friendsRequest", friendRequests);
        mav.addObject("chatGroups",chatGroups);
        mav.addObject("invitationMessages",invitationMessages);
    }

    private boolean empty(String s){
        return s == null || s.trim().length() == 0;
    }
}
