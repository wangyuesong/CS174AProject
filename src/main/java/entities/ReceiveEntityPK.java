package entities;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by yuesongwang on 11/14/16.
 */
public class ReceiveEntityPK implements Serializable {
    private String email;
    private int messageId;

    @Column(name = "email", nullable = false, length = 20)
    @Id
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "message_id", nullable = false)
    @Id
    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ReceiveEntityPK that = (ReceiveEntityPK) o;

        if (messageId != that.messageId) return false;
        if (email != null ? !email.equals(that.email) : that.email != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = email != null ? email.hashCode() : 0;
        result = 31 * result + messageId;
        return result;
    }
}
