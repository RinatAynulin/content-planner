package contentplanner.datasets;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * Created by Aynulin on 18.11.2016.
 */

@Embeddable
public class PostPK implements Serializable {

    @Column(name = "id")
    protected int postId; //postid

    @Column(name = "group_id")
    protected int groupId;

    private static final long serialVersionUID = 1L;

    public PostPK() {
    }

    public PostPK(int groupId, int postId) {
        this.groupId = groupId;
        this.postId = postId;
    }

    public PostPK(int groupId) {
        this.groupId = groupId;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PostPK postPK = (PostPK) o;

        if (groupId != postPK.groupId) return false;
        return postId == postPK.postId;

    }

    @Override
    public int hashCode() {
        int result = groupId;
        result = 31 * result + postId;
        return result;
    }
}
