package entities;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by yuesongwang on 11/14/16.
 */
public class FriendWithEntityPK implements Serializable {
    private String firstEmail;
    private String secondEmail;

    @Column(name = "first_email", nullable = false, length = 20)
    @Id
    public String getFirstEmail() {
        return firstEmail;
    }

    public void setFirstEmail(String firstEmail) {
        this.firstEmail = firstEmail;
    }

    @Column(name = "second_email", nullable = false, length = 20)
    @Id
    public String getSecondEmail() {
        return secondEmail;
    }

    public void setSecondEmail(String secondEmail) {
        this.secondEmail = secondEmail;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FriendWithEntityPK that = (FriendWithEntityPK) o;

        if (firstEmail != null ? !firstEmail.equals(that.firstEmail) : that.firstEmail != null) return false;
        if (secondEmail != null ? !secondEmail.equals(that.secondEmail) : that.secondEmail != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = firstEmail != null ? firstEmail.hashCode() : 0;
        result = 31 * result + (secondEmail != null ? secondEmail.hashCode() : 0);
        return result;
    }
}
