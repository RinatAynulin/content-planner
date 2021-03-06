package contentplanner;

import contentplanner.exceptions.AddressFormatException;
import contentplanner.exceptions.GroupNotFoundException;
import contentplanner.exceptions.UserNotFoundException;
import contentplanner.repositories.GroupRepository;
import contentplanner.repositories.PostRepository;
import contentplanner.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by Aynulin on 14.11.2016.
 */
@Component
public class Validator {
    private final UserRepository userRepository;
    private final GroupRepository groupRepository;
    private final PostRepository postRepository;

    @Autowired
    public Validator(UserRepository userRepository, GroupRepository groupRepository, PostRepository postRepository) {
        this.userRepository = userRepository;
        this.groupRepository = groupRepository;
        this.postRepository = postRepository;
    }

    public void validateGroup(String groupIdString) {
        int groupId;
        try {
            groupId = Integer.parseInt(groupIdString);
        } catch (NumberFormatException e) {
            throw new AddressFormatException();
        }
        groupRepository.findById(groupId).orElseThrow(
                () -> new GroupNotFoundException(groupId));
    }

    public void validateId(String postId) {
        try {
            Integer.parseInt(postId);
        } catch (NumberFormatException e) {
            throw new AddressFormatException();
        }
    }
}
