package contentplanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Collection;
import java.util.Optional;

/**
 * Created by Aynulin on 13.11.2016.
 */

@RestController
@RequestMapping("/{username}")
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

    @RequestMapping(value = "posts", method = RequestMethod.GET)
    Collection<Post> readPosts(@PathVariable String username) {
        validateUser(username);
        return postRepository.findByAuthorUsername(username);
    }

    @RequestMapping(value = "posts/{postId}", method = RequestMethod.GET)
    Post readPost(@PathVariable("postId") String postId, @PathVariable String username) {
        validateUser(username);
        validateId(postId);
        return postRepository.findOne(Long.parseLong(postId));
    }

    @RequestMapping(value = "groups", method = RequestMethod.GET)
    Collection<Group> readGroups(@PathVariable String username) {
        validateUser(username);
        return groupRepository.findByAdminUsername(username);
    }

    @RequestMapping(value = "/{groupId}", method = RequestMethod.POST)
    ResponseEntity<?> add(@PathVariable("username") String username,  @PathVariable("groupId") String groupId, @RequestBody Post input) {
        validateUser(username);
        validateGroup(groupId);
        Group group = groupRepository.findById(Long.parseLong(groupId)).get();
        return userRepository
                .findByUsername(username)
                .map(account -> {
                    Post result = postRepository.save(new Post(group, input.getMessage(), input.getAttachments(),
                            input.getPublishDate(), account));

                    URI location = ServletUriComponentsBuilder
                            .fromCurrentRequest().path("/{id}")
                            .buildAndExpand(result.getId()).toUri();

                    return ResponseEntity.created(location).build();
                })
                .orElse(ResponseEntity.noContent().build());
    }

    private void validateGroup(String groupIdString) {
        Long groupId;
        try {
            groupId = Long.parseLong(groupIdString);
        } catch (NumberFormatException e) {
            throw new AddressFormatException();
        }
        groupRepository.findById(groupId).orElseThrow(
                () -> new GroupNotFoundException(groupId));
    }

    private void validateUser(String username) {
        userRepository.findByUsername(username).orElseThrow(
                () -> new UserNotFoundException(username));
    }

    private void validateId(String postId) {
        try {
            Long.parseLong(postId);
        } catch (NumberFormatException e) {
            throw new AddressFormatException();
        }
    }
}
