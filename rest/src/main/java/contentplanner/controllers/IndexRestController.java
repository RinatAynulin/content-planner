package contentplanner.controllers;

import contentplanner.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

/**
 * Created by Aynulin on 14.11.2016.
 */

@RestController
public class IndexRestController {
    private final UserRepository userRepository;
    private final GroupRepository groupRepository;
    private final PostRepository postRepository;
    private final Validator validator;

    @Autowired
    public IndexRestController(UserRepository userRepository, GroupRepository groupRepository, PostRepository postRepository, Validator validator) {
        this.userRepository = userRepository;
        this.groupRepository = groupRepository;
        this.postRepository = postRepository;
        this.validator = validator;
    }

    @RequestMapping(method = RequestMethod.POST)
    ResponseEntity<?> addUser(@RequestBody User input) {
        User result = userRepository.save(new User(input.getUsername(), input.getPassword(),
                input.getEmail(), input.getToken()));

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{username}")
                .buildAndExpand(result.getUsername()).toUri();

        return ResponseEntity.created(location).build();
    }
}
