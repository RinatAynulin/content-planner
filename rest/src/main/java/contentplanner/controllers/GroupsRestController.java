package contentplanner.controllers;

import contentplanner.*;
import contentplanner.datasets.Group;
import contentplanner.services.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * Created by Aynulin on 14.11.2016.
 */
@RestController
@RequestMapping("/groups")
public class GroupsRestController {
    private final GroupService groupService;
    private final Validator validator;

    @Autowired
    public GroupsRestController(GroupService groupService, Validator validator) {
        this.groupService = groupService;
        this.validator = validator;
    }

    @RequestMapping(value = "{groupId}", method = RequestMethod.GET)
    Optional<Group> readGroup(@PathVariable("groupId") String groupId) {
        validator.validateGroup(groupId);
        validator.validateId(groupId);
        return groupService.getGroup(Integer.parseInt(groupId));
    }
}
