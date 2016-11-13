package contentplanner;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

/**
 * Created by Aynulin on 13.11.2016.
 */
public interface GroupRepository extends JpaRepository<Group, Long> {
    Collection<Group> findByAdminUsername(String username);
}
