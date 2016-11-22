package contentplanner.controllers;

import contentplanner.*;
import contentplanner.datasets.User;
import contentplanner.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    private final UserService userService;
    private final Validator validator;

    @Autowired
    public IndexRestController(UserService userService, Validator validator) {
        this.userService = userService;
        this.validator = validator;
    }

    @RequestMapping(method = RequestMethod.POST)
    ResponseEntity<?> addUser(@RequestBody User input) {
        User result = userService.addUser(input);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{username}")
                .buildAndExpand(result.getUsername()).toUri();

        return ResponseEntity.created(location).build();
    }
}
