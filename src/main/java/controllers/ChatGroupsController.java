package controllers;

import beans.view.ChatGroups;
import entities.MessagesEntity;
import entities.UsersEntity;
import org.apache.log4j.Logger;
import org.eclipse.persistence.exceptions.DatabaseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import services.ChatGroupsService;
import services.MessagesService;
import services.UsersService;

import javax.persistence.PersistenceException;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuesongwang on 11/19/16.
 */
@Controller
@RequestMapping("/chatGroups/{groupId}")
public class ChatGroupsController {

    private static final Logger logger = Logger.getLogger(ChatGroupsController.class);

    @Autowired
    ChatGroupsService groupsService;

    @Autowired
    UsersService usersService;

    @Autowired
    MessagesService messagesService;

    @RequestMapping(method= {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView showChatGroup(@PathVariable Integer groupId, HttpServletRequest request){
        //Messages
        //Participants
        //My friends list
        String email = (String)request.getSession().getAttribute("email");
        ModelAndView mav = new ModelAndView("chatGroup");
        addModels(groupId, email, mav);
        return mav;
    }



    @RequestMapping(value = "/sendMessage",method = RequestMethod.POST)
    public ModelAndView sendMessage(@PathVariable Integer groupId, HttpServletRequest request, String message){
        String email = (String)request.getSession().getAttribute("email");
        messagesService.createChatGroupMessage(groupId, message, email);
        ModelAndView mav = new ModelAndView("redirect:/chatGroups/" + groupId);
        return mav;
    }

    @RequestMapping(value = "/deleteMessage",method = RequestMethod.POST)
    public ModelAndView deleteMessage(@PathVariable Integer groupId, HttpServletRequest request, Integer messageId){
        String email = (String)request.getSession().getAttribute("email");
        messagesService.deleteChatGroupMessage(groupId, messageId);
        ModelAndView mav = new ModelAndView("redirect:/chatGroups/" + groupId);
        return mav;
    }


    @RequestMapping(value="/inviteFriend", method = RequestMethod.POST)
    public ModelAndView inviteFriend(@PathVariable Integer groupId, HttpServletRequest request, String friendEmail){
        String email = (String)request.getSession().getAttribute("email");
        ModelAndView mav = new ModelAndView("redirect:/chatGroups/" + groupId);
        messagesService.createInvitationMessage(email, friendEmail, groupId);
        return mav;
    }

    @RequestMapping(value="/updateGroupSetting", method = RequestMethod.POST)
    public ModelAndView updateGroupSetting(@PathVariable Integer groupId, HttpServletRequest request, String groupName, Integer duration){
        String email = (String)request.getSession().getAttribute("email");
        ModelAndView mav = new ModelAndView("redirect:/chatGroups/" + groupId);
        groupsService.updateGroupSettings(groupId, groupName, duration);
        return mav;
    }



    private void addModels(@PathVariable Integer groupId, String email, ModelAndView mav) {
        List<MessagesEntity> chatGroupMessages = groupsService.getChatGroupsMessages(groupId);
        List<UsersEntity> participants = groupsService.getParticipants(groupId);
        List<UsersEntity> myFriendsList = usersService.getFriends(email);

        mav.addObject("chatGroup",groupsService.getChatGroup(groupId));
        mav.addObject("chatGroupMessages", chatGroupMessages);
        mav.addObject("participants", participants);
        List<UsersEntity> invitableFriendsList = new ArrayList<UsersEntity>();
        for(UsersEntity e : myFriendsList){
            if(!participants.contains(e)){
                invitableFriendsList.add(e);
            }
        }
        mav.addObject("inviteFriends",invitableFriendsList);
        mav.addObject("isOwner", groupsService.isOwner(groupId, email));
    }




}
