package services;

import beans.view.CircleMessage;
import beans.view.PrivateMessage;
import controllers.ChatGroupsController;
import entities.*;
import org.apache.log4j.Logger;
import org.eclipse.persistence.exceptions.DatabaseException;
import org.springframework.stereotype.Service;
import sun.plugin2.message.Message;

import javax.persistence.PersistenceException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuesongwang on 11/20/16.
 */
@Service
public class MessagesService extends BaseService{

    private static final Logger logger = Logger.getLogger(MessagesService.class);

    public void createChatGroupMessage(Integer groupId, String message, String email) {
        try {
            begin();
            MessagesEntity m = new MessagesEntity();
            m.setSender(email);
            m.setContent(message);
            em.persist(m);
            em.flush();

            ChatGroupMessagesEntity cm = new ChatGroupMessagesEntity();
            cm.setGroupId(groupId);
            cm.setId(m.getId());
            em.persist(cm);
            em.flush();
            commit();
        }
        catch (PersistenceException e){
            em.getTransaction().rollback();
            logger.error("Chat group message created failed");
        }
    }

    public void deleteChatGroupMessage(Integer groupId, Integer messageId) {
        try {
            begin();
            MessagesEntity m = em.find(MessagesEntity.class, messageId);
            m.setDeleted(true);
            em.persist(m);
            commit();
        }
        catch (PersistenceException e){
            em.getTransaction().rollback();
            logger.error("Chat group message deleted failed",e);
        }
    }

    public void createInvitationMessage(String email, String friendEmail, Integer groupId) {
        begin();
        try {
            MessagesEntity m = new MessagesEntity();
            m.setSender(email);
            em.persist(m);
            em.flush();

            ReceiveEntity r = new ReceiveEntity();
            r.setEmail(friendEmail);
            r.setMessageId(m.getId());
            em.persist(r);
            em.flush();

            InvitationMessagesEntity i = new InvitationMessagesEntity();
            i.setGroupId(groupId);
            i.setId(m.getId());
            i.setReceiver(friendEmail);
            i.setAccepted(false);
            em.persist(i);
            em.flush();
            commit();
        }
        catch (PersistenceException e){
            em.getTransaction().rollback();
            logger.error("Invitation Message created failed", e);
        }
    }



    public List<CircleMessage> getMyCircleMessages(String email) {
        List<Object[]> result = em.createQuery
                ("select distinct c.id, c.isPublic, c.readCount, m.content, m.createdAt, m.sender from CircleMessagesEntity c, MessagesEntity m, ReceiveEntity r " +
                        "where (m.id = c.id and m.id = r.messageId and r.email = :email and m.deleted = false) " +
                        "or (m.id = c.id and m.id = r.messageId and m.sender = :email and r.email != :email and m.deleted = false)" +
                        "order by m.createdAt ASC").
                setParameter("email",email).getResultList();

        List<CircleMessage> messages = new ArrayList<CircleMessage>();
        for(Object[] o : result){
            getMessage(messages, o);
        }
        return messages;
    }

    private void getMessage(List<CircleMessage> messages, Object[] o) {
        CircleMessage c = new CircleMessage();
        c.setId((Integer)o[0]);
        c.setIsPublic((Boolean)o[1]);
        c.setReadCount((Integer)o[2]);
        c.setContent(o[3].toString());
        c.setCreatedAt((Timestamp) o[4]);
        c.setSender(o[5].toString());
        List<TopicWordsEntity> tags =
                em.createQuery("select t from TopicWordsEntity t, TaggedWithEntity tw " +
                "where tw.id = :messageId and t.name = tw.name")
                .setParameter("messageId",(Integer)o[0]).getResultList();
        String taggedWith = "";
        for(TopicWordsEntity tag: tags){
            taggedWith += tag.getName() + ",";
        }
        if(taggedWith.length() != 0){
            taggedWith = taggedWith.substring(0, taggedWith.length() - 1);
        }
        c.setTopicWords(taggedWith);
        messages.add(c);
    }

    public void createMyCircleMessages(String email, String message, boolean isPublic, String[] friendsToShare, String topicWords) {
        String[] topicWordsList = topicWords.split(",");
        if(topicWords.length() == 0){
            topicWordsList = new String[0];
        }
        try {
            begin();
            MessagesEntity m = new MessagesEntity();
            m.setContent(message);
            m.setSender(email);
            m.setDeleted(false);
            em.persist(m);
            em.flush();

            CircleMessagesEntity cm = new CircleMessagesEntity();
            cm.setId(m.getId());
            cm.setIsPublic(isPublic);
            cm.setReadCount(0);
            em.persist(cm);
            em.flush();

            for(String topicWord : topicWordsList){
                TopicWordsEntity t = em.find(TopicWordsEntity.class, topicWord);
                if(t == null) {
                    t = new TopicWordsEntity();
                    t.setName(topicWord);
                    em.persist(t);
                    em.flush();
                }
                TaggedWithEntity tw = new TaggedWithEntity();
                tw.setName(t.getName());
                tw.setId(m.getId());
                em.persist(tw);
            }
            for(String friendEmail: friendsToShare) {
                ReceiveEntity e = new ReceiveEntity();
                e.setMessageId(m.getId());
                e.setEmail(friendEmail);
                em.persist(e);
            }
            commit();
        }
        catch (PersistenceException e){
            em.getTransaction().rollback();
            logger.error("Error while sending MyCircleMessages", e);
        }
    }

    public void deleteMyCircleMessage(Integer messageId) {
        try{
            begin();
            MessagesEntity m = em.find(MessagesEntity.class, messageId);
            m.setDeleted(true);
            em.persist(m);
            commit();
        }
        catch (PersistenceException e){
            em.getTransaction().rollback();
            logger.error("Error while deleting MyCircleMessages");
        }
    }


    public List<PrivateMessage> getPrivateMessages(String email, String friendEmail) {
        List<Object[]> objects = em.createQuery("select m.id, m.content, m.sender, r.email, m.deleted, m.createdAt, pm.senderVisible, pm.receiverVisible " +
                "from MessagesEntity m " +
                "JOIN PrivateMessagesEntity pm on(m.id = pm.id) " +
                "JOIN ReceiveEntity r on(r.messageId = pm.id) " +
                "where (r.email = :email and m.sender = :friendEmail and pm.receiverVisible = true) " +
                "or (r.email = :friendEmail and m.sender = :email and pm.senderVisible = true)" +
                "order by m.createdAt ASC")
                .setParameter("email",email)
                .setParameter("friendEmail",friendEmail)
                .getResultList();
        List<PrivateMessage> messages = new ArrayList<PrivateMessage>();
        for(Object[] o: objects){
            PrivateMessage m = new PrivateMessage();
            m.setId((Integer)o[0]);
            m.setContent(o[1].toString());
            m.setSender(o[2].toString());
            m.setReceiver(o[3].toString());
            m.setDeleted((Boolean)o[4]);
            m.setCreatedAt((Timestamp)o[5]);
            m.setSenderVisible((Boolean)o[6]);
            m.setReceiverVisible((Boolean)o[7]);
            messages.add(m);
        }
        return messages;
    }

    public void deletePrivateMessage(String email, Integer messageId) {
        try{
            begin();
            PrivateMessagesEntity p = em.find(PrivateMessagesEntity.class, messageId);
            MessagesEntity m = em.find(MessagesEntity.class, messageId);
            //I am the sender
            if(m.getSender().equals(email)){
                p.setSenderVisible(false);
            }
            else{
                p.setReceiverVisible(false);
            }
            em.persist(p);
            commit();
        }
        catch (PersistenceException e){
            em.getTransaction().rollback();
            logger.error("Error while deleting private message", e);
        }
    }

    public void createPrivateMessage(String email, String userEmail, String message) {
        try{
            begin();
            MessagesEntity m = new MessagesEntity();
            m.setSender(email);
            m.setContent(message);

            em.persist(m);
            em.flush();

            PrivateMessagesEntity pm = new PrivateMessagesEntity();
            pm.setId(m.getId());
            pm.setSenderVisible(true);
            pm.setReceiverVisible(true);
            em.persist(pm);

            ReceiveEntity r = new ReceiveEntity();
            r.setEmail(userEmail);
            r.setMessageId(m.getId());

            em.persist(r);

            commit();
        }
        catch (PersistenceException e){
            em.getTransaction().rollback();
            logger.error("Error while creating private message", e);
        }
    }
}
