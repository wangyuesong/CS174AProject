package beans.view;

import java.sql.Timestamp;

/**
 * Created by yuesongwang on 11/16/16.
 */
public class CircleMessage {
    private int id;
    private String content;
    private String sender;

    private Boolean isPublic;
    private Integer readCount;

    private Timestamp createdAt;

    private Timestamp updatedAt;

    private String topicWords;

    public String getTopicWords() {
        return topicWords;
    }

    public void setTopicWords(String topicWords) {
        this.topicWords = topicWords;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

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

    public Boolean getIsPublic() {
        return isPublic;
    }

    public void setIsPublic(Boolean aPublic) {
        isPublic = aPublic;
    }

    public Integer getReadCount() {
        return readCount;
    }

    public void setReadCount(Integer readCount) {
        this.readCount = readCount;
    }


    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }
}