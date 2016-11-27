package entities;

import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by yuesongwang on 11/14/16.
 */
@Repository
@Entity
@Table(name = "FriendWith", schema = "DBProject", catalog = "")
@IdClass(FriendWithEntityPK.class)
public class FriendWithEntity {
    private String firstEmail;
    private String secondEmail;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private Boolean pending;

    @Id
    @Column(name = "first_email", nullable = false, length = 20)
    public String getFirstEmail() {
        return firstEmail;
    }

    public void setFirstEmail(String firstEmail) {
        this.firstEmail = firstEmail;
    }

    @Id
    @Column(name = "second_email", nullable = false, length = 20)
    public String getSecondEmail() {
        return secondEmail;
    }

    public void setSecondEmail(String secondEmail) {
        this.secondEmail = secondEmail;
    }

    @Basic
    @Column(name = "created_at", nullable = false)
    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    @Basic
    @Column(name = "updated_at", nullable = false)
    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Basic
    @Column(name = "pending", nullable = true)
    public Boolean getPending() {
        return pending;
    }

    public void setPending(Boolean pending) {
        this.pending = pending;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FriendWithEntity that = (FriendWithEntity) o;

        if (firstEmail != null ? !firstEmail.equals(that.firstEmail) : that.firstEmail != null) return false;
        if (secondEmail != null ? !secondEmail.equals(that.secondEmail) : that.secondEmail != null) return false;
        if (createdAt != null ? !createdAt.equals(that.createdAt) : that.createdAt != null) return false;
        if (updatedAt != null ? !updatedAt.equals(that.updatedAt) : that.updatedAt != null) return false;
        if (pending != null ? !pending.equals(that.pending) : that.pending != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = firstEmail != null ? firstEmail.hashCode() : 0;
        result = 31 * result + (secondEmail != null ? secondEmail.hashCode() : 0);
        result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
        result = 31 * result + (updatedAt != null ? updatedAt.hashCode() : 0);
        result = 31 * result + (pending != null ? pending.hashCode() : 0);
        return result;
    }
}
