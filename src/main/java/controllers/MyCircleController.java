package controllers;

import beans.view.CircleMessage;
import beans.view.InvitationMessage;
import entities.ChatGroupsEntity;
import entities.TopicWordsEntity;
import entities.UsersEntity;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import services.MessagesService;
import services.UsersService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * Created by yuesongwang on 11/21/16.
 */
@Controller
@RequestMapping("/myCircle")
public class MyCircleController {

    @Autowired
    UsersService usersService;

    @Autowired
    MessagesService messagesService;

    private static final Logger logger = Logger.getLogger(MainController.class);

    @RequestMapping(method= RequestMethod.GET)
    public ModelAndView getMyCircle(HttpServletRequest request){
        ModelAndView mav = new ModelAndView("myCircle");
        String email = (String)request.getSession().getAttribute("email");
        addObjects(email, mav);
        return mav;
    }

    @RequestMapping(value="/sendMessage", method= RequestMethod.POST)
    public ModelAndView postMyCircleMessage(HttpServletRequest request, String isPublic, String topicWords, String message, String[] friendsToShare){
        ModelAndView mav = new ModelAndView("redirect:/myCircle");
        String email = (String)request.getSession().getAttribute("email");
        messagesService.createMyCircleMessages(email, message, isPublic == null ? false: true , friendsToShare == null ? new String[0]: friendsToShare, topicWords);
        return mav;
    }


    @RequestMapping(value="/deleteMessage", method= RequestMethod.POST)
    public ModelAndView deleteMyCircleMessage(HttpServletRequest request, Integer messageId){
        ModelAndView mav = new ModelAndView("redirect:/myCircle");
        String email = (String)request.getSession().getAttribute("email");
        messagesService.deleteMyCircleMessage(messageId);
        return mav;
    }

    @RequestMapping(value="/changeTopicWords", method= RequestMethod.POST)
    public ModelAndView changeTopicWords(HttpServletRequest request, String topicWords){
        ModelAndView mav = new ModelAndView("redirect:/myCircle");
        String email = (String)request.getSession().getAttribute("email");
        usersService.changeTopicWords(email, topicWords);
        return mav;
    }

    private void addObjects(String email, ModelAndView mav) {
        List<CircleMessage> myCircleMessages = messagesService.getMyCircleMessages(email);
        List<UsersEntity> friends = usersService.getFriends(email);
        List<TopicWordsEntity> topicWords = usersService.getTopicWords(email);
        String topicWordString = "";
        for(TopicWordsEntity t: topicWords){
            topicWordString += (t.getName() + ",");
        }
        if(topicWordString.length() > 0){
            topicWordString = topicWordString.substring(0, topicWordString.length() - 1);
        }

        mav.addObject("myCircleMessages",myCircleMessages);
        mav.addObject("friends", friends);
        mav.addObject("topicWords",topicWordString);
    }
}
