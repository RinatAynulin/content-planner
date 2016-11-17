package contentplanner;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.Optional;

/**
 * Created by Aynulin on 13.11.2016.
 */
public interface GroupRepository extends JpaRepository<Group, Long> {
    Collection<Group> findByAdminsUsername(String username);
    Collection<Group> findByAdminsId(int id);
    Optional<Group> findById(int id);
}
