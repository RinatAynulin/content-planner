package contentplanner.controllers;

import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import contentplanner.*;
import contentplanner.datasets.Group;
import contentplanner.datasets.Post;
import contentplanner.datasets.User;
import contentplanner.repositories.GroupRepository;
import contentplanner.repositories.PostRepository;
import contentplanner.repositories.UserRepository;
import contentplanner.services.ApiService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Collection;
import java.util.HashSet;

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

    @RequestMapping(value = "{groupId}", method = RequestMethod.PUT)
    ResponseEntity<?> updatePost(@PathVariable String username, @PathVariable String groupId, @RequestBody Post input) {
        validator.validateUser(username);
        validator.validateId(groupId);
        userRepository
                .findByUsername(username)
                .map(account -> {
                    try {
                        ApiService api = new ApiService(account.getId(), account.getToken());
                        api.editPost(input, Integer.parseInt(groupId));
                        postRepository.updatePost(input.getId(), input.getMessage(), input.getAttachments(), input.getPublishDate());
                        return ResponseEntity.ok().build();
                    } catch (ClientException | ApiException e) {
                        e.printStackTrace();
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR);
                    }
                })
                .orElse(ResponseEntity.noContent().build());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @RequestMapping(value = "/{groupId}/{postId}", method = RequestMethod.DELETE)
    ResponseEntity<?> deletePost(@PathVariable String username, @PathVariable String groupId, @PathVariable String postId) {
        validator.validateUser(username);
        validator.validateId(postId);
        validator.validateId(groupId);
        userRepository
                .findByUsername(username)
                .map(account -> {
                    try {
                        ApiService api = new ApiService(account.getId(), account.getToken());
                        api.unschedulePost(Integer.parseInt(postId), Integer.parseInt(groupId));
                        postRepository.delete(Integer.parseInt(postId));
                        return ResponseEntity.ok().build();
                    } catch (ClientException | ApiException e) {
                        e.printStackTrace();
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR);
                    }
                })
                .orElse(ResponseEntity.noContent().build());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @RequestMapping(value = "groups", method = RequestMethod.GET)
    Collection<Group> readGroups(@PathVariable String username) {
        validator.validateUser(username);
        return groupRepository.findByAdminsUsername(username);
    }

    @RequestMapping(value = "/{groupId}", method = RequestMethod.POST)
    ResponseEntity<?> addPost(@PathVariable("username") String username,  @PathVariable("groupId") String groupId, @RequestBody Post input) {
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
                        Logger.getLogger(UserRestController.class).info("Post was scheduled: " + toPost);
                        Post result = postRepository.save(toPost);
                        Logger.getLogger(UserRestController.class).info("Post was added in db: " + result);
                        URI location = ServletUriComponentsBuilder
                                .fromCurrentRequest().path("/{id}")
                                .buildAndExpand(result.getId()).toUri();
                        return ResponseEntity.created(location).build();
                    } catch (ClientException | ApiException e) {
                        e.printStackTrace();
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
                    }
                })
                .orElse(ResponseEntity.noContent().build());
    }

    @RequestMapping(method = RequestMethod.POST)
    ResponseEntity<?> addGroup(@PathVariable("username") String username, @RequestBody Group input) {
        validator.validateUser(username);
        return userRepository
                .findByUsername(username)
                .map(account -> {
                    Group result = groupRepository.save(new Group(input.getId(), input.getName()));
                    result.setAdmins(new HashSet<User>() {{
                        add(account);
                    }});
                    return ResponseEntity.ok().build();
                })
                .orElse(ResponseEntity.noContent().build());
    }
}
