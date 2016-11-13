package contentplanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

/**
 * Created by Aynulin on 13.11.2016.
 */

@RestController
@RequestMapping("/{userId}/posts")
public class ContentPlannerRestController {
    private final UserRepository userRepository;
    private final GroupRepository groupRepository;
    private final PostRepository postRepository;

    @Autowired
    ContentPlannerRestController(UserRepository userRepository,
                                 GroupRepository groupRepository,
                                 PostRepository postRepository) {
        this.userRepository = userRepository;
        this.groupRepository = groupRepository;
        this.postRepository = postRepository;
    }

    @RequestMapping(method = RequestMethod.GET)
    Collection<Post> readPosts(@PathVariable String userId) {
        validateUser(userId);
        return postRepository.findByAuthorUsername(userId);
    }

    private void validateUser(String userId) {
        userRepository.findByUsername(userId).orElseThrow(
                () -> new UserNotFoundException(userId));
    }
}
