package entities;

import org.springframework.stereotype.Repository;

import javax.persistence.*;

/**
 * Created by yuesongwang on 11/14/16.
 */
@Repository
@Entity
@Table(name = "ChatGroupMessages", schema = "DBProject", catalog = "")
public class ChatGroupMessagesEntity {
    private int id;
    private Integer groupId;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ChatGroupMessagesEntity that = (ChatGroupMessagesEntity) o;

        if (id != that.id) return false;
        if (groupId != null ? !groupId.equals(that.groupId) : that.groupId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (groupId != null ? groupId.hashCode() : 0);
        return result;
    }
}
