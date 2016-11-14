package contentplanner.controllers;

import contentplanner.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Collection;
import java.util.Optional;

/**
 * Created by Aynulin on 14.11.2016.
 */
@RestController
@RequestMapping("/groups")
public class GroupsRestController {
    private final UserRepository userRepository;
    private final GroupRepository groupRepository;
    private final PostRepository postRepository;
    private final Validator validator;

    @Autowired
    public GroupsRestController(UserRepository userRepository, GroupRepository groupRepository, PostRepository postRepository, Validator validator) {
        this.userRepository = userRepository;
        this.groupRepository = groupRepository;
        this.postRepository = postRepository;
        this.validator = validator;
    }

    @RequestMapping(value = "{groupId}", method = RequestMethod.GET)
    Group readGroup(@PathVariable("groupId") String groupId) {
        validator.validateGroup(groupId);
        validator.validateId(groupId);
        return groupRepository.findOne(Long.parseLong(groupId));
    }
}
