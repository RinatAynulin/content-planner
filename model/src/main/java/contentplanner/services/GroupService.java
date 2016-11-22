package contentplanner.services;

import contentplanner.datasets.Group;
import contentplanner.datasets.User;
import contentplanner.repositories.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;

/**
 * Created by Aynulin on 22.11.2016.
 */

@Component("groupService")
@Transactional
public class GroupService {
    private GroupRepository groupRepository;

    @Autowired
    public GroupService(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    public Optional<Group> getGroup(int groupId) {
        return groupRepository.findById(groupId);
    }

    public Collection<Group> getGroup(String adminUsername) {
        return groupRepository.findByAdminsUsername(adminUsername);
    }

    public void addGroup(int id, String name, User admin) {
        Group group = groupRepository.save(new Group(id, name, new HashSet<User>() {{
            add(admin);
        }}));
    }
}
