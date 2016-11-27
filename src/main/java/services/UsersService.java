package services;

import beans.UserCreateBean;
import beans.view.*;
import entities.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.FieldResult;
import javax.persistence.PersistenceException;
import java.sql.Timestamp;
import java.util.*;

/**
 * Created by yuesongwang on 11/14/16.
 */
@Service
public class UsersService extends  BaseService{

    private static final Logger logger = Logger.getLogger(UsersService.class);
    @Autowired
    private UsersEntity usersEntity;

    public UsersEntity find(Object primaryKey) {
        return em.find(UsersEntity.class, primaryKey);
    }

    public boolean isValidUser(String userName, String password) {
        if(userName == null  || password == null ){
            return false;
        }
        UsersEntity user = em.find(UsersEntity.class, userName);
        if(user == null || !user.getPassword().equals(password)){
            return false;
        }
        return true;
    }

    public boolean isManager(String email){
        return em.find(UsersEntity.class, email).getIsManager();
    }


    public void acceptFriendRequest(String email, String friendEmail){
        try {
            begin();
            int updateCount = em.createQuery("Update FriendWithEntity e SET e.pending = false where " +
                    "(e.firstEmail = :email and e.secondEmail = :friendEmail) or (e.secondEmail = :email and e.firstEmail = :friendEmail)")
                    .setParameter("email", email)
                    .setParameter("friendEmail", friendEmail)
                    .executeUpdate();
            //Create bi-directional friendship
            if(updateCount != 2){
                FriendWithEntityPK pkForward= new FriendWithEntityPK();
                pkForward.setFirstEmail(email);
                pkForward.setSecondEmail(friendEmail);
                FriendWithEntity forward = em.find(FriendWithEntity.class, pkForward);
                createFriendShip(friendEmail, email, forward);

                FriendWithEntityPK pkBcakward= new FriendWithEntityPK();
                pkBcakward.setFirstEmail(friendEmail);
                pkBcakward.setSecondEmail(email);
                FriendWithEntity backward = em.find(FriendWithEntity.class, pkBcakward);
                createFriendShip(email, friendEmail, backward);
            }
            commit();
        }catch (PersistenceException e){
            logger.error("Error while accepting friend request",e);
        }
    }

    private void createFriendShip(String email, String friendEmail, FriendWithEntity backward) {
        if(backward == null){
            FriendWithEntity f = new FriendWithEntity();
            f.setFirstEmail(friendEmail); f.setSecondEmail(email);
            f.setPending(false);
            em.persist(f);
        }
    }


    public  List<ChatGroupsEntity> getChatGroups(String email) {
        List<ChatGroupsEntity> result = em.createQuery
                ("select c from ChatGroupsEntity c JOIN MemberOfEntity m on (m.email = :email) and m.groupId = c.id")
                .setParameter("email",email).getResultList();
        return result;
    }

    public  List<UsersEntity> getFriendRequests(String email) {
        List<UsersEntity> result = em.createQuery
                ("select u2 from UsersEntity u1 " +
                        "JOIN FriendWithEntity f ON (u1.email = f.firstEmail) " +
                        "JOIN UsersEntity u2 ON(u2.email = f.secondEmail) where u1.email = :email and f.pending = true")
                .setParameter("email",email).getResultList();
        return result;
    }

    public  List<PrivateMessage> getPrivateMessages(String email) {
        return null;
    }

    public  List<UsersEntity> getFriends(String email) {
        List<UsersEntity> result = em.createQuery
                ("select u2 from UsersEntity u1 " +
                        "JOIN FriendWithEntity f ON (u1.email = f.firstEmail) " +
                        "JOIN UsersEntity u2 ON(u2.email = f.secondEmail) where u1.email = :email and f.pending = false")
                .setParameter("email",email).getResultList();
        return result;
    }

    public List<InvitationMessage> getInvitationMessages(String email) {
        List<Object[]> result = em.createQuery(
                "select m.id,i.groupId, c.name, i.accepted, i.receiver,m.sender from InvitationMessagesEntity i " +
                        "JOIN MessagesEntity m on(i.id = m.id) " +
                        "JOIN ChatGroupsEntity c on(c.id = i.groupId)" +
                        "where i.receiver = :email and i.accepted = false"
        ).setParameter("email",email).getResultList();

        List<InvitationMessage> messages = new ArrayList<InvitationMessage>();
        for(Object[] o : result){
            InvitationMessage i = new InvitationMessage();
            i.setId((Integer)o[0]);
            i.setGroupId((Integer)o[1]);
            i.setGroupName(o[2].toString());
            i.setAccepted((Boolean)o[3]);
            i.setReceiver(o[4].toString());
            i.setSender(o[5].toString());
            messages.add(i);
        }
        return messages;
    }

    public void acceptInvitation(Integer messageId, String email, Integer groupId) {
        begin();
        em.createQuery("Update InvitationMessagesEntity i set i.accepted = true where i.id = :id").setParameter("id", messageId).executeUpdate();


        MemberOfEntity m = new MemberOfEntity();
        m.setEmail(email);
        m.setGroupId(groupId);

        em.persist(m);

        commit();

    }

    //numOfMessageWithin7Days: default 0
    //timstampOfMostRecentMessage: default begin of time
    //
    public List<UsersEntity> findAllUsersAndRelatedInfo(String email, String[] topicWords, int numOfMessageWithin7Days, Timestamp timestampOfMostRecentMessage) {
        List<UsersEntity> all =
                em.createQuery("select distinct u from UsersEntity u " +
                        "LEFT JOIN MessagesEntity m on (m.sender = u.email)"+
                        "where u.email like :email group by u.email having " +
                        "COUNT(m) > :numOfMessageWithin7Days " +
                        "and MIN(m.createdAt) > :timestampOfMostRecentMessage")
                        .setParameter("email", "%" + email + "%")
                        .setParameter("numOfMessageWithin7Days", numOfMessageWithin7Days)
                        .setParameter("timestampOfMostRecentMessage", timestampOfMostRecentMessage)
                        .getResultList();
        if(topicWords.length == 0){
            return all;
        }

        Set<UsersEntity> topicWordsRelatedEntity =
                new HashSet<UsersEntity>(em.createQuery("select distinct u from UsersEntity u " +
                "LEFT JOIN InterestedInEntity i on (i.email = u.email)" +
                "LEFT JOIN TopicWordsEntity t on (t.name = i.name) where t.name in :names")
                .setParameter("names", Arrays.asList(topicWords))
                .getResultList());

        List<UsersEntity> finalResult = new ArrayList<UsersEntity>();
        for(UsersEntity u: all){
            if(topicWordsRelatedEntity.contains(u)){
                finalResult.add(u);
            }
        }
        return finalResult;
    }

    public void changeTopicWords(String email, String topicWords) {
        String[] topicWordsList = topicWords.length() == 0 ? new String[0] : topicWords.split(",");

        try {
            begin();
            //Delete all previous interested words
            List<InterestedInEntity> previousInterested = em.createQuery("select i from InterestedInEntity i where i.email = :email").setParameter("email", email).getResultList();
            for(InterestedInEntity interested: previousInterested){
                em.remove(interested);
            }
            em.flush();
            for(String word: topicWordsList){
                TopicWordsEntity topicWord = null;
                topicWord = em.find(TopicWordsEntity.class, word);
                if(topicWord == null){
                    topicWord= new TopicWordsEntity();
                    topicWord.setName(word);
                    em.persist(topicWord);
                    em.flush();
                }
                InterestedInEntity e = new InterestedInEntity();
                e.setEmail(email);
                e.setName(topicWord.getName());
                em.persist(e);
            }
            commit();
        }
        catch (PersistenceException e){
            logger.info("Error while changing topic words");
        }
    }

    public List<TopicWordsEntity> getTopicWords(String email) {
        return em.createQuery("select t from TopicWordsEntity t " +
                "JOIN InterestedInEntity i on(t.name = i.name)" +
                "JOIN UsersEntity u on(u.email = i.email)" +
                "where u.email = :email").setParameter("email",email).getResultList();
    }

    public void createUser(UserCreateBean ub) {
        try {
            begin();
            UsersEntity u = new UsersEntity();
            u.setName(ub.getName());
            u.setEmail(ub.getUserEmail());
            u.setIsManager(ub.getIsManager());
            u.setPassword(ub.getPassword());
            u.setScreenName(ub.getScreenName());
            em.persist(u);
            commit();
        }
        catch (PersistenceException e){
            logger.info("Error while creating new users");
        }
    }

    public UsersEntity searchUser(String friendEmail) {
        return em.find(UsersEntity.class, friendEmail);
    }

    public void sendFriendRequest(String email, String friendEmail) {
        try{
            begin();
            FriendWithEntity f = new FriendWithEntity();
            f.setFirstEmail(email);
            f.setSecondEmail(friendEmail);
            f.setPending(true);
            em.persist(f);
            commit();
        }
        catch (PersistenceException e){
            logger.error("Error while creating friend request");
        }
    }
}
