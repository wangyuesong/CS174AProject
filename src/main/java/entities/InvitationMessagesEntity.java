package entities;

import org.springframework.stereotype.Repository;

import javax.persistence.*;

/**
 * Created by yuesongwang on 11/14/16.
 */
@Repository
@Entity
@Table(name = "InvitationMessages", schema = "DBProject", catalog = "")
public class InvitationMessagesEntity {
    private int id;
    private Integer groupId;
    private Boolean accepted;
    private String receiver;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "group_id", nullable = true)
    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    @Basic
    @Column(name = "accepted", nullable = true)
    public Boolean getAccepted() {
        return accepted;
    }

    public void setAccepted(Boolean accepted) {
        this.accepted = accepted;
    }

    @Basic
    @Column(name = "receiver", nullable = false)
    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        InvitationMessagesEntity that = (InvitationMessagesEntity) o;

        if (id != that.id) return false;
        if (groupId != null ? !groupId.equals(that.groupId) : that.groupId != null) return false;
        if (accepted != null ? !accepted.equals(that.accepted) : that.accepted != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (groupId != null ? groupId.hashCode() : 0);
        result = 31 * result + (accepted != null ? accepted.hashCode() : 0);
        return result;
    }
}
