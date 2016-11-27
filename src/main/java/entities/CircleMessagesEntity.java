package entities;

import org.springframework.stereotype.Repository;

import javax.persistence.*;

/**
 * Created by yuesongwang on 11/14/16.
 */
@Repository
@Entity
@Table(name = "CircleMessages", schema = "DBProject", catalog = "")
public class CircleMessagesEntity {
    private int id;
    private Boolean isPublic;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "is_public", nullable = true)
    public Boolean getIsPublic() {
        return isPublic;
    }

    public void setIsPublic(Boolean isPublic) {
        this.isPublic = isPublic;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CircleMessagesEntity that = (CircleMessagesEntity) o;

        if (id != that.id) return false;
        if (isPublic != null ? !isPublic.equals(that.isPublic) : that.isPublic != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (isPublic != null ? isPublic.hashCode() : 0);
        return result;
    }
}
