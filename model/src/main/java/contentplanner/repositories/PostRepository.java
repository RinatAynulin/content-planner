package contentplanner.repositories;

import contentplanner.datasets.Post;
import contentplanner.datasets.PostPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

/**
 * Created by Aynulin on 13.11.2016.
 */
public interface PostRepository extends JpaRepository<Post, PostPK> {
    Collection<Post> findByAuthorUsername(String username);

    Collection<Post> findByGroupId(int id);

    Collection<Post> findByPublishDateGreaterThan(int publishDate); // use with current time for getting future posts

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("update Post p set p.message=:message" +
            ", p.attachments=:attachments" +
            ", p.publishDate=:publishDate  where p.id=:id and p.groupId=:groupId")
    public void updatePost(@Param("id") int id, @Param("groupId") int groupId, @Param("message") String message,
                                  @Param("attachments") String attachments, @Param("publishDate") int publishDate);
}
