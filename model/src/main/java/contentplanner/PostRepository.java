package contentplanner;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

/**
 * Created by Aynulin on 13.11.2016.
 */
public interface PostRepository extends JpaRepository<Post, Long> {
    Collection<Post> findByAuthorUsername(String username);
    Collection<Post> findByGroupId(int id);
}
