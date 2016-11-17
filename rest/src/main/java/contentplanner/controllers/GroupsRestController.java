package contentplanner.controllers;

import contentplanner.*;
import contentplanner.datasets.Group;
import contentplanner.repositories.GroupRepository;
import contentplanner.repositories.PostRepository;
import contentplanner.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
        return groupRepository.findOne(Integer.parseInt(groupId));
    }
}
