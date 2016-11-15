package contentplanner.controllers;

import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import contentplanner.*;
import contentplanner.services.ApiService;
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
public class UserRestController {
    private final UserRepository userRepository;
    private final GroupRepository groupRepository;
    private final PostRepository postRepository;
    private final Validator validator;

    @Autowired
    UserRestController(UserRepository userRepository,
                       GroupRepository groupRepository,
                       PostRepository postRepository,
                       Validator validator) {
        this.userRepository = userRepository;
        this.groupRepository = groupRepository;
        this.postRepository = postRepository;
        this.validator = validator;
    }

    @RequestMapping(value = "posts", method = RequestMethod.GET)
    Collection<Post> readPosts(@PathVariable String username) {
        validator.validateUser(username);
        return postRepository.findByAuthorUsername(username);
    }

    @RequestMapping(value = "posts/{postId}", method = RequestMethod.GET)
    Post readPost(@PathVariable("postId") String postId, @PathVariable String username) {
        validator.validateUser(username);
        validator.validateId(postId);
        return postRepository.findOne(Long.parseLong(postId));
    }

    @RequestMapping(value = "groups", method = RequestMethod.GET)
    Collection<Group> readGroups(@PathVariable String username) {
        validator.validateUser(username);
        return groupRepository.findByAdminUsername(username);
    }

    @RequestMapping(value = "/{groupId}", method = RequestMethod.POST)
    ResponseEntity<?> addGroup(@PathVariable("username") String username,  @PathVariable("groupId") String groupId, @RequestBody Post input) {
        validator.validateUser(username);
        validator.validateGroup(groupId);
        Group group = groupRepository.findById(Integer.parseInt(groupId)).get();
        return userRepository
                .findByUsername(username)
                .map(account -> {
                    Post toPost = new Post(group, input.getMessage(), input.getAttachments(),
                            input.getPublishDate(), account);
                    try {
                        ApiService api = new ApiService(account.getId(), account.getToken());
                        toPost.setId(api.schedulePost(toPost));
                        Post result = postRepository.save(toPost);
                        URI location = ServletUriComponentsBuilder
                                .fromCurrentRequest().path("/{id}")
                                .buildAndExpand(result.getId()).toUri();
                        return ResponseEntity.created(location).build();
                    } catch (ClientException | ApiException e) {
                        e.printStackTrace();
                    }
                    return ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.noContent().build());
    }

    @RequestMapping(method = RequestMethod.POST)
    ResponseEntity<?> addGroup(@PathVariable("username") String username, @RequestBody Group input) {
        validator.validateUser(username);
        return userRepository
                .findByUsername(username)
                .map(account -> {
                    Group result = groupRepository.save(new Group(input.getId(), input.getName(), account));
                    return ResponseEntity.ok().build();
                })
                .orElse(ResponseEntity.noContent().build());
    }
}
