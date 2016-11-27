package beans.view;

import java.sql.Timestamp;

/**
 * Created by yuesongwang on 11/16/16.
 */
public class PrivateMessage {
    private int id;
    private String content;
    private String sender;
    private String receiver;
    private boolean deleted;
    private Timestamp createdAt;
    private boolean senderVisible;
    private boolean receiverVisible;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public boolean getSenderVisible() {
        return senderVisible;
    }

    public void setSenderVisible(boolean senderVisible) {
        this.senderVisible = senderVisible;
    }

    public boolean getReceiverVisible() {
        return receiverVisible;
    }

    public void setReceiverVisible(boolean receiverVisible) {
        this.receiverVisible = receiverVisible;
    }
}
