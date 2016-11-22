package contentplanner.services;

import contentplanner.datasets.User;
import contentplanner.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Created by Aynulin on 22.11.2016.
 */

@Component("userService")
@Transactional
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> getUser(String username) {
        return userRepository.findByUsername(username);
    }

    public User addUser(User user) {
        return userRepository.save(new User(user.getId(), user.getUsername(), user.getPassword(), user.getEmail(), user.getToken()));
    }
}
