package contentplanner;

import contentplanner.exceptions.AddressFormatException;
import contentplanner.exceptions.GroupNotFoundException;
import contentplanner.exceptions.UserNotFoundException;
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
        Long groupId;
        try {
            groupId = Long.parseLong(groupIdString);
        } catch (NumberFormatException e) {
            throw new AddressFormatException();
        }
        groupRepository.findById(groupId).orElseThrow(
                () -> new GroupNotFoundException(groupId));
    }

    public void validateUser(String username) {
        userRepository.findByUsername(username).orElseThrow(
                () -> new UserNotFoundException(username));
    }

    public void validateId(String postId) {
        try {
            Long.parseLong(postId);
        } catch (NumberFormatException e) {
            throw new AddressFormatException();
        }
    }
}
