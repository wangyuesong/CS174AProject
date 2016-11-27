package beans;

/**
 * Created by yuesongwang on 11/24/16.
 */
public class UserSearchBean {
    private String email;
    private String topicWords;
    private String timestampOfMostRecentMessage;
    private String numOfMessagesWithin7Days;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTopicWords() {
        return topicWords;
    }

    public void setTopicWords(String topicWords) {
        this.topicWords = topicWords;
    }

    public String getTimestampOfMostRecentMessage() {
        return timestampOfMostRecentMessage;
    }

    public void setTimestampOfMostRecentMessage(String timestampOfMostRecentMessage) {
        this.timestampOfMostRecentMessage = timestampOfMostRecentMessage;
    }

    public String getNumOfMessagesWithin7Days() {
        return numOfMessagesWithin7Days;
    }

    public void setNumOfMessagesWithin7Days(String numOfMessagesWithin7Days) {
        this.numOfMessagesWithin7Days = numOfMessagesWithin7Days;
    }
}
