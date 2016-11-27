package services;

import beans.view.ChatGroups;
import beans.view.ChatGroupsMessage;
import entities.ChatGroupMessagesEntity;
import entities.ChatGroupsEntity;
import entities.MessagesEntity;
import entities.UsersEntity;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.PersistenceException;
import java.util.Date;
import java.util.List;

/**
 * Created by yuesongwang on 11/19/16.
 */
@Service
public class ChatGroupsService extends BaseService{

    private static final Logger logger = Logger.getLogger(ChatGroupsService.class);
    @Autowired
    private UsersEntity usersEntity;

    @Autowired
    private ChatGroupsEntity chatGroupsEntity;

    public ChatGroupsEntity getChatGroup(int groupId){
        return em.find(ChatGroupsEntity.class, groupId);
    }

    public List<MessagesEntity> getChatGroupsMessages(int groupId){
        int duration = em.find(ChatGroupsEntity.class, groupId).getDuration();
        Date neededDate = TimeService.getKDaysBeforeSystemTime(duration);
        return em.createQuery("select m from ChatGroupMessagesEntity cgm JOIN MessagesEntity m on (cgm.id = m.id) " +
                "where m.deleted = false and cgm.groupId = :groupId and m.createdAt > :date order by m.createdAt asc").
                setParameter("groupId",groupId)
                .setParameter("date",neededDate).
                getResultList();
    }

    public List<UsersEntity> getParticipants(Integer groupId) {
        return em.createQuery
                ("select u from UsersEntity u " +
                "JOIN MemberOfEntity m on (m.email = u.email) " +
                "JOIN ChatGroupsEntity c on(m.groupId = c.id)" +
                        "where c.id = :groupId").setParameter("groupId",groupId).getResultList();
    }


    public Boolean isOwner(Integer groupId, String email) {
        return em.find(ChatGroupsEntity.class,groupId).getOwner().equals(email);
    }

    public void updateGroupSettings(Integer groupId, String groupName, Integer duration) {
        try {
            begin();
            ChatGroupsEntity c = em.find(ChatGroupsEntity.class, groupId);
            c.setDuration(duration);
            c.setName(groupName);
            em.persist(c);

            commit();
        }catch (PersistenceException e){
            logger.error("Error update group settings" ,e);
            em.getTransaction().rollback();
        }
    }
}

