package entities;

import javax.persistence.*;

/**
 * Created by yuesongwang on 11/14/16.
 */
@Entity
@Table(name = "PrivateMessages", schema = "DBProject", catalog = "")
public class PrivateMessagesEntity {
    private int id;
    private Boolean senderVisible;
    private Boolean receiverVisible;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "sender_visible", nullable = true)
    public Boolean getSenderVisible() {
        return senderVisible;
    }

    public void setSenderVisible(Boolean senderVisible) {
        this.senderVisible = senderVisible;
    }

    @Basic
    @Column(name = "receiver_visible", nullable = true)
    public Boolean getReceiverVisible() {
        return receiverVisible;
    }

    public void setReceiverVisible(Boolean receiverVisible) {
        this.receiverVisible = receiverVisible;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PrivateMessagesEntity that = (PrivateMessagesEntity) o;

        if (id != that.id) return false;
        if (senderVisible != null ? !senderVisible.equals(that.senderVisible) : that.senderVisible != null)
            return false;
        if (receiverVisible != null ? !receiverVisible.equals(that.receiverVisible) : that.receiverVisible != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (senderVisible != null ? senderVisible.hashCode() : 0);
        result = 31 * result + (receiverVisible != null ? receiverVisible.hashCode() : 0);
        return result;
    }
}
