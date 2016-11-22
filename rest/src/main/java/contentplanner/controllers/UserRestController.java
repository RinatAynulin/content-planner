package contentplanner.controllers;

import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import contentplanner.*;
import contentplanner.datasets.Group;
import contentplanner.datasets.Post;
import contentplanner.datasets.User;
import contentplanner.exceptions.GroupNotFoundException;
import contentplanner.exceptions.UserNotFoundException;
import contentplanner.services.ApiService;
import contentplanner.services.GroupService;
import contentplanner.services.PostService;
import contentplanner.services.UserService;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Collection;

/**
 * Created by Aynulin on 13.11.2016.
 */

@RestController
@RequestMapping("/{username}")
public class UserRestController {
    private final UserService userService;
    private final GroupService groupService;
    private final PostService postService;
    private final Validator validator;

    @Autowired
    UserRestController(UserService userService,
                       GroupService groupService,
                       PostService postService,
                       Validator validator) {
        this.userService = userService;
        this.groupService = groupService;
        this.postService = postService;
        this.validator = validator;
    }

    @RequestMapping(value = "posts", method = RequestMethod.GET)
    Collection<Post> readPosts(@PathVariable String username) {
        return postService.getPosts(username);
    }

    @RequestMapping(method = RequestMethod.PUT)
    ResponseEntity<?> updatePost(@PathVariable String username, @RequestBody Post input) {
        User user = userService.getUser(username).orElseThrow(() -> new UserNotFoundException(username));
        try {
            ApiService api = new ApiService(user.getId(), user.getToken());
            api.editPost(input, input.getId().getGroupId());
            postService.updatePost(input);
            return ResponseEntity.ok().build();
        } catch (ClientException | ApiException | HibernateException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @RequestMapping(value = "/{groupId}/{postId}", method = RequestMethod.DELETE)
    ResponseEntity<?> deletePost(@PathVariable String username, @PathVariable String groupId, @PathVariable String postId) {
        validator.validateId(postId);
        validator.validateId(groupId);
        User user = userService.getUser(username).orElseThrow(() -> new UserNotFoundException(username));
        try {
            ApiService api = new ApiService(user.getId(), user.getToken());
            api.unschedulePost(Integer.parseInt(postId), Integer.parseInt(groupId));
            postService.deletePost(Integer.parseInt(groupId), Integer.parseInt(postId));
            return ResponseEntity.ok().build();
        } catch (ClientException | ApiException | HibernateException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @RequestMapping(value = "/{groupId}", method = RequestMethod.POST)
    ResponseEntity<?> addPost(@PathVariable("username") String username,
                              @PathVariable("groupId") String groupId,
                              @RequestBody Post input) {
        validator.validateGroup(groupId);
        User user = userService.getUser(username).orElseThrow(() -> new UserNotFoundException(username));
        Group group = groupService.getGroup(Integer.parseInt(groupId)).orElseThrow(() -> new GroupNotFoundException(groupId));
        Post toPost = new Post(group, input.getMessage(), input.getAttachments(),
                input.getPublishDate(), user);
        try {
            ApiService api = new ApiService(user.getId(), user.getToken());
            toPost.setPostId(api.schedulePost(toPost));
            Post result = postService.addPost(toPost);
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest().path("/{id}")
                    .buildAndExpand(result.getId()).toUri();
            return ResponseEntity.created(location).build();
        } catch (ClientException | ApiException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @RequestMapping(value = "groups", method = RequestMethod.GET)
    Collection<Group> readGroups(@PathVariable String username) {
        return groupService.getGroup(username);
    }

    @RequestMapping(method = RequestMethod.POST)
    ResponseEntity<?> addGroup(@PathVariable("username") String username, @RequestBody Group input) {
        User user = userService.getUser(username).orElseThrow(() -> new UserNotFoundException(username));
        try {
            groupService.addGroup(input.getId(), input.getName(), user);
            return ResponseEntity.ok().build();
        } catch (HibernateException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
